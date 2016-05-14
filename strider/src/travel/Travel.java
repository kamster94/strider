package travel;

import java.time.LocalDate;

public class Travel 
{
	private int id;
	private String name;
	private LocalDate startdate;
	private LocalDate enddate;
	private int id_country_origin;
	private int id_city_origin;
	private int id_country_destination;
	private int id_city_destination;
	private int companionsnumber;
	
	public Travel(String namex, 
				LocalDate startdatex, 
				LocalDate enddatex, 
				int idcountryoriginx, 
				int idcityoriginx, 
				int idcountrydestinationx, 
				int idcitydestinationx, 
				int companionsnumberx) 
	{
		name = namex;
		startdate = startdatex;
		enddate = enddatex;
		id_country_origin = idcountryoriginx;
		id_city_origin = idcityoriginx;
		id_country_destination = idcountrydestinationx;
		id_city_destination = idcitydestinationx;
		companionsnumber = companionsnumberx;
	}

	public void setId(int idx)
	{
		id = idx;
	}
	
	public void setName(String namex)
	{
		name = namex;
	}
	
	public void setStartDate(LocalDate startdatex)
	{
		startdate = startdatex;
	}
	
	public void setEndDate(LocalDate enddatex)
	{
		enddate = enddatex;
	}
	
	public void setCountryOriginId(int idx)
	{
		id_country_origin = idx;
	}
	
	public void setCityOriginId(int idx)
	{
		id_city_origin = idx;
	}
	
	public void setCountryDestinationId(int idx)
	{
		id_country_destination = idx;
	}
	
	public void setCityDestinationId(int idx)
	{
		id_city_destination = idx;
	}
	
	public void setCompanionsNumber(int num)
	{
		companionsnumber = num;
	}
	
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public LocalDate getStartDate()
	{
		return startdate;
	}
	
	public LocalDate getEndDate()
	{
		return enddate;
	}
	
	public int getCountryOriginId()
	{
		return id_country_origin;
	}
	
	public int getCityOriginId()
	{
		return id_city_origin;
	}
	
	public int getCountryDestinationId()
	{
		return id_country_destination;
	}
	
	public int getCityDestinationId()
	{
		return id_city_destination;
	}
	
	public int getCompanionsNumber()
	{
		return companionsnumber;
	}
}
