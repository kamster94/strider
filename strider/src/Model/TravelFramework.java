package Model;

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
		//System.out.println("Companions: " + curtravel.getCompanionsNumber());
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
	
	public void setTravel(Travel t) 
	{
		clearTravel();
		curtravel = t;
	}

	public void clearTravel()
	{
		curtravel = null;
	}
	
	public boolean hasTravel()
	{
		if(curtravel == null)return false;
		else return true;
	}
	
	public Travel getTravel()
	{
		return curtravel;
	}
}
