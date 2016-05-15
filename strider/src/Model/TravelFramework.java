package Model;

import java.time.LocalDate;

public class TravelFramework 
{
	private static TravelFramework tfinstance;
	private Travel curtravel;
	
	public void print()
	{
		System.out.println("=====TRAVEL=====");
		System.out.println("Name: " + curtravel.getName());
		System.out.println("Startdate: " + curtravel.getStartDate());
		System.out.println("Enddate: " + curtravel.getEndDate());
		System.out.println("Start country: " + curtravel.getCountryOriginId());
		System.out.println("Start city: " + curtravel.getCityOriginId());
		System.out.println("End country: " + curtravel.getCountryDestinationId());
		System.out.println("End city: " + curtravel.getCityDestinationId());
		System.out.println("Companions: " + curtravel.getCompanionsNumber());
	}
	
	private TravelFramework() 
	{
		curtravel = null;
	}

	public static TravelFramework getInstance()
	{
		if(tfinstance == null)tfinstance = new TravelFramework();
		return tfinstance;
	}
	
	public void createNewTravel(String namex, LocalDate startdatex, LocalDate enddatex, int idcountryoriginx, int idcityoriginx, int idcountrydestinationx, int idcitydestinationx, int companionsnumberx) 
	{
		if(curtravel == null)
		{
			curtravel = new Travel(namex, startdatex, enddatex, idcountryoriginx, idcityoriginx, idcountrydestinationx, idcitydestinationx, companionsnumberx);
		}
	}

	public boolean hasTravel()
	{
		if(curtravel == null)return false;
		else return true;
	}
	
	public Travel getCurrentTravel()
	{
		return curtravel;
	}
}
