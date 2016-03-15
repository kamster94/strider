package countryWarnings;


import org.json.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbConnection.DbAccess;



public class SampleController implements Initializable{

    @FXML
    private Button findButton;

    @FXML
    private Button findCityButton;
        
    @FXML
    private WebView cityWebView;
    
    @FXML
    private WebView countryWebView;
    
    private static int checkForMatchingCountry;  
	private static int num;
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		findButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	    String newLine = System.getProperty("line.separator");
	        		String htmlString = "";
	        	    String finalmente = "<font face='WildWest'>";        	
	        		String textFromCountryNameBox = Main.countryBox.getEditor().getText(); 
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        		Document document;
	        		String cityNamesSql = "SELECT C.CityName FROM DBA.City C inner join DBA.Country Cr on C.IDCountry = Cr.IDCountry "
   	   	        	+ "where Cr.CountryName = '" +  textFromCountryNameBox +"'";
   	   	            checkForMatchingCountry = 0;
   	   	            num = 0;
	        	    
	        	    	        	  	
	               try {								
	            	   for(int i = 0; i < Main.countryNames.size(); i++){ //petla do szukania wpisanego kraju
				
							if(textFromCountryNameBox.equals(Main.countryNames.get(i))){
							checkForMatchingCountry = 1;
							num = i;							
							}											
	            	   }
					//jesli wybrano miasto z listy dostepnych panstw
					if(checkForMatchingCountry == 1){
				    
						//Laczenie z baza danych do wyciagniecia listy miast na podstawie wybranego panstwa  	
						try {	    
				   	   	     
				   	   	     	
				   	   	      String connectionString = "jdbc:sqlanywhere:uid="+"Artureczek"+";pwd="+"debil"+";eng=traveladvisordb;database=traveladvisordb;host=5.134.69.28:15144";
				   	 	      Connection con = DriverManager.getConnection(connectionString);					 	         			  
							  Statement stmt = con.createStatement();
						      ResultSet rs = stmt.executeQuery(cityNamesSql);
						      ObservableList cityData = FXCollections.observableArrayList();  
				   	 	       
						      while (rs.next())
				   	 	      cityData.add(rs.getString("CityName"));
				   	
				   	 	      rs.close();
				   	 	      stmt.close();
				   	 	      con.close();
				   	 	      Main.cityBox.setItems(cityData); 
				   	   	        	
				   	   	      } catch (SQLException e1) {
				   	   		
				   				e1.printStackTrace();
				   			}	
						
					//parsowanie informacji z MSZ	
						
					url+=Main.countryHtmls.get(num);
					document = Jsoup.connect(url)
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
								finalmente += "<b>" + elem2.text().toUpperCase() + "</b></br>";						
								}	        
							}
					        if(elem.tagName().equals("div"))
					        finalmente += elem.text() + "</br>";				        
					    }
						finalmente += "</br>";				
	
					}										
					for (Element link : elems4) {
						
						Elements linkChildren = link.children();
						
						for (Element elem : linkChildren) {					        
					        if(elem.tagName().equals("h3"))
					        finalmente += "<b>" + elem.text().toUpperCase() + "</b></br>";
					        
					        if(elem.tagName().equals("div"))
						    finalmente += elem.text() + "</br>";
					        
					    }
						finalmente += "</br></font>";								
					}
					//zapisywanie plikow cache o wybranych juz panstwach
					File htmlTemplateFile = new File("./htmls/template.html");
	  				htmlString = FileUtils.readFileToString(htmlTemplateFile);
	  				String title = "";
	  				htmlString = htmlString.replace("$title", title);
	  				htmlString = htmlString.replace("$body", finalmente);
	  				File newHtmlFile = new File("./htmls/" + textFromCountryNameBox + ".html");
	  				FileUtils.writeStringToFile(newHtmlFile, htmlString);

						
					}else{
					//jesli wpisano cos innego niz nazwe panstwa z listy
					finalmente ="<b>Prosze wpisac poprawnie nazwe Panstwa<b>";

					}
					//zaladuj html do WebView
					
					
				} catch(NullPointerException e1){
					finalmente ="<b>Prosze wpisac poprawnie nazwe Panstwa<b>";
				} catch (IOException e) {
					finalmente ="<b>Brak po³¹czenia z internetem<b>";
				} finally{
					countryWebView.getEngine().loadContent(finalmente);
				}

            
	        }
	    });
		
		
		findCityButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	
	        	String htmlString = ""; 
	        	String textFromCityNameBox = Main.cityBox.getEditor().getText();
        		String url = "https://pl.wikipedia.org/w/api.php?action=query&list=geosearch&gsradius=10000&gspage=";
        		String url2 =  "&gslimit=100&gsprop=type|name|&format=json";	   
        		url += textFromCityNameBox;
        		url += url2;
        
        		String finalmente = "";
        	    try{
        		  InputStream is = new URL(url).openStream();
        	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        	      String jsonText = readAll(rd);
        	      JSONObject json = new JSONObject(jsonText);
        	     // System.out.println(jsonText);

        	      JSONArray arr  = json.getJSONObject("query").getJSONArray("geosearch");
				  
				  finalmente += "<b><font face='WildWest'>INTERESUJ¥CE MIEJSCA I WYDARZENIA</b></font></br></br>";
  				for (int i = 0; i < arr.length(); i++)
  				{
  					String title = arr.getJSONObject(i).getString("title");  				   		
  				    finalmente += title;
  				    finalmente += "</br>";  	  				    
  				}
  				finalmente += "</font>";
  				File htmlTemplateFile = new File("./htmls/template.html");
  				htmlString = FileUtils.readFileToString(htmlTemplateFile);
  				String title = "";
  				htmlString = htmlString.replace("$title", title);
  				htmlString = htmlString.replace("$body", finalmente);
  				File newHtmlFile = new File("./htmls/" + textFromCityNameBox + ".html");
  				FileUtils.writeStringToFile(newHtmlFile, htmlString);
  				cityWebView.getEngine().loadContent(finalmente);
  			//	Main.webEngine.load("new.html");
  			//	cityWebView.getEngine().load(newHtmlFile.toURI().toURL().toString());  				
  					 				
        	    }catch(Exception e){
        	    	 cityWebView.getEngine().loadContent("<b>Proszê poprawnie wpisaæ nazwê miasta<b>");
        	        	    }
       	   
        	    //cityWebView.getEngine().load("new.html");  	
        	   
        	     
	        	
	        }
	    });

    
	}

	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
}
