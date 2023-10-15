package User_Defined_Classes;

import java.util.*;

public class DronePilot {
    private String pilotAccount;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String taxID;
    private String licenseID;
    private int numberOfDeliveries;

    public static HashMap<String, DronePilot> dronePilotHashMap = new HashMap<>();
    private static ArrayList<String> pilotLicenseList = new ArrayList<>();

    public String getPilotAccount() {
        return pilotAccount;
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
    public String getTaxID() {
        return taxID;
    }
    public String getLicenseID() {
        return licenseID;
    }
    public int getNumberOfDeliveries() {
        return numberOfDeliveries;
    }
    public void addNumberOfDeliveries() {
        numberOfDeliveries++;
    }
    public DronePilot(String pilotAccount, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, int numberOfDeliveries) {
        this.pilotAccount = pilotAccount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.numberOfDeliveries = numberOfDeliveries;
    }
    public void addPilot(DronePilot pilot) {
        if (dronePilotHashMap.containsKey(pilot.getPilotAccount())) {
            System.out.println("ERROR:pilot_identifier_already_exists");
        } else if (pilotLicenseList.contains(pilot.getLicenseID())) {
            System.out.println("ERROR:pilot_license_already_exists");
        } else {
            dronePilotHashMap.put(pilot.getPilotAccount(), pilot);
            pilotLicenseList.add(pilot.getLicenseID());
            System.out.println("OK:change_completed");
        }
    }

    public void displayPilots() {
        List<DronePilot> pilots = new ArrayList<>(dronePilotHashMap.values());

        pilots.sort(Comparator.comparing(DronePilot::getPilotAccount));

        for (DronePilot dronePilot : pilots) {
            System.out.println("name:" + dronePilot.getFirstName() + "_" + dronePilot.getLastName() + ",phone:" + dronePilot.getPhoneNumber() + ",taxID:" + dronePilot.getTaxID() + ",licenseID:" + dronePilot.getLicenseID() + ",experience:" + dronePilot.getNumberOfDeliveries());
        }

        System.out.println("OK:display_completed");
    }
}
