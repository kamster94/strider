package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
	public List<Day> days;
	public float transportcost;
	public float hotelcost;
	public float attractioncost;
	public float allcost;
	
	public Travel(String namex, LocalDate startdatex, LocalDate enddatex) 
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

	public String getLatestCountryInTravel()
	{
		String country = null;
		
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).transport != null)country = days.get(i).transport.country_end;
		}
		return country;
	}
	
	public String getLatestCityInTravel()
	{
		String city = null;
		
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).transport != null)city = days.get(i).transport.city_end;
		}
		return city;
	}
	
	
	public Day getDayByDate(LocalDate date)
	{
		Day foundday = null;
		for(Day d : days)
		{
			if(d.date.equals(date))foundday = d;
		}
		return foundday;
	}
	
	public void addAttractionToDay(LocalDate date, Attraction attr)
	{
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).date.equals(date))days.get(i).attractions.add(attr);
		}
	}
	
	public void setHotelToDay(LocalDate date, Hotel hot)
	{
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).date.equals(date))days.get(i).hotel = hot;
		}
	}
	
	public void setTransportToDay(LocalDate date, Transport trans)
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
	public long getDaysNumber()
	{
		return ChronoUnit.DAYS.between(startdate, enddate) + 1;
	}
	
}
