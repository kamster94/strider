package countryWarnings;


import java.util.ArrayList;
import java.util.List;
import dbConnection.DbAccess;
import desktopGui.ScreensController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;




public class Main extends Application{

	 static StackPane root;
	 static Stage mystage;

	 public static Stage mainStage;
	 
	 public static final String WARNINGS = "warnings";
	 public static final String WARNINGS_FXML = "/countryWarnings/fxmlCR/fxml_Sample.fxml"; 
		
	 public static final String MAP = "map";
     public static final String MAP_FXML = "/countryWarnings/fxmlCR/fxml_map.fxml"; 
	
	 
	 
	@Override	
	public void start(Stage primaryStage) {
		
		mainStage = primaryStage;		
	    ScreensController mainContainer = new ScreensController();
	    
	    mainContainer.loadScreen(Main.WARNINGS, Main.WARNINGS_FXML);
		mainContainer.loadScreen(Main.MAP, Main.MAP_FXML);		
		mainContainer.setScreen(Main.WARNINGS);
						
		root = new StackPane();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add(getClass().getResource("fxmlCR/application.css").toExternalForm());
		primaryStage.show(); 
				
		mystage = primaryStage;
	
	}
	
	
	public static void main(String[] args) {
	launch(args);
	}


}
