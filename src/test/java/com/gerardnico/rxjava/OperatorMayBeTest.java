package com.gerardnico.rxjava;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class OperatorMayBeTest {

    @Test
    public void mayBeTest() throws InterruptedException {
        Disposable d = Maybe.just("Hello World")
                .delay(10, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onStart() {
                        System.out.println("Started");
                    }

                    @Override
                    public void onSuccess(String value) {
                        System.out.println("Success: " + value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });

        Thread.sleep(5000);

        d.dispose();
    }
}
