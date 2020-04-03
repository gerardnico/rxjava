package com.gerardnico.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

/**
 * See also
 * https://github.com/ReactiveX/RxJava/wiki/Combining-Observables
 *
 */
public class DependentFlowTest {

    /**
     * Dependent Sub-flow
     * https://github.com/ReactiveX/RxJava#dependent-sub-flows
     */
    @Test
    public void DependentSubFlowTest() {
        Flowable<Erp.Inventory> inventorySource = Erp.getInventoryAsync();
        inventorySource
                .flatMap(inventoryItem -> Erp.getDemandAsync(inventoryItem.getId())
                        .map(demand -> "Item " + inventoryItem.getName() + " has demand " + demand))
                .subscribe(System.out::println);
    }

    @Test
    public void depedentValueTest() {
        Erp.getInventoryAsync()
                .flatMap(inventoryItem -> Erp.getDemandAsync(inventoryItem.getId())
                        .map(demand -> "Item " + inventoryItem.getName() + " has demand " + demand))
                .subscribe(System.out::println);
    }
}
