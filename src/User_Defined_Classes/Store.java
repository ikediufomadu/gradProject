package User_Defined_Classes;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private String storeName;
    private int storeRevenue;
    static HashMap<String, Store> storeHashMap = new HashMap<>();

    public String getStoreName() {
        return storeName;
    }
    public int getStoreRevenue() {
        return storeRevenue;
    }
    public void addRevenue(int credits) {
        storeRevenue += credits;
    }
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
        for (Map.Entry<String, Store> entry: storeHashMap.entrySet()) {
            Store store = entry.getValue();
            String storeName = store.getStoreName();
            int storeRevenue = store.getStoreRevenue();
            System.out.println("name:" + storeName + ", revenue:" + storeRevenue);
        }
        System.out.println("OK:display_completed");
    }
}
