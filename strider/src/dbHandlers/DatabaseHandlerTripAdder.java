package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import Model.Travel;
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
	private static String pushSql = "CALL DBA.fAddTrip(";
	
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

			
		boolean addstatus = DbAccess.getInstance().pushToDb(pushSql + "1, " + curtravel.getCountryDestinationId() + "," + curtravel.getCityDestinationId()
	    + "," + curtravel.getCountryOriginId()  + "," + curtravel.getCityOriginId()  + ",'" +  curtravel.getName() 
	    + "','" + beginDate + "','" + endDate + "'," + curtravel.getCompanionsNumber() + ")");
			
		tripID = DbAccess.getInstance().getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip");
			
		System.out.println(addstatus);
		
		return tripID;
	}
}
