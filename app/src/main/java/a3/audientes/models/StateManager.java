package a3.audientes.models;

public class StateManager {
    // static variable single_instance of type Singleton
    private static StateManager single_instance = null;

    // variable
    boolean bluetooth = false;
    boolean hearable = false;

    // private constructor restricted to this class itself
    private StateManager() {

    }

    // static method to create instance of Singleton class
    public static StateManager getInstance() {
        if (single_instance == null)
            single_instance = new StateManager();

        return single_instance;
    }

    // Getters and setters


    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isHearable() {
        return hearable;
    }

    public void setHearable(boolean hearable) {
        this.hearable = hearable;
    }
}