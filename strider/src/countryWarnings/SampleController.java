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
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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



public class SampleController implements Initializable{

    @FXML
    private Button findButton;

    @FXML
    private Button findCityButton;
    
    @FXML
    private TextField cityTextField;
    
    @FXML
    private WebView cityWebView;
    
    @FXML
    private WebView countryWebView;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String newLine = System.getProperty("line.separator");
		findButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	   
	        		String htmlString = "";
	        	    String finalmente = "<font face='WildWest'>";        	
	        		String textFromCountryNameBox = Main.box.getEditor().getText(); 
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        	    Document document;
	        	    
	        	    int checkForMatchingCountry = 0;  
					int num=0;
	        	    	        	  	
	               try {
			
					
					for(int i = 0; i < Main.countryNames.size(); i++){ //petla do szukania wpisanego kraju
						
							if(textFromCountryNameBox.equals(Main.countryNames.get(i))){
							checkForMatchingCountry = 1;
							num = i;
						}
						
						
					}
					
					if(checkForMatchingCountry == 1){
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
					
					File htmlTemplateFile = new File("./htmls/template.html");
	  				htmlString = FileUtils.readFileToString(htmlTemplateFile);
	  				String title = "";
	  				htmlString = htmlString.replace("$title", title);
	  				htmlString = htmlString.replace("$body", finalmente);
	  				File newHtmlFile = new File("./htmls/" + textFromCountryNameBox + ".html");
	  				FileUtils.writeStringToFile(newHtmlFile, htmlString);

						
					}else{
					
					finalmente ="<b>Prosze wpisac poprawnie nazwe Panstwa<b>";

					}
					
					countryWebView.getEngine().loadContent(finalmente);
					
					/*
					String url2 = Main.url2;
					url2+= Main.wikiCitiesListHtmls.get(num);
					Main.data2 = FXCollections.observableArrayList();
					
					document = Jsoup.connect(url2)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();
					
					Elements elems = document.select(("table[class=wikitable sortable]"));//ostrzezenia o panstwie

					Element first = elems.first();
					
					Elements linkChildren = first.children();
					
					for (Element link : linkChildren) {
						
						
						 
						Elements linkGrandChildren = link.children();
												
							for (Element elem2 : linkGrandChildren) {					        
								
								Elements linkGrandGrandChildren = elem2.children();
							
								for (Element elem3 : linkGrandGrandChildren) {					        
								     
									Elements linkGrandGrandGrandChildren = elem3.children();
									
									for (Element elem5 : linkGrandGrandGrandChildren) {					         
												if(elem5.tagName().equals("a"))
											    Main.data2.add(elem5.text());
											
										    }
						
							}					        					        
					    }																	
					}
					
					Main.box2.setItems(Main.data2);
					
					*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            
	        }
	    });
		
		
		findCityButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	String htmlString = ""; 
	        	String textFromCityNameBox = cityTextField.getText(); 
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
        	      System.out.println(jsonText);

        	      JSONArray arr  = json.getJSONObject("query").getJSONArray("geosearch");
				  
				  finalmente += "<b><font face='WildWest'>INTERESUJ¥CE MIEJSCA I WYDARZENIA</b></font></br></br>";
  				for (int i = 0; i < arr.length(); i++)
  				{
  					String title = arr.getJSONObject(i).getString("title");  				   		
  				    finalmente += title;
  				    finalmente += "</br>";  	  				    
  				}
  			//	System.out.println(finalmente); 
  				finalmente += "</font>";
  				File htmlTemplateFile = new File("./htmls/template.html");
  				htmlString = FileUtils.readFileToString(htmlTemplateFile);
  				String title = "";
  				htmlString = htmlString.replace("$title", title);
  				htmlString = htmlString.replace("$body", finalmente);
  				File newHtmlFile = new File("./htmls/" + textFromCityNameBox + ".html");
  				FileUtils.writeStringToFile(newHtmlFile, htmlString);
      	 
  			//	Main.webEngine.load("new.html");
  			//	cityWebView.getEngine().load(newHtmlFile.toURI().toURL().toString());  				
  					 				
        	    }catch(Exception e){
        	    	 cityWebView.getEngine().loadContent("<b>Proszê poprawnie wpisaæ nazwê miasta<b>");
        	        	    }
       	   
        	    //cityWebView.getEngine().load("new.html");  	
        	    cityWebView.getEngine().loadContent(finalmente);
        	     
	        	
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
