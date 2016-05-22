package desktopGui;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.jsoup.nodes.Document;
import Model.TransportDetails;
import Model.AttractionDetails;
import Model.HotelDetails;
import Model.Travel;
import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerAttractionAdder;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerHotelAdder;
import dbHandlers.DatabaseHandlerTransportAdder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerCreateTravel implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;


    @FXML
    private HBox a_hbox_countrybox;

    @FXML
    private TextField a_textfield_name;

    @FXML
    private HBox a_hbox_citybox;

    @FXML
    private TextField a_textfield_street;

    @FXML
    private TextField a_textfield_zipcode;

    @FXML
    private TextField a_textfield_number;

    @FXML
    private TextField a_textfield_openfrom;

    @FXML
    private TextField a_textfield_opentill;

    @FXML
    private TextField a_textfield_price;

    @FXML
    private HBox a_hbox_currencybox;

    @FXML
    private TextArea a_textarea_notes;

    @FXML
    private Button a_button_addattraction;

    @FXML
    private HBox h_hbox_countrybox;

    @FXML
    private TextField h_textfield_country;

    @FXML
    private HBox h_hbox_citybox;

    @FXML
    private TextField h_textfield_street;

    @FXML
    private TextField h_textfield_zipcode;

    @FXML
    private TextField h_textfield_number;

    @FXML
    private DatePicker h_datepicker_start;

    @FXML
    private TextField h_textfield_hour_start;

    @FXML
    private DatePicker h_datepicker_end;

    @FXML
    private TextField h_textfield_hour_end;

    @FXML
    private TextField h_textfield_price;

    @FXML
    private VBox h_vbox_currencybox;

    @FXML
    private TextArea h_textarea_notes;

    @FXML
    private Button h_button_addhotel;

    @FXML
    private HBox t_hbox_countrybox_start;

    @FXML
    private DatePicker t_datepicker_start;

    @FXML
    private HBox t_hbox_citybox_start;

    @FXML
    private TextField t_textfield_hour_start;

    @FXML
    private HBox t_hbox_countrybox_end;

    @FXML
    private DatePicker t_datepicker_end;

    @FXML
    private HBox t_hbox_citybox_end;

    @FXML
    private TextField t_textfield_hour_end;

    @FXML
    private ComboBox<String> t_choicebox_transport_category;

    @FXML
    private TextField t_textfield_price;

    @FXML
    private HBox t_hbox_currencybox;

    @FXML
    private WebView t_webview_calcprice;

    @FXML
    private TextArea t_textarea_notes;

    @FXML
    private Button t_button_addtransport;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_summary;
    /////////////////////////////////////
    @FXML
    private static ComboBox<String> t_combobox_mycurrency;
    @FXML
    private static ComboBox<String> t_combobox_transportcurrency;
    @FXML
    private static ComboBox<String> h_combobox_mycurrency;
    @FXML
    private static ComboBox<String> h_combobox_currency;
    @FXML
    private static ComboBox<String> a_comboboxmycurrency;
    @FXML
    private static ComboBox<String> a_combobox_attrcurrency;
    @FXML
    private static ComboBox<String> a_combobox_countryfrom;
    @FXML
    private static ComboBox<String> a_combobox_cityfrom;
    @FXML
    private static ComboBox<String> h_combobox_country;
    @FXML
    private static ComboBox<String> h_combobox_city;
    @FXML
    private static ComboBox<String> t_combobox_country_from;
    @FXML
    private static ComboBox<String> t_combobox_city_from;
    @FXML
    private static ComboBox<String> t_combobox_country_to;
    @FXML
    private static ComboBox<String> t_combobox_city_to;
    
    public static void lateInitialize()
    {
    	
    	t_combobox_country_from.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCountryOriginId());
    	t_combobox_city_from.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCityOriginId());
    	
    	h_combobox_country.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCountryOriginId());
    	h_combobox_city.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCityOriginId());
    	
    	a_combobox_countryfrom.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCountryOriginId());
    	a_combobox_cityfrom.getSelectionModel().select(TravelFramework.getInstance().getCurrentTravel().getCityOriginId());

    	//Preferowana waluta usera

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		/*
		a_listview_attractions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				String sql2 = "Select * from DBA.Attraction where AttractionName = '" + newValue + "'";
				String zipcode = DbAccess.getInstance().getSingeStringFromDb(sql2, "ZipCode");
				String streetName = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetName");
				String streetNumber = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetNumber");
				String openTime = DbAccess.getInstance().getSingeStringFromDb(sql2, "OpeningTime");
				String closeTime = DbAccess.getInstance().getSingeStringFromDb(sql2, "ClosingTime");
				
				a_textfield_name.setText(newValue);
				a_textfield_zipcode.setText(zipcode);
				a_textfield_street.setText(streetName);
				a_textfieldnumber.setText(streetNumber);
				a_textfield_open.setText(openTime);
				a_textfield_closed.setText(closeTime);	
			}
		});
		
		h_listview_hotels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldvalue, String newvalue) 
			{
				String sql2 = "Select * from DBA.Hotel where HotelName =" +"'" + newvalue + "'";
				String zipcode = DbAccess.getInstance().getSingeStringFromDb(sql2, "ZipCode");
				String streetName = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetName");
				String streetNumber = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetNumber");
				
				h_textfield_name.setText(newvalue);
				h_textfield_zipcode.setText(zipcode);
				h_textfield_street.setText(streetName);
				h_textfield_number.setText(streetNumber);
			}
		});
		
		a_combobox_countryfrom = WindowMain.getCountryBox();
		a_combobox_cityfrom = WindowMain.getCityBox();
		h_combobox_country = WindowMain.getCountryBox();
		h_combobox_city = WindowMain.getCityBox();
		t_combobox_city_to = WindowMain.getCityBox();
		t_combobox_city_from = WindowMain.getCityBox();
		t_combobox_country_to = WindowMain.getCountryBox();
		t_combobox_country_from = WindowMain.getCountryBox();
		a_comboboxmycurrency = WindowMain.getCurrencyBox();
		a_combobox_attrcurrency = WindowMain.getCurrencyBox();
		h_combobox_currency = WindowMain.getCurrencyBox();
		h_combobox_mycurrency = WindowMain.getCurrencyBox();
		t_combobox_mycurrency = WindowMain.getCurrencyBox();
		t_combobox_transportcurrency = WindowMain.getCurrencyBox();
		a_vbox_country_from.getChildren().add(a_combobox_countryfrom);
		a_vbox_city_from.getChildren().add(a_combobox_cityfrom);
		h_vboxcountry.getChildren().add(h_combobox_country);
		h_vboxcity.getChildren().add(h_combobox_city);
		t_vbox_countryfrom.getChildren().add(t_combobox_country_from);
		t_vbox_countryto.getChildren().add(t_combobox_country_to);
		t_vbox_cityfrom.getChildren().add(t_combobox_city_from);
		t_vbox_cityto.getChildren().add(t_combobox_city_to);
		a_vbox_mycurrency.getChildren().add(a_comboboxmycurrency);
		a_vbox_currency.getChildren().add(a_combobox_attrcurrency);
		h_vbox_mycurrency.getChildren().add(h_combobox_mycurrency);
		h_vbox_currency.getChildren().add(h_combobox_currency);
		t_vbox_mycurrency.getChildren().add(t_combobox_mycurrency);
		t_vbox_currency.getChildren().add(t_combobox_transportcurrency);
		
		t_combobox_transportcategory.getItems().setAll(DatabaseHandlerTransportAdder.getInstance().getCategories());
		
		/////ATTRACTION/////
		a_combobox_countryfrom.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				a_combobox_cityfrom.getSelectionModel().clearSelection();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(a_combobox_countryfrom.getSelectionModel().getSelectedItem());
				a_combobox_cityfrom.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
				
		    	a_comboboxmycurrency.getSelectionModel().select(DatabaseHandlerCommon.getInstance().getCurrencyNameForGivenCountry(User.getInstance().getCountryId()));
			}
		});
		a_combobox_cityfrom.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				a_listview_attractions.getItems().removeAll(a_listview_attractions.getItems());
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(a_combobox_countryfrom.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(a_combobox_cityfrom.getSelectionModel().getSelectedItem());
				a_listview_attractions.getItems().setAll(DatabaseHandlerAttractionAdder.getInstance().getAttractions(cityid, countryid));
			}
		});
		
		/////HOTEL/////
		h_combobox_country.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				h_combobox_city.getSelectionModel().clearSelection();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(h_combobox_country.getSelectionModel().getSelectedItem());
				h_combobox_city.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
		    	h_combobox_mycurrency.getSelectionModel().select(DatabaseHandlerCommon.getInstance().getCurrencyNameForGivenCountry(User.getInstance().getCountryId()));
			}
		});
		h_combobox_city.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(h_combobox_country.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(h_combobox_city.getSelectionModel().getSelectedItem());
				
				h_listview_hotels.getItems().setAll(DatabaseHandlerHotelAdder.getInstance().getHotels(cityid, countryid));
			}
		});
		
		/////TRANSPORT/////
		t_combobox_country_from.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				t_combobox_city_from.getSelectionModel().clearSelection();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(t_combobox_country_from.getSelectionModel().getSelectedItem());
				t_combobox_city_from.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
		    	t_combobox_mycurrency.getSelectionModel().select(DatabaseHandlerCommon.getInstance().getCurrencyNameForGivenCountry(User.getInstance().getCountryId()));
			}
		});
		t_combobox_city_from.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{

			}
		});
		
		t_combobox_transportcategory.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int tcategory = DatabaseHandlerTransportAdder.getInstance().getCategoryId(t_combobox_transportcategory.getSelectionModel().getSelectedItem());
				System.out.println("Tcategory : " + tcategory);
				t_listview_companies.getItems().setAll(DatabaseHandlerTransportAdder.getInstance().getProviders(tcategory));
			}
		});
		
		t_combobox_country_to.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				t_combobox_city_to.getSelectionModel().clearSelection();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(t_combobox_country_to.getSelectionModel().getSelectedItem());
				t_combobox_city_to.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
			}
		});
		*/
		button_summary.setOnAction(this);
		
		a_textfield_zipcode.setDisable(true);
		a_textfield_name.setDisable(true);
		a_textfield_street.setDisable(true);
		
		/*
		a_combobox_countryfrom.setPromptText("Country");
		a_combobox_cityfrom.setPromptText("City");
		a_textfield_zipcode.setPromptText("Zip code");
		a_textfield_name.setPromptText("Attraction name");
		a_textfield_street.setPromptText("Street");
		a_textfield_price.setPromptText("Attraction price");
		a_comboboxmycurrency.setPromptText("My currency");
		a_combobox_attrcurrency.setPromptText("Local currency");
		a_textarea_notes.setPromptText("Your notes on this attraction.");
		*/
		
		//h_textfield_zipcode.setDisable(true);
		//h_textfield_street.setDisable(true);
		//h_textfield_number.setDisable(true);
		
	//	h_combobox_country.setPromptText("Country");
	//	h_combobox_city
		
		
	}
	
	private boolean acheckInputCompletion()
	{
		if(a_combobox_countryfrom.getValue() != null &&
			a_combobox_cityfrom.getValue() != null &&
			a_textfield_price.getText() != null &&
			a_comboboxmycurrency.getValue() != null &&
			a_combobox_attrcurrency.getValue() != null)
		{
			return true;
		}
		else return false;
	}
	
	private boolean hcheckInputCompletion()
	{
		if(h_combobox_country.getValue() != null &&
			h_combobox_city.getValue() != null &&
			h_combobox_mycurrency.getValue() != null &&
			h_combobox_currency.getValue() != null)
		{
			return true;
		}
		else return false;
		
	}
	
	private boolean tcheckInputCompletion()
	{
		if(t_combobox_country_from.getValue() != null &&
			t_combobox_city_from.getValue() != null &&
			t_combobox_country_to.getValue() != null &&
			t_combobox_city_to.getValue() != null &&
			t_datepicker_start.getValue() != null &&
			t_datepicker_end.getValue() != null &&
			t_textfield_price.getText().isEmpty() == false &&
			t_combobox_mycurrency.getValue() != null)
			//t_combobox_transportcurrency.getValue() != null &&
		{
			return true;
		}
		else return false;
		
	}

	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	@Override
	public void handle(ActionEvent event) 
	{
		
	}
	
}
