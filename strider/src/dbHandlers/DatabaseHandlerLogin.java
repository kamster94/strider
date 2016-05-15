package dbHandlers;

import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerLogin {
	
	public static DbAccess dataBaseAccess;
	public User user;
	
	public DatabaseHandlerLogin(){
		dataBaseAccess = new DbAccess("Kamster","sql");
	}
	
	public int loginUser(String email, String password){
		int status = dataBaseAccess.getIntFromDb("CALL DBA.fCheckUser(@email = '" + email + "', @haslo = '" + password + "')");
		if (status == 1) {
			user = User.getInstance();
			user.setEmail(email);
			String userName = dataBaseAccess.getSingeStringFromDb("SELECT UserName FROM DBA.UserData WHERE DBA.Email = '" + email + "'", "UserName");
			user.setUserName(userName);
		}
		return status;
	}
}
