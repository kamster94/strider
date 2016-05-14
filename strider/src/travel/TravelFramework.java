package travel;

import java.time.LocalDate;

public class TravelFramework 
{
	private static TravelFramework tfinstance;
	private Travel curtravel;
	
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
	
	public void updateName(String namex)
	{
		
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
