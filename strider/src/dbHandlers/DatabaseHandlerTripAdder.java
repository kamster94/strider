package dbHandlers;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import Model.Attraction;
import Model.Hotel;
import Model.Transport;
import Model.Travel;
import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;
import javafx.scene.control.TextField;

public class DatabaseHandlerTripAdder 
{
	private Travel curtravel;
	private static DatabaseHandlerTripAdder myinstance;
	public static DbAccess dataBaseAccess;
	private static String Countrysql = "Select IDCountry From Country where CountryName =";
	private static String Citysql1 = "Select C.IDCity From City where CityName =";
	private static String Citysql2 = "and IDCountry =";
	
	private DatabaseHandlerTripAdder(){
		dataBaseAccess = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerTripAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerTripAdder();
		return myinstance;
	}
	
	public void setTravel(Travel trav)
	{
		curtravel = trav;
	}
	
	public boolean pushHotelToDatabase(Hotel hot)
	{
		int userid = User.getInstance().getId();
		
		int tripid = TravelFramework.getInstance().getTravel().getId();
		int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(hot.currency);
		int countryid = DatabaseHandlerCommon.getInstance().getCountryId(hot.country);
		int cityid = DatabaseHandlerCommon.getInstance().getCityId(hot.city);
		float price = hot.pricepernite;
		String notes = hot.notes;
		String street = hot.street;
		String streetnumber = hot.number;
		String zipcode = hot.zipcode;
		String hotelname = hot.name;
		int hotelid = DatabaseHandlerHotelAdder.getInstance().getHotelId(hotelname);
		Timestamp arrivaldate = Timestamp.valueOf(hot.accomodation_startdate);
		Timestamp leavingdate = Timestamp.valueOf(hot.accomodation_enddate);
		String reservationpath = hot.filepath;
		
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddHotelDetails(" + userid + "," + tripid + "," +
		currencyid + "," + countryid + "," + cityid + "," + price + ",'" + notes + "','" + street +"','" + streetnumber +
		"','" + zipcode +"','" + hotelname +"','" + arrivaldate + "','" + leavingdate +"','" + reservationpath + "')");
		return addstatus;
	}
	
	private int getHourFromString(String txt)
	{
		return Integer.parseInt(txt.substring(0, 1));
	}
	
	private int getMinutesFromString(String txt)
	{
		return Integer.parseInt(txt.substring(3, 4));
	}
	
	public boolean pushTransportToDatabase(Transport trans)
	{
		/*int userid = User.getInstance().getId();
		int tripid = TravelFramework.getInstance().getTravel().getId();
		int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(trans.currency);
		int transcat = DatabaseHandlerTransportAdder.getInstance().getCategoryId(trans.transportcategory);
		String name = trans.n ?????
		int countryid_target = DatabaseHandlerCommon.getInstance().getCountryId(trans.country_end);
		int cityid_target = DatabaseHandlerCommon.getInstance().getCityId(trans.city_end);
		int countryid_start = DatabaseHandlerCommon.getInstance().getCountryId(trans.country_start);
		int cityid_start = DatabaseHandlerCommon.getInstance().getCityId(trans.city_start);
		TimeStamp arrivaldatetime = trans.enddatetime;
		TimeStamp startdatetime = trans.startdatetime;
		float price = trans.price;
		
		
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddAttractionDetails(" + userid + "," + tripid + "," +
		currencyid + "," + countryid + "," + cityid + "," + price + ",'"+notes+"','"+visitdate+"','"+streetname+"','"+number + "','" +
		zipcode+"','"+attractionname+"','"+opentime+"','"+opentill+"')");
		return addstatus;*/
		return false;
	}
	
	public List<String> getTravelList()
	{
		int userid = User.getInstance().getId();
		return dbConnection.getStringsFromDb("SELECT * FROM DBA.Trip WHERE IDUser = " + userid, Arrays.asList("Trips"));	
	}
	
	
	
	public boolean pushAttractionToDatabase(Attraction att)
	{
		int userid = User.getInstance().getId();
		int tripid = TravelFramework.getInstance().getTravel().getId();
		int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(att.currency);
		int countryid = DatabaseHandlerCommon.getInstance().getCountryId(att.country);
		int cityid = DatabaseHandlerCommon.getInstance().getCityId(att.city);
		float price = att.price;
		String notes = att.notes;
		Date visitdate = Date.valueOf(att.date);
		String streetname = att.street;
		String number = att.number;
		String zipcode = att.zipcode;
		String attractionname = att.name;
		LocalTime opentime = LocalTime.of(getHourFromString(att.openfrom), getMinutesFromString(att.openfrom));
		LocalTime opentill = LocalTime.of(getHourFromString(att.opento), getMinutesFromString(att.opento));
		
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddAttractionDetails(" + userid + "," + tripid + "," +
		currencyid + "," + countryid + "," + cityid + "," + price + ",'"+notes+"','"+visitdate+"','"+streetname+"','"+number + "','" +
		zipcode+"','"+attractionname+"','"+opentime+"','"+opentill+"')");
		return addstatus;
	}
	
	
	public int pushTravelToDatabase()
	{
		int tripID = 0;
		
		int userid = User.getInstance().getId();
		int countryid_end = TravelFramework.getInstance().getTravel().getCountryDestinationId();
		int cityid_end = TravelFramework.getInstance().getTravel().getCityDestinationId();
		int countryid_start = TravelFramework.getInstance().getTravel().getCountryOriginId();
		int cityid_start = TravelFramework.getInstance().getTravel().getCityOriginId();
		String travelname = TravelFramework.getInstance().getTravel().getName();
		int compnumber = 0;
		
		
		Date beginDate = Date.valueOf(TravelFramework.getInstance().getTravel().getStartDate());
		Date endDate = Date.valueOf(TravelFramework.getInstance().getTravel().getEndDate());

		
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddTrip(" + userid +
		"," + countryid_end + "," + cityid_end +
		"," + countryid_start + "," + cityid_start +
		",'" + travelname + "','" + beginDate + "','" + endDate +
		"'," + compnumber + ")");
		
		tripID = DbAccess.getInstance().getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip WHERE IDUser = " + userid);
			
		System.out.println(addstatus);
		
		return tripID;
	}
}
