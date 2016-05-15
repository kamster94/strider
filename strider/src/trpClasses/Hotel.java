package trpClasses;

public class Hotel {

	
	public String streetName;
	public String streetNumber;
    public String zipCode;
    public String hotelName;

    
    
    public Hotel (String strN, String strNmb, String zip, String httN){
    	
    	this.streetName = strN;
    	this.streetNumber = strNmb;
    	this.zipCode = zip;
    	this.hotelName = httN;
  	
    }



	public String getStreetName() {
		return streetName;
	}



	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}



	public String getStreetNumber() {
		return streetNumber;
	}



	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}



	public String getZipCode() {
		return zipCode;
	}



	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}



	public String getHotelName() {
		return hotelName;
	}



	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	
}
