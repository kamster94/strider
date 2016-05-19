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
		//TODO: Zwraca� info o pa�stwie w postaci stringa
		return null;
	}
	
	public String getCityInformation(String cityname)
	{
		//TODO: Zwraca� info o ciekawych rzeczach z/wok� danego miasta (nazwa miasta dobra do wyszukiwania? xd)
		return null;
	}
	
	public String getWeatherInformation(String cityname)
	{
		//TODO: Zwraca� info o pogodzie w danym mie�cie
		return null;
	}
	
	public String getCurrencyInformation(String countryname)
	{
		//TODO: Zwraca� info o walucie danego pa�stwa
		return null;
	}
}
