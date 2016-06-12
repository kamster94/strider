package dbHandlers;

import java.util.Arrays;

import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerLogin 
{
	private static DatabaseHandlerLogin myinst;
	
	private DatabaseHandlerLogin() { }
	
	public static DatabaseHandlerLogin getInstance()
	{
		if(myinst == null)myinst = new DatabaseHandlerLogin();
		return myinst;
	}
	
	public int verifyDataValidity(String email, String password)
	{
		if (!email.contains("@") || email.length() > 32) return 1;
		if (password.length() < 6 || password.length() > 32) return 2;
		return 0;
	}
	
	public boolean checkEmailAvailability(String email)
	{
		return !DbAccess.getInstance().checkBoolInDb("SELECT DBA.fCheckIfExist(?)", Arrays.asList(email));
	}
	
	public boolean updateUserPreferences(int countryid, int cityid, int currencyid)
	{
		boolean status = DbAccess.getInstance().pushToDb("UPDATE DBA.PersonalUsersData SET IDCountry = " + countryid + ", IDCity = " + cityid + ", IDCurrency = " + currencyid + " WHERE IDUser = " + User.getInstance().getId());
		return status;
	}
	
	public int loginUser(String email, String password)
	{
		int status = DbAccess.getInstance().getIntFromDb("SELECT DBA.fCheckUser('" + email + "', '" + password + "')");
		
		if (status == 1) 
		{
			User.getInstance().setEmail(email);
			String userName = DbAccess.getInstance().getSingeStringFromDb("SELECT UserName FROM DBA.UserData WHERE UserData.Email = '" + email + "'", "UserName");
			int userId = DbAccess.getInstance().getIntFromDb("SELECT IDUser FROM DBA.UserData WHERE UserData.Email = '" + email + "'");
			int currencyId = DbAccess.getInstance().getIntFromDb("SELECT IDCurrency FROM DBA.PersonalUsersData WHERE PersonalUsersData.IDUser = " + userId);
			int cityId = DbAccess.getInstance().getIntFromDb("SELECT IDCity FROM DBA.PersonalUsersData WHERE PersonalUsersData.IDUser = " + userId);
			int countryId = DbAccess.getInstance().getIntFromDb("SELECT IDCountry FROM DBA.PersonalUsersData WHERE PersonalUsersData.IDUser = " + userId);
			
			User.getInstance().setId(userId);
			User.getInstance().setUserName(userName);
			User.getInstance().setCurrencyId(currencyId);
			User.getInstance().setCityId(cityId);
			User.getInstance().setCountryId(countryId);
		}
		return status;
	}
}
