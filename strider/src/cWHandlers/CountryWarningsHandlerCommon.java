package cWHandlers;

import java.util.List;

import countryWarnings.CitiesList;
import countryWarnings.CityInformation;
import countryWarnings.CountriesList;
import countryWarnings.CountryInformation;
import countryWarnings.CurrencyInformation;
import countryWarnings.Main;
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
		CityInformation.getInstance().setCityName(cityname.replaceAll(" ", "_"));
		return CityInformation.getInstance().getCityInformationHtml().toString();
	}
	
	public String getWeatherInformation(String cityname)
	{
		//TODO: Zwracaæ info o pogodzie w danym mieœcie
		return null;
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
