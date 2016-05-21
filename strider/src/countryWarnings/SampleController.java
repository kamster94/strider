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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
import desktopGui.ControlledScreen;
import desktopGui.ScreensController;
import desktopGui.WindowMain;



public class SampleController implements Initializable, ControlledScreen{

	ScreensController myController; 
	
	  @FXML
	    private AnchorPane pane;

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

	    @FXML
	    private TextArea celsiusTextArea;
    
    private ComboBox<String> cityBox;
    private ComboBox<String> countryBox;
    
    
    
    
	public static CityInformation cityInformation;
	public static CountryInformation countryInformation;
	public static WeatherInformation weatherInformation;
	public static CurrencyInformation currencyInformation;
	public static CitiesList list;
	
	
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CountriesList.setCountryHtmls();
		CountriesList.setCountryNames();
		Font myFont = new Font("Serif", 88);
		celsiusTextArea.setFont(myFont);
		celsiusTextArea.setEditable(false);
		
		countryBox = new ComboBox();
		countryBox.setItems(CountriesList.getCountryNamesList());
		new AutoCompleteComboBoxListener(countryBox);
		countryBox.setLayoutX(37);
		countryBox.setLayoutY(40);
		countryBox.setPrefWidth(190);
				
		cityBox = new ComboBox();	
		new AutoCompleteComboBoxListener(cityBox);
		cityBox.setLayoutX(490);
		cityBox.setLayoutY(40);
		cityBox.setPrefWidth(190);
		

		pane.getChildren().addAll(countryBox, cityBox);		
		showMapBttn.setDisable(true);
		
		findCountryButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	 
	        countryInformation = new CountryInformation(countryBox.getEditor().getText(),
	        "http://www.polakzagranica.msz.gov.pl" + CountriesList.getCountryHtmlsPosition(setCityList()));        
	        currencyInformation = new CurrencyInformation(countryInformation);
	        
	        StringBuilder countryInfoText =  countryInformation.getCountryInformationHtml();
	        StringBuilder currencyInfoText = currencyInformation.getCurrencyInformationHtml();
	        
	        countryWebView.getEngine().load(countryInformation.countryURL);
	        //countryWebView.getEngine().loadContent(countryInfoText.toString());
	        currencyWebView.getEngine().loadContent(currencyInfoText.toString());
	        
	        		        	
	        }
	    });
		
		
		findCityButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	
	        	//laczenie z mediawiki w celu sciagniecia i parsowania informacji o ciekawych miejscach 
	        	//w okolicach wybranego miasta	        	
	
        		cityInformation = new CityInformation(cityBox.getEditor().getText().replaceAll(" ", "_"),true);
        		weatherInformation = new WeatherInformation(cityInformation);
        		
        		StringBuilder cityInformationString = cityInformation.getCityInformationHtml();
        		StringBuilder weatherInformationString = weatherInformation.getWeatherInformationHtml();
        		
        		cityWebView.getEngine().loadContent(cityInformationString.toString());  	
        	//	weatherWebView.getEngine().loadContent(weatherInformationString.toString());
        		weatherWebView.getEngine().load(weatherInformation.pictureAdress);
        		celsiusTextArea.setText(weatherInformationString.toString());
        		showMapBttn.setDisable(false);
	        }
	    });

		
		showMapBttn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        myController.setScreen(Main.MAP);        	
	        MapController.map.setCenter(cityInformation.coordinations);
	        }
	    });

	}
	
	private int setCityList(){

	   int num = 0;
	   String selectedCountry = countryBox.getEditor().getText(); 
							
	   for(int i = 0; i < CountriesList.getCountryNamesList().size(); i++)
	   {   //petla do szukania koncowki html wpisanego kraju
			if(selectedCountry.equals(CountriesList.getCountryNamesList().get(i)))
			num = i;																				
	   }	   		   
	   CitiesList.setListOfCities(selectedCountry);
       cityBox.setItems(CitiesList.getListOfCities()); 
		   
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
		


	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage; 
		
	}
	

}




