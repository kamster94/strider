package dbHandlers;

import java.sql.Date;

import dbConnection.DbAccess;
import travel.AttractionDetails;
import travel.Travel;
import trpClasses.Attraction;

public class DatabaseHandlerAttractionAdder {

	
	private AttractionDetails attr;
	public static DbAccess dataBaseAccess;
	private static String pushSql = "CALL DBA.fAddAttraction(";
	
	public void setTravel(AttractionDetails trav)
	{
		attr = trav;
	}

	public int pushTravelToDatabase()
	{
		int attractionID = 0;
			
		dataBaseAccess = new DbAccess("adriank","debil");

			
		boolean addstatus = dataBaseAccess.pushToDb(pushSql + "1, " + idtripa + "," + attr.getCountryId()
				 + "," + attr.getCityId()  + "," + attr.getCountryId()  + "," + attr.getCityId()  + "," + attr.getCurrencyId()
				 + "," +  attr.getPrice()  + ",'" + attr.getNotes() + "')");
			
		attractionID = dataBaseAccess.getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip");
			
		System.out.println(addstatus);
		
		return attractionID;
		}
	
	
}
