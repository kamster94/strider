package Model;

import dbConnection.DbAccess;

public class VisitedHotels 
{
	private int countryId;
	private int cityId;
	private int hotelId;
	private String hotelName;
	private String streetName;
	private String streetNumber;
	private String zipCode;
	
	private DbAccess dbConnection;
	
	public VisitedHotels(int countryId, int cityId, int hotelId) {
		dbConnection = DbAccess.getInstance();
		this.countryId = countryId;
		this.cityId = cityId;
		this.hotelId = hotelId;
		this.hotelName = dbConnection.getSingeStringFromDb("SELECT HotelName FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "HotelName");
		this.streetName = dbConnection.getSingeStringFromDb("SELECT StreetName FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "StreetName");
		this.streetNumber = dbConnection.getSingeStringFromDb("SELECT StreetNumber FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "StreetNumber");
		this.zipCode = dbConnection.getSingeStringFromDb("SELECT ZipCode FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "ZipCode");
	}
	
	public int getCountryId() {
		return countryId;
	}
	
	public int getCityId() {
		return cityId;
	}
	
	public int getHotelId() {
		return hotelId;
	}
	
	public String getHotelName() {
		return hotelName;
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
}
