package countryWarnings;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CurrencyInformation {

	
CountryInformation country;

public CurrencyInformation (CountryInformation cntr){
	
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
				
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return information;
		
	}


String getCurrencyURL(){
	
	String url = "";
	
	try {	    
		
   	      String currencySQL = "Select C.CurrencyShortcut from DBA.Currency C inner join DBA.CountrysCurrency CC on C.IDCurrency = CC.IDCurrency"
   	      		+ " inner join DBA.Country CR on CC.IDCountry = CR.IDCountry where CR.CountryName = '" + this.country.countryName + "'";

   	      String connectionString = "jdbc:sqlanywhere:uid=Artureczek;pwd=debil";
  	      //String connectionString = "jdbc:sqlanywhere:uid="+"Artureczek"+";pwd="+"debil"+";eng=traveladvisordb;database=traveladvisordb;host=5.134.69.28:15144";
	      Connection con = DriverManager.getConnection(connectionString);					 	         			  
		  Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(currencySQL);
	      ObservableList cityData = FXCollections.observableArrayList();  
	      String shortcut = "";
	      
	      while (rs.next()){
	      shortcut = rs.getString("CurrencyShortcut");
	      }

	      rs.close();
	      stmt.close();
	      con.close();
	      
	      url = "http://www.x-rates.com/calculator/?from=" + shortcut + "&to=PLN&amount=1";
  	        	
  	      } catch (SQLException e1) {
  		
			e1.printStackTrace();
		}	
	
	
	
	return url;
	
	
}
	
	
}
