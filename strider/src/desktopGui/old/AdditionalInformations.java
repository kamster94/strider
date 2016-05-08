package desktopGui.old;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdditionalInformations
{
	static Stage mystage;
	private static AdditionalInformationsController mycontroller;
	public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	public static List<String> countryNames;
	public static List<String> citiesOfCountries;
	public static ArrayList<String> countryHtmls;
	public static ObservableList<String> countryData;
	public static ObservableList<String> cityData;
	public static ComboBox<String> cityBox;
	public static ComboBox<String> countryBox;
	static public DbAccess dataBaseAccess;

	private static AdditionalInformations instnc;
	
	private AdditionalInformations()
	{
		System.out.println("Cumstructor");
		Document document;
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		
		try
		{
			//dataBaseAccess = new DbAccess("Artureczek","debil");
			//countryNames = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName")));
			//countryHtmls = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink")));
			countryData = FXCollections.observableArrayList(); //Do sugestii wyszukiwania krajow
			
			
			//for(int j=0; j<countryNames.size(); j++)
			//{
				//countryData.add(countryNames.get(j));								
			//}
				
		} 
		catch (Exception e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		try 
		{

            
			cityBox = new ComboBox<String>();	
			countryBox = new ComboBox<String>();
			
			new AutoCompleteComboBoxListener(cityBox);
			new AutoCompleteComboBoxListener(countryBox);
			countryBox.setItems(countryData);
		
			
			
			//mycontroller.getVboxCitybox().getChildren().add(cityBox);
			//mycontroller.getVboxCountrybox().getChildren().add(countryBox);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static AdditionalInformations getInstance()
	{
		if(instnc == null)
		{
			instnc = new AdditionalInformations();
			System.out.println("Creating instance");
		}
		System.out.println("Returning instance");
		return instnc;
	}


}
