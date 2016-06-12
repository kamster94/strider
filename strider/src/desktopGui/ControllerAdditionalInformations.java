package desktopGui;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class ControllerAdditionalInformations implements Initializable, ControlledScreen, EventHandler<ActionEvent>
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
		button_findcountry.setText("ZnajdŸ");
		button_findcity = new Button();
		button_findcity.setText("ZnajdŸ");
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
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	public void clearCityComponents()
	{
		cityWebView.getEngine().load(null);
		weatherWebView.getEngine().load(null);
		celsiusTextArea.setText("");
	}
	
	public void clearCountryComponents()
	{
		currencyWebView.getEngine().load(null);
		countryWebView.getEngine().load(null);
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_back)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == button_findcountry)
		{
			citybox.getSelectionModel().clearSelection();
			
			if(countrybox.getSelectionModel().isEmpty() == false)
			{
				clearCountryComponents();
				clearCityComponents();
				
				citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countrybox.getSelectionModel().getSelectedIndex()));
				CountryInformation countryinfo = CountryWarningsHandlerCommon.getInstance().getCountryInformation(countrybox.getSelectionModel().getSelectedItem());
				CurrencyInformation currencyinfo = new CurrencyInformation(countryinfo);
				currencyWebView.getEngine().loadContent(currencyinfo.getCurrencyInformationHtml().toString());
				countryWebView.getEngine().load(countryinfo.countryURL);
				
				if(countrybox.getSelectionModel().getSelectedItem().equals("Polska"))
				{
					
					countryWebView.setDisable(true);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Informacje szczegó³owe");
					alert.setHeaderText(null);
					alert.setContentText("Ministerstwo Spraw Zagranicznych nie udostêpnia informacji o Polsce.");
					alert.showAndWait();
				}
				else
				{
					countryWebView.setDisable(false);
				}
			}
		}
		else if(arg0.getSource() == button_findcity)
		{
			if(citybox.getSelectionModel().isEmpty() == false)
			{
				CityInformation cityinfo = CountryWarningsHandlerCommon.getInstance().getCityInformation(citybox.getSelectionModel().getSelectedItem());
				WeatherInformation weatherinfo = new WeatherInformation(cityinfo);
				cityWebView.getEngine().loadContent(cityinfo.getCityInformationHtml().toString());
				weatherWebView.getEngine().load(weatherinfo.pictureAdress);
				celsiusTextArea.setText(weatherinfo.celsius);
			}
			else clearCityComponents();
		}
	}	
}
