package countryWarnings;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;




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
    
    
    
    public static StringBuilder cityInformationString;
	public static CityInformation cityInformation;
	public static CountryInformation countryInformation;
	public static WeatherInformation weatherInformation;
	public static CurrencyInformation currencyInformation;
	public static CitiesList list;
	
	
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CountriesList.setCountryHtmls();
		CountriesList.setCountryNames(); //ladowanie list observablelist adresow oraz nazw panstw w klasie CountriesList
		Font myFont = new Font("Serif", 88);
		celsiusTextArea.setFont(myFont);
		celsiusTextArea.setEditable(false); //ustawianie czcionki i mozliwosci edytowania w celsiusTextArea
		setOpeningLinksToBrowser(cityWebView); //ustawianie otwierania linkow w cityWebView tak aby wyskakiwaly w przegladarce
		countryBox = new ComboBox();  //dodawanie comboboxow
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
		showMapBttn.setDisable(true); //wyszarz guzik do zmiany na mape do momentu, az nie zostanie wybrane miasto
		
		
		findCountryButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	 
	        countryInformation = new CountryInformation(countryBox.getEditor().getText(),
	        "http://www.polakzagranica.msz.gov.pl" + CountriesList.getCountryHtmlsPosition(setCityList()));        
	        currencyInformation = new CurrencyInformation(countryInformation);
	        
	        StringBuilder currencyInfoText = currencyInformation.getCurrencyInformationHtml();
	        
	        countryWebView.getEngine().load(countryInformation.countryURL);
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
        		
        		cityInformationString = cityInformation.getCityInformationHtml();      
        		cityWebView.getEngine().loadContent(cityInformationString.toString());  	
        	
        		weatherWebView.getEngine().load(weatherInformation.getPictureAdress());
        		celsiusTextArea.setText(weatherInformation.getCelsius());
        		weatherLabel.setText(weatherInformation.getWeatherDescription());
        		
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
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage; 
		
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	        	
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}

	
	public static void setOpeningLinksToBrowser(WebView webView){
		
		webView.getEngine().setCreatePopupHandler(
				    new Callback<PopupFeatures, WebEngine>() {
				        @Override
				        public WebEngine call(PopupFeatures config) {
				            // grab the last hyperlink that has :hover pseudoclass
				            Object o = webView
				                    .getEngine()
				                    .executeScript(
				                            "var list = document.querySelectorAll( ':hover' );"
				                                    + "for (i=list.length-1; i>-1; i--) "
				                                    + "{ if ( list.item(i).getAttribute('href') ) "
				                                    + "{ list.item(i).getAttribute('href'); break; } }");

				            // open in native browser
				            try {
				                if (o != null) {
				                    Desktop.getDesktop().browse(
				                            new URI(o.toString()));
				                } else {
				                    System.out.println("No result from uri detector: " + o);
				                }
				            } catch (IOException e) {
				               e.printStackTrace();
				            } catch (URISyntaxException e) {
				               e.printStackTrace();
				            }

				            // prevent from opening in webView
				            return null;
				        }
				    });
		
	}
	
	
	
	  
}

