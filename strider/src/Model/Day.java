package Model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Day 
{
	public List<Attraction> attractions;
	public LocalDate date;
	public Hotel hotel;
	public Transport transport;
	public String country;
	public String city;
	
	public Day(LocalDate datex) 
	{
		attractions = new LinkedList<Attraction>();
		date = datex;
	}
}
