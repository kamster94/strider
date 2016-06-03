package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerHotelAdder {
	
	private static DatabaseHandlerHotelAdder myinstance;
	private DbAccess dbConnection;
	private User user;
	private TravelFramework travel;
	
	private DatabaseHandlerHotelAdder(){
		dbConnection = DbAccess.getInstance();
		user = User.getInstance();
		travel = TravelFramework.getInstance();
	}
	
	public static DatabaseHandlerHotelAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerHotelAdder();
		return myinstance;
	}
	
	/*
	public void setHotel(HotelDetails hotel){
		this.hotel = hotel;
	}
	
	public boolean pushHotelDetails()
	{
		String sql = "SELECT DBA.fAddHotelDetails (" + user.getId() + ", " + hotel.getHotelId() + ", " + travel.getCurrentTravel().getId() + ", "
				+ hotel.getCountryId() + ", " + hotel.getCityId() + ", " + hotel.getCountryId() + ", " + hotel.getCityId() + ", "
				+ hotel.getCurrencyId() + ", '" + Date.valueOf(hotel.getArrivalDate()) + "', '" + Date.valueOf(hotel.getLeavingDate()) + "', "
				+ hotel.getPrice() + ", '" + hotel.getLink() + "', '" + hotel.getNotes() + "')";
		System.out.println(sql);
		int status = dbConnection.getIntFromDb(sql);
		if (status == 1) return true;
		else return false;
	}
	*/
	public int getHotelId(String name){
		return dbConnection.getIntFromDb("SELECT IDHotel FROM DBA.Hotel WHERE HotelName = '" + name + "'");
	}
	
	public String getHotelName(int idcity, int idhotel)
	{
		return dbConnection.getSingeStringFromDb("SELECT HotelName FROM DBA.Hotel WHERE IDHotel = " + idhotel + " AND IDCity = " + idcity, "HotelName");
	}
	
	public List<String> getHotels(int cityId, int countryId){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Hotel WHERE IDCity = " + cityId + " AND IDCountry = " + countryId, Arrays.asList("HotelName"));
	}
}
