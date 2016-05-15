package travel;

public class AttractionDetails 
{
	private int id_attraction;
	private int id_country;
	private int id_city;
	private int id_currency;
	private float price;
	private String notes;
	
	public AttractionDetails(int id_attraction, int id_countryx, int id_cityx, int id_currencyx, float pricex, String notesx) 
	{
		id_country = id_countryx;
		id_city = id_cityx;
		id_currency = id_currencyx;
		price = pricex;
		notes = notesx;
	}
	public int getAttractionId()
	{
		return id_attraction;
	}
	public int getCountryId()
	{
		return id_country;
	}
	
	public int getCityId()
	{
		return id_city;
	}
	
	public int getCurrencyId()
	{
		return id_currency;
	}
	
	public float getPrice()
	{
		return price;
	}
	
	public String getNotes()
	{
		return notes;
	}
}
