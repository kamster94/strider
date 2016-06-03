package dbHandlers;

import java.util.Arrays;
import java.util.List;

import dbConnection.DbAccess;

public class DatabaseHandlerCommon {
	
	private DbAccess dbConnection;
	public static DatabaseHandlerCommon self;
	
	private DatabaseHandlerCommon(){
		dbConnection = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerCommon getInstance(){
		if (self == null) self = new DatabaseHandlerCommon();
		return self;
	}
	
	public List<String> getCountriesList(){
		return dbConnection.getStringsFromDb("SELECT CountryName FROM DBA.Country", Arrays.asList("CountryName"));
	}
	
	public List<String> getHotelsList()
	{
		return dbConnection.getStringsFromDb("SELECT HotelName from DBA.Hotel", Arrays.asList("HotelName"));
	}
	
	public int getCountryId(String name){
		return dbConnection.getIntFromDb("SELECT IDCountry FROM DBA.Country WHERE CountryName = '" + name + "'");
	}
	
	public List<String> getCities(int countryId){
		return dbConnection.getStringsFromDb("SELECT CityName FROM DBA.City WHERE IDCountry = " + countryId, Arrays.asList("CityName"));	
	}
	
	public int getCityId(String name){
		return dbConnection.getIntFromDb("SELECT IDCity FROM DBA.City WHERE CityName = '" + name + "'");
	}
	
	public List<String> getCurrencies(){
		return dbConnection.getStringsFromDb("SELECT CurrencyShortcut FROM DBA.Currency ORDER BY CurrencyShortcut", Arrays.asList("CurrencyShortcut"));	
	}
	
	public int getCurrencyId(String shortcut){
		return dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.Currency WHERE CurrencyShortcut = '" + shortcut + "'");
	}
	
	public String getCurrencyNameForGivenCountry(int countryId)
	{
		int currencyId = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.CountrysCurrency WHERE IDCountry = '" + countryId + "'");
		return dbConnection.getSingeStringFromDb("SELECT CurrencyShortcut FROM DBA.Currency WHERE IDCurrency = " + currencyId, "CurrencyShortcut");
	}
}
