package Model;

import dbConnection.DbAccess;

public class VisitedAttractions {

	private int countryId;
	private int cityId;
	private int attractionId;
	private String attractionName;
	private String streetName;
	private String streetNumber;
	private String zipCode;
	private String openingTime;
	private String closingTime;
	
	private DbAccess dbConnection;
	
	public VisitedAttractions(int countryId, int cityId, int attractionId) {
		dbConnection = DbAccess.getInstance();
		this.countryId = countryId;
		this.cityId = cityId;
		this.attractionId = attractionId;
		this.attractionName = dbConnection.getSingeStringFromDb("SELECT AttractionName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "AttractionName");
		this.streetName = dbConnection.getSingeStringFromDb("SELECT StreetName FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "StreetName");
		this.streetNumber = dbConnection.getSingeStringFromDb("SELECT StreetNumber FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "StreetNumber");
		this.zipCode = dbConnection.getSingeStringFromDb("SELECT ZipCode FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "ZipCode");
		this.openingTime = dbConnection.getSingeStringFromDb("SELECT OpeningTime FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "OpeningTime");
		this.closingTime = dbConnection.getSingeStringFromDb("SELECT ClosingTime FROM DBA.Attraction WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDAttraction = " + attractionId, "ClosingTime");
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

	public String getStreetName() {
		return streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public DbAccess getDbConnection() {
		return dbConnection;
	}

	
}
