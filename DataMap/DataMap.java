package dg.CS2334.Mesonet.DataMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.TreeMap;

/*
 * This class is for reading in a .mdf mesonet weather data file, parsing it and
 * sorting it into a data catalog, and performing calculations on the data. This
 * way statistics on the file can easily be retrieved and presented.
 */
public class DataMap {
	private HashMap<DataType,ArrayList<Observation>> dataCatalog;
	private EnumMap<StatType,TreeMap<DataType,Statistic>> statCatalog;
	private int numStations = 0;
	private ZonedDateTime dateTime;

	/*
	 * Constructs a data map from the given file in the given folder. Parsing and
	 * statistics calculations are done automatically, so the data map has all the
	 * statistical data from the file upon instantiation.
	 */
	public DataMap (String filename, String folder) throws IOException {
		dateTime = ZonedDateTime.of(LocalDateTime.parse(filename.substring(0,12),
				DateTimeFormatter.ofPattern("yyyyMMddHHmm")),ZoneId.of("America/Chicago"));
		dataCatalog = new HashMap<>();
		statCatalog = new EnumMap<StatType,TreeMap<DataType,Statistic>>(StatType.class);
		for (DataType key : DataType.values())
			dataCatalog.put(key,new ArrayList<Observation>());
		parse(filename,folder);
		calculateStats();
	}

	/* Parses the file and puts all of the data into the data catalog. */
	private void parse(String filename, String folder) throws IOException {
		String[] line;
		BufferedReader br = new BufferedReader(new FileReader(folder + "/" + filename));
		br.readLine(); br.readLine(); // useless lines
		ArrayList<String> headers = new ArrayList<>(
				Arrays.asList(br.readLine().trim().split("\\s+")));

		while (br.ready()) { // all other lines have the data
			line = br.readLine().trim().split("\\s+");
			// parse each line and add its data to the catalog
			for (DataType key : dataCatalog.keySet())
				dataCatalog.get(key).add(new Observation(
						Double.parseDouble(line[headers.indexOf(key.name())]),key,line[0]));
			++numStations; // each line is one station's data
		}
		br.close();
	}

	/*
	 * Calculates statistics on the data in the data catalog and puts them into the
	 * stats catalog.
	 */
	private void calculateStats() {
		for (StatType key : StatType.values())
			statCatalog.put(key,new TreeMap<DataType,Statistic>());

		for (DataType key : dataCatalog.keySet()) {
			ArrayList<Observation> dataSet = dataCatalog.get(key);
			Observation currData, max = dataSet.get(0), min = dataSet.get(0);
			double total = 0;
			int validStations = numStations; // assume all stations are valid

			for (int i = 1; i < dataSet.size(); ++i) {
				currData = dataSet.get(i);
				if (currData.isValid()) {
					total += currData.getValue();
					if (currData.getValue() > max.getValue())
						max = currData;
					else if (currData.getValue() < min.getValue()) min = currData;
				} 
				else --validStations; // remove station if it's invalid
			}

			if (numStations - validStations < 10) { // only calculate if not too much missing data
				statCatalog.get(StatType.MAXIMUM).put(key,new Statistic(max.getValue(),key,
						max.getStid(),validStations,dateTime,StatType.MAXIMUM));
				statCatalog.get(StatType.MINIMUM).put(key,new Statistic(min.getValue(),key,
						min.getStid(),validStations,dateTime,StatType.MINIMUM));
				statCatalog.get(StatType.AVERAGE).put(key,new Statistic(total / validStations,key,
						"Mesonet",validStations,dateTime,StatType.AVERAGE));
			}
		}
	}

	/* This method gives the statistic with the given data and stat type. */
	public Statistic getStatistic(StatType statType, DataType dataType) throws NullPointerException {
		return statCatalog.get(statType).get(dataType);
	}
}
