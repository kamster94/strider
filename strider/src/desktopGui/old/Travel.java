package desktopGui.old;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Travel 
{
	public Date startdate;
	public Date enddate;
	public int companionnumber;
	public String fromcountry;
	public String fromcity;
	public String tocountry;
	public List<Attraction> attractionlist;
	public List<Transport> transportlist;
	public List<Hotel> hotellist;
	
	public Travel(Date start, Date end, String fromcountryx, String fromcityx, String tocountryx, int companions) 
	{
		this.startdate = start;
		this.enddate = end;
		this.fromcountry = fromcountryx;
		this.fromcity = fromcityx;
		this.tocountry = tocountryx;
		this.companionnumber = companions;
		
		attractionlist = new LinkedList<Attraction>();
		transportlist = new LinkedList<Transport>();
		hotellist = new LinkedList<Hotel>();
	}

	public int getTravelDuration()
	{
		return (int)((enddate.getTime() - startdate.getTime()) / (24 * 60 * 60 * 1000));
	}
	
	
}
