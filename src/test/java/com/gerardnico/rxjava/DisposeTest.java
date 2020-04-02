package com.gerardnico.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.observers.DisposableObserver;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class DisposeTest {

    @Test
    public void disposeTest() throws InterruptedException {
        Observable<Object> e = ObservableApp.customObservableNonBlocking();
        Disposable d = e.subscribe(System.out::println);

        Thread.sleep(500);
        // Should show around 5 elements
        d.dispose();
        Thread.sleep(100);
    }
}
