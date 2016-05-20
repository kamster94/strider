package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import Model.Travel;
import Model.TravelFramework;
import dbConnection.DbAccess;
import Model.AttractionDetails;
import trpClasses.Attraction;

public class DatabaseHandlerAttractionAdder
{
	private static DatabaseHandlerAttractionAdder myinstance;
	private static DbAccess dbConnection;
	private static AttractionDetails attr;
	private static String pushSql = "CALL DBA.fAddAttractionDetails(";
	
	private DatabaseHandlerAttractionAdder(){
		dbConnection = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerAttractionAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerAttractionAdder();
		return myinstance;
	}
	
	public static void setAttraction(AttractionDetails trav)
	{
		attr = trav;
	}

	public static int pushAttractionToDatabase()
	{
		int attractionID = 0;

		boolean addstatus = dbConnection.pushToDb(pushSql + "1, " + TravelFramework.getInstance().getCurrentTravel().getId() + "," + attr.getAttractionId() + "," + attr.getCountryId()
				 + "," + attr.getCityId()  + "," + attr.getCountryId()  + "," + attr.getCityId()  + "," + attr.getCurrencyId()
				 + "," +  attr.getPrice()  + ",'" + attr.getNotes() + "')");

			
		System.out.println(addstatus);
		
		return attractionID;
		}
	
	public static List<String> getAttractions(int cityId, int countryId){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Attraction WHERE IDCity = " + cityId + " AND IDCountry = " + countryId, Arrays.asList("AttractionName"));
	}
}
