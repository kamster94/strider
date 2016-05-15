package trpClasses;

public class Attraction {

	
	public String streetName;
	public String streetNumber;
    public String zipCode;
    public String attractionName;
    public int openingTime;
    public int closingTime;
    
    
    public Attraction (String strN, String strNmb, String zip, String attN, int opTime, int clTime){
    	
    	this.streetName = strN;
    	this.streetNumber = strNmb;
    	this.zipCode = zip;
    	this.attractionName = attN;
    	this.openingTime = opTime;
    	this.closingTime = clTime;
    	
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


	public String getAttractionName() {
		return attractionName;
	}


	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}


	public int getOpeningTime() {
		return openingTime;
	}


	public void setOpeningTime(int openingTime) {
		this.openingTime = openingTime;
	}


	public int getClosingTime() {
		return closingTime;
	}


	public void setClosingTime(int closingTime) {
		this.closingTime = closingTime;
	}
	
}
