package dbHandlers;

import java.util.Arrays;
import java.util.List;

import Model.NewUser;
import dbConnection.DbAccess;

public class DatabaseHandlerRegister {
	
	private static DbAccess dataBaseAccess;
	private NewUser user;
	
	public void setUserCandidate(NewUser usr)
	{
		user = usr;
	}
	
	public DatabaseHandlerRegister(){
		dataBaseAccess = DbAccess.getInstance();
	}
	
	public int verifyDataValidity(){
		if (user.getUserName().length() > 32) return 1;	//Chappi: Nie sprawdzamy czy dlugosc username jest wieksza od czegos jak w haslo?
		if (!user.getEmail().contains("@") || user.getEmail().length() > 32) return 2;
		if (user.getPassword().length() < 6 || user.getPassword().length() > 32) return 3;
		return 0;
		
	}
	
	public boolean checkEmailAvailability(){
		int check = dataBaseAccess.getIntFromDb("SELECT DBA.fCheckIfExist('" + user.getEmail() + "')");
		if (check == 0) return true;
		else return false;
	}
	
	public int sendToDb(){
		return dataBaseAccess.getIntFromDb("SELECT DBA.fAddToUsers ('" + user.getEmail() + "', '"
				+ user.getPassword() + "', '" + user.getUserName() + "', " + user.getCurrencyId() + ", " + user.getCountryId() + ", " 
				+ user.getCityId() +")");
	}
}
