package desktopGui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

import countryWarnings.AutoCompleteComboBoxListener;
import dbConnection.DbAccess;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApplicationWindow extends Application
{
	static Stage mystage;
	private static MainApplicationWindowController mycontroller;
	 public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	 public static List<String> countryNames;
	 public static List<String> citiesOfCountries;
	 public static ArrayList<String> countryHtmls;
	 public static ObservableList countryData;
	 public static ObservableList cityData;
	 static public DbAccess dataBaseAccess;
	 public static ComboBox cityBox;
	 public static ComboBox countryBox;
	 
	 
	 
	@Override
	public void start(Stage primaryStage)
	{
		Document document;
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		 try {
			 
			 dataBaseAccess = new DbAccess("Artureczek","debil");
			 countryNames = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName")));
			 countryHtmls = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink")));
			 countryData = FXCollections.observableArrayList(); //Do sugestii wyszukiwania krajow
				
				for(int j=0; j<countryNames.size(); j++){
				countryData.add(countryNames.get(j));								
				}
				
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		
		try 
		{
			mystage = primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/TravelAdvisorMainWindow.fxml"));
            BorderPane root = (BorderPane)fxmlLoader.load();
            mycontroller = (MainApplicationWindowController) fxmlLoader.getController();

            mystage.setScene(new Scene(root, 800, 600));
            mystage.getScene().getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
            mystage.show();
            
			cityBox = new ComboBox();	
			countryBox = new ComboBox();
			
			new AutoCompleteComboBoxListener(cityBox);
			new AutoCompleteComboBoxListener(countryBox);
			countryBox.setItems(countryData);
		
			mycontroller.getVboxCitybox().getChildren().add(cityBox);
			mycontroller.getVboxCountrybox().getChildren().add(countryBox);
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
