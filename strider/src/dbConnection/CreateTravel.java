package dbConnection;

import java.util.Arrays;
import java.util.List;

public class CreateTravel {
	
	private DbAccess dbConnection;
	
	public CreateTravel(){
		dbConnection = new DbAccess("Kamster","sql");
	}
	
	public List<String> fillCountryNames(){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName"));
	}

	public List<String> fillcountryHtmls(){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink"));
	}
}
