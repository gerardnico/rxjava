package com.gerardnico.rxjava;

import io.reactivex.Flowable;
import org.junit.Test;

public class HelloWorldTest {

    /**
     * https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#hello-world
     */
    @Test
    public void helloWorldTest() {
        String[] args = {"Nico","Gerard"};
        Flowable.fromArray(args)
                .subscribe(s -> System.out.println("Hello " + s + "!"));
    }

}
