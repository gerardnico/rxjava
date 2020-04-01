package com.gerardnico.rxjava;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadTest {


    @Test
    public void threadTest() {
        // main thread exits before the background thread had a chance to run and emit values.
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(s->System.out.println("Got: " + s));
    }

    @Test
    public void threadLatchTest() throws InterruptedException {
        // main thread exits before the background thread had a chance to run and emit values.
        final CountDownLatch latch = new CountDownLatch(5);
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(s->{
                    latch.countDown();
                    System.out.println("Got: " + s);
                });
        latch.await();
    }
}
