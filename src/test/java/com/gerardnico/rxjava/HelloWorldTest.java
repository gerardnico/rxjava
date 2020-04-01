package com.gerardnico.rxjava;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
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
    public void observableSubscribeWithObserver() {
        Observable
                .just(1, 2, 3)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
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

    @Test
    public void observableSubscribeWithConsumer() {
        Observable
                .just(1, 2, 3)
                .subscribe(System.out::println);
    }


}
