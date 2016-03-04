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
	}
	
	public List<String> getCountries(){
		return dbConnection.getStringsFromDb("TODO: sql", Arrays.asList(email));	
	}
	
	public List<String> getCurrencies(){
		return dbConnection.getStringsFromDb("TODO: sql", Arrays.asList(email));	
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
		return dbConnection.pushToDb("TODO: sql", Arrays.asList(email));
	}
	
	public boolean sendToDb(){
		return dbConnection.pushToDb("TODO: sql", Arrays.asList(userName, password, city, email, Integer.toString(countryId), Integer.toString(currencyId)));
	}

}
