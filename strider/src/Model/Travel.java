package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Travel 
{
	private int id;
	private String name;
	private LocalDateTime startdate;
	private LocalDateTime enddate;
	private int id_country_origin;
	private int id_city_origin;
	private int companionsnumber;
	public List<Day> days;
	
	
	public Travel(String namex, LocalDateTime startdatex, LocalDateTime enddatex) 
	{
		name = namex;
		startdate = startdatex;
		enddate = enddatex;
		days = new LinkedList<Day>();
		
		for(int i = 0; i < getDaysNumber(); i++)
		{
			days.add(new Day(startdate.plusDays(i)));
		}
		
		
		
	}

	public void addAttractionToDay(LocalDateTime date, Attraction attr)
	{
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).date.equals(date))days.get(i).attractions.add(attr);
		}
	}
	
	public void setHotelToDay(LocalDateTime date, Hotel hot)
	{
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).date.equals(date))days.get(i).hotel = hot;
		}
	}
	
	public void setTransportToDay(LocalDateTime date, Transport trans)
	{
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).date.equals(date))days.get(i).transport = trans;
		}
	}
	
	public void printDays()
	{
		for(Day d : days)
		{
			System.out.println("Day : " + d.date);
		}
	}
	
	/*
	public void addStage(StageType stg)
	{
		travelelements.add(stg);
		System.out.println("ADDED STAGE");
	}
	
	public StageType getStage(int indx)
	{
		return travelelements.get(indx);
	}
	
	public List<StageType> getStageList()
	{
		return travelelements;
	}
	
	
	*/
	public void setId(int idx)
	{
		id = idx;
	}
	public void setName(String namex)
	{
		name = namex;
	}
	public void setStartDate(LocalDateTime startdatex)
	{
		startdate = startdatex;
	}
	public void setEndDate(LocalDateTime enddatex)
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
	public LocalDateTime getStartDate()
	{
		return startdate;
	}
	public LocalDateTime getEndDate()
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
	public long getDaysNumber()
	{
		return ChronoUnit.DAYS.between(startdate, enddate) + 1;
	}
	
}
