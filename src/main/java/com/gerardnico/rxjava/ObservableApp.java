
package com.gerardnico.rxjava;


import io.reactivex.rxjava3.core.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.IntStream;

public class ObservableApp {

    public static void print(Observable<?> o) {
        System.out.println("Observable:");
        o.subscribe(s -> System.out.println("  * " + s));
    }

    /**
     * Create an Observable from scratch by using the Create operator.
     * * Doc: http://reactivex.io/documentation/operators/create.html
     * * Example: https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#creating-an-observable-via-the-create-method
     * This example shows a custom Observable that blocks
     * when subscribed to (does not spawn an extra thread).
     */
    public static Observable<Object> customObservableBlocking() {
        return Observable.create(aSubscriber -> {
            IntStream.range(0, 75).forEach(i -> {
                if (!aSubscriber.isDisposed()) {
                    aSubscriber.onNext("value_" + i);
                }
            });

            // after sending all values we complete the sequence
            if (!aSubscriber.isDisposed()) {
                aSubscriber.onComplete();
                System.out.println("OnComplete fired");
            }
        });
    }

    /**
     * Create an Observable from scratch by using the Create operator.
     * <p>
     * Example: https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#asynchronous-observable-example
     * <p>
     * This example shows a custom Observable that does not block
     * when subscribed to as it spawns a separate thread.
     */
    public static Observable<Object> customObservableNonBlocking() {
        return Observable.create(observableEmitter -> new Thread(() -> {
            try {
                IntStream.range(0, 10000).forEach(i -> {

                    if (!observableEmitter.isDisposed()) {
                        if (Math.floorMod(i, 1000) == 0) {
                            observableEmitter.onNext("value_" + i);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        System.out.println("Observable was disposed");
                        throw new RuntimeException("Disposed");
                    }
                });

                // after sending all values we complete the sequence
                observableEmitter.onComplete();
                System.out.println("OnComplete fired");

            } catch (Exception e) {
                System.out.println("Error received: "+e.getMessage());
            }

        }).start());
    }

    /**
     * Fetch a list of Wikipedia articles asynchronously, with error handling.
     * https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava#asynchronous-observable-example
     *
     * @param wikipediaArticlesNames
     * @return
     */
    public static Observable<String> fetchWikipediaArticleAsynchronously(String... wikipediaArticlesNames) {
        return Observable.create(observableEmitter -> new Thread(() -> {
            try {
                for (String articleName : wikipediaArticlesNames) {
                    System.out.println("Fetching Article " + articleName);
                    if (observableEmitter.isDisposed()) {
                        return;
                    }
                    observableEmitter.onNext(getText("https://en.wikipedia.org/wiki/" + articleName));
                }
                if (!observableEmitter.isDisposed()) {
                    observableEmitter.onComplete();
                }
            } catch (Throwable t) {
                if (false == observableEmitter.isDisposed()) {
                    observableEmitter.onError(t);
                }
            }
        }).start());
    }

    /**
     * Help function
     *
     * @param url
     * @return
     */
    public static String getText(String url) {

        try {

            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            StringBuilder response = new StringBuilder();
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
            }
            return response.toString();

        } catch (IOException e) {
            // System.err.println("Error: " + e);
            throw new RuntimeException(e);
        }
    }


}
