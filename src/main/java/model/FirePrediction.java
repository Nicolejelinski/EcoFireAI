package src.main.java.model;

import java.time.LocalDateTime;

/**
 * Represents a fire risk prediction model
 */
public class FirePrediction {
    private final String predictionId;
    private double riskLevel;
    private double confidence;
    private LocalDateTime timestamp;

    public FirePrediction(String predictionId) {
        this.predictionId = predictionId;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Calculates fire risk based on environmental factors
     * @param temperature current temperature
     * @param humidity current humidity
     * @param windSpeed current wind speed
     * @return calculated risk level (0-1)
     */
    public double calculateRisk(double temperature, double humidity, double windSpeed) {
        // simplified risk calculation algorithm
        double tempFactor = temperature > 30 ? 0.6 : 0.3;
        double humidityFactor = humidity < 30 ? 0.7 : 0.2;
        double windFactor = windSpeed > 15 ? 0.5 : 0.2;

        this.riskLevel = (tempFactor + humidityFactor + windFactor) / 3;
        this.confidence = 0.9; // Assuming high confidence for demo
        this.timestamp = LocalDateTime.now();

        return riskLevel;
    }

    /**
     * gets the confidence level of the prediction
     * @return confidence value (0-1)
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * gets the risk level of the prediction
     * @return risk value (0-1)
     */
    public double getRiskLevel() {
        return riskLevel;
    }
}