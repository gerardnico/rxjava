package com.gerardnico.rxjava;

import org.junit.Test;

public class CompositionTest {

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
}
