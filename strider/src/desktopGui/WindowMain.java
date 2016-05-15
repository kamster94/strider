package desktopGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.nodes.Document;
import countryWarnings.AutoCompleteComboBoxListener;
import dbConnection.DbAccess;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowMain extends Application
{
	static Stage mystage;
	static StackPane root;

	public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	public static List<String> countryNames;
	public static List<String> citiesOfCountries;
	public static ArrayList<String> countryHtmls;
	public static ObservableList<String> countryData;
	public static ObservableList<String> cityData;
	public static ComboBox<String> cityBox;
	public static ComboBox<String> countryBox;
	static public DbAccess dataBaseAccess;
	
    public static final String SPLASH_SCREEN = "splash";
	public static final String SPLASH_SCREEN_FXML = "fxml/fxml_splashscreen.fxml"; 
	
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
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//To jest ca�y pierdolony clue tego �eby to chujstwo dzia�a�o
		//Je�li te trzy linijki wsadzimy za kodem z main container to zanim sie wykonaj�, controlery pod-okien ju� b�d� chcia�y 
		//te pierdolone podpowiadajki a to daje nullchujwdupeexception
		
		setupComboBoxData();
		cityBox = getCityBox();
		countryBox = getCountryBox();
		
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(WindowMain.SPLASH_SCREEN, WindowMain.SPLASH_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.ADDINFO_SCREEN, WindowMain.ADDINFO_SCREEN_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_1, WindowMain.NEWTRAVEL_1_FXML);
		mainContainer.loadScreen(WindowMain.NEWTRAVEL_2, WindowMain.NEWTRAVEL_2_FXML);
		mainContainer.loadScreen(WindowMain.TRAVEL_SUMMARY, WindowMain.TRAVEL_SUMMARY_FXML);
		
		mainContainer.setScreen(WindowMain.SPLASH_SCREEN);

		root = new StackPane();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
		primaryStage.show(); 
		
		mystage = primaryStage;
	}
	
	public void setupComboBoxData()
	{
		Document document;
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		
		try
		{
			dataBaseAccess = new DbAccess("adriank","debil");
			if(dataBaseAccess.testConnection() == false)
			{
				System.out.println("Can't connect to online database :<");
			}
			else
			{
				System.out.println("Connection to online database succesfull!");
			}
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
		ComboBox<String> cityBox = new ComboBox<String>();	

		new AutoCompleteComboBoxListener<String>(cityBox);
		
		return cityBox;
	}
	
	public static ComboBox<String> getCountryBox()
	{
		ComboBox<String> countryBox = new ComboBox<String>();
		
		new AutoCompleteComboBoxListener<String>(countryBox);
		countryBox.setItems(countryData);
		
		return countryBox;
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
