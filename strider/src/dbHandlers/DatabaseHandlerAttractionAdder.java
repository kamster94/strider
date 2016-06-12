package dbHandlers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Model.Attraction;
import Model.Travel;
import Model.TravelFramework;
import Model.User;
import Model.VisitedAttractions;
import dbConnection.DbAccess;

public class DatabaseHandlerAttractionAdder
{
	private static DatabaseHandlerAttractionAdder myinstance;
	private static DbAccess dbConnection;
	private Attraction attraction;

	private DatabaseHandlerAttractionAdder(){
		dbConnection = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerAttractionAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerAttractionAdder();
		return myinstance;
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
	
	public List<VisitedAttractions> getVisitedAttractions(){
		User user = User.getInstance();
		List<Integer> ids = dbConnection.getIntegersFromDb("SELECT IDCountry, IDCity, IDAttraction FROM DBA.AttractionDetail WHERE IDUser = " + user.getId() + " GROUP BY IDCountry, IDCity, IDAttraction", Arrays.asList("IDCountry", "IDCity", "IDAttraction"));
		List<VisitedAttractions> visitedAttractions = new ArrayList<VisitedAttractions>();
		if (ids.size()==0) return null;
		for (int i = 0; i <= ids.size()-1; i+=3){
			VisitedAttractions visited = new VisitedAttractions(ids.get(i), ids.get(i+1), ids.get(i+2));
			visitedAttractions.add(visited);
		}
		
		//Set<VisitedAttractions> atset = new LinkedHashSet<VisitedAttractions>(visitedAttractions);

		return visitedAttractions;
	}
}
