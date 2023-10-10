package User_Defined_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static User_Defined_Classes.Store.storeHashMap;
import static User_Defined_Classes.DronePilot.dronePilotHashMap;

public class Drone {
    private String droneID;
    private int maxCarryWeight;
    private int tripsBeforeService;
    private String belongsToWhichStore;
    private int numOfOrdersDone;
    private int remainingCapacity;

    public String getDroneID() {
        return droneID;
    }
    public int getMaxCarryWeight() {
        return maxCarryWeight;
    }
    public int getTripsBeforeService() {
        return tripsBeforeService;
    }
    public String getBelongsToWhichStore() {
        return belongsToWhichStore;
    }
    public int getNumOfOrdersDone() {
        return numOfOrdersDone;
    }
    public int getRemainingCapacity() {
        return remainingCapacity;
    }
    public void setMaxCarryWeight(int addedWeight) {
        maxCarryWeight -= addedWeight;
    }

    public static HashMap<String, ArrayList<Drone>> storeDroneCatalog = new HashMap<>();
    private static HashMap<DronePilot, Drone> dronePilotToDroneMap = new HashMap<>();
    public Drone(String belongsToWhichStore, String droneID, int maxCarryWeight, int tripsBeforeService) {
        this.belongsToWhichStore = belongsToWhichStore;
        this.droneID = droneID;
        this.maxCarryWeight = maxCarryWeight;
        this.tripsBeforeService = tripsBeforeService;
        this.numOfOrdersDone = 0;
        this.remainingCapacity = maxCarryWeight;
    }

    public void addDrone(Drone drone) {
        String storeName = drone.getBelongsToWhichStore();

        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            if (!storeDroneCatalog.containsKey(storeName)) {
                storeDroneCatalog.put(storeName, new ArrayList<>());
            }

            ArrayList<Drone> storeDrones = storeDroneCatalog.get(storeName);
            boolean droneExists = storeDrones.stream().anyMatch(d -> d.getDroneID().equals(drone.getDroneID()));
            if (droneExists) {
                System.out.println("ERROR:drone_identifier_already_exists");
            } else {
                storeDroneCatalog.get(storeName).add(drone);
                System.out.println("OK:change_completed");
            }
        }
    }

    public void displayDrones(String storeNameToSearch) {
        if (storeDroneCatalog.containsKey(storeNameToSearch)) {
            ArrayList<Drone> storeDrones = storeDroneCatalog.get(storeNameToSearch);
            for (Drone drone : storeDrones) {
                if (dronePilotToDroneMap.containsValue(drone)) {
                    System.out.println("droneID:" + drone.getDroneID() + ",total_cap:" + drone.getMaxCarryWeight() + ",num_orders:" + drone.getNumOfOrdersDone() + ",remaining_cap:" + drone.getRemainingCapacity() + ",trips_left:" + drone.getTripsBeforeService() + ",flown_by:" + getDronePilot(drone));
                } else {
                    System.out.println("droneID:" + drone.getDroneID() + ",total_cap:" + drone.getMaxCarryWeight() + ",num_orders:" + drone.getNumOfOrdersDone() + ",remaining_cap:" + drone.getRemainingCapacity() + ",trips_left:" + drone.getTripsBeforeService());
                }
            }
            System.out.println("OK:display_completed");
        } else {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
    }

    public void flyDrone(String storeName, String droneID, String pilotAccount) {
        if (!storeHashMap.containsKey(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            ArrayList<DronePilot> dronePilotArrayList = new ArrayList<>(dronePilotHashMap.values());
            ArrayList<Drone> listOfDrones = storeDroneCatalog.get(storeName);
            boolean pilotExists = false;
            boolean droneExists = false;
            Drone droneToMap = null;
            for (Drone drone : listOfDrones) {
                if (drone.getDroneID().equals(droneID)) {
                    droneExists = true;
                    droneToMap = drone;
                    break;
                }
            }

            for (DronePilot dronePilot : dronePilotArrayList) {
                if (dronePilot.getPilotAccount().equals(pilotAccount)) {
                    pilotExists = true;
                    break;
                }
            }
            if (!droneExists) {
                System.out.println("ERROR:drone_identifier_does_not_exist");
            } else if (!pilotExists) {
                System.out.println("ERROR:pilot_identifier_does_not_exist");
            } else {
                dronePilotToDroneMap.put(dronePilotHashMap.get(pilotAccount), droneToMap);
                System.out.println("OK:change_completed");
            }
        }
    }

    private String getDronePilot(Drone drone) {
        for (Map.Entry<DronePilot, Drone> entry : dronePilotToDroneMap.entrySet()) {
            if (entry.getValue().equals(drone)) {
                return entry.getKey().getFirstName() + "_" + entry.getKey().getLastName();
            }
        }

        return null;
    }
}
