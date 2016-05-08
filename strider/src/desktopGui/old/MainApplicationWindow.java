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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplicationWindow extends Application
{
	static Stage mystage;
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

	@Override
	public void start(Stage primaryStage)
	{
		mystage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/fxml_mainwindow.fxml"));
		try 
		{
			BorderPane root = (BorderPane)fxmlLoader.load();
            mycontroller = (MainApplicationWindowController) fxmlLoader.getController();
            
            mystage.setScene(new Scene(root, 800, 600));
            mystage.getScene().getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
            mystage.show();
            
            
           //setupComboBoxData();
           AddComboBoxesToAdditionalInformations();
           AddComboBoxesToCreateTravelSetup();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
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
	
	
	
	public void AddComboBoxesToAdditionalInformations()
	{
		cityBox = new ComboBox<String>();	
		countryBox = new ComboBox<String>();
		
		new AutoCompleteComboBoxListener(cityBox);
		new AutoCompleteComboBoxListener(countryBox);
		countryBox.setItems(countryData);
		
		mycontroller.getAdditionalInformationVboxCitybox().getChildren().add(cityBox);
		mycontroller.getAdditionalInformationVboxCountrybox().getChildren().add(countryBox);
	}
	
	public void AddComboBoxesToCreateTravelSetup()
	{
		cityBox = new ComboBox<String>();	
		countryBox = new ComboBox<String>();
		
		new AutoCompleteComboBoxListener(cityBox);
		new AutoCompleteComboBoxListener(countryBox);
		countryBox.setItems(countryData);
		
		mycontroller.getCreateTravelVboxCitybox().getChildren().add(cityBox);
		mycontroller.getCreateTravelVboxCountrybox().getChildren().add(countryBox);
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
