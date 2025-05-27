package src.main.java.model;

/**
 * Specialized drone with thermal imaging capabilities
 */
public class ThermalDrone extends Drone {
    private final double thermalResolution;
    private final double maxTempDetection;

    public ThermalDrone(String droneId, String initialLocation, double thermalResolution, double maxTempDetection) {
        super(droneId, initialLocation);
        this.thermalResolution = thermalResolution;
        this.maxTempDetection = maxTempDetection;
    }

    /**
     * Detects thermal hotspots in the area
     * @param sensitivity detection sensitivity threshold
     * @return map of hotspot coordinates and temperatures
     */
    public String detectHotspots(double sensitivity) {
        if (status != DroneStatus.IN_FLIGHT) {
            throw new IllegalStateException("Drone must be in flight for thermal scanning");
        }
        batteryLevel -= 10; // Thermal scanning consumes more power
        return String.format("Thermal hotspots detected with sensitivity %.2f", sensitivity);
    }

    // Overloaded method with default sensitivity
    public String detectHotspots() {
        return detectHotspots(0.5); // Default sensitivity
    }

    @Override
    public String scanArea() {
        String regularScan = super.scanArea();
        return regularScan + "\n" + detectHotspots();
    }
}