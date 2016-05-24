package desktopGui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import cWHandlers.CountryWarningsHandlerCommon;
import countryWarnings.CityInformation;
import countryWarnings.CountryInformation;
import countryWarnings.CurrencyInformation;
import countryWarnings.SampleController;
import countryWarnings.WeatherInformation;
import dbHandlers.DatabaseHandlerCommon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class ControllerAdditionalInformations implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController; 
	
    @FXML
    private HBox hbox_countrybox;
    @FXML
    private HBox hbox_citybox;
    @FXML
    private WebView countryWebView;
    @FXML
    private WebView cityWebView;
    @FXML
    private WebView weatherWebView;
    @FXML
    private WebView currencyWebView;
    @FXML
    private Button button_back;
    @FXML
    private TextArea celsiusTextArea;
    
    //Sztuczne
    @FXML
    private Button button_findcountry;
    @FXML
    private Button button_findcity;
    @FXML
    private ComboBox<String> citybox;
    @FXML
    private ComboBox<String> countrybox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Font myFont = new Font("Serif", 50);
		celsiusTextArea.setFont(myFont);
		celsiusTextArea.setEditable(false);
		button_findcountry = new Button();
		button_findcountry.setText("Znajdü");
		button_findcity = new Button();
		button_findcity.setText("Znajdü");
		button_findcountry.setOnAction(this);
		button_findcity.setOnAction(this);
		button_back.setOnAction(this);
		citybox = WindowMain.getCityBox();
		countrybox = WindowMain.getCountryBox();
		hbox_citybox.getChildren().add(citybox);
		hbox_citybox.getChildren().add(button_findcity);
		hbox_countrybox.getChildren().add(countrybox);
		hbox_countrybox.getChildren().add(button_findcountry);
		
		
		SampleController.setOpeningLinksToBrowser(cityWebView);
		//weatherWebView
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_back)
		{
			clearComponents();
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == button_findcountry)
		{
			citybox.getSelectionModel().clearSelection();
			citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countrybox.getSelectionModel().getSelectedIndex()));
			
			CountryInformation countryinfo = CountryWarningsHandlerCommon.getInstance().getCountryInformation(countrybox.getSelectionModel().getSelectedItem());
		
			CurrencyInformation currencyinfo = new CurrencyInformation(countryinfo);
			currencyWebView.getEngine().loadContent(currencyinfo.getCurrencyInformationHtml().toString());
			countryWebView.getEngine().load(countryinfo.countryURL);
	
			System.out.println(countryinfo.countryURL);
		}
		else if(arg0.getSource() == button_findcity)
		{
			CityInformation cityinfo = CountryWarningsHandlerCommon.getInstance().getCityInformation(citybox.getSelectionModel().getSelectedItem());
			WeatherInformation weatherinfo = new WeatherInformation(cityinfo);
	
			cityWebView.getEngine().loadContent(cityinfo.getCityInformationHtml().toString());
			weatherWebView.getEngine().load(weatherinfo.pictureAdress);
			
			//URL weatherurl = getClass().getResource("textures/weather_icon.html");
			weatherWebView.getEngine().load(weatherinfo.pictureAdress);
			
			//weatherWebView.getEngine().load(weatherurl.toExternalForm());
			//weatherWebView.getEngine().setUserStyleSheetLocation(getClass().getResource("textures/css_weather_sunny.css").toString());
			//weatherWebView.getEngine().reload();
			celsiusTextArea.setText(weatherinfo.celsius);
		}
		
	}	

	@Override
	public void clearComponents() 
	{
		
	}
}
