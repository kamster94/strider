package Model;

public class Stage 
{
	private int id;
	private int id_country_start;
	private int id_city_start;
	private int id_country_end;
	private int id_city_end;
	private StageType stype;
	
	public Stage() 
	{
		// TODO Auto-generated constructor stub
	}

	public void setId(int idx)
	{
		id = idx;
	}
	public void setStartCountryId(int idx)
	{
		id_country_start = idx;
	}
	public void setStartCityId(int idx)
	{
		id_city_start = idx;
	}
	public void setEndCountryId(int idx)
	{
		id_country_end = idx;
	}
	public void setEndCityId(int idx)
	{
		id_city_end = idx;
	}
	public void setStageType(StageType stypex)
	{
		stype = stypex;
	}
	
	public int getId()
	{
		return id;
	}
	public int getStartCountryId()
	{
		return id_country_start;
	}
	public int getStartCityId()
	{
		return id_city_start;
	}
	public int getEndCountryId()
	{
		return id_country_end;
	}
	public int getEndCityId()
	{
		return id_city_end;
	}
	public StageType getStageType()
	{
		return stype;
	}
}
