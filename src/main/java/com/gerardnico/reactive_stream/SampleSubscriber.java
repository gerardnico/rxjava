package com.gerardnico.reactive_stream;

import io.reactivex.rxjava3.functions.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Simple Subscriber
 *
 * Found at
 * https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/Flow.html
 *
 * @param <T>
 */
class SampleSubscriber<T> implements Subscriber<T> {
    final Consumer<? super T> consumer;
    Subscription subscription;
    final long bufferSize;
    long count;
    SampleSubscriber(long bufferSize, Consumer<? super T> consumer) {
        this.bufferSize = bufferSize;
        this.consumer = consumer;
    }
    public void onSubscribe(Subscription subscription) {
        long initialRequestSize = bufferSize;
        count = bufferSize - bufferSize / 2; // re-request when half consumed
        (this.subscription = subscription).request(initialRequestSize);
    }
    public void onNext(T item) {
        if (--count <= 0)
            subscription.request(count = bufferSize - bufferSize / 2);
        try {
            consumer.accept(item);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
    public void onError(Throwable ex) { ex.printStackTrace(); }
    public void onComplete() {}

}
