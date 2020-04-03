package com.gerardnico.rxjava;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.Test;

/**
 * https://github.com/ReactiveX/RxJava/wiki/Error-Handling
 * https://github.com/ReactiveX/RxJava#error-handling
 *
 *
 * https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators
 * There are a variety of operators that you can use to react to or recover from onError notifications from reactive sources, such as Observables. For example, you might:
 *
 *   * swallow the error and switch over to a backup Observable to continue the sequence
 *   * swallow the error and emit a default item
 *   * swallow the error and immediately try to restart the failed Observable
 *   * swallow the error and try to restart the failed Observable after some back-off interval
 */
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
                    public void onSubscribe(@NonNull Disposable d) {
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
