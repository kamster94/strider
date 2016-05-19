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
	
	public List<String> getCities(int countryId){
		dbConnection.start();
		return dbConnection.getStringsFromDb("SELECT CityName FROM DBA.City WHERE IDCountry = " + countryId, Arrays.asList("CityName"));	
	}
	
	public List<String> getCurrencies(){
		dbConnection.start();
		return dbConnection.getStringsFromDb("SELECT CurrencyShortcut FROM DBA.Currency", Arrays.asList("CurrencyShortcut"));	
	}
}
