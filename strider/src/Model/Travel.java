package Model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Travel 
{
	private int id;
	private String name;
	private LocalDate startdate;
	private LocalDate enddate;
	private int id_country_origin;
	private int id_city_origin;
	private int companionsnumber;
	private List<Stage> travelstages;
	
	
	public Travel(String namex, LocalDate startdatex, LocalDate enddatex, int idcountryoriginx, int idcityoriginx, int companionsnumberx) 
	{
		name = namex;
		startdate = startdatex;
		enddate = enddatex;
		id_country_origin = idcountryoriginx;
		id_city_origin = idcityoriginx;
		companionsnumber = companionsnumberx;
		travelstages = new LinkedList<Stage>();
	}

	public void addStage(Stage stg)
	{
		travelstages.add(stg);
	}
	
	public Stage getStage(int indx)
	{
		return travelstages.get(indx);
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
		return getCountryOriginId();
	}
	public int getCityDestinationId()
	{
		return getCityOriginId();
	}
	public int getCompanionsNumber()
	{
		return companionsnumber;
	}
}
