package dbHandlers;

import java.util.Arrays;
import java.util.List;

import Model.NewUser;
import dbConnection.DbAccess;

public class DatabaseHandlerRegister {
	
	public static DbAccess dataBaseAccess;
	public NewUser user;
	
	public DatabaseHandlerRegister(){
		dataBaseAccess = DbAccess.getInstance();
	}

	public List<String> getCountries(){
		return dataBaseAccess.getStringsFromDb("SELECT CountryName FROM DBA.Country", Arrays.asList("CountryName"));	
	}
	
	public List<String> getCities(int countryId){
		return dataBaseAccess.getStringsFromDb("SELECT CityName FROM DBA.City WHERE IDCountry = " + countryId, Arrays.asList("CityName"));	
	}
	
	public List<String> getCurrencies(){
		return dataBaseAccess.getStringsFromDb("SELECT CurrencyShortcut FROM DBA.Currency", Arrays.asList("CurrencyShortcut"));	
	}
	
	public int verifyDataValidity(){
		if (user.getUserName().length() > 32) return 1;	//Chappi: Nie sprawdzamy czy dlugosc username jest wieksza od czegos jak w haslo?
		if (!user.getEmail().contains("@") || user.getEmail().length() > 32) return 2;
		if (user.getPassword().length() < 6 || user.getPassword().length() > 32) return 3;
		if (user.getCity().length() > 32) return 4;		//Chappi: Ditto
		if (user.getCurrency().length() != 3) return 5;
		return 0;
		
	}
	
	public boolean checkEmailAvailability(){
		return !dataBaseAccess.checkBoolInDb("SELECT DBA.fCheckIfExist(?)", Arrays.asList(user.getEmail()));
	}
	
	public int sendToDb(){
		return dataBaseAccess.getIntFromDb("SELECT DBA.fAddToUsers ('" + user.getEmail() + "', '"
				+ user.getPassword() + "', '" + user.getUserName() + "', " + user.getCurrencyId() + ", " + user.getCountryId() + ", " 
				+ user.getCityId() +")");
	}
}
