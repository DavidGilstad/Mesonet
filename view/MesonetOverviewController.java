package dg.CS2334.Mesonet.view;

import java.io.IOException;

import javax.swing.JOptionPane;

import dg.CS2334.Mesonet.DataMap.Statistic;
import dg.CS2334.Mesonet.MainApp;
import dg.CS2334.Mesonet.DataMap.DataMap;
import dg.CS2334.Mesonet.DataMap.DataType;
import dg.CS2334.Mesonet.DataMap.StatType;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/* Class for controlling the calculating functions of the mesonet application. */
public class MesonetOverviewController {
    @FXML private RadioButton Maximum;
    @FXML private RadioButton Minimum;
    @FXML private RadioButton Average;
    @FXML private ToggleGroup StatButtons;

    @FXML private CheckBox tair;
    @FXML private CheckBox ta9m;
    @FXML private CheckBox srad;
    @FXML private CheckBox wspd;
    @FXML private CheckBox pres;
    @FXML private CheckBox rain;

    @FXML private TableView<Statistic> statTable;
    @FXML private TableColumn<Statistic, String> stationColumn;
    @FXML private TableColumn<Statistic, String> parameterColumn;
    @FXML private TableColumn<Statistic, String> statisticsColumn;
    @FXML private TableColumn<Statistic, String> valueColumn;
    @FXML private TableColumn<Statistic, String> reportingColumn;
    @FXML private TableColumn<Statistic, String> dateColumn;

    private MainApp mainApp;

    public MesonetOverviewController() {}

    @FXML private void initialize() {
        stationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStid()));
        parameterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        statisticsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatType().name()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.1f %s", cellData.getValue().getValue(), cellData.getValue().getType().getUnits())));
        reportingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getNumStations())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAndTime()));
    }

    public void setMainApp(MainApp main) { mainApp = main; }

    @FXML private void handleExit() { System.exit(0); }

    @FXML private void handleCalculate() {
        try {
            DataMap data = new DataMap(mainApp.getFilename(), "Resources/Data");

            if (tair.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.TAIR));
            if (ta9m.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.TA9M));
            if (srad.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.SRAD));
            if (wspd.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.WSPD));
            if (pres.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.PRES));
            if (rain.isSelected()) statTable.getItems().add(data.getStatistic(this.getStatType(), DataType.RAIN));
            
            tair.setSelected(false); ta9m.setSelected(false); srad.setSelected(false);
            wspd.setSelected(false); pres.setSelected(false); rain.setSelected(false);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "We cannot find the given file.", "File not found",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(null, "Select a valid file, statistic, and parameter", "Empty Fields", JOptionPane.ERROR_MESSAGE);
        }
    }

    private StatType getStatType() {
        if (Maximum.isSelected()) return StatType.MAXIMUM;
        else if (Average.isSelected()) return StatType.AVERAGE;
        else if (Minimum.isSelected()) return StatType.MINIMUM;
        else return null;
    }
}
