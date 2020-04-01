package com.gerardnico.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.junit.Test;

public class HelloWorldTest {

    /**
     * https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#hello-world
     */
    @Test
    public void helloWorldTest() {
        String[] args = {"Nico", "Gerard"};
        Flowable.fromArray(args)
                .subscribe(s -> System.out.println("Hello " + s + "!"));
    }

    @Test
    public void observerOnLifecycle() {
        Observable
                .just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("OnSubscribe Observable.");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("OnNext Observable: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error Observable");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed Observable.");
                    }
                });
    }



}
