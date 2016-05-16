package dbHandlers;

import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerLogin {
	
	public static DbAccess dataBaseAccess;
	public User user;
	
	public DatabaseHandlerLogin(){
		dataBaseAccess = DbAccess.getInstance();
	}
	
	
	public int loginUser(String email, String password){
		int status = dataBaseAccess.getIntFromDb("SELECT DBA.fCheckUser('" + email + "', '" + password + "')");
		
		System.out.println(status);
		System.out.println("id" + User.getInstance().getId());
		if (status == 0) {
			user = User.getInstance();
			user.setEmail(email);
			String userName = DbAccess.getInstance().getSingeStringFromDb("SELECT UserName FROM DBA.UserData WHERE UserData.Email = '" + email + "'", "UserName");
			int userId = DbAccess.getInstance().getIntFromDb("SELECT IDUser FROM DBA.UserData WHERE UserData.Email = '" + email + "'");
			user.setId(userId);
			
			user.setUserName(userName);
		}
		return status;
	}
}
