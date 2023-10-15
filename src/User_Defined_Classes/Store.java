package User_Defined_Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Store {
    private String storeName;
    private int storeRevenue;
    private int numberOfPurchases = 0;
    private int numberOfTransfers = 0;
    public void increaseNumberOfTransfers() {
        numberOfTransfers++;
    }
    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }
    public String getStoreName() {
        return storeName;
    }
    public int getStoreRevenue() {
        return storeRevenue;
    }
    public void increaseNumberOfPurchases() {
        numberOfPurchases++;
    }
    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }
    public void addRevenue(int credits) {
        storeRevenue += credits;
    }

    public static HashMap<String, Store> storeHashMap = new HashMap<>();
    public Store(String storeName, int storeRevenue) {
        this.storeName = storeName;
        this.storeRevenue = storeRevenue;
    }

    public void addToDatabase(String storeName, Store store) {
        if (!storeHashMap.containsKey(storeName)) {
            storeHashMap.put(storeName, store);
            System.out.println("OK:change_completed");
        }
        else {
            System.out.println("ERROR:store_identifier_already_exists");
        }
    }

    public void displayStores() {
        TreeMap<String, Store> sortedStores = new TreeMap<>(storeHashMap);

        for (Map.Entry<String, Store> entry : sortedStores.entrySet()) {
            Store store = entry.getValue();
            String storeName = store.getStoreName();
            int storeRevenue = store.getStoreRevenue();
            System.out.println("name:" + storeName + ", revenue:" + storeRevenue);
        }

        System.out.println("OK:display_completed");
    }
}
