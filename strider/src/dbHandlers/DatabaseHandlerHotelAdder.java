package dbHandlers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Hotel;
import Model.Review;
import Model.TravelFramework;
import Model.User;
import Model.VisitedAttractions;
import Model.VisitedHotels;
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
	
	/*
	public boolean pushHotelRating(int countryid, int cityid, int hotelid, Review rev)
	{
		int userid = User.getInstance().getId();
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddHotelReview("userid +          ")");
		return addstatus;
	}
	*/
	
	public boolean pushHotelReview(VisitedHotels hot, Review rev)
	{
		int id_user = User.getInstance().getId();
		int id_country = hot.getCountryId();
		int id_city = hot.getCityId();
		int id_hotel = hot.getHotelId();
		int cleanliness = rev.getCleanlinessRating();
		int comfort = rev.getComfortRating();
		int localization = rev.getLocalizationRating();
		int amenities = rev.getAmenitiesRating();
		int personel = rev.getPersonelRating();
		int valmoney = rev.getValueForMoneyRating();
		int average = rev.getAverageRating();
		String notes = rev.getReviewNotes();
		LocalDate revdate = LocalDate.now();
		
		
		boolean revstatus = dbConnection.pushToDb("CALL DBA.fAddHotelReview(" + id_user + "," + id_country + "," + id_city + "," + id_hotel +
												  "," + cleanliness + "," + comfort + "," + localization + "," + amenities + "," + personel +
												  "," + valmoney + ",'" + average + "','" + notes +"','" + revdate + "')");
				  
				return revstatus;
	}
	
	public float getHotelReview(int countryid, int cityid, int hotelid)
	{
		return dbConnection.getFloatFromDb("SELECT AverageGrade FROM DBA.HotelReview WHERE IDUser = " + User.getInstance().getId() + " AND IDHotel = " + hotelid + " AND IDCountry = " + countryid + " AND IDCity = " + cityid);
	}
	
	
	
	public int getHotelId(int countryid, int cityid, String name){
		return dbConnection.getIntFromDb("SELECT IDHotel FROM DBA.Hotel WHERE HotelName = '" + name + "' AND IDCountry = " + countryid + " AND IDCity = " + cityid);
	}
	
	public String getHotelName(int idcity, int idhotel)
	{
		return dbConnection.getSingeStringFromDb("SELECT HotelName FROM DBA.Hotel WHERE IDHotel = " + idhotel + " AND IDCity = " + idcity, "HotelName");
	}
	
	public List<String> getHotels(int cityId, int countryId){
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Hotel WHERE IDCity = " + cityId + " AND IDCountry = " + countryId, Arrays.asList("HotelName"));
	}
	
	public List<VisitedHotels> getVisitedHotels(){
		User user = User.getInstance();
		List<Integer> ids = dbConnection.getIntegersFromDb("SELECT IDCountry, IDCity, IDHotel FROM DBA.HotelDetail WHERE IDUser = " + user.getId() + " GROUP BY IDCountry, IDCity, IDHotel", Arrays.asList("IDCountry", "IDCity", "IDHotel"));
		List<VisitedHotels> visitedHotels = new ArrayList<VisitedHotels>();
		if (ids.size()==0) return null;
		for (int i = 0; i <= ids.size()-1; i+=3){
			VisitedHotels visited = new VisitedHotels(ids.get(i), ids.get(i+1), ids.get(i+2));
			visitedHotels.add(visited);
		}
		return visitedHotels;
	}
}
