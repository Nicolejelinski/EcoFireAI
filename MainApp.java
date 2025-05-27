import src.main.java.model.*;
import src.main.java.model.service.FireRiskCalculator;
import src.main.java.model.service.DroneCoordinator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application for EcoFireAI system
 */
public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // inicializando motiramento de area x
        MonitoringCenter center = new MonitoringCenter("MC-001", "Floresta");
        AlertSystem alertSystem = new AlertSystem("ALERT-001");
        FireRiskCalculator riskCalculator = new FireRiskCalculator();
        DroneCoordinator droneCoordinator = new DroneCoordinator();

        // criando sensores
        List<Sensor> sensors = new ArrayList<>();
        System.out.println("=== Sensor Setup ===");
        System.out.print("Enter temperature sensor location: ");
        Sensor tempSensor = new Sensor("TEMP-001", scanner.nextLine(), SensorTypes.TEMPERATURE);

        System.out.print("Enter humidity sensor location: ");
        Sensor humiditySensor = new Sensor("HUM-001", scanner.nextLine(), SensorTypes.HUMIDITY);

        System.out.print("Enter wind sensor location: ");
        Sensor windSensor = new Sensor("WIND-001", scanner.nextLine(), SensorTypes.WIND_SPEED);

        sensors.add(tempSensor);
        sensors.add(humiditySensor);
        sensors.add(windSensor);

        // Activate sensors
        sensors.forEach(Sensor::activate);

        // Create drones
        List<Drone> drones = new ArrayList<>();
        System.out.println("\n=== Drone Setup ===");
        System.out.print("Enter regular drone deployment location: ");
        Drone regularDrone = new Drone("DRONE-001", scanner.nextLine());

        System.out.print("Enter thermal drone deployment location: ");
        ThermalDrone thermalDrone = new ThermalDrone("THERMAL-001", scanner.nextLine(), 0.5, 500);

        drones.add(regularDrone);
        drones.add(thermalDrone);

        // System demonstration
        System.out.println("\n=== System Demonstration ===");

        // 1. Monitor sensors
        System.out.println("\nMonitoring sensors...");
        System.out.println(center.monitorSensors(sensors));

        // 2. Calculate fire risk
        FirePrediction prediction = riskCalculator.calculateComprehensiveRisk(
                tempSensor, humiditySensor, windSensor);

        System.out.printf("\nCurrent fire risk: %.2f (Confidence: %.2f)%n",
                prediction.getRiskLevel(), prediction.getConfidence());

        // 3. Dispatch drones if risk is high
        if (prediction.getRiskLevel() > 0.3) {
            System.out.println("\nRisk level elevated. Dispatching drones...");
            System.out.println(droneCoordinator.dispatchSurveyMission(drones, "High Risk Zone", false));

            // Simulate thermal drone finding hotspots
            String alertMsg = "Thermal anomalies detected in northern sector";
            alertSystem.sendAlert(AlertLevels.HIGH, alertMsg);
        }

        // 4. Generate final report
        List<FirePrediction> predictions = new ArrayList<>();
        predictions.add(prediction);

        System.out.println("\n=== Final System Report ===");
        System.out.println(center.generateReport(sensors, drones, predictions));

        scanner.close();
    }
}