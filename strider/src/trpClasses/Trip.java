package trpClasses;

import java.util.Date;

public class Trip {

	public String tripName;
	public String arrivalCountry;
	public String arrivalCity;
	public String leavingCountry;
	public String leavingCity;
	public Date beginDate = new Date();
	public Date endDate = new Date();

	
	public Trip(String trName, String arrCntr, String arrCity, String leavCntr, String leavCity, Date beg, Date end){
		this.tripName = trName;
		this.arrivalCity = arrCity;
		this.arrivalCountry = arrCntr;
		this.leavingCity = leavCity;
		this.leavingCountry = leavCntr;
		this.beginDate = beg;
		this.endDate = end;
	}
	
	
	public String getTripName() {
		return tripName;
	}


	public void setTripName(String tripName) {
		this.tripName = tripName;
	}


}
