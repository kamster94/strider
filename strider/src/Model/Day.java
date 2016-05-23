package Model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Day 
{
	public List<Attraction> attractions;
	public LocalDateTime date;
	//Hotel hotel;
	//Transport transport;
	
	public Day(LocalDateTime datex) 
	{
		attractions = new LinkedList<Attraction>();
		date = datex;
		
		//hotel = new Hotel();
		//transport = new Transport();
	}

}
