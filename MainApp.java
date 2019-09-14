package dg.CS2334.Mesonet;

import java.io.IOException;
import dg.CS2334.Mesonet.view.MesonetOverviewController;
import dg.CS2334.Mesonet.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 * This application is a weather app that reads a .mdf mesonet weather file and
 * presents statistics on the weather data inside the file.
 * 
 * 			@author David Gilstad
 * 		  @version January 9, 2019
 */
public class MainApp extends Application {
	private String filename;
	private Stage primaryStage;
	private BorderPane rootLayout;

	public void setFilename(String name) { filename = name; }
	
	public String getFilename() { return filename; }
	
	public Stage getPrimaryStage() { return primaryStage; }

	/* Method for creating the application. */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mesonet");

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/Weather.png"));

		initRootLayout();
		showMesonetOverview();
	}

	/* Creates the root layout for the application. */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Creates the calculation buttons and table for presenting statistics. */
	public void showMesonetOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MesonetOverview.fxml"));
			AnchorPane MesonetOverview = (AnchorPane)loader.load();

			rootLayout.setCenter(MesonetOverview);

			MesonetOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Main method that runs the application */
	public static void main(String[] args) { launch(args); }
}
