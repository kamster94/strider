package desktopGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplicationWindow extends Application
{
	static Stage mystage;
	
	@Override
	public void start(Stage primaryStage)
	{
		try 
		{
			mystage = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("fxml/TravelAdvisorMainWindow.fxml"));
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Travel Advisor");
			primaryStage.show();
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
