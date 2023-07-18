package io.aharoj;

public class VoltageLevelAnalyzer 
{
    public static final float DANGEROUS_VOLTAGE_DEFAULT_LOWER_LIMIT = 30;
    private final float dangerousVoltageLowerLimit;

    public VoltageLevelAnalyzer() {
        this.dangerousVoltageLowerLimit = DANGEROUS_VOLTAGE_DEFAULT_LOWER_LIMIT;
    }

    public VoltageLevelAnalyzer(float dangerousVoltageLowerLimit) {
        this.dangerousVoltageLowerLimit = dangerousVoltageLowerLimit;
    }

    public float getDangerousVoltageLowerLimit() {
        return dangerousVoltageLowerLimit;
    }

    public boolean isDangerous(float voltageLevel) {
        return voltageLevel >= dangerousVoltageLowerLimit;
    }
}