package dbConnection;

import java.util.Arrays;

public class CreateUser {
	
	private String userName;
	private String city;
	private String email;
	private String country;
	private String password;
	private String currency; 
	
	public CreateUser(String userName, String city, String email, String country, String password, String currency){
		this.userName = userName;
		this.city = city;
		this.email = email;
		this.country = country;
		this.password = password;
		this.currency = currency;
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
		DbAccess dbConnection = new DbAccess("DBA","sql");
		return dbConnection.pushToDb("TODO: sql", Arrays.asList(email));
	}
	
	public boolean sendToDb(){
		DbAccess dbConnection = new DbAccess("DBA","sql");
		return dbConnection.pushToDb("TODO: sql", Arrays.asList(userName, city, email, country, password, currency));
	}

}
