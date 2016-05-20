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
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;

    @FXML
    private VBox a_vbox_country_from;
    @FXML
    private Button a_button_findcities;
    @FXML
    private VBox a_vbox_city_from;
    @FXML
    private TextField a_textfield_zipcode;
    @FXML
    private TextField a_textfield_name;
    @FXML
    private TextField a_textfield_street;
    @FXML
    private TextField a_textfieldnumber;
    @FXML
    private TextField a_textfield_open;
    @FXML
    private TextField a_textfield_closed;
    @FXML
    private TextField a_textfield_price;
    @FXML
    private TextArea a_textarea_notes;
    @FXML
    private ListView<String> a_listview_attractions;
    @FXML
    private Button a_button_add;
    @FXML
    private Button h_button_add;
    @FXML
    private Tab tabhotel;
    @FXML
    private VBox h_vboxcountry;
    @FXML
    private VBox h_vboxcity;
    @FXML
    private Button h_button_findhotels;
    @FXML
    private TextField h_textfield_zipcode;
    @FXML
    private TextField h_textfield_name;
    @FXML
    private TextField h_textfield_street;
    @FXML
    private TextField h_textfield_number;
    @FXML
    private DatePicker h_datepicker_arrival;
    @FXML
    private DatePicker h_datepicker_departure;
    @FXML
    private TextField h_textfield_pricepernite;
    @FXML
    private TextArea h_textarea_notes;
    @FXML
    private ListView<String> h_listview_hotels;
    @FXML
    private ComboBox<String> h_combobox_book;
    @FXML
    private ComboBox<String> h_combobox_rated;
    @FXML
    private Tab tabtransport;
    @FXML
    private VBox t_vbox_countryfrom;
    @FXML
    private VBox t_vbox_countryto;
    @FXML
    private Button t_button_findcities_to;
    @FXML
    private VBox t_vbox_cityfrom;
    @FXML
    private VBox t_vbox_cityto;
    @FXML
    private DatePicker t_datepicker_start;
    @FXML
    private TextField t_textfield_starttime;
    @FXML
    private DatePicker t_datepicker_end;
    @FXML
    private TextField t_textfield_endtime;
    @FXML
    private TextField t_textfield_price;
    @FXML
    private TextArea t_textarea_notes;
    @FXML
    private ComboBox<String> t_combobox_transportcategory;
    @FXML
    private ListView<String> t_listview_companies;
    @FXML
    private Button t_button_add;
    @FXML
    private Button button_previous;
    @FXML
    private Button button_summary;
    @FXML
    private VBox a_vbox_mycurrency;
    @FXML
    private VBox a_vbox_currency;
    @FXML
    private VBox h_vbox_mycurrency;
    @FXML
    private VBox h_vbox_currency;
    @FXML
    private VBox t_vbox_mycurrency;
    @FXML
    private VBox t_vbox_currency;
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
		
		a_button_add.setOnAction(this);
		h_button_add.setOnAction(this);
		t_button_add.setOnAction(this);
		button_previous.setOnAction(this);
		button_summary.setOnAction(this);
		
		a_textfield_zipcode.setDisable(true);
		a_textfield_name.setDisable(true);
		a_textfield_street.setDisable(true);
		a_textfieldnumber.setDisable(true);
		a_textfield_open.setDisable(true);
		a_textfield_closed.setDisable(true);
		//a_combobox_attrcurrency.setDisable(true);
		
		a_combobox_countryfrom.setPromptText("Country");
		a_combobox_cityfrom.setPromptText("City");
		a_textfield_zipcode.setPromptText("Zip code");
		a_textfield_name.setPromptText("Attraction name");
		a_textfield_street.setPromptText("Street");
		a_textfieldnumber.setPromptText("Number");
		a_textfield_open.setPromptText("Open from");
		a_textfield_closed.setPromptText("Closed at");
		a_textfield_price.setPromptText("Attraction price");
		a_comboboxmycurrency.setPromptText("My currency");
		a_combobox_attrcurrency.setPromptText("Local currency");
		a_textarea_notes.setPromptText("Your notes on this attraction.");
		
		h_textfield_zipcode.setDisable(true);
		h_textfield_name.setDisable(true);
		h_textfield_street.setDisable(true);
		h_textfield_number.setDisable(true);
		
		h_combobox_country.setPromptText("Country");
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
			h_datepicker_arrival.getValue() != null &&
			h_datepicker_departure.getValue() != null &&
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
			t_combobox_mycurrency.getValue() != null &&
			t_combobox_transportcurrency.getValue() != null &&
			t_combobox_transportcategory.getValue() != null &&
			t_listview_companies.getSelectionModel().isEmpty() == false)
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
		if(event.getSource() == a_button_add)
		{
			if(acheckInputCompletion() == true)
			{
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(a_combobox_countryfrom.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(a_combobox_cityfrom.getSelectionModel().getSelectedItem());
				int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(a_combobox_attrcurrency.getSelectionModel().getSelectedItem());
				int attractionid = DatabaseHandlerAttractionAdder.getInstance().getAttractionId(cityid, a_listview_attractions.getSelectionModel().getSelectedItem());

				AttractionDetails ad = new AttractionDetails(attractionid, countryid, cityid, currencyid, Integer.parseInt(a_textfield_price.getText()), a_textarea_notes.getText());
				DatabaseHandlerAttractionAdder.getInstance().setAttraction(ad);
				boolean attractionaddstatus = DatabaseHandlerAttractionAdder.getInstance().pushAttractionToDatabase();

				if(attractionaddstatus == true)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("New attraction added");
					alert.setHeaderText(null);
					alert.setContentText("Attraction added succesfully!");
					alert.showAndWait();
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Couldn't add new attraction");
					alert.setHeaderText(null);
					alert.setContentText("SQL or Database error!");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Cannot add new attraction");
				alert.setHeaderText(null);
				alert.setContentText("Not all required informations provided!");
				alert.showAndWait();
			}
		}
		else if(event.getSource() == h_button_add)
		{
			if(hcheckInputCompletion() == true)
			{
				int hotelid = DatabaseHandlerHotelAdder.getInstance().getHotelId(h_listview_hotels.getSelectionModel().getSelectedItem());
				int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(h_combobox_currency.getSelectionModel().getSelectedItem());
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(h_combobox_country.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(h_combobox_city.getSelectionModel().getSelectedItem());
				
				
				HotelDetails hd = new HotelDetails(hotelid, currencyid, Integer.parseInt(h_textfield_pricepernite.getText()), h_textarea_notes.getText(), h_datepicker_arrival.getValue(), h_datepicker_departure.getValue(), countryid, cityid);
				DatabaseHandlerHotelAdder.getInstance().setHotel(hd);
				boolean hoteladdstatus = DatabaseHandlerHotelAdder.getInstance().pushHotelDetails();
				
				if(hoteladdstatus == true)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("New hotel added");
					alert.setHeaderText(null);
					alert.setContentText("Hotel added succesfully!");
					alert.showAndWait();
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Couldn't add new hotel");
					alert.setHeaderText(null);
					alert.setContentText("SQL or Database error!");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Couldn't add new hotel");
				alert.setHeaderText(null);
				alert.setContentText("Not all required informations provided!");
				alert.showAndWait();
			}
			
		}
		else if(event.getSource() == t_button_add)
		{
			if(tcheckInputCompletion() == true)
			{
				int idcurrency = DatabaseHandlerCommon.getInstance().getCurrencyId(t_combobox_transportcurrency.getSelectionModel().getSelectedItem());
				int idtransportcategory = DatabaseHandlerTransportAdder.getInstance().getCategoryId(t_combobox_transportcategory.getSelectionModel().getSelectedItem());
				int idtransport = DatabaseHandlerTransportAdder.getInstance().getTransportId(t_listview_companies.getSelectionModel().getSelectedItem());
				int idcountrytarget = DatabaseHandlerCommon.getInstance().getCountryId(t_combobox_country_to.getSelectionModel().getSelectedItem());
				int idcitytarget = DatabaseHandlerCommon.getInstance().getCityId(t_combobox_city_to.getSelectionModel().getSelectedItem());
				int idcountryorigin = DatabaseHandlerCommon.getInstance().getCountryId(t_combobox_country_from.getSelectionModel().getSelectedItem());
				int idcityorigin = DatabaseHandlerCommon.getInstance().getCityId(t_combobox_city_from.getSelectionModel().getSelectedItem());
		

				TransportDetails tdetails = new TransportDetails(idcurrency, idtransportcategory, idtransport, idcountrytarget, idcitytarget, idcountryorigin, idcityorigin, t_datepicker_start.getValue(), t_datepicker_end.getValue(), Integer.parseInt(t_textfield_price.getText()), "dummy link", t_textarea_notes.getText());
				DatabaseHandlerTransportAdder.getInstance().setTransportDetails(tdetails);
				boolean transportaddstatus = DatabaseHandlerTransportAdder.getInstance().pushTransportDetails();
				
				if(transportaddstatus == true)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("New transport added");
					alert.setHeaderText(null);
					alert.setContentText("Transport added succesfully!");
					alert.showAndWait();
				}
				else 
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Couldn't add new transport");
					alert.setHeaderText(null);
					alert.setContentText("SQL or Database error!");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Cannot add new transport");
				alert.setHeaderText(null);
				alert.setContentText("Not all required informations provided!");
				alert.showAndWait();
			}
		}
		else if(event.getSource() == button_previous)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_1);
		}
		else if(event.getSource() == button_summary)
		{
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
	}
}
