package cWHandlers;

import java.util.List;

import countryWarnings.CitiesList;
import countryWarnings.CityInformation;
import countryWarnings.CountriesList;
import countryWarnings.CountryInformation;
import countryWarnings.CurrencyInformation;
import countryWarnings.Main;
import countryWarnings.WeatherInformation;
import dbHandlers.DatabaseHandlerCommon;

public class CountryWarningsHandlerCommon 
{
	private static CountryWarningsHandlerCommon myinstance;
	
	private CountryWarningsHandlerCommon()
	{
		CountriesList.setCountryNames();
		CountriesList.setCountryHtmls();
	}

	public static CountryWarningsHandlerCommon getInstance()
	{
		if(myinstance == null)myinstance = new CountryWarningsHandlerCommon();
		return myinstance;
	}
	
	public String getCountryInformation(String countryname)
	{
        CountryInformation countryInformation = new CountryInformation(countryname, "http://www.polakzagranica.msz.gov.pl" + CountriesList.getCountryHtmlsPosition(setCityList(countryname)));  
		return countryInformation.getCountryInformationHtml().toString();
	}
	
	public String getCityInformation(String cityname)
	{
		CityInformation ci = new CityInformation(cityname.replaceAll(" ", "_"),false);
		return ci.getCityInformationHtml().toString();
	}
	
	public String getWeatherInformation(String cityname)
	{
		CityInformation ci = new CityInformation(cityname.replaceAll(" ", "_"),false);
		WeatherInformation weatherInformation = new WeatherInformation(ci);
		return weatherInformation.getWeatherInformationHtml().toString();
	}
	
	public String getCurrencyInformation(String countryname)
	{
		CountryInformation countryInformation = new CountryInformation(countryname, "http://www.polakzagranica.msz.gov.pl" + CountriesList.getCountryHtmlsPosition(setCityList(countryname)));  
		CurrencyInformation currencyInformation = new CurrencyInformation(countryInformation);
		return currencyInformation.getCurrencyInformationHtml().toString();
	}
	
	private int setCityList(String countryname)
	{
		int num = -1;
		List<String> countrylist = DatabaseHandlerCommon.getInstance().getCountriesList();
		
		for(int i = 0; i < countrylist.size(); i++)
		{ 
			if(countryname.equals(countrylist.get(i)))
				num = i;																				
		}	   		   
		return num;
	}
}
