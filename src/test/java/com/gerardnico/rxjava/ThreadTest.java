package com.gerardnico.rxjava;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadTest {


    @Test
    public void threadTest() {
        // main thread exits before the background thread had a chance to run and emit values.
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(s -> System.out.println("Got: " + s));
    }

    /**
     * To avoid that the main thread exits before the background thread had a chance to run and emit values
     * We use a latch to synchronize the execution between threads.
     * <p>
     * !!!!!!!!!!!!!!! Not THREAD.SLEEP !!!!!!!!!!!!!!
     * Always use a CountDownLatch.
     * One common mistake is to use Thread.sleep() instead of a latch to synchronize the execution between threads.
     * This is a bad idea because it not really synchronizes anything,
     * but just keeps one thread alive for a specific amount of time.
     * If:
     * * the actual calls take less time you are wasting time,
     * * it takes longer you won't get the desired effect.
     * If you do this in unit tests, be prepared for a good amount of non-determinism and randomly failing tests.
     *
     * @throws InterruptedException From: https://developer.couchbase.com/documentation/server/3.x/developer/java-2.0/observables.html
     */
    @Test
    public void threadAwaitLatchTest() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(5);
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(s -> {
                    latch.countDown();
                    System.out.println("Got: " + s);
                });
        latch.await();
    }

    /**
     * https://github.com/ReactiveX/RxJava#simple-background-computation
     * Typically, you can move computations or blocking IO to some other thread via subscribeOn.
     * Once the data is ready, you can make sure they get processed on the foreground or GUI thread via observeOn.
     */
    @Test
    public void simpleBackgroundComputation() throws InterruptedException {

        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
