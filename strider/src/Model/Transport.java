package Model;

import java.time.LocalDate;

public class Transport extends StageType
{
	
	private int idCurrency;
	private int idTransportCategory;
	private int idTransport;
	private int idCountryArrival;
	private int idCityArrival;
	private int idCountryLeaving;
	private int idCityLeaving;
	private LocalDate arrivalDatetime;
	private LocalDate leavingDatetime;
	private float price;
	private String link;
	private String notes;
	
	public Transport(int idCurrency, int idTransportCategory, int idTransport, int idCountryArrival, int idCityArrival,
			int idCountryLeaving, int idCityLeaving, LocalDate arrivalDatetime, LocalDate leavingDatetime, float price,
			String link, String notes) {
		this.idCurrency = idCurrency;
		this.idTransportCategory = idTransportCategory;
		this.idTransport = idTransport;
		this.idCountryArrival = idCountryArrival;
		this.idCityArrival = idCityArrival;
		this.idCountryLeaving = idCountryLeaving;
		this.idCityLeaving = idCityLeaving;
		this.arrivalDatetime = arrivalDatetime;
		this.leavingDatetime = leavingDatetime;
		this.price = price;
		this.link = link;
		this.notes = notes;
	}
	public int getIdCurrency() {
		return idCurrency;
	}
	public int getIdTransportCategory() {
		return idTransportCategory;
	}
	public int getIdTransport() {
		return idTransport;
	}
	public int getIdCountryArrival() {
		return idCountryArrival;
	}
	public int getIdCityArrival() {
		return idCityArrival;
	}
	public int getIdCountryLeaving() {
		return idCountryLeaving;
	}
	public int getIdCityLeaving() {
		return idCityLeaving;
	}
	public LocalDate getArrivalDatetime() {
		return arrivalDatetime;
	}
	public LocalDate getLeavingDatetime() {
		return leavingDatetime;
	}
	public float getPrice() {
		return price;
	}
	public String getLink() {
		return link;
	}
	public String getNotes() {
		return notes;
	}
	
	
	
}
