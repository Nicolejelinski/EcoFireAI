package src.main.java.model.service;

import src.main.java.model.Drone;
import src.main.java.model.ThermalDrone;
import java.util.List;


/**
 * Coordinates drone operations and missions
 */
public class DroneCoordinator {

    /**
     * Dispatches drones to survey a high-risk area
     * @param drones list of available drones
     * @param location target location
     * @param isCritical whether this is a critical mission
     * @return mission report
     */
    public String dispatchSurveyMission(List<Drone> drones, String location, boolean isCritical) {
        StringBuilder report = new StringBuilder();
        report.append("Dispatching survey mission to ").append(location).append("\n");

        for (Drone drone : drones) {
            try {
                drone.takeOff();

                if (drone instanceof ThermalDrone) {
                    ThermalDrone thermalDrone = (ThermalDrone) drone;
                    String result = isCritical ?
                            thermalDrone.detectHotspots(0.8) : // Higher sensitivity for critical
                            thermalDrone.detectHotspots();
                    report.append(result).append("\n");
                } else {
                    report.append(drone.scanArea()).append("\n");
                }

                drone.returnToBase();
            } catch (Exception e) {
                report.append("Drone ").append(drone.getDroneId())
                        .append(" failed: ").append(e.getMessage()).append("\n");
            }
        }

        return report.toString();
    }
}