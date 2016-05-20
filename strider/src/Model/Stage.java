package Model;

public class Stage 
{
	private int id;
	private int id_country_start;
	private int id_city_start;
	private int id_country_end;
	private int id_city_end;
	
	public Stage(int idx, int id_country_startx, int id_city_startx, int id_country_endx, int id_city_endx) 
	{
		id = idx;
		id_country_start = id_country_startx;
		id_city_start = id_city_startx;
		id_country_end = id_country_endx;
		id_city_end = id_city_endx;
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
}
