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
import javafx.scene.control.Label;
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

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import dbConnection.DbAccess;



public class SampleController implements Initializable{

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
    private Label currencyLabel;
    
    @FXML
    private WebView weatherWebView;
    
    @FXML
    private Label weatherLabel;
    
    @FXML
    private Button showMapBttn;
    
    private static int checkForMatchingCountry;  
	private static int num;
	private static ArrayList<Double> coords;
	public static CityInformation cityInformation;
	public static CountryInformation countryInformation;
	public static WeatherInformation weatherInformation;
	public static CurrencyInformation currencyInformation;
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		showMapBttn.setDisable(true);
		
		findCountryButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	 
	        countryInformation = new CountryInformation( Main.countryBox.getEditor().getText(),
	        "http://www.polakzagranica.msz.gov.pl" + Main.countryHtmls.get(setCityList()));        
	        currencyInformation = new CurrencyInformation(countryInformation);
	        
	        StringBuilder countryInfoText =  countryInformation.getCountryInformationHtml();
	        StringBuilder currencyInfoText = currencyInformation.getCurrencyInformationHtml();
	        
	        countryWebView.getEngine().loadContent(countryInfoText.toString());
	        currencyWebView.getEngine().loadContent(currencyInfoText.toString());

	        		        	
	        }
	    });
		
		
		findCityButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	
	        	//laczenie z mediawiki w celu sciagniecia i parsowania informacji o ciekawych miejscach 
	        	//w okolicach wybranego miasta	        	
	
        		cityInformation = new CityInformation(Main.cityBox.getEditor().getText().replaceAll(" ", "_"));
        		weatherInformation = new WeatherInformation(cityInformation);
        		
        		StringBuilder cityInformationString = cityInformation.getCityInformationHtml();
        		StringBuilder weatherInformationString = weatherInformation.getWeatherInformationHtml();
        		
        		cityWebView.getEngine().loadContent(cityInformationString.toString());  	
        		weatherWebView.getEngine().loadContent(weatherInformationString.toString());
        		
        		showMapBttn.setDisable(false);
	        }
	    });

		
		showMapBttn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        changeToMap();	        		       	        	
	        }
	    });

	}
	
	private int setCityList(){
		
		int checkForMatchingCountry = 0;
		int num = 0;
		String textFromCountryNameBox = Main.countryBox.getEditor().getText(); 
		String cityNamesSql = "SELECT C.CityName FROM DBA.City C inner join DBA.Country Cr on C.IDCountry = Cr.IDCountry "
	   	        	+ "where Cr.CountryName = '" +  textFromCountryNameBox +"' order by 1 asc";
		
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
	       	   	     
			  //String connectionString = "jdbc:sqlanywhere:uid=Artureczek;pwd=debil";
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
	}
   	   	        	
   	   	      } catch (SQLException e1) {
   	   		
   				e1.printStackTrace();
   			}		
		
	return num;
   
	}
   		
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
		
	private static void changeToMap(){
      
		Platform.runLater(new Runnable() {
			
			@Override public void run() {
				  Main.mapOptions.center(CityInformation.coordinations)
		            .mapType(MapTypeIdEnum.ROADMAP)
		            .overviewMapControl(false)
		            .panControl(false)
		            .rotateControl(false)
		            .scaleControl(false)
		            .streetViewControl(false)
		            .zoomControl(false)
		            .zoom(12);

		    Main.map = Main.mapView.createMap(Main.mapOptions);		
			Main.mainStage.setScene(Main.scene2);
			Main.mainStage.show();
			
			  }
			});
	   
	}
	

}




