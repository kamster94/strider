package desktopGui.old;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import countryWarnings.AutoCompleteComboBoxListener;
import countryWarnings.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MainApplicationWindowController_OBSOLETE implements Initializable, EventHandler<ActionEvent>
{
    @FXML
    private Button findCountryButton;

    @FXML
    private Button findCityButton;
    
    @FXML
    private WebView cityWebView;
    
    @FXML
    private WebView countryWebView;
    
    @FXML
    private WebView currencyWebView;
    
    @FXML
    private WebView weatherWebView;
    
	@FXML
	private Label mwlabelwelcomemessage;

	@FXML
	private Label mwlabelaccountstatus;

	@FXML
	private Label mwlabelversion;

	@FXML
	private ToggleButton mwtogglebuttoncreatenewtravel;

	@FXML
	private ToggleButton mwtogglebuttonviewcurrenttravel;

	@FXML
	private ToggleButton mwtogglebuttonadditionalinformation;

	@FXML
	private ToggleButton mwtogglebuttontravelhistory;

	@FXML
	private ToggleButton mwtogglebuttonreviewhotels;

	@FXML
	private Button mwbuttonexit;

	@FXML
	private Label mwailabelpanetype;

	@FXML
	private Label mwailabelcountry;

	@FXML
	private ImageView mwaiimageviewmszlogo;

	@FXML
	private ScrollPane mwaiscrollpanemsz;

	@FXML
	private Label mwailabelcity;

	@FXML
	private Label mwailabelcitydoesntexist;

	@FXML
	private ImageView mwaiimageviewmediawikilogo;

	@FXML
	private ScrollPane mwaiscrollpanemediawiki;

	@FXML
	private Button mwaibuttonok;

	@FXML
	private Label mwailabelcurrencyname;

	@FXML
	private Label mwailabelcurrencystate;

    @FXML
    private ScrollPane mwpaneadditionalinformation;
    
    @FXML
    private ScrollPane mwpanecreatenewtravel;
    
    @FXML
    private VBox vboxleftside;
    
    @FXML
    private VBox vboxrightside;
    
    @FXML
    private ComboBox<?> countryBox;
    
    @FXML
    private ComboBox<?> cityBox;
	
    @FXML
    private BorderPane mainborderpane;
    
    @FXML
    private VBox newtravelwizard_2;

    @FXML
    private VBox newtravelwizard_1;

    @FXML
    private VBox newtravelwizard_3;
    
    @FXML
    private VBox vboxcitybox;
    
    @FXML
    private VBox vboxcountrybox;
    
    @FXML
    private Button newtravelwizard_1_createnewtravel;

    @FXML
    private Button newtravelwizard_2_cancel;

    @FXML
    private Button newtravelwizard_2_next;
    
    @FXML
    private Button newtravelwizard_3_back;
    
	final ToggleGroup tg = new ToggleGroup();
	
	final Image mszlogoimage = new Image("desktopGui/textures/ta_msz.png");
	final Image mwikilogoimage = new Image("desktopGui/textures/ta_mwiki.png");
	
    private static int checkForMatchingCountry;  
	private static int num;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		mwtogglebuttoncreatenewtravel.setOnAction(this);
		mwtogglebuttonviewcurrenttravel.setOnAction(this);
		mwtogglebuttonadditionalinformation.setOnAction(this);
		mwtogglebuttontravelhistory.setOnAction(this);
		mwtogglebuttonreviewhotels.setOnAction(this);
		mwbuttonexit.setOnAction(this);

		newtravelwizard_1_createnewtravel.setOnAction(this);
		newtravelwizard_2_cancel.setOnAction(this);
		newtravelwizard_2_next.setOnAction(this);
		newtravelwizard_3_back.setOnAction(this);
		
		mwtogglebuttoncreatenewtravel.setToggleGroup(tg);
		mwtogglebuttonviewcurrenttravel.setToggleGroup(tg);
		mwtogglebuttonadditionalinformation.setToggleGroup(tg);
		mwtogglebuttontravelhistory.setToggleGroup(tg);
		mwtogglebuttonreviewhotels.setToggleGroup(tg);
		
		mwaiimageviewmszlogo.setImage(mszlogoimage);
		mwaiimageviewmediawikilogo.setImage(mwikilogoimage);
		
		mwpaneadditionalinformation.setDisable(true);
		mwpaneadditionalinformation.setVisible(false);
		
		mwpanecreatenewtravel.setDisable(true);
		mwpanecreatenewtravel.setVisible(false);
		
		findCountryButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	    String newLine = System.getProperty("line.separator");
	        		String htmlString = "";
	        	    String finalmente = "<font face='WildWest'>";        	
	        		String textFromCountryNameBox = MainApplicationWindow.countryBox.getEditor().getText(); 
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        		Document document;
	        		String cityNamesSql = "SELECT C.CityName FROM DBA.City C inner join DBA.Country Cr on C.IDCountry = Cr.IDCountry "
   	   	        	+ "where Cr.CountryName = '" +  textFromCountryNameBox +"' order by 1 asc";
   	   	            checkForMatchingCountry = 0;
   	   	            num = 0;
   	   	            setCurrency(textFromCountryNameBox);
	        	    	        	  	
	               try {								
	            	   for(int i = 0; i < MainApplicationWindow.countryNames.size(); i++){ //petla do szukania wpisanego kraju
				
							if(textFromCountryNameBox.equals(MainApplicationWindow.countryNames.get(i))){
							checkForMatchingCountry = 1;
							num = i;							
							}											
	            	   }
					//jesli wybrano miasto z listy dostepnych panstw
					if(checkForMatchingCountry == 1){
				    
						//Laczenie z baza danych do wyciagniecia listy miast na podstawie wybranego panstwa  	
						try {	    
				   	   	     
							  //String connectionString = "jdbc:sqlanywhere:uid=DBA;pwd=sql";
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
				   	 	  MainApplicationWindow.cityBox.setItems(cityData); 
				   	   	        	
				   	   	      } catch (SQLException e1) {
				   	   		
				   				e1.printStackTrace();
				   			}	
						
					//parsowanie informacji z MSZ	
						
					url+=MainApplicationWindow.countryHtmls.get(num);
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
	        	
	        	//laczenie z mediawiki w celu sciagniecia i parsowania informacji o ciekawych miejscach 
	        	//w okolicach wybranego miasta
	        	
	        	String htmlString = ""; 
	        	String textFromCityNameBox = MainApplicationWindow.cityBox.getEditor().getText();
	        	
	        	textFromCityNameBox = textFromCityNameBox.replaceAll(" ", "_");
	        	        	
        		String url = "https://pl.wikipedia.org/w/api.php?action=query&list=geosearch&gsradius=10000&gspage=";
        		String url2 =  "&gslimit=100&gsprop=type|name|&format=json";	   
        		url += textFromCityNameBox;
        		url += url2;
        		System.out.println(url);
        
        		String finalmente = "";
        	    try{
        		  InputStream is = new URL(url).openStream();
        	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        	      String jsonText = readAll(rd);
        	      JSONObject json = new JSONObject(jsonText);

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
  					 				
        	    }catch(JSONException e){
        	    	//e.printStackTrace();
        	    	cityWebView.getEngine().loadContent("<b>Nie znaleziono informacji o mieœcie<b>");
        	    }
	        	catch(IOException e1){
    	    	//e1.printStackTrace();
    	    	cityWebView.getEngine().loadContent("<b>Proszê poprawnie wpisaæ nazwê miasta<b>");
	        		}
        	    //cityWebView.getEngine().load("new.html");  	
        	   
        	    setWeather(textFromCityNameBox);
	        	
	        }
	    });
	}
	
	private void setWeather(String city){
		
	String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
			+ city + "%22%20)%20and%20u%3D'c'&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";	
		
		System.out.println(url);
	try{
		  InputStream is = new URL(url).openStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);

	      JSONArray arr  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
	      JSONObject obj  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
	      JSONObject con  = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition");

	      String nL = System.getProperty("line.separator");
	      String date ="Date: "+ obj.getString("pubDate");
	      String temp ="Temperatura: "+ con.getString("temp"); 
	      String weather = arr.getJSONObject(0).getString("text");  
	      
	      if(weather.equals("Showers"))
	    	  weather = "Ulewa";
	      else if(weather.equals("Mostly Cloudy"))
	    	  weather = "Du¿e zachmurzenie";
	      else if(weather.equals("Partly Cloudy"))
	    	  weather = "Czêœciowe zachmurzenie";
	      else if(weather.equals("Scattered Showers"))
	    	  weather = "Miejscowe opady";
	      else if(weather.equals("Scattered Showers"))
	    	  weather = "Deszcz ze œniegiem";
	      else if(weather.equals("Rain"))
	    	  weather = "Deszcz";
	      
	      weather = "Pogoda: " + weather;
	      weatherWebView.getEngine().loadContent(date + "</br></br>" + temp + "</br></br>" + weather);
	      

	    }catch(JSONException e){
	    	weatherWebView.getEngine().loadContent("Wyjeba³o b³¹d JSON :/");
		      
	    }
  		catch(IOException e1){
  			weatherWebView.getEngine().loadContent("Wyjeba³o b³¹d IO :/");
  		}
	     
	
	}
	
	
	private void setCurrency(String country){
		
		String url = "";
		
		try {	    
  	   	      String currencySQL = "Select C.CurrencyShortcut from DBA.Currency C inner join DBA.CountrysCurrency CC on C.IDCurrency = CC.IDCurrency"
  	   	      		+ " inner join DBA.Country CR on CC.IDCountry = CR.IDCountry where CR.CountryName = '" + country + "'";
	   	     	
  	   	    //  String currencySQL = "Select * from Currency C";
	   	     
  	   	 
  	   	     // String connectionString = "jdbc:sqlanywhere:uid=Artureczek;pwd=debil";
 	   	      String connectionString = "jdbc:sqlanywhere:uid="+"Artureczek"+";pwd="+"debil"+";eng=traveladvisordb;database=traveladvisordb;host=5.134.69.28:15144";
 	 	      Connection con = DriverManager.getConnection(connectionString);					 	         			  
			  Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(currencySQL);
		      ObservableList cityData = FXCollections.observableArrayList();  
		      String shortcut = "";
		      
		      while (rs.next()){
 	 	      shortcut = rs.getString("CurrencyShortcut");
 	 	      //System.out.println(shortcut);
		      }
 	
 	 	      rs.close();
 	 	      stmt.close();
 	 	      con.close();
 	 	      
 	 	      url = "http://www.x-rates.com/calculator/?from=" + shortcut + "&to=PLN&amount=1";
 	   	        	
 	   	      } catch (SQLException e1) {
 	   		
 				e1.printStackTrace();
 			}	
		
		try{
		Document document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")		
				.get();
				
				Elements foreign = document.select(("span[class=ccOutputTxt]"));//ostrzezenia o panstwie
				Elements pln = document.select(("span[class=ccOutputRslt]")); //informacje o panstwie
				
				if(!foreign.text().equals("0.00 --- ="))
				currencyWebView.getEngine().loadContent(foreign.text() + pln.text());
				else
					currencyWebView.getEngine().loadContent("Nie znaleziono przelicznika waluty");
				
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	
	
	//Ponizsze dwie funkcje zwracaja VBoxy do ktorych trzeba dodac uzupelniajki
	public VBox getVboxCitybox()
	{
		return vboxcitybox;
	}
	
	public VBox getVboxCountrybox()
	{
		return vboxcountrybox;
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == mwbuttonexit)
		{
			MainApplicationWindow.closeWindow();
		}
		else if(arg0.getSource() == newtravelwizard_1_createnewtravel)
		{
			newtravelwizard_1.setDisable(true);
			newtravelwizard_1.setVisible(false);
			
			newtravelwizard_2.setDisable(false);
			newtravelwizard_2.setVisible(true);
		}
		else if(arg0.getSource() == newtravelwizard_2_cancel)
		{
			newtravelwizard_1.setDisable(false);
			newtravelwizard_1.setVisible(true);
			
			newtravelwizard_2.setDisable(true);
			newtravelwizard_2.setVisible(false);
		}
		else if(arg0.getSource() == newtravelwizard_2_next)
		{
			newtravelwizard_2.setDisable(true);
			newtravelwizard_2.setVisible(false);
			
			newtravelwizard_3.setDisable(false);
			newtravelwizard_3.setVisible(true);
		}
		else if(arg0.getSource() == newtravelwizard_3_back)
		{			
			newtravelwizard_3.setDisable(true);
			newtravelwizard_3.setVisible(false);
			
			newtravelwizard_2.setDisable(false);
			newtravelwizard_2.setVisible(true);
		}
		else if(arg0.getSource() == mwtogglebuttoncreatenewtravel ||
				arg0.getSource() == mwtogglebuttonviewcurrenttravel ||
				arg0.getSource() == mwtogglebuttonadditionalinformation ||
				arg0.getSource() == mwtogglebuttontravelhistory ||
				arg0.getSource() == mwtogglebuttonreviewhotels)
		{
			if(mwtogglebuttonadditionalinformation.isSelected())
			{
				mwpaneadditionalinformation.setDisable(false);
				mwpaneadditionalinformation.setVisible(true);
			}
			else
			{
				mwpaneadditionalinformation.setDisable(true);
				mwpaneadditionalinformation.setVisible(false);
			}
			
			if(mwtogglebuttoncreatenewtravel.isSelected())
			{
				mwpanecreatenewtravel.setDisable(false);
				mwpanecreatenewtravel.setVisible(true);
				newtravelwizard_1.setDisable(false);
				newtravelwizard_1.setVisible(true);
			}
			else
			{
				mwpanecreatenewtravel.setDisable(true);
				mwpanecreatenewtravel.setVisible(false);
				
				newtravelwizard_1.setDisable(true);
				newtravelwizard_1.setVisible(false);
				
				newtravelwizard_2.setDisable(true);
				newtravelwizard_2.setVisible(false);
				
				newtravelwizard_3.setDisable(true);
				newtravelwizard_3.setVisible(false);
			}
		}
	}
}
