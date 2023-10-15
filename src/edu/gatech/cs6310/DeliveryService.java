package edu.gatech.cs6310;
import User_Defined_Classes.*;

import java.util.Scanner;

public class DeliveryService {
    Store store;
    Item item;
    DronePilot pilot;
    Drone drone;
    Customer customer;
    Order order = new Order();
    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                if (wholeInputLine.startsWith("//")) {
                    continue;
                }
                if (tokens[0].equals("make_store")) {
                    store = new Store(tokens[1], Integer.parseInt(tokens[2]));
                    store.addToDatabase(store.getStoreName(), store);

                } else if (tokens[0].equals("display_stores")) {
                    store.displayStores();
                } else if (tokens[0].equals("sell_item")) {
                    item = new Item(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    item.addItem(item);

                } else if (tokens[0].equals("display_items")) {
                    item.displayItems(tokens[1]);
                } else if (tokens[0].equals("make_pilot")) {
                    pilot = new DronePilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7]));
                    pilot.addPilot(pilot);

                } else if (tokens[0].equals("display_pilots")) {
                    pilot.displayPilots();
                } else if (tokens[0].equals("make_drone")) {
                    drone = new Drone(tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                    drone.addDrone(drone);

                } else if (tokens[0].equals("display_drones")) {
                    drone.displayDrones(tokens[1]);

                } else if (tokens[0].equals("fly_drone")) {
                    drone.flyDrone(tokens[1], tokens[2], tokens[3]);

                } else if (tokens[0].equals("make_customer")) {
                    customer = new Customer(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    customer.addCustomer(customer);

                } else if (tokens[0].equals("display_customers")) {
                    customer.displayCustomers();
                } else if (tokens[0].equals("start_order")) {
                    customer.startOrder(tokens[1], tokens[2], tokens[3], tokens[4]);

                } else if (tokens[0].equals("display_orders")) {
                    customer.displayOrders(tokens[1]);

                } else if (tokens[0].equals("request_item")) {
                    order.requestItem(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));

                } else if (tokens[0].equals("purchase_order")) {
                    order.purchaseOrder(tokens[1], tokens[2]);

                } else if (tokens[0].equals("cancel_order")) {
                    order.cancelOrder(tokens[1], tokens[2]);

                } else if (tokens[0].equals("transfer_order")) {
                    order.transferOrder(tokens[1], tokens[2], tokens[3]);

                } else if (tokens[0].equals("display_efficiency")) {
                    order.displayEfficiency();

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}