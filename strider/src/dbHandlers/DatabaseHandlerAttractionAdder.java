package dbHandlers;

import java.sql.Date;

import dbConnection.DbAccess;
import travel.AttractionDetails;
import travel.Travel;
import travel.TravelFramework;
import trpClasses.Attraction;

public class DatabaseHandlerAttractionAdder {

	
	private AttractionDetails attr;
	public static DbAccess dataBaseAccess;
	private static String pushSql = "CALL DBA.fAddAttraction(";
	
	public void setAttraction(AttractionDetails trav)
	{
		attr = trav;
	}

	public int pushAttractionToDatabase()
	{
		int attractionID = 0;
			
		dataBaseAccess = new DbAccess("adriank","debil");

			
		boolean addstatus = dataBaseAccess.pushToDb(pushSql + "1, " + TravelFramework.getInstance().getCurrentTravel().getId() + "," + attr.getAttractionId() + "," + attr.getCountryId()
				 + "," + attr.getCityId()  + "," + attr.getCountryId()  + "," + attr.getCityId()  + "," + attr.getCurrencyId()
				 + "," +  attr.getPrice()  + ",'" + attr.getNotes() + "')");
			System.out.println(addstatus);
		attractionID = dataBaseAccess.getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip");
			
		System.out.println(addstatus);
		
		return attractionID;
		}
	
	
}
