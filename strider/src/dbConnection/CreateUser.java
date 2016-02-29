package dbConnection;

import java.util.Arrays;

public class CreateUser {
	
	private String userName;
	private String city;
	private String email;
	private String country;
	private String password;
	private int currency; 
	
	public CreateUser(String userName, String city, String email, String country, String password, int currency){
		//TODO: sprawdzenie, czy dane s¹ poprawne
		this.userName = userName;
		this.city = city;
		this.email = email;
		this.country = country;
		this.password = password;
		this.currency = currency;
	}
	
	public boolean sendToDb(){
		DbAccess dbConnection = new DbAccess("DBA","sql");
		return dbConnection.pushToDb("TODO: sql", Arrays.asList(userName, city, email, country, password, currency));
	}

}
