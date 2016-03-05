package dbConnection;

import java.util.Arrays;
import java.util.List;

public class CreateUser {
	
	private String userName;
	private String city;
	private String email;
	private int countryId;
	private String password;
	private String currency; 
	private int currencyId;
	private DbAccess dbConnection;
	private int cityId;
	
	public CreateUser(){
		dbConnection = new DbAccess("Kamster","sql");
	}
	
	public void setData(String userName, String city, String email, int countryId, String password, String currency, int currencyId){
		this.userName = userName;
		this.city = city;
		this.email = email;
		this.countryId = countryId;
		this.password = password;
		this.currency = currency;
		this.currencyId = currencyId;
		cityId = dbConnection.getIntFromDb("SELECT IDCity FROM DBA.City WHERE CityName = '" + city + "' AND IDCountry = " + countryId);
		if (cityId == -1) {
			int count = dbConnection.getIntFromDb("SELECT COUNT(*) FROM DBA.City");
			count++;
			dbConnection.pushToDb("INSERT INTO DBA.City (IDCountry, IDCity, CityName) VALUES (" + countryId + ", " + count + ", '" + city + "')", null);
			cityId = count;
		}
	}
	
	public List<String> getCountries(){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName"));	
	}
	
	public List<String> getCurrencies(){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Currency", Arrays.asList("CurrencyShortcut"));	
	}
	
	public boolean verifyDataValidity(){
		if (userName.length() > 32) return false;
		if (!email.contains("@") || email.length() > 32) return false;
		if (password.length() < 6 || password.length() > 32) return false;
		if (city.length() > 32) return false;
		if (currency.length() != 3) return false;
		return true;
		
	}
	
	public boolean checkEmailAvailability(){
		return dbConnection.checkBoolInDb("SELECT DBA.fCheckIfExist(?)", Arrays.asList(email));
	}
	
	public boolean sendToDb(){
		return dbConnection.pushToDb("CALL DBA.pAddToUsers (@email = ?, @haslo = ?, @username = ?, @idcurrency = ?, @idcountry = ?, @idcity = ?)", Arrays.asList(email, password, userName, Integer.toString(currencyId), Integer.toString(countryId), Integer.toString(cityId)));
	}

}
