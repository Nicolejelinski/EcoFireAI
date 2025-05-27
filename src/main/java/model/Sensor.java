package src.main.java.model;

/**
 * Represents an IoT sensor for environmental monitoring
 */
public class Sensor {
    private final String sensorId;
    private String location;
    private boolean active;
    private SensorTypes type;

    public Sensor(String sensorId, String location, SensorTypes type) {
        this.sensorId = sensorId;
        this.location = location;
        this.type = type;
        this.active = false;
    }

    /**
     * Activates the sensor for data collection
     */
    public void activate() {
        this.active = true;
        System.out.println("Sensor " + sensorId + " activated at " + location);
    }

    /**
     * Deactivates the sensor
     */
    public void deactivate() {
        this.active = false;
        System.out.println("Sensor " + sensorId + " deactivated");
    }

    /**
     * Reads environmental data from the sensor
     * @return sensor reading value
     */
    public double readData() {
        if (!active) {
            throw new IllegalStateException("Sensor is not active");
        }
        // sensor config
        return type == SensorTypes.TEMPERATURE ? Math.random() * 70 :
                type == SensorTypes.HUMIDITY ? Math.random() * 20 :
                        Math.random() * 10;
    }

    // getters
    public String getSensorId() {
        return sensorId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public SensorTypes getType() {
        return type;
    }
}