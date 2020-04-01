package com.gerardnico.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

public class ErrorHandlingTest {

    /**
     * https://developer.couchbase.com/documentation/server/3.x/developer/java-2.0/observables.html
     */
    @Test
    public void observerErrorOnNextTest() {
        Observable
                .just(1, 2, 3)
                .doOnNext(integer -> {
                    if (integer.equals(2)) {
                        throw new RuntimeException("I don't like 2");
                    }
                })
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
                        System.out.println("Error Observable: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed Observable.");
                    }
                });
    }

    @Test
    public void errorHandlingWithObservableEmitter() throws InterruptedException {
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

    @Test
    public void onErrorArgInSubscribeTest() {
        Observable
                .just(1, 2, 3)
                .subscribe(i->{
                    if (i==2){
                        throw new RuntimeException("Not 2 please");
                    } else {
                        System.out.println(i);
                    }
                },throwable -> {
                    System.out.println("Error: "+throwable);
                });
    }


}
