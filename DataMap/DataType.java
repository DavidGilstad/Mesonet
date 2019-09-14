package dg.CS2334.Mesonet.DataMap;

/*
 * This class is useful for holding and displaying the data with the given units
 * or type. This class is for displaying Mesonet data, so no conversion methods
 * are provided, and this class should only be used for data in the units C,
 * W/m^2, m/s, mBar, and cm.
 */
public enum DataType {
    TAIR("C"), // air temp at 1.5m
    TA9M("C"), // air temp at 9.0m
    SRAD("W/m^2"),
    WSPD("m/s"),
    PRES("mBar"),
    RAIN("cm");

    private String units;
    private DataType(String units) { this.units = units; }

    /* Returns a string of the abbreviated units for this enum type. */
    public String getUnits() { return units; }
}
