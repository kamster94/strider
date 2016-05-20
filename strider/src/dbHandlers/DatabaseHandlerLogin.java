package dbHandlers;

import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerLogin {
	
	public static DbAccess dbConnection;
	public User user;
	
	public DatabaseHandlerLogin(){
		dbConnection = DbAccess.getInstance();
	}
	
	
	public int loginUser(String email, String password){
		int status = dbConnection.getIntFromDb("SELECT DBA.fCheckUser('" + email + "', '" + password + "')");
		
		if (status == 0) {
			user = User.getInstance();
			user.setEmail(email);
			String userName = dbConnection.getSingeStringFromDb("SELECT UserName FROM DBA.UserData WHERE UserData.Email = '" + email + "'", "UserName");
			int userId = dbConnection.getIntFromDb("SELECT IDUser FROM DBA.UserData WHERE UserData.Email = '" + email + "'");
			int currencyId = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.UserData WHERE UserData.Email = '" + email + "'");;
			int cityId = dbConnection.getIntFromDb("SELECT IDCity FROM DBA.UserData WHERE UserData.Email = '" + email + "'");;
			int countryId = dbConnection.getIntFromDb("SELECT IDCountry FROM DBA.UserData WHERE UserData.Email = '" + email + "'");;
			user.setId(userId);
			user.setUserName(userName);
			user.setCurrencyId(currencyId);
			user.setCityId(cityId);
			user.setCountryId(countryId);
		}
		return status;
	}
}
