package countryWarnings;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CountryInformation {
	
	public String countryName;
	public String countryURL;
	
	public CountryInformation(String name, String html){
		
		this.countryName = name;
		this.countryURL = html;
		
	}
	
	
    public StringBuilder getCountryInformationHtml(){
    	
    	StringBuilder information = new StringBuilder("<font face='WildWest'>");
  	    String htmlString = "<font face='WildWest'>";        	
  		Document document;

	       try{ 		   
			System.out.println(this.countryURL);
			document = Jsoup.connect(this.countryURL)
			.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			.referrer("http://www.google.com")		
			.get();
			
			Elements elems3 = document.select(("div[class=warning]"));//ostrzezenia o panstwie
			Elements elems4 = document.select(("div[class=item]")); //informacje o panstwie
			
			
			for (Element link : elems3) {
				
				Elements linkChildren = link.children();
				
				for (Element elem : linkChildren) {

					if(elem.tagName().equals("a")){
					
					Elements linkGrandChildren = elem.children();
						for (Element elem2 : linkGrandChildren) {

						if(elem2.tagName().equals("h3"))
						htmlString += "<b>" + elem2.text().toUpperCase() + "</b></br>";		

						}	        
					}
			        if(elem.tagName().equals("div"))
			        htmlString += elem.text() + "</br>";		
			        
			    }
				htmlString += "</br>";				

			}										
			for (Element link : elems4) {
				
				Elements linkChildren = link.children();
				
				for (Element elem : linkChildren) {					        
			        if(elem.tagName().equals("h3"))
			        htmlString += "<b>" + elem.text().toUpperCase() + "</b></br>";
			        
			        if(elem.tagName().equals("div"))
				    htmlString += elem.text() + "</br>";
			
			    }
				htmlString += "</br></font>";	
				
			}
			//zapisywanie plikow cache o wybranych juz panstwach
			 createHtmlFile(htmlString);
								
		} catch(NullPointerException e1){
			htmlString ="<b>Prosze wpisac poprawnie nazwe Panstwa<b>";
		} 
	       
	       
	       
	       catch (UnknownHostException e2) {
			e2.printStackTrace();
			htmlString ="<b>MSZ nie dostarcza informacji o Polsce.<b>";
		}
	       catch (IOException e) {
			e.printStackTrace();
			htmlString ="<b>Brak po³¹czenia z internetem<b>";
		}
	      
	       information.append(htmlString);
    	
    	   return information;
    }
    
    public void createHtmlFile(String html) throws IOException{
    	
    	String htmlString = "";
    	File htmlTemplateFile = new File("./htmls/template.html");
		htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = "";
		htmlString = htmlString.replace("$title", title);
		htmlString = htmlString.replace("$body",  html);
		File newHtmlFile = new File("./htmls/" + this.countryName + ".html");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);
    	   	      	
    }
	
	

}
