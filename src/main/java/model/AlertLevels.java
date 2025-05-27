package src.main.java.model;

public enum AlertLevels {
    LOW, MEDIUM, HIGH, CRITICAL;

    public int getLevelValue() {
        return this.ordinal();
    }
}
