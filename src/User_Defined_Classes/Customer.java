package User_Defined_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static User_Defined_Classes.Drone.storeDroneCatalog;
import static User_Defined_Classes.Store.storeHashMap;

public class Customer {
    private String customerAccount;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int customerRating;
    private int currentCredit;

    private static HashMap<String, Customer> customerHashMap = new HashMap<>();
    private static HashMap<String, ArrayList<String>> storeOrderList = new HashMap<>();

    public String getCustomerAccount() {
        return customerAccount;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getCustomerRating() {
        return customerRating;
    }
    public int getCurrentCredit() {
        return currentCredit;
    }

    public Customer(String customerAccount, String firstName, String lastName, String phoneNumber, int customerRating, int currentCredit) {
        this.customerAccount = customerAccount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.customerRating = customerRating;
        this.currentCredit = currentCredit;
    }

    public void addCustomer(Customer customer) {
        if (customerHashMap.containsKey(customer.getCustomerAccount())) {
            System.out.println("ERROR:customer_identifier_already_exists");
        } else {
            customerHashMap.put(customer.getCustomerAccount(), customer);
            System.out.println("OK:change_completed");
        }
    }

    public void displayCustomers() {
        for (Map.Entry<String, Customer> entry : customerHashMap.entrySet()) {
            Customer customer = entry.getValue();
            System.out.println("name:" + customer.getFirstName() + "_" + customer.getLastName() + ",phone:" + customer.getPhoneNumber() + ",rating:" + customer.getCustomerRating() + ",credit:" + customer.getCurrentCredit());
        }
        System.out.println("OK:display_completed");
    }
    public void startOrder(String storeName, String orderIdentifier, String droneIdentifier, String customerAccount) {
        ArrayList<Drone> droneArrayList = storeDroneCatalog.get(storeName);
        ArrayList<String> storeArrayList = storeOrderList.get(storeName);
        boolean containsDroneID = false;
        boolean containsCustomerID = false;
        boolean storeOrderIdentifier = false;

        if (droneArrayList != null) {
            for (Drone drone : droneArrayList) {
                if (drone.getDroneID().equals(droneIdentifier)) {
                    containsDroneID = true;
                    break;
                }
            }
        }
        for (Map.Entry<String, Customer> entry : customerHashMap.entrySet()) {
            Customer customer = entry.getValue();
            if (customer.getCustomerAccount().equals(customerAccount)) {
                containsCustomerID = true;
                break;
            }
        }
        if  (storeArrayList != null) {
            for (String storeOrderID : storeArrayList) {
                if (storeOrderID.equals(orderIdentifier)) {
                    storeOrderIdentifier = true;
                    break;
                }
            }
        }

        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else if (!containsDroneID) {
            System.out.println("ERROR:drone_identifier_does_not_exist");
        } else if (!containsCustomerID) {
            System.out.println("ERROR:customer_identifier_does_not_exist");
        } else if (storeOrderIdentifier) {
            System.out.println("ERROR:order_identifier_already_exists");
        } else {
            if (!storeOrderList.containsKey(storeName)) {
                storeOrderList.put(storeName, new ArrayList<>());
            }
            storeOrderList.get(storeName).add(orderIdentifier);
            System.out.println("OK:change_completed");
        }
    }
}
