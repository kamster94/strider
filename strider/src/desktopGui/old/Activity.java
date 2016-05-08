package desktopGui.old;

public class Activity {

	private String name;
	//TODO przerobic na prawdziwe godziny zamiast intow
	private int starttime;
	private int duration;
	private int endtime;
	
	public Activity()
	{
		this.name = "";
		this.starttime = 0;
		this.duration = 0;
		this.endtime = 0;
	}
	
	public Activity(String namex, int starttimex, int durationx) 
	{
		this.name = namex;
		this.starttime = starttimex;
		this.duration = durationx;
		this.endtime = this.starttime + this.duration;
		
	}
	
	public void setName(String namex)
	{
		this.name = namex;
	}
	
	public void setStartTime(int starttimex)
	{
		this.starttime = starttimex;
	}
	
	public void setDuration(int durationx)
	{
		this.duration = durationx;
	}
	
	public void setEndTime(int endtimex)
	{
		this.endtime = endtimex;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getStartTime()
	{
		return this.starttime;
	}
	
	public int getDuration()
	{
		return this.duration;
	}

	public int getEndTime()
	{
		return this.endtime;
	}
}
