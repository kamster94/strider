package desktopGui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import countryWarnings.AutoCompleteComboBoxListener;
import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerCommon;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowMain extends Application
{
	static Stage mystage;
	static StackPane root;
	
    public static final String SPLASH_SCREEN = "splash";
	public static final String SPLASH_SCREEN_FXML = "fxml/fxml_splashscreen.fxml"; 
	
    public static final String CREATEACCOUNT_SCREEN = "createnewaccount";
	public static final String CREATEACCOUNT_SCREEN_FXML = "fxml/fxml_createaccount.fxml"; 
	
    public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "fxml/fxml_main.fxml"; 
	
	public static final String ADDINFO_SCREEN = "additionalinfo";
	public static final String ADDINFO_SCREEN_FXML = "fxml/fxml_additionalinformations.fxml";

	public static final String NEWTRAVEL_1 = "newtravel1";
	public static final String NEWTRAVEL_1_FXML = "fxml/fxml_createtravel_first.fxml";
	
	public static final String NEWTRAVEL_2 = "newtravel2";
	public static final String NEWTRAVEL_2_FXML = "fxml/fxml_createtravel_second.fxml";
	
	public static final String TRAVEL_SUMMARY = "travelsummary";
	public static final String TRAVEL_SUMMARY_FXML = "fxml/fxml_travelsummary.fxml";
	
	public static Logger guiLog = Logger.getLogger("guiLog"); 
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//Pozwalam sobie dodaæ log by ³atwiej odczytywaæ b³êdy i komunikaty
		//k.
		try 
		{  
			FileHandler fh;  
	        fh = new FileHandler("./logs/guiLog.log", true);  
	        guiLog.addHandler(fh);
	        guiLog.setUseParentHandlers(false);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } 
		catch (SecurityException e) 
		{  
	        e.printStackTrace();  
	    } 
		catch (IOException e) 
		{  
	        e.printStackTrace();  
	    }
		
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(WindowMain.SPLASH_SCREEN, WindowMain.SPLASH_SCREEN_FXML);
		mainContainer.setScreen(WindowMain.SPLASH_SCREEN);
		
		//Nie ma sensu ³adowaæ tego ¿eby u¿ytkownik mia³ krzaka, bo i tak sie nie zaloguje skoro nie ma po³¹czenia z baz¹ ;3
		if(DbAccess.getInstance().testConnection() == true)
		{

		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Connection error");
			alert.setHeaderText(null);
			alert.setContentText("Couldn't establish connection to the database.");
			alert.showAndWait();
		}
		
		//Wrzuciæ to powy¿ej do ifa ¿eby nie ³adowa³o wszystkiego jeœli nie ma po³¹czenia z baz¹
		mainContainer.loadScreen(WindowMain.CREATEACCOUNT_SCREEN, WindowMain.CREATEACCOUNT_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.ADDINFO_SCREEN, WindowMain.ADDINFO_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_1, WindowMain.NEWTRAVEL_1_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_2, WindowMain.NEWTRAVEL_2_FXML);
		mainContainer.loadScreen(WindowMain.TRAVEL_SUMMARY, WindowMain.TRAVEL_SUMMARY_FXML);
		
		root = new StackPane();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
		primaryStage.show(); 
		
		mystage = primaryStage;
	}
	
	public static ComboBox<String> getCityBox()
	{
		ComboBox<String> cityBox = new ComboBox<String>();	
		new AutoCompleteComboBoxListener<String>(cityBox);
		return cityBox;
	}
	
	public static ComboBox<String> getCountryBox()
	{
		ComboBox<String> countryBox = new ComboBox<String>();
		new AutoCompleteComboBoxListener<String>(countryBox);
		countryBox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCountriesList());
		return countryBox;
	}
	
	public static ComboBox<String> getCurrencyBox()
	{
		ComboBox<String> currencyBox = new ComboBox<String>();
		new AutoCompleteComboBoxListener<String>(currencyBox);
		currencyBox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCurrencies());
		return currencyBox;
	}

	
	public static void resizeWindowToContents()
	{
		mystage.getScene().getWindow().sizeToScene();
	}
	
	public static void closeWindow()
	{
		FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.play();
		
		ft.setOnFinished(new EventHandler<ActionEvent>() 
		{
			@Override
		    public void handle(ActionEvent event)
			{
				mystage.close();
		    }
		});	
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
