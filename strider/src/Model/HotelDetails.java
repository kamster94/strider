package Model;

import java.time.LocalDate;

public class HotelDetails
{
	private int id_hotel;
	private int id_currency;
	private float price;
	private String link;
	private String notes;
	private LocalDate arrivaldate;
	private LocalDate leavingdate;
	private int id_country;
	private int id_city;

	public HotelDetails(int id_hotelx, int id_currencyx, float pricex, String notesx, LocalDate arrivaldatex, LocalDate leavingdatex, int id_countryx, int id_cityx) 
	{
		id_hotel = id_hotelx;
		id_currency = id_currencyx;
		price = pricex;
		link = "TODO ADD LINKS FOR FUCKS SAKE";
		notes = notesx;
		arrivaldate = arrivaldatex;
		leavingdate = leavingdatex;
		id_country = id_countryx;
		id_city = id_cityx;
	}
	public int getHotelId()
	{
		return id_hotel;
	}
	public int getCurrencyId()
	{
		return id_currency;
	}
	public float getPrice()
	{
		return price;
	}
	public String getLink()
	{
		return link;
	}
	public String getNotes()
	{
		return notes;
	}
	public LocalDate getArrivalDate()
	{
		return arrivaldate;
	}
	public LocalDate getLeavingDate()
	{
		return leavingdate;
	}
	public int getCountryId()
	{
		return id_country;
	}
	public int getCityId()
	{
		return id_city;
	}
}


