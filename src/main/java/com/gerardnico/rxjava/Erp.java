package com.gerardnico.rxjava;

import io.reactivex.rxjava3.core.Flowable;

import java.util.HashMap;

/**
 * A class that simule two dependent async flow
 * used in the demo test
 */
public class Erp {

    /**
     *
     * @return a flow
     */
    public static Flowable<Inventory> getInventoryAsync(){
        Inventory engine = new Inventory("engine");
        engine.setWarehouse("warehouse1");
        Inventory wheel = new Inventory("wheel");
        wheel.setWarehouse("warehouse2");
        return Flowable.fromArray(engine,wheel);
    }

    /**
     *
     * @param id
     * @return the demand for an inventory
     */
    public static Flowable<Integer> getDemandAsync(String id){
        HashMap<String,Integer> demands = new HashMap<>();
        demands.put("engine",2);
        demands.put("wheel",4);
        return Flowable.fromArray(demands.get(id));
    }

    /**
     *
     * @param inventoryId
     * @return the demand for an inventory
     */
    public static Flowable<Integer> getStock(String inventoryId, String warehouseId){
        HashMap<String,HashMap<String,Integer>> stocks = new HashMap<>();
        HashMap<String, Integer> engineStock = new HashMap<>();
        engineStock.put("warehouse1",2);
        engineStock.put("warehouse2",4);
        stocks.put("engine", engineStock);
        HashMap<String, Integer> wheelStock = new HashMap<>();
        wheelStock.put("warehouse1",2);
        wheelStock.put("warehouse2",4);
        stocks.put("wheel",wheelStock);
        return Flowable.fromArray(stocks.get(inventoryId).get(warehouseId));
    }

    /**
     * The inventory
     */
    public static class Inventory {
        private String warehouse;

        public Inventory(String name) {
            this.name = name;
        }

        String name;
        String getName(){ return name; }

        public String getId() {
            return name;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }
    }


}
