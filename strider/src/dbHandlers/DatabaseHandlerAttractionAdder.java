package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import Model.Travel;
import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;
import Model.AttractionDetails;

public class DatabaseHandlerAttractionAdder
{
	private static DatabaseHandlerAttractionAdder myinstance;
	private static DbAccess dbConnection;
	private static AttractionDetails attr;

	private DatabaseHandlerAttractionAdder(){
		dbConnection = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerAttractionAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerAttractionAdder();
		return myinstance;
	}
	
	public void setAttraction(AttractionDetails trav)
	{
		attr = trav;
	}

	public boolean pushAttractionToDatabase()
	{
		boolean addstatus = dbConnection.pushToDb("CALL DBA.fAddAttractionDetails(" + User.getInstance().getId() + 
				"," + TravelFramework.getInstance().getCurrentTravel().getId() + "," + attr.getAttractionId() + 
				"," + attr.getCountryId() + "," + attr.getCityId()  + "," + attr.getCountryId()  + 
				"," + attr.getCityId()  + "," + attr.getCurrencyId() +
				"," +  attr.getPrice()  + ",'" + attr.getNotes() + "')");
		
		return addstatus;
	}
	
	public int getAttractionId(int cityid, String name)
	{
		return dbConnection.getIntFromDb("SELECT IDAttraction FROM DBA.Attraction WHERE IDCity = " + cityid + " AND AttractionName = '" + name + "'");
	}
	
	public String getAttractionName(int cityid, int attrid)
	{
		return dbConnection.getSingeStringFromDb("SELECT AttractionName FROM DBA.Attraction WHERE IDCity = " + cityid + " AND IDAttraction = " + attrid, "AttractionName");
				
	}
	
	public List<String> getAttractions(int cityId, int countryId){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Attraction WHERE IDCity = " + cityId + " AND IDCountry = " + countryId, Arrays.asList("AttractionName"));
	}
}
