package dbHandlers;

import java.sql.Date;

import dbConnection.DbAccess;
import travel.Travel;
import trpClasses.Trip;

public class DatabaseHandlerTripAdder 
{
	private Travel curtravel;
	public static DbAccess dataBaseAccess;
	private static String Countrysql = "Select IDCountry From Country where CountryName =";
	private static String Citysql1 = "Select C.IDCity From City where CityName =";
	private static String Citysql2 = "and IDCountry =";
	private static String pushSql = "CALL DBA.fAddTrip(";
	
	public void setTravel(Travel trav)
	{
		curtravel = trav;
	}

	public int pushTravelToDatabase()
	{
		int tripID = 0;
			
		dataBaseAccess = new DbAccess("adriank","debil");
			
		Date beginDate = Date.valueOf(curtravel.getStartDate());
		Date endDate = Date.valueOf(curtravel.getEndDate());

			
		boolean addstatus = dataBaseAccess.pushToDb(pushSql + "1, " + curtravel.getCountryDestinationId() + "," + curtravel.getCityDestinationId()
	    + "," + curtravel.getCountryOriginId()  + "," + curtravel.getCityOriginId()  + ",'" +  curtravel.getName() 
	    + "','" + beginDate + "','" + endDate + "'," + curtravel.getCompanionsNumber() + ")");
			
		tripID = dataBaseAccess.getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip");
			
		System.out.println(addstatus);
		
		return tripID;
		}
}
