package desktopGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

import countryWarnings.AutoCompleteComboBoxListener;
import dbConnection.DbAccess;
import desktopGui.old.MainApplicationWindowController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowMain extends Application
{
	static Stage mystage;
	static StackPane root;
	
	private static MainApplicationWindowController mycontroller;
	public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	public static List<String> countryNames;
	public static List<String> citiesOfCountries;
	public static ArrayList<String> countryHtmls;
	public static ObservableList<String> countryData;
	public static ObservableList<String> cityData;
	public static ComboBox<String> cityBox;
	public static ComboBox<String> countryBox;
	static public DbAccess dataBaseAccess;
	
    public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "fxml/fxml_main.fxml"; 
	
	public static final String ADDINFO_SCREEN = "additionalinfo";
	public static final String ADDINFO_SCREEN_FXML = "fxml/fxml_additionalinformations.fxml";

	public static final String NEWTRAVEL_1 = "newtravel1";
	public static final String NEWTRAVEL_1_FXML = "fxml/fxml_createtravel_first.fxml";
	
	public static final String NEWTRAVEL_2 = "newtravel2";
	public static final String NEWTRAVEL_2_FXML = "fxml/fxml_createtravel_second.fxml";
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.ADDINFO_SCREEN, WindowMain.ADDINFO_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_1, WindowMain.NEWTRAVEL_1_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_2, WindowMain.NEWTRAVEL_2_FXML);
		
		mainContainer.setScreen(WindowMain.MAIN_SCREEN);

		root = new StackPane();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
		mystage = primaryStage;
		
		//Uncumment this if running without database
		setupComboBoxData();
	}
	
	public void setupComboBoxData()
	{
		Document document;
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		
		try
		{
			dataBaseAccess = new DbAccess("Artureczek","debil");
			countryNames = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName")));
			countryHtmls = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink")));
			countryData = FXCollections.observableArrayList(); //Do sugestii wyszukiwania krajow
		
			for(int j=0; j<countryNames.size(); j++)
			{
				countryData.add(countryNames.get(j));								
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ComboBox<String> getCityBox()
	{
		cityBox = new ComboBox<String>();	

		new AutoCompleteComboBoxListener(cityBox);
		
		return cityBox;
	}
	
	public static ComboBox<String> getCountryBox()
	{
		countryBox = new ComboBox<String>();
		
		new AutoCompleteComboBoxListener(countryBox);
		countryBox.setItems(countryData);
		
		return countryBox;
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
