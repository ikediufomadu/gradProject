package User_Defined_Classes;

public class SystemState {
    public void reflectGoodSystemStateChange() {
        System.out.println("OK:change_completed");
    }
    public void reflectBadSystemStateChange() {
        System.out.println("ERROR:store_identifier_already_exists");
    }
}
