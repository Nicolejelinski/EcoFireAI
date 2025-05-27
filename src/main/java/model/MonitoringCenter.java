package src.main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Central monitoring and control hub
 */
public class MonitoringCenter {
    private final String centerId;
    private String region;
    private List<String> operators;

    public MonitoringCenter(String centerId, String region) {
        this.centerId = centerId;
        this.region = region;
        this.operators = new ArrayList<>();
    }

    /**
     * Monitors all sensors in the network
     * @param sensors list of sensors to monitor
     * @return summary report
     */
    public String monitorSensors(List<Sensor> sensors) {
        StringBuilder report = new StringBuilder();
        report.append("Sensor Monitoring Report for ").append(region).append("\n");

        for (Sensor sensor : sensors) {
            try {
                double reading = sensor.readData();
                report.append(String.format("Sensor %s: %.2f\n", sensor.getSensorId(), reading));
            } catch (IllegalStateException e) {
                report.append(String.format("Sensor %s: INACTIVE\n", sensor.getSensorId()));
            }
        }

        return report.toString();
    }

    /**
     * Coordinates multiple drones
     * @param drones list of drones to control
     */
    public void controlDrones(List<Drone> drones) {
        System.out.println("Initiating drone control sequence");
        for (Drone drone : drones) {
            drone.takeOff();
            // Simulate some flight operations
            if (drone instanceof ThermalDrone) {
                ((ThermalDrone) drone).detectHotspots();
            } else {
                drone.scanArea();
            }
            drone.returnToBase();
        }
    }

    /**
     * Generates a comprehensive monitoring report
     * @param sensors list of sensors
     * @param drones list of drones
     * @param predictions list of predictions
     * @return formatted report
     */
    public String generateReport(List<Sensor> sensors, List<Drone> drones, List<FirePrediction> predictions) {
        StringBuilder report = new StringBuilder();
        report.append("=== ECOFIREAI MONITORING REPORT ===\n");
        report.append("Region: ").append(region).append("\n\n");

        report.append("-- Sensor Status --\n");
        sensors.forEach(s -> report.append(s.getSensorId())
                .append(": ")
                .append(s.isActive() ? "ACTIVE" : "INACTIVE")
                .append("\n"));

        report.append("\n-- Drone Status --\n");
        drones.forEach(d -> report.append(d.getDroneId())
                .append(": ")
                .append(d.getStatus())
                .append("\n"));

        report.append("\n-- Fire Predictions --\n");
        predictions.forEach(p -> report.append(String.format("Risk: %.2f (Confidence: %.2f)\n",
                p.getRiskLevel(), p.getConfidence())));

        return report.toString();
    }
}