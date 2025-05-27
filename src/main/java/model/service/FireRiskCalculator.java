package src.main.java.model.service;

import src.main.java.model.FirePrediction;
import src.main.java.model.Sensor;

/**
 * Service for calculating and analyzing fire risks
 */
public class FireRiskCalculator {

    /**
     * Calculates comprehensive fire risk based on multiple sensors
     * @param tempSensor temperature sensor
     * @param humiditySensor humidity sensor
     * @param windSensor wind speed sensor
     * @return FirePrediction with calculated risk
     */
    public FirePrediction calculateComprehensiveRisk(Sensor tempSensor, Sensor humiditySensor, Sensor windSensor) {
        FirePrediction prediction = new FirePrediction("PRED-" + System.currentTimeMillis());

        try {
            double temp = tempSensor.readData();
            double humidity = humiditySensor.readData();
            double windSpeed = windSensor.readData();

            prediction.calculateRisk(temp, humidity, windSpeed);
        } catch (IllegalStateException e) {
            System.err.println("Sensor error: " + e.getMessage());
            prediction.calculateRisk(0, 0, 0); // Default safe values
        }

        return prediction;
    }

    /**
     * Determines if evacuation is recommended
     * @param prediction the fire prediction to evaluate
     * @return true if evacuation recommended
     */
    public boolean recommendEvacuation(FirePrediction prediction) {
        return prediction.getRiskLevel() > 0.7;
    }
}