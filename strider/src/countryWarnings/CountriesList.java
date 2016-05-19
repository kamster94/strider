package countryWarnings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dbConnection.DbAccess;
import javafx.collections.ObservableList;

public class CountriesList {

	public static ObservableList countryNames;
	public static ObservableList countryHtmls;
	private static DbAccess dataBaseConnection;
	private static String sqlString = "SELECT * FROM DBA.Country";
	
	
	public static void setCountryNames() 
	{
		dataBaseConnection = DbAccess.getInstance();
		countryNames = dataBaseConnection.getStringsFromDbAsObservableList(sqlString, "CountryName");		
	}
	
	public static void setCountryHtmls() 
	{
		dataBaseConnection = DbAccess.getInstance();
		countryHtmls = dataBaseConnection.getStringsFromDbAsObservableList(sqlString, "MSZlink");		
	}
	
	
	public static ObservableList getCountryNamesList()
	{		
		return countryNames;			
	}
	
	public static ObservableList getCountryHtmlsList()
	{		
		return countryHtmls;			
	}
	
	public static Object getCountryNamesPosition(int i)
	{
		return countryNames.get(i);
	}
	
	public static Object getCountryHtmlsPosition(int i)
	{
		return countryHtmls.get(i);
	}
	
}
