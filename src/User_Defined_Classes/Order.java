package User_Defined_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static User_Defined_Classes.Customer.*;
import static User_Defined_Classes.Drone.storeDroneCatalog;
import static User_Defined_Classes.Item.storeCatalog;
import static User_Defined_Classes.Store.storeHashMap;


public class Order {
    public static HashMap<String, ArrayList<String>> orderIDtoItemsList = new HashMap<>();

//    public Order()

//    public int calculateWeight(){}
//    public boolean addOrderLine(int orderWeight, int maxCarryWeight) {}
    public void requestItem(String storeName, String orderIdentifier, String itemName, int itemQuantity, int itemPrice) {
        ArrayList<String> storeArrayList = storeOrderList.get(storeName);
        ArrayList<Item> storeItemList = storeCatalog.get(storeName);
        ArrayList<Drone> storeDrone = storeDroneCatalog.get(storeName);
        ArrayList<String> storeItems = orderIDtoItemsList.get(orderIdentifier);
        boolean storeOrderIdentifier = false;
        boolean itemExists = false;
        boolean canDroneCarry = true;
        Customer customer = null;
        Drone mainDrone = null;
        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderIdentifier)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }
        if (storeItemList != null) {
            for (Item item : storeItemList) {
                if (item.getItemName().equals(itemName)) {
                    itemExists = true;
                    break;
                }
            }
        }
        if (customerHashMap != null) {
            for (Map.Entry<String, Customer> entry : customerHashMap.entrySet()) {
                if (customerIDAndCustomerOrder.containsKey(entry.getKey())) {
                    customer = entry.getValue();
                }
            }
        }
        if (storeDrone != null) {
            for (Drone drone : storeDrone) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    if (drone.getRemainingCapacity() < itemQuantity) {
                        canDroneCarry = false;
                    }
                    mainDrone = drone;
                }
            }
        }
        
        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else if (!storeOrderIdentifier) {
            System.out.println("ERROR:order_identifier_does_not_exist");
        } else if (!itemExists) {
            System.out.println("ERROR:item_identifier_does_not_exist");
        } else if (storeItems != null && storeItems.contains(itemName)) {
            System.out.println("ERROR:item_already_ordered");
        } else if (customer.getCurrentCredit() < itemPrice) {
            System.out.println("ERROR:customer_cant_afford_new_item");
        } else if (!canDroneCarry) {
            System.out.println("ERROR:drone_cant_carry_new_item");
        } else {
            if (!orderIDtoItemsList.containsKey(orderIdentifier)) {
                orderIDtoItemsList.put(orderIdentifier, new ArrayList<>());
            } else {
                orderIDtoItemsList.get(orderIdentifier).add(itemName);
                mainDrone.setMaxCarryWeight(itemQuantity);
                customer.setCurrentCredit(itemPrice);
            }
        }
    }

}
