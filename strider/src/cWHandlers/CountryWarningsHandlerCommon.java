package cWHandlers;

import countryWarnings.CountriesList;
import countryWarnings.CountryInformation;
import countryWarnings.CurrencyInformation;
import countryWarnings.Main;

public class CountryWarningsHandlerCommon 
{
	private static CountryWarningsHandlerCommon myinstance;
	
	private CountryWarningsHandlerCommon(){}

	public static CountryWarningsHandlerCommon getInstance()
	{
		if(myinstance == null)myinstance = new CountryWarningsHandlerCommon();
		return myinstance;
	}
	
	public String getCountryInformation(String countryname)
	{
		//TODO: Zwracaæ info o pañstwie w postaci stringa
		return null;
	}
	
	public String getCityInformation(String cityname)
	{
		//TODO: Zwracaæ info o ciekawych rzeczach z/wokó³ danego miasta (nazwa miasta dobra do wyszukiwania? xd)
		return null;
	}
	
	public String getWeatherInformation(String cityname)
	{
		//TODO: Zwracaæ info o pogodzie w danym mieœcie
		return null;
	}
	
	public String getCurrencyInformation(String countryname)
	{
		//TODO: Zwracaæ info o walucie danego pañstwa
		return null;
	}
}
