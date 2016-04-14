package desktopGui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class DraggableCalendarTest extends Application 
{
	static Stage mystage;
		
		@Override
		public void start(Stage primaryStage)
		{
			try 
			{
				mystage = primaryStage;
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("fxml/CreateNewTravelSummary.fxml"));
				Scene scene = new Scene(root,600,400);
				scene.getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Create new account");
				primaryStage.setResizable(false);
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

