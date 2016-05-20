package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import Model.Travel;
import Model.User;
import dbConnection.DbAccess;
import Model.AttractionDetails;
import trpClasses.Trip;

public class DatabaseHandlerTripAdder 
{
	private Travel curtravel;
	public static DbAccess dataBaseAccess;
	private static String Countrysql = "Select IDCountry From Country where CountryName =";
	private static String Citysql1 = "Select C.IDCity From City where CityName =";
	private static String Citysql2 = "and IDCountry =";
	
	public DatabaseHandlerTripAdder(){
		dataBaseAccess = DbAccess.getInstance();
	}
	
	public void setTravel(Travel trav)
	{
		curtravel = trav;
	}

	public int pushTravelToDatabase()
	{
		int tripID = 0;
			
		Date beginDate = Date.valueOf(curtravel.getStartDate());
		Date endDate = Date.valueOf(curtravel.getEndDate());

		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddTrip(" + User.getInstance().getId() +
		"," + curtravel.getCountryDestinationId() + "," + curtravel.getCityDestinationId() +
		"," + curtravel.getCountryOriginId() + "," + curtravel.getCityOriginId() +
		",'" + curtravel.getName() + "','" + Date.valueOf(curtravel.getStartDate()) + "','" + Date.valueOf(curtravel.getEndDate()) +
		"'," + curtravel.getCompanionsNumber() + ")");
		
		tripID = DbAccess.getInstance().getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip WHERE IDUser = " + User.getInstance().getId());
			
		System.out.println(addstatus);
		
		return tripID;
	}
}
