package User_Defined_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static User_Defined_Classes.Store.storeHashMap;

public class Item {
    private String itemName;
    private int itemWeight;
    private String belongsToStore;

    private static HashMap<String, ArrayList<Item>> storeCatalog = new HashMap<>();
    public String getItemName() {
        return itemName;
    }
    public int getItemWeight() {
        return itemWeight;
    }

    public String getBelongsToStore() {
        return belongsToStore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item otherItem = (Item) o;
        return Objects.equals(itemName, otherItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName);
    }

    public Item(String belongsToStore, String itemName, int itemWeight) {
        this.belongsToStore = belongsToStore;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }
    public void addItem(Item item) {
        String storeName = item.getBelongsToStore();

        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            if (!storeCatalog.containsKey(storeName)) {
                storeCatalog.put(item.getBelongsToStore(), new ArrayList<>());
            }

            ArrayList<Item> storeItems = storeCatalog.get(storeName);
            boolean itemExists = storeItems.stream().anyMatch(i -> i.getItemName().equals(item.getItemName()));
            if (itemExists) {
                System.out.println("ERROR:item_identifier_already_exists");
            } else {
                storeCatalog.get(storeName).add(item);
                System.out.println("OK:change_completed");
            }
        }
    }
    public void displayItems(String storeNameToSearch) {
        if (storeCatalog.containsKey(storeNameToSearch)) {
            ArrayList<Item> storeItems = storeCatalog.get(storeNameToSearch);
            for (Item item : storeItems) {
                System.out.println(item.getItemName() + "," + item.getItemWeight());
            }
            System.out.println("OK:display_completed");
        } else {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
    }
}
