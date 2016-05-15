package trpClasses;

import dbConnection.DbAccess;

public class TripController {
	
	public Trip trip;
	public int arrivalCountryID;
	public int arrivalCityID;
	public int leaveCountryID;
	public int leaveCityID;
	public static DbAccess dataBaseAccess;
	
	private static String Countrysql = "Select IDCountry From Country where CountryName =";
	private static String Citysql1 = "Select C.IDCity From City  where CityName =";
	private static String Citysql2 = "and IDCountry =";
	
	private static String pushSql = "CALL DBA.fAddTrip(";
	
	
	public TripController(Trip tr){
		this.trip = tr;		
		
		dataBaseAccess = new DbAccess("Artureczek","debil");
		
		this.leaveCountryID = new Integer(dataBaseAccess.getIntFromDb(Countrysql + this.trip.leavingCountry));
		this.leaveCityID = new Integer(dataBaseAccess.getIntFromDb(Citysql1 + this.trip.leavingCity + Citysql2 + this.leaveCountryID));
		this.arrivalCountryID = new Integer(dataBaseAccess.getIntFromDb(Countrysql + this.trip.arrivalCountry));
		this.arrivalCityID = new Integer(dataBaseAccess.getIntFromDb(Citysql1 + this.trip.leavingCity + Citysql2 + this.leaveCountryID));
		
						
	}
	
	public void pushToDB(){
		
		dataBaseAccess = new DbAccess("Artureczek","debil");
		
		java.sql.Date beginDate = new java.sql.Date(this.trip.beginDate.getTime());
		java.sql.Date endDate = new java.sql.Date(this.trip.endDate.getTime());
		
		
		dataBaseAccess.pushToDb(pushSql + "1, " + this.arrivalCountryID + "," + this.arrivalCityID
				 + "," + this.leaveCountryID  + "," + this.leaveCityID  + "," +  this.trip.tripName 
				 + "," + beginDate + "," + endDate + ")");
		
	}
	
	
}
