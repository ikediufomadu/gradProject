package User_Defined_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static User_Defined_Classes.Customer.*;
import static User_Defined_Classes.Drone.dronePilotToDroneMap;
import static User_Defined_Classes.Drone.storeDroneCatalog;
import static User_Defined_Classes.Item.storeCatalog;
import static User_Defined_Classes.Store.storeHashMap;


public class Order {
    public class ItemOrder {
        private String itemID;
        private String itemName;
        private int itemQuantity;
        private int itemPrice;
        private int itemWeight;
        private int totalItemPrice;

        public String getItemID() {
            return itemID;
        }
        public String getItemName() {
            return itemName;
        }
        public int getItemQuantity() {
            return itemQuantity;
        }
        public int getItemPrice() {
            return itemPrice;
        }
        public int getItemWeight() {
            return itemWeight;
        }
        public int getTotalItemPrice() {
            return totalItemPrice;
        }

        public ItemOrder(String itemID, String itemName, int itemQuantity, int itemPrice, int itemWeight) {
            this.itemID = itemID;
            this.itemName = itemName;
            this.itemQuantity = itemQuantity;
            this.itemPrice = itemPrice;
            this.itemWeight = itemWeight * itemQuantity;
            this.totalItemPrice = itemPrice * itemQuantity;
        }
    }
    Order order = new Order();
    private int numberOfTransfers = 0;
    public void increaseNumberOfTransfers() {
        numberOfTransfers++;
    }
    private int getNumberOfTransfers() {
        return numberOfTransfers;
    }
    public static HashMap<String, ArrayList<String>> orderIDtoItemsList = new HashMap<>();
    public static HashMap<String, ArrayList<ItemOrder>> storeNametoOrderIDList = new HashMap<>();
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
        Item item = null;
        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderIdentifier)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }
        if (storeItemList != null) {
            for (Item tempItem : storeItemList) {
                if (tempItem.getItemName().equals(itemName)) {
                    item = tempItem;
                    itemExists = true;
                    break;
                }
            }
        }
        if (customerHashMap != null) {
            for (Map.Entry<String, Customer> entry : customerHashMap.entrySet()) {
                if (customerIDAndCustomerOrder.containsKey(entry.getKey())) {
                    customer = entry.getValue();
                    break;
                }
            }
        }
        if (storeDrone != null) {
            for (Drone drone : storeDrone) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    if (drone.getCurrentCapacity() < itemQuantity || drone.getCurrentCapacity() < 0) {
                        canDroneCarry = false;
                    }
                    mainDrone = drone;
                    break;
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
            }
            if (!storeNametoOrderIDList.containsKey(storeName)) {
                storeNametoOrderIDList.put(storeName, new ArrayList<>());
            }

            mainDrone.lowerCurrentCapacity(itemQuantity * item.getItemWeight());
            customer.setCurrentCredit(itemPrice * itemQuantity);
            orderIDtoItemsList.get(orderIdentifier).add(itemName);
            storeNametoOrderIDList.get(storeName).add(new ItemOrder(orderIdentifier, itemName, itemQuantity, itemPrice, item.getItemWeight()));
            System.out.println("OK:change_completed");
        }
    }

    public void purchaseOrder(String storeName, String orderID) {
        ArrayList<String> storeArrayList = storeOrderList.get(storeName);
        boolean storeOrderIdentifier = false;
        Drone mainDrone = null;
        DronePilot dronePilot = null;
        ItemOrder mainItemOrder = null;
        String customerID = null;
        Customer customer = null;
        Store store = storeHashMap.get(storeName);
        ArrayList<ItemOrder> itemOrderArrayList = storeNametoOrderIDList.get(storeName);
        ArrayList<Drone> storeDrone = storeDroneCatalog.get(storeName);
        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderID)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }
        if (storeDrone != null) {
            for (Drone drone : storeDrone) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    mainDrone = drone;
                    break;
                }
            }
        }
        if (dronePilotToDroneMap != null) {
            for (Map.Entry<DronePilot, Drone> entry : dronePilotToDroneMap.entrySet()) {
                if (entry.getValue().getDroneID().equals(mainDrone.getDroneID())) {
                    dronePilot = entry.getKey();
                }
            }
        }
        if (itemOrderArrayList != null) {
            for (ItemOrder itemOrder : itemOrderArrayList) {
                if (itemOrder.getItemID().equals(orderID)) {
                    mainItemOrder = itemOrder;
                }
            }
        }
        if (customerIDAndCustomerOrder != null) {
            for (Map.Entry<String, String> entry : customerIDAndCustomerOrder.entrySet()) {
                if (entry.getValue().equals(orderID)) {
                    customerID = entry.getKey();
                }
            }
        }
        customer = customerHashMap.get(customerID);
        if (!mainDrone.getHasPilot()) {
            System.out.println("ERROR:drone_needs_pilot");
        } else if (mainDrone.getTripsBeforeService() <= 1) {
            System.out.println("ERROR:drone_needs_fuel");
        } else if (!storeOrderIdentifier) {
            System.out.println("ERROR:order_identifier_does_not_exist");
        } else if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            customer.updateCreditBalance();
            store.addRevenue(mainItemOrder.getTotalItemPrice());
            store.increaseNumberOfPurchases();
            mainDrone.lowerTripsBeforeService();
            dronePilot.addNumberOfDeliveries();

            storeOrderList.remove(storeArrayList.remove(orderID));
            customerIDAndCustomerOrder.remove(customer.getCustomerAccount());
            droneIDAndCustomerOrder.remove(mainDrone.getDroneID());
            orderIDtoItemsList.remove(orderID);
            storeNametoOrderIDList.remove(itemOrderArrayList);
            System.out.println("OK:change_completed");
        }
    }
    public void cancelOrder(String storeName, String orderID) {
        ArrayList<String> storeArrayList = storeOrderList.get(storeName);
        ArrayList<ItemOrder> itemOrderArrayList = storeNametoOrderIDList.get(storeName);
        ArrayList<Drone> storeDrone = storeDroneCatalog.get(storeName);

        boolean storeOrderIdentifier = false;
        Drone mainDrone = null;
        ItemOrder mainItemOrder = null;
        String customerID = null;
        Customer customer = null;
        Store store = storeHashMap.get(storeName);

        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderID)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }
        if (storeDrone != null) {
            for (Drone drone : storeDrone) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    mainDrone = drone;
                    break;
                }
            }
        }
        if (itemOrderArrayList != null) {
            for (ItemOrder itemOrder : itemOrderArrayList) {
                if (itemOrder.getItemID().equals(orderID)) {
                    mainItemOrder = itemOrder;
                }
            }
        }
        if (customerIDAndCustomerOrder != null) {
            for (Map.Entry<String, String> entry : customerIDAndCustomerOrder.entrySet()) {
                if (entry.getValue().equals(orderID)) {
                    customerID = entry.getKey();
                }
            }
        }
        customer = customerHashMap.get(customerID);
        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else if (!storeOrderIdentifier) {
            System.out.println("ERROR:order_identifier_does_not_exist");
        } else {
            storeOrderList.remove(storeArrayList.remove(orderID));
            customerIDAndCustomerOrder.remove(customer.getCustomerAccount());
            droneIDAndCustomerOrder.remove(mainDrone.getDroneID());
            orderIDtoItemsList.remove(orderID);
            storeNametoOrderIDList.remove(itemOrderArrayList);
            System.out.println("OK:change_completed");
        }
    }
    public void transferOrder(String storeName, String orderID, String newDroneID) {
        ArrayList<Drone> storeDrone = storeDroneCatalog.get(storeName);
        ArrayList<String> storeArrayList = storeOrderList.get(storeName);
        ArrayList<Order.ItemOrder> storeOrderList = storeNametoOrderIDList.get(storeName);
        int orderWeight = 0;
        boolean storeOrderIdentifier = false;
        boolean oldDroneExists = false;

        Drone oldDrone = null;
        Drone newDrone = null;
        Order.ItemOrder mainItemOrder = null;
        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderID)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }
        if (storeDrone != null) {
            for (Drone drone : storeDrone) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    oldDrone = drone;
                    oldDroneExists = true;
                }
                if (drone.getDroneID().equals(newDroneID)) {
                    assert oldDrone != null;
                    if (drone.getDroneID().equals(oldDrone.getDroneID())) {
                        System.out.println("ERROR:new_drone_cannot_be_the_same_as_current_drone");
                        break;
                    }
                }
                if (drone.getDroneID().equals(newDroneID)) {
                    newDrone = drone;
                }
            }
        }
        if (storeOrderList != null) {
                for (Order.ItemOrder itemOrder : storeOrderList) {
                    if (itemOrder.getItemID().equals(orderID)) {
                        mainItemOrder = itemOrder;
                        break;
                    }
                }
        }
        if (mainItemOrder != null) {
            orderWeight = mainItemOrder.getItemWeight();
        }

        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else if (!storeOrderIdentifier || mainItemOrder == null) {
            System.out.println("ERROR:order_identifier_does_not_exist");
        } else if (!oldDroneExists || newDrone == null) {
            System.out.println("ERROR:drone_identifier_does_not_exist");
        } else if (newDrone.getCurrentCapacity() < orderWeight) {
            System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
        } else if (!newDrone.getHasPilot()) {
            System.out.println("ERROR:new_drone_needs_pilot");
        } else {
            oldDrone.returnCapacitySpace(orderWeight);
            newDrone.lowerCurrentCapacity(orderWeight);
            order.increaseNumberOfTransfers();
            // Removes relationship between old drone and orderID
            droneIDAndCustomerOrder.remove(oldDrone.getDroneID());
            droneIDAndCustomerOrder.put(newDrone.getDroneID(), orderID);
            System.out.println("fffffffffffffffffffffffffffffffffffffff"+ " " + oldDrone.getDroneID() + " "+ mainItemOrder.itemWeight + " " + orderWeight);
            System.out.println("OK:change_completed");
        }
    }
//    public void displayEfficiency() {
//        System.out.println("name: " + "overloads:" + "transfers:" + order.getNumberOfTransfers());
//    }
    public void displayEfficiency() {
        for (Map.Entry<String, Store> storeEntry : storeHashMap.entrySet()) {
            Store store = storeEntry.getValue();
            String storeName = storeEntry.getKey();

            int purchasedOrders = store.getNumberOfPurchases();
            int overloads = calculateOverloads(storeName);
            int transfers = calculateTransfers(storeName);
            System.out.println("name:" + storeName + "overloads:" + "transfers:" + order.getNumberOfTransfers());
        }

        System.out.println("OK:display_completed");
    }

    public int calculateOverloads(String storeName) {
        ArrayList<Drone> storeDrones = storeDroneCatalog.get(storeName);
        int overloads = 0;

        if (storeDrones != null) {
            for (Drone drone : storeDrones) {
                int extraPackages = calculateExtraPackages(drone, storeName);
                overloads += extraPackages;
            }
        }

        return overloads;
    }

    public int calculateExtraPackages(Drone drone, String storeName) {
        ArrayList<Order.ItemOrder> storeOrderList = storeNametoOrderIDList.get(storeName);
        int extraPackages = 0;

        if (storeOrderList != null) {
            for (Order.ItemOrder itemOrder : storeOrderList) {
                if (droneIDAndCustomerOrder.containsKey(drone.getDroneID())) {
                    String orderID = droneIDAndCustomerOrder.get(drone.getDroneID());
                    if (!itemOrder.getItemID().equals(orderID)) {
                        extraPackages++;
                    }
                }
            }
        }

        return extraPackages;
    }

    public int calculateTransfers(String storeName) {
        ArrayList<String> storeOrderList = storeOrderList.get(storeName);
        int transfers = 0;

        if (storeOrderList != null) {
            transfers = storeOrderList.size();
        }

        return transfers;
    }

}
