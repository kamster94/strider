package countryWarnings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dbConnection.DbAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CitiesList {

	public static ObservableList listOfCities;
	private static DbAccess dataBaseConnection;
	private static String sqlPart1 = "SELECT * FROM DBA.City C inner join DBA.Country Cr on C.IDCountry = Cr.IDCountry "
	        	+ "where Cr.CountryName = '";
	private static String sqlPart2 = "' order by 1 asc";
	
	

	public static ObservableList getListOfCities()
	{
		return listOfCities;
	}

	public static void setListOfCities(String cityName) 
	{
		dataBaseConnection = DbAccess.getInstance();
		listOfCities = dataBaseConnection.getStringsFromDbAsObservableList(sqlPart1 + cityName + sqlPart2, "CityName");		
	}
	

	
}
