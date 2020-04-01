package com.gerardnico.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

public class OperatorCompositionTest {

    /**
     * Transforming Observables with Operators
     * https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#transforming-observables-with-operators
     *
     * Asynchronously calls 'customObservableNonBlocking' and defines
     * a chain of operators to apply to the callback sequence.
     */
    @Test
    public void simpleComposition() {
        ObservableApp.customObservableBlocking()
                .skip(10)
                .take(5)
                .map(s->s+"_x_form")
                .subscribe(s->System.out.println("onNext => "+s));
    }

    /**
     * Take Return the next 5 elements
     */
    @Test
    public void takeTest() {
        Observable
                .just("The", "Dave", "Brubeck", "Quartet", "Time", "Out")
                .take(5)
                .subscribe(System.out::println);
    }
}
