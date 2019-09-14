package dg.CS2334.Mesonet.view;

import java.io.File;

import dg.CS2334.Mesonet.MainApp;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class RootLayoutController {
    private String filename;
    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }
    
    @FXML private void handleOpenFile() {
        FileChooser file = new FileChooser();
        file.setTitle("Open File");
        try {
            file.setInitialDirectory(new File("C:\\Users\\david\\eclipse-workspace\\MesonetFX\\Data"));
            filename = file.showOpenDialog(mainApp.getPrimaryStage()).getName();
            mainApp.setFilename(filename);
        } catch (NullPointerException e1) {}
    }
    
    @FXML private void handleExit() { System.exit(0); }
}