import src.main.java.model.*;
import src.main.java.model.service.FireRiskCalculator;
import src.main.java.model.service.DroneCoordinator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

/**
 * main application for EcoFireAI system
 */
public class MainApp {
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        setupLogger();

        Scanner scanner = new Scanner(System.in);

        // initializing monitoring center
        MonitoringCenter center = new MonitoringCenter("MC-001", "Zone X");
        AlertSystem alertSystem = new AlertSystem("ALERT-001");
        FireRiskCalculator riskCalculator = new FireRiskCalculator();
        DroneCoordinator droneCoordinator = new DroneCoordinator();

        logger.info("Initializing sensors...");
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
        sensors.forEach(Sensor::activate);

        logger.info("Initializing drones...");
        List<Drone> drones = new ArrayList<>();
        System.out.println("\n=== Drone Setup ===");
        System.out.print("Enter standard drone deployment location: ");
        Drone regularDrone = new Drone("DRONE-001", scanner.nextLine());

        System.out.print("Enter thermal drone deployment location: ");
        ThermalDrone thermalDrone = new ThermalDrone("THERMAL-001", scanner.nextLine(), 0.5, 500);

        drones.add(regularDrone);
        drones.add(thermalDrone);

        System.out.println("\n=== Launching Continuous Monitoring ===");
        System.out.println(center.monitorSensors(sensors));

        List<FirePrediction> predictions = new ArrayList<>();
        FirePrediction prediction;
        boolean dronesDispatched = false;

        while (true) {
            logger.info("Reading sensors...");
            double temp = tempSensor.readData();
            double hum = humiditySensor.readData();
            double wind = windSensor.readData();

            prediction = riskCalculator.calculateComprehensiveRisk(tempSensor, humiditySensor, windSensor);
            double risk = prediction.getRiskLevel();
            double confidence = prediction.getConfidence();

            System.out.printf("Temperature: %.2f°C | Humidity: %.2f%% | Wind Speed: %.2f km/h%n", temp, hum, wind);
            System.out.printf("Fire Risk Level: %.2f (Confidence: %.2f)%n", risk, confidence);
            logger.info(String.format("Sensor readings - Temp: %.2f°C, Humidity: %.2f%%, Wind: %.2f km/h", temp, hum, wind));
            logger.info(String.format("Fire Risk: %.2f | Confidence: %.2f", risk, confidence));

            predictions.add(prediction);

            if (risk > 0.49) {
                if (!dronesDispatched) {
                    logger.warning("Elevated fire risk detected. Deploying drones...");
                    System.out.println("Elevated fire risk detected. Deploying drones...");
                    System.out.println(droneCoordinator.dispatchSurveyMission(drones, "High Risk Zone", false));
                    alertSystem.sendAlert(AlertLevels.HIGH, "Thermal anomalies detected in the northern sector.");
                    dronesDispatched = true;
                } else {
                    logger.info("Fire risk remains elevated.");
                    System.out.println("Fire risk remains elevated...");
                }
            } else {
                if (dronesDispatched) {
                    logger.info("Fire risk controlled. Drones returning to base.");
                    System.out.println("Fire risk controlled. Drones are returning to base.");
                    dronesDispatched = false;
                } else {
                    logger.info("Conditions stable. Continuing monitoring...");
                    System.out.println("Conditions stable. Continuing monitoring...");
                }
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                logger.severe("Monitoring interrupted. Shutting down.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void setupLogger() {
        try {
            LogManager.getLogManager().reset();
            Logger rootLogger = Logger.getLogger("");
            FileHandler fileHandler = new FileHandler("EcoFireAI.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Could not set up logger: " + e.getMessage());
        }
    }
}
