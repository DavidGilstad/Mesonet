package dg.CS2334.Mesonet.DataMap;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/*
 * This class is used to hold and display the Maximum, Minimum, and average
 * value of Observations from a set of data. Calculations of these statistics
 * must be done elsewhere, such as in the DataMap class.
 */
public class Statistic extends Observation {
    private int numStations;
    private ZonedDateTime dateTime;
    private StatType statType;

    /* Constructs a Statistic with the given values. */
    public Statistic(double value, DataType dataType, String stid, int stations, ZonedDateTime time, StatType stat) {
        super(value, dataType, stid);
        numStations = stations;
        dateTime = time;
        statType = stat;
    }

    public int getNumStations() { return numStations; }
    public String getDateAndTime() { return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss z")); }
    public StatType getStatType() { return statType; }
}
