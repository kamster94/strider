package Model;

import dbConnection.DbAccess;

public class VisitedHotels {
	
	private int countryId;
	private int cityId;
	private int hotelId;
	private String hotelName;
	
	private DbAccess dbConnection;
	
	public VisitedHotels(int countryId, int cityId, int hotelId) {
		dbConnection = DbAccess.getInstance();
		this.countryId = countryId;
		this.cityId = cityId;
		this.hotelId = hotelId;
		this.hotelName = dbConnection.getSingeStringFromDb("SELECT HotelName FROM DBA.Hotel WHERE IDCountry = " + countryId + " AND IDCity = " + cityId + " AND IDHotel = " + hotelId, "HotelName");
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

}
