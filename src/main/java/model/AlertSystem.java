package src.main.java.model;

import java.time.Instant;

/**
 * Handles alert generation and notification
 */
public class AlertSystem {
    private final String alertId;
    private AlertLevels severity;
    private String message;
    private Instant timestamp;

    public AlertSystem(String alertId) {
        this.alertId = alertId;
    }

    /**
     * Sends an alert to relevant parties
     * @param severity level of alert severity
     * @param message alert content
     * @return confirmation message
     */
    public String sendAlert(AlertLevels severity, String message) {
        this.severity = severity;
        this.message = message;
        this.timestamp = Instant.now();

        String notification = String.format("[%s] ALERT %s: %s",
                timestamp.toString(), severity, message);

        System.out.println(notification);
        return "Alert dispatched: " + notification;
    }

    /**
     * escalates an alert to higher severity
     * @param newSeverity the escalated severity level
     */
    public void escalateAlert(AlertLevels newSeverity) {
        if (newSeverity.getLevelValue() > severity.getLevelValue()) {
            severity = newSeverity;
            System.out.println("Alert escalated to " + newSeverity);
        }
    }
}