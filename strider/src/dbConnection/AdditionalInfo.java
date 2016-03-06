package dbConnection;

import java.util.Arrays;
import java.util.List;

public class AdditionalInfo {

	private DbAccess dbConnection;
	
	public AdditionalInfo(){
		dbConnection = new DbAccess("Kamster","sql");
	}
	
	public List<String> getCountries(){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName"));	
	}
	
	public List<String> getCurrency(int selectedCountry){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Currency WHERE IDCountry = " + selectedCountry, Arrays.asList("CurrencyName"));	
	}
	
	public List<String> getCurrencyShort(int selectedCountry){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Currency WHERE IDCountry = " + selectedCountry, Arrays.asList("CurrencyShortcut"));	
	}
}
