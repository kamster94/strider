package dbHandlers;

import java.sql.Date;

import Model.Travel;
import Model.TravelFramework;
import dbConnection.DbAccess;
import travel.AttractionDetails;
import trpClasses.Attraction;

public class DatabaseHandlerAttractionAdder
{
	private AttractionDetails attr;
	private static String pushSql = "CALL DBA.fAddAttractionDetails(";
	
	public void setAttraction(AttractionDetails trav)
	{
		attr = trav;
	}

	public int pushAttractionToDatabase()
	{
		int attractionID = 0;

		boolean addstatus = DbAccess.getInstance().pushToDb(pushSql + "1, " + TravelFramework.getInstance().getCurrentTravel().getId() + "," + attr.getAttractionId() + "," + attr.getCountryId()
				 + "," + attr.getCityId()  + "," + attr.getCountryId()  + "," + attr.getCityId()  + "," + attr.getCurrencyId()
				 + "," +  attr.getPrice()  + ",'" + attr.getNotes() + "')");

			
		System.out.println(addstatus);
		
		return attractionID;
		}
	
	
}
