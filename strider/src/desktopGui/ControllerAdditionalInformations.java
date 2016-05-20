package desktopGui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;
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

import cWHandlers.CountryWarningsHandlerCommon;
import countryWarnings.CountriesList;
import countryWarnings.CountryInformation;
import countryWarnings.CurrencyInformation;
import countryWarnings.Main;
import dbHandlers.DatabaseHandlerCommon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerAdditionalInformations implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController; 
	
    @FXML
    private VBox vbox_countrybox;
    @FXML
    private VBox vbox_citybox;
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
    
    //Uzupelniajki, nie ma ich w FXML'u
    @FXML
    private ComboBox<String> citybox;
    @FXML
    private ComboBox<String> countrybox;
    
    
	final Image mszlogoimage = new Image("desktopGui/textures/ta_msz.png");
	final Image mwikilogoimage = new Image("desktopGui/textures/ta_mwiki.png");
	
	final Image icon_home = new Image("desktopGui/textures/button_home.png");
    private static int checkForMatchingCountry;  
	private static int num;
	
	public static void lateInitialize()
	{

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		//mwaiimageviewmszlogo.setImage(mszlogoimage);
		//mwaiimageviewmediawikilogo.setImage(mwikilogoimage);
		
		button_back.setOnAction(this);
		ImageView ico = new ImageView(icon_home);
		ico.setFitWidth(35);
		ico.setFitHeight(35);
		
		//backtohome.setGraphic(ico);

		citybox = WindowMain.getCityBox();
		countrybox = WindowMain.getCountryBox();
		vbox_citybox.getChildren().add(citybox);
		vbox_countrybox.getChildren().add(countrybox);
		
		countrybox.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				citybox.getSelectionModel().clearSelection();
				citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countrybox.getSelectionModel().getSelectedIndex()));
			
				countryWebView.getEngine().loadContent(CountryWarningsHandlerCommon.getInstance().getCountryInformation(countrybox.getSelectionModel().getSelectedItem()));
				currencyWebView.getEngine().loadContent(CountryWarningsHandlerCommon.getInstance().getCurrencyInformation(countrybox.getSelectionModel().getSelectedItem()));
			}
		});
		
		citybox.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				if(citybox.getSelectionModel().isEmpty() == false)
				cityWebView.getEngine().loadContent(CountryWarningsHandlerCommon.getInstance().getCityInformation(citybox.getSelectionModel().getSelectedItem()));
				
				//TODO: Wyœwietliæ dane o pogodzie w WebView
			}
		});
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
		
	}	

	@Override
	public void clearComponents() 
	{
		
	}
}
