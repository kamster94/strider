package dbConnection;

import java.util.Arrays;
import java.util.List;

public class CreateUser extends Thread{
	
	private String userName;
	private String city;
	private String email;
	private int countryId;
	private String password;
	private String currency; 
	private int currencyId;
	private int cityId;
	//private DbAccess dbConnection;
	
	public CreateUser(){
		//dbConnection = new DbAccess("Kamster","sql");
	}
	
	public void run(){
	}
	
	public boolean testDb(){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		return dbConnection.testConnection();
		}
	
	public void setData(String userName, String city, int cityId, String email, int countryId, String password, String currency, int currencyId){
		this.userName = userName;
		this.city = city;
		this.cityId = cityId;
		this.email = email;
		this.countryId = countryId;
		this.password = password;
		this.currency = currency;
		this.currencyId = currencyId;
	}
	
	public List<String> getCountries(){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		dbConnection.start();
		return dbConnection.getStringsFromDb("SELECT CountryName FROM DBA.Country", Arrays.asList("CountryName"));	
	}
	
	public List<String> getCities(int countryId){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		dbConnection.start();
		return dbConnection.getStringsFromDb("SELECT CityName FROM DBA.City WHERE IDCountry = " + countryId, Arrays.asList("CityName"));	
	}
	
	public List<String> getCurrencies(){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		dbConnection.start();
		return dbConnection.getStringsFromDb("SELECT CurrencyShortcut FROM DBA.Currency", Arrays.asList("CurrencyShortcut"));	
	}
	
	public int verifyDataValidity(){
		if (userName.length() > 32) return 1;
		if (!email.contains("@") || email.length() > 32) return 2;
		if (password.length() < 6 || password.length() > 32) return 3;
		if (city.length() > 32) return 4;
		if (currency.length() != 3) return 5;
		return 0;
		
	}
	
	public boolean checkEmailAvailability(){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		dbConnection.start();
		return !dbConnection.checkBoolInDb("SELECT DBA.fCheckIfExist(?)", Arrays.asList(email));
	}
	
	public boolean sendToDb(){
		DbAccess dbConnection = new DbAccess("Kamster","sql");
		dbConnection.start();
		return dbConnection.pushToDb("CALL DBA.pAddToUsers (@email = '" + email + "', @haslo = '" + password + "', @username = '" + userName + "', @idcurrency = " + currencyId + ", @idcountry = " + countryId + ", @idcity = " + cityId +")");
	}

}
