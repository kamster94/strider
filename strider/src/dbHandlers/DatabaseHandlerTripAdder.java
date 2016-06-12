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
	
	private DatabaseHandlerTripAdder()
	{
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
		int hotelid = DatabaseHandlerHotelAdder.getInstance().getHotelId(countryid, cityid, hotelname);
		Timestamp arrivaldate = Timestamp.valueOf(hot.accomodation_startdate);
		Timestamp leavingdate = Timestamp.valueOf(hot.accomodation_enddate);
		String reservationpath = hot.filepath;
		boolean addstatus = false;
		
		if(hot.iscustom == true)
		{
			System.out.println("adding custom hotel");
			addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddHotelDetails(" + userid + "," + tripid + "," +
			currencyid + "," + countryid + "," + cityid + "," + price + ",'" + notes + "','" + street +"','" + streetnumber +
			"','" + zipcode +"','" + hotelname +"','" + arrivaldate + "','" + leavingdate +"','" + reservationpath + "')");
		}
		else
		{
			System.out.println("hotelid = " + hotelid);
			addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddHotelDetails(" + userid + "," + hotelid + "," + tripid + "," +
			countryid + "," + cityid + "," + currencyid + ",'" + arrivaldate + "','" + leavingdate + "'," + price + ",'" + 
			reservationpath + "','" + notes + "')");
		}
		return addstatus;
	}
	
	private int getHourFromString(String txt)
	{
		return Integer.parseInt(txt.substring(0, 2));
	}
	
	private int getMinutesFromString(String txt)
	{
		return Integer.parseInt(txt.substring(3, 5));
	}
	
	public boolean pushTransportToDatabase(Transport trans)
	{
		int userid = User.getInstance().getId();
		int tripid = TravelFramework.getInstance().getTravel().getId();
		int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(trans.currency);
		int transcat = DatabaseHandlerTransportAdder.getInstance().getCategoryId(trans.transportcategory);
		String transname = trans.provider; //temporary
		int transid = DatabaseHandlerCommon.getInstance().getTransportId(transcat, trans.provider);
		int countryid_target = DatabaseHandlerCommon.getInstance().getCountryId(trans.country_end);
		int cityid_target = DatabaseHandlerCommon.getInstance().getCityId(trans.city_end);
		int countryid_start = DatabaseHandlerCommon.getInstance().getCountryId(trans.country_start);
		int cityid_start = DatabaseHandlerCommon.getInstance().getCityId(trans.city_start);
		Timestamp arrivaldatetime = Timestamp.valueOf(trans.enddatetime);
		Timestamp startdatetime = Timestamp.valueOf(trans.startdatetime);
		float price = (float)trans.price;
		float calcprice = trans.calcdcost;
		String link = trans.filepath;
		String notes = trans.notes;
		System.out.println("categoryid = " + transcat);
		System.out.println("providername = " + trans.provider);
		System.out.println("transportid = " + transid);
		
		boolean addstatus = false;
	
		addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddTransportDetails(" + userid + "," + tripid + "," +
		currencyid + "," + transcat + "," + transid + "," + countryid_target + ","+cityid_target+","+countryid_start+","+cityid_start + 
		",'" + arrivaldatetime + "','" + startdatetime + "'," + price + ",'" + link + "','" + notes + "'," + calcprice + ")");
		

			/*
			addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddTransportDetails(" + userid + "," + tripid + "," +
			currencyid + "," + transcat + ",'" + transname + "'," + countryid_target + ","+cityid_target+","+countryid_start+","+cityid_start+",'"+arrivaldatetime + "','" +
			startdatetime+"',"+price+",'"+link+"','"+notes+"'," + calcprice + ")");
			 */
		
		
		return addstatus;
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
		LocalTime opentime;
		LocalTime opentill;
		int attractionid = DatabaseHandlerCommon.getInstance().getAttractionId(countryid, cityid, attractionname);
		
		if(att.openfrom.isEmpty() == true)opentime = LocalTime.MIN;
		else opentime = LocalTime.of(getHourFromString(att.openfrom), getMinutesFromString(att.openfrom));
		if(att.opento.isEmpty() == true)opentill = LocalTime.MIN;
		else opentill = LocalTime.of(getHourFromString(att.opento), getMinutesFromString(att.opento));
		
		boolean addstatus = false;
		
		if(att.iscustom == false)
		{
			addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddAttractionDetails(" + userid + "," + tripid + "," +
			attractionid + "," + countryid + "," + cityid + "," + currencyid + "," + price + ",'" + notes + "','" + visitdate +"')");
		}
		else
		{
			addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fCompactAddAttractionDetails(" + userid + "," + tripid + "," +
			currencyid + "," + countryid + "," + cityid + "," + price + ",'"+notes+"','"+visitdate+"','"+streetname+"','"+number + "','" +
			zipcode+"','"+attractionname+"','"+opentime+"','"+opentill+"')");	
		}
		

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
		float cost_transport = TravelFramework.getInstance().getTravel().transportcost;
		float cost_hotel = TravelFramework.getInstance().getTravel().hotelcost;
		float cost_attraction = TravelFramework.getInstance().getTravel().attractioncost;
		float cost_total = TravelFramework.getInstance().getTravel().allcost;
		
		Date beginDate = Date.valueOf(TravelFramework.getInstance().getTravel().getStartDate());
		Date endDate = Date.valueOf(TravelFramework.getInstance().getTravel().getEndDate());

		
		boolean addstatus = DbAccess.getInstance().pushToDb("CALL DBA.fAddTrip(" + userid +
		"," + countryid_end + "," + cityid_end +
		"," + countryid_start + "," + cityid_start +
		",'" + travelname + "','" + beginDate + "','" + endDate +
		"'," + compnumber + "," + cost_transport + "," + cost_hotel + "," + cost_attraction + "," + cost_total + ")");
		
		tripID = DbAccess.getInstance().getIntFromDb("SELECT MAX(IDTrip) FROM DBA.Trip WHERE IDUser = " + userid);
			
		System.out.println(addstatus);
		
		return tripID;
	}
}
