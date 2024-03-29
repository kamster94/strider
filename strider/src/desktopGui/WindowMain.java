package desktopGui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import com.lynden.gmapsfx.javascript.object.LatLong;
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
import javafx.scene.image.Image;
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
	
    public static final String SPLASH_SCREEN_OFFLINE = "splash_offline";
	public static final String SPLASH_SCREEN_OFFLINE_FXML = "fxml/fxml_splashscreen_offline.fxml"; 
	
    public static final String CREATEACCOUNT_SCREEN = "createnewaccount";
	public static final String CREATEACCOUNT_SCREEN_FXML = "fxml/fxml_createaccount.fxml"; 
	
    public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "fxml/fxml_main.fxml"; 
	
	public static final String ADDINFO_SCREEN = "additionalinfo";
	public static final String ADDINFO_SCREEN_FXML = "fxml/fxml_additionalinformations.fxml";

	public static final String NEWTRAVELFIRST = "newtravelfirst";
	public static final String NEWTRAVELFIRST_FXML = "fxml/fxml_createtravelfirst.fxml";
	
	public static final String NEWTRAVELSECOND = "newtravelsecond";
	public static final String NEWTRAVELSECOND_FXML = "fxml/fxml_createtravelsecond.fxml";
	
	public static final String TRAVEL_SUMMARY = "travelsummary";
	public static final String TRAVEL_SUMMARY_FXML = "fxml/fxml_travelsummary.fxml";
	
	public static final String RATE_HOTEL = "ratehotel";
	public static final String RATE_HOTEL_FXML = "fxml/fxml_ratehotel.fxml";
	
	public static final String OPTIONS = "options";
	public static final String OPTIONS_FXML = "fxml/fxml_changeuseroptions.fxml";
	
	public static final String TRAVEL_HISTORY = "travelhistory";
	public static final String TRAVEL_HISTORY_FXML = "fxml/fxml_travelhistory.fxml";
	
	public static Logger guiLog = Logger.getLogger("guiLog"); 
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//Pozwalam sobie doda� log by �atwiej odczytywa� b��dy i komunikaty
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
		
		//Nie ma sensu �adowa� tego �eby u�ytkownik mia� krzaka, bo i tak sie nie zaloguje skoro nie ma po��czenia z baz� ;3
		//Ju� jest sens ;* k.
		//dzi�ki <3 a.
		
		if(DbAccess.getInstance().testConnection() && DbAccess.getInstance().testInternetConnection())
		{
			mainContainer.loadScreenAndSet(WindowMain.SPLASH_SCREEN, WindowMain.SPLASH_SCREEN_FXML);
		}
		else
		{
			mainContainer.loadScreenAndSet(WindowMain.SPLASH_SCREEN_OFFLINE, WindowMain.SPLASH_SCREEN_OFFLINE_FXML);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Brak po��czenia");
			alert.setHeaderText(null);
			alert.setContentText("Wyst�pi� problem podczas pr�by po��czenia z internetem lub baz� danych.");
			alert.showAndWait();
		}
		
		root = new StackPane();

		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);

		primaryStage.setTitle("Travel Advisor");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("textures/app_icon.png")));
		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show(); 
		
		mystage = primaryStage;
	}
	
	public static void refreshWindowContents()
	{
		mystage.getScene().getWindow().sizeToScene();
	}

	public static LatLong getDefaultCoordinations()
	{
		return new LatLong(52.232222, 21.008333);
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
		
		//IRR, KRW, COP, IDR-> format wyk�adniczy lel
		
		
		
		currencyBox.getItems().addAll("ARS","BRL","CLP","CZK","HUF","IRR","KRW","MUR","NOK","PLN","SAR","SEK","TTD","USD","DKK","ISK","ILS","KWD","MXN","OMR","QAR","SGD","CHF","AUD","BND","BHD","COP","EUR","INR","JPY","LYD","NPR","PKR","ZAR","TWD","AED","BWP","CAD","IDR","KZT","MYR","NZD","PHP","RUB","LKR","THB","GBP");
		//currencyBox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCurrencies());
		return currencyBox;
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
