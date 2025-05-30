package src.main.java.model;

/**
 * base class for all drone types in the system
 */
public class Drone {
    protected final String droneId;
    protected String currentLocation;
    protected int batteryLevel;
    protected DroneStatus status;

    public Drone(String droneId, String initialLocation) {
        this.droneId = droneId;
        this.currentLocation = initialLocation;
        this.batteryLevel = 100;
        this.status = DroneStatus.IDLE;
    }

    /**
     * initiates drone takeoff sequence
     */
    public void takeOff() {
        if (batteryLevel < 20) {
            throw new IllegalStateException("Insufficient battery for takeoff");
        }
        status = DroneStatus.IN_FLIGHT;
        System.out.println("Drone " + droneId + " taking off from " + currentLocation);
    }

    /**
     * lands the drone at current position
     */
    public void land() {
        status = DroneStatus.LANDED;
        System.out.println("Drone " + droneId + " landing at " + currentLocation);
    }

    /**
     * simulates area scanning
     * @return simulated image data
     */
    public String scanArea() {
        if (status != DroneStatus.IN_FLIGHT) {
            throw new IllegalStateException("Drone must be in flight to scan");
        }
        batteryLevel -= 5;
        return "Image data from " + currentLocation;
    }

    /**
     * returns drone to its base location
     */
    public void returnToBase() {
        System.out.println("Drone " + droneId + " returning to base");
        currentLocation = "Base Station";
        land();
    }

    // Correct getters (replacing the incorrect char[] version)
    public String getDroneId() {
        return droneId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public DroneStatus getStatus() {
        return status;
    }
}

enum DroneStatus {
    IDLE, IN_FLIGHT, LANDED, MAINTENANCE
}