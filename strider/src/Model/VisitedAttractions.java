package Model;

import dbConnection.DbAccess;

public class VisitedAttractions {

	private int countryId;
	private int cityId;
	private int attractionId;
	private String attractionName;
	
	private DbAccess dbConnection;
	
	public VisitedAttractions(int countryId, int cityId, int attractionId) {
		dbConnection = DbAccess.getInstance();
		this.countryId = countryId;
		this.cityId = cityId;
		this.attractionId = attractionId;
		System.out.println("SELECT AttractionName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId);
		this.attractionName = dbConnection.getSingeStringFromDb("SELECT AttractionName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "AttractionName");
	}
	
	public int getCountryId() {
		return countryId;
	}

	public int getCityId() {
		return cityId;
	}

	public int getAttractionId() {
		return attractionId;
	}

	public String getAttractionName() {
		return attractionName;
	}

}
