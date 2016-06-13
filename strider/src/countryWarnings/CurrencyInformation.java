package countryWarnings;

import java.awt.List;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dbConnection.DbAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CurrencyInformation {

	
CountryInformation country;
static DbAccess dataBaseConnection;

public CurrencyInformation (CountryInformation cntr)
{
		this.country = cntr;	
}
	
	
	
public StringBuilder getCurrencyInformationHtml(){
		
		
		StringBuilder information = new StringBuilder();		
		String url = getCurrencyURL();
		
		
		try{
		Document document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")		
				.get();
				
				Elements foreign = document.select(("span[class=ccOutputTxt]"));
				Elements pln = document.select(("span[class=ccOutputRslt]")); 
				
				if(!foreign.text().equals("0.00 --- ="))
				information.append(foreign.text() + pln.text());
				else
				information.append("Nie znaleziono przelicznika waluty");
				
		}catch(UnknownHostException e1){
		    information.append("Brak po��czenia z internetem");
		}catch(IOException e){
			information.append("Nie znaleziono przelicznika waluty");
		}
		
		return information;
		
	}


String getCurrencyURL(){
	
	String url = "";
	dataBaseConnection = DbAccess.getInstance();
	
	String currencySQL = "Select C.CurrencyShortcut from DBA.Currency C inner join DBA.CountrysCurrency CC on C.IDCurrency = CC.IDCurrency"
	+ " inner join DBA.Country CR on CC.IDCountry = CR.IDCountry where CR.CountryName = '" + this.country.countryName + "'";
	
	String shortcut = dataBaseConnection.getSingeStringFromDb(currencySQL, "CurrencyShortcut");
	url = "http://www.x-rates.com/calculator/?from=" + shortcut + "&to=PLN&amount=1";
	return url;
		
}

	public static double getUserCurrencyCost(double cost, String currency, String usersCurrency){
	
		
	double usersCost = 0;
	String url = "http://www.x-rates.com/calculator/?from=" + currency + "&to=" + usersCurrency + "&amount=" +cost;
		
	try
	{
		Document document = Jsoup.connect(url)
		.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
		.referrer("http://www.google.com")		
		.get();
				
		Elements foreign = document.select(("span[class=ccOutputTxt]"));
		Elements users = document.select(("span[class=ccOutputRslt]")); 
				
		if(!foreign.text().equals("0.00 --- ="))
		{
			String str = users.text().replaceAll(" ", "").replaceAll("[A-Z]", "");
			int i = 0;
			
			while(!str.substring(i,i+1).equals("."))i++;
				
						
			str = str.substring(0, i+3);
				
			//Adrianek: Ten kalkulator dopierdala przecinki oddzielajace tysiace i sie spierdala jak podroz duzo kosztuje, to fixuje
			str = str.replaceAll(",", "");
				
			usersCost = Double.valueOf(str);
		}
		else usersCost = 0;
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
			System.out.println("Nie znaleziono przelicznika waluty");
		}
	return usersCost;
}
	
	
}
