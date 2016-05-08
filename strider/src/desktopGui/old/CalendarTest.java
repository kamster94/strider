package desktopGui.old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.nodes.Document;
import countryWarnings.AutoCompleteComboBoxListener;
import dbConnection.DbAccess;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalendarTest extends Application
{
	static Stage mystage;
	private static CalendarController mycontroller;


	@Override
	public void start(Stage primaryStage)
	{

		
			mystage = primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calendar.fxml"));
		try 
		{
			
			VBox root = (VBox)fxmlLoader.load();
            mycontroller = (CalendarController) fxmlLoader.getController();

            
            mystage.setScene(new Scene(root, 800, 600));
            mystage.getScene().getStylesheets().add(getClass().getResource("ta_mainwindow.css").toExternalForm());
            mystage.show();

            
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	

	
	public static void closeWindow()
	{
		mystage.close();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
