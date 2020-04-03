package com.gerardnico.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

public class ParallelTest {

    /**
     * Sequential Square computation
     *
     *
     * This example flow
     * * squares the numbers from 1 to 10 on the computation Scheduler
     * * consumes the results on the caller thread of blockingSubscribe (on this example main)
     *
     * The lambda v -> v * v doesn't run in parallel for this flow;
     * it receives the values 1 to 10 on the same computation thread one after the other.
     *
     * The parallel flow is shown below {@link #parallelSquareProcessingTest()}
     *
     * @see <a href="https://github.com/ReactiveX/RxJava#concurrency-within-a-flow">Concurrency with flow</a>
     */
    @Test
    public void notParallelTest() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);
    }

    /**
     * Square computation in parallel
     */
    @Test
    public void parallelSquareProcessingTest() {

        Flowable.range(1, 10)
                .flatMap(v ->Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    @Test
    public void flowableParallelSquareTest() {
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);
    }
}
