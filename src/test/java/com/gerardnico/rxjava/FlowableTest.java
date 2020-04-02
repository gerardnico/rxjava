package com.gerardnico.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

public class FlowableTest {

    /**
     * https://github.com/ReactiveX/RxJava#simple-background-computation
     *
     * RxJava's reactive types are immutable; each of the method calls returns a new Flowable with added behavior.
     * Example
     */
    @Test
    public void flowableWithoutFluentApiTest() throws InterruptedException {
        Flowable<String> source = Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

        showForeground.subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000);
    }
}
