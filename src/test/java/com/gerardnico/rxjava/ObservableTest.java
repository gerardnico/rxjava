/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.gerardnico.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ObservableTest {

    @Test
    public void observableFromArray() {
        Observable<String> o = Observable.fromArray("a", "b", "c");
        ObservableApp.print(o);
    }

    @Test
    public void observableFromList() {
        List<String> l = Arrays.asList("listElement1", "listElement2", "listElement3");
        Observable<String> o = Observable.fromIterable(l);
        ObservableApp.print(o);
    }

    @Test
    public void observableFromOneObject() {
        Observable<String> o = Observable.just("1");
        ObservableApp.print(o);
    }

    @Test
    public void observableFromNObject() {
        Observable<String> o = Observable.just("1","2","3");
        ObservableApp.print(o);
    }

    @Test
    public void observableCreateBlocking() {
        ObservableApp.customObservableBlocking().subscribe(s-> System.out.println(s));
    }
    @Test
    public void observableCreateNonBlocking() throws InterruptedException {
        Observable<Object> objectObservable = ObservableApp.customObservableNonBlocking();
        Disposable disposable = objectObservable.subscribe(s -> System.out.println(s));
        // Needed to wait. Otherwise the JVM may exist before the print of the 50 elements
        while (!disposable.isDisposed()){
            Thread.sleep(200);
            System.out.println("No yet disposed");
        }
    }

    @Test
    public void observableCreateNonBlockingWikipedia() throws InterruptedException {
        Observable<String> objectObservable = ObservableApp.fetchWikipediaArticleAsynchronously("Tiger", "Elephant");
        Disposable disposable = objectObservable.subscribe(System.out::println);
        // Needed to wait. Otherwise the JVM may exist before the print of the 50 elements
        while (!disposable.isDisposed()){
            Thread.sleep(2000);
            System.out.println("Not disposed");
        }
    }

    @Test
    public void errorHandling() throws InterruptedException {
        Observable<String> objectObservable = ObservableApp.fetchWikipediaArticleAsynchronously("NonExistent", "Elephant");
        Disposable disposable = objectObservable.subscribe(
                System.out::println,
                System.err::println);
        // Needed to wait. Otherwise the JVM may exist before the print of the 50 elements
        while (!disposable.isDisposed()){
            Thread.sleep(2000);
            System.out.println("Not disposed");
        }
    }
}
