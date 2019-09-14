package dg.CS2334.Mesonet.DataMap;

/*
 * This class holds and gives information for a single measurement of weather
 * data: 1) value of the measurement 2) data type of the measurement 3) station
 * id for the measurement 4) whether or not this measurement is valid (> -999).
 */
public class Observation {
    private double value;
    private DataType type;
    private String stid;
    private boolean valid;

    /* Constructs an Observation with the given value, dataType, and stid. */
    public Observation(double measureValue, DataType dataType, String station) {
        value = measureValue;
        type = dataType;
        stid = station;
        valid = value != -999;
    }

    // Getters
    public double getValue() { return value; }
    public DataType getType() { return type; }
    public String getStid() { return stid; }
    public boolean isValid() { return valid; }
}
