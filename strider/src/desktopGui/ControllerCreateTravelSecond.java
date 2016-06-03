package desktopGui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsLeg;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsRoute;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.DirectionsSteps;
import com.lynden.gmapsfx.service.directions.TravelModes;

import Model.Attraction;
import Model.Hotel;
import Model.Transport;
import Model.TravelFramework;
import Model.User;
import Model.VisitedAttractions;
import Model.VisitedHotels;
import cWHandlers.CountryWarningsHandlerCommon;
import countryWarnings.CityInformation;
import countryWarnings.MapController;
import countryWarnings.ResultsInformation;
import countryWarnings.SampleController;
import countryWarnings.WeatherInformation;
import dbHandlers.DatabaseHandlerAttractionAdder;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerHotelAdder;
import dbHandlers.DatabaseHandlerTransportAdder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>, MapComponentInitializedListener, DirectionsServiceCallback
{
	ScreensController myController;
	
	RatingBox hotelrating;
	
	@FXML
	private ListView<String> a_myattractions;
	@FXML
	private ListView<String> h_myhotels;
	@FXML
	private TextField h_textfield_reservation;
	@FXML
	private Button h_button_reservation;
	@FXML
	private HBox h_hbox_ratingbox;
	@FXML
	private Button button_makeroute;
    @FXML
    private Slider slider_tripday;
    @FXML
    private HBox a_hbox_countrybox;
    @FXML
    private Label t_label_transportpriceconsumptionfuckyou;
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
    private Button t_button_calculate;
    @FXML
    private TextField a_textfield_opentill;
    @FXML
    private TextField a_textfield_price;
    @FXML
    private Label label_travelday;
    @FXML
    private HBox a_hbox_currencybox;
    @FXML
    private TextArea a_textarea_notes;
    @FXML
    private Button a_button_addattraction;
    @FXML
    private TextField t_textfield_calcdprice;
    @FXML
    private HBox h_hbox_countrybox;
    @FXML
    private TextField h_textfield_name;
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
    private Label t_label_calcprice;
    
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
    private ComboBox<String> t_combobox_transport_category;
    @FXML
    private ComboBox<String> t_combobox_transport_company;
    @FXML
    private TextField t_textfield_price;
    @FXML
    private HBox t_hbox_currencybox;
    @FXML
    private TextArea t_textarea_notes;
    @FXML
    private Button t_button_addtransport;
    @FXML
    private Button button_cancel;
    @FXML
    private Button button_summary;
    @FXML
    private VBox t_vbox_mapbox;
    @FXML
    private WebView a_cityview;
    /////////////////////////////////////
    @FXML
    private ComboBox<String> a_countrybox;
    @FXML
    private ComboBox<String> a_citybox;
    @FXML
    private ComboBox<String> a_currencybox;
    @FXML
    private ComboBox<String> h_countrybox;
    @FXML
    private ComboBox<String> h_citybox;
    @FXML
    private ComboBox<String> h_currencybox;
    @FXML
    private ComboBox<String> t_countrybox_start;
    @FXML
    private ComboBox<String> t_citybox_start;
    @FXML
    private ComboBox<String> t_countrybox_end;
    @FXML
    private ComboBox<String> t_citybox_end;
    @FXML
    private ComboBox<String> t_currencybox;
    
    private Button a_button_findcities;
    private Button a_button_findattractions;
    private Button h_button_findcities;
    private Button t_button_findcities;
    
    public static  MapOptions mapOptions;
    public static GoogleMapView mapView;
    public static double distance;
	public static GoogleMap map;
	private static int counter = 0;
	protected DirectionsPane directions;
	protected DirectionsService ds;
	protected DirectionsRequest dr;
	
	private double calctransportcost;
	String dist;
	
	LocalDate curdate;
	LocalDate maxdate;
	
    DirectionsRenderer renderer;
    
    public void setupInitialValues()
    {
    	long daysnum = TravelFramework.getInstance().getTravel().getDaysNumber();
    	LocalDate startdate = TravelFramework.getInstance().getTravel().getStartDate();
    	maxdate = TravelFramework.getInstance().getTravel().getEndDate();
    	
    	curdate = startdate.plusDays((int)slider_tripday.getValue() - 1);
    	label_travelday.setText("Dzie� podr�y: " + curdate.getDayOfMonth() + "-" + curdate.getMonthValue() + "-" + curdate.getYear());
    	slider_tripday.setMax(daysnum);

		t_countrybox_start.getSelectionModel().select(User.getInstance().getCountryId());
		t_citybox_start.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(User.getInstance().getCountryId()));
    	t_citybox_start.getSelectionModel().select(User.getInstance().getCityId());
    	
    	a_countrybox.getSelectionModel().select(t_countrybox_start.getSelectionModel().getSelectedIndex());
    	a_citybox.getSelectionModel().select(t_citybox_start.getSelectionModel().getSelectedIndex());

    	h_countrybox.getSelectionModel().select(t_countrybox_start.getSelectionModel().getSelectedIndex());
    	h_citybox.getSelectionModel().select(t_citybox_start.getSelectionModel().getSelectedIndex());

    	t_datepicker_start.setValue(curdate);
    	h_datepicker_start.setValue(curdate);
    }
    
    public void clearComponents()
    {
    	a_textfield_name.setText("");
    	a_textfield_street.setText("");
    	a_textfield_zipcode.setText("");
    	a_textfield_number.setText("");
    	a_textfield_openfrom.setText("");
    	a_textfield_opentill.setText("");
    	a_textfield_price.setText("");
    	a_currencybox.getSelectionModel().clearSelection();
    	a_textarea_notes.setText("");
    	h_textfield_name.setText("");
    	h_textfield_street.setText("");
    	h_textfield_zipcode.setText("");
    	h_textfield_number.setText("");
    	h_textfield_hour_start.setText("");
    	h_datepicker_end.setValue(null);
    	h_textfield_hour_end.setText("");
    	h_textfield_price.setText("");
    	h_currencybox.getSelectionModel().clearSelection();
    	h_textarea_notes.setText("");
    	h_textfield_reservation.setText("");
    	t_textfield_hour_start.setText("");
    	t_countrybox_end.getSelectionModel().clearSelection();
    	t_citybox_end.getSelectionModel().clearSelection();
    	t_textfield_hour_end.setText("");
    	t_combobox_transport_category.getSelectionModel().clearSelection();
    	t_textfield_price.setText("");
    	t_textarea_notes.setText("");
    }
    
    public boolean shouldUpdateTravelPlaces()
    {
    	if((t_countrybox_start.getSelectionModel().getSelectedItem() == t_countrybox_end.getSelectionModel().getSelectedItem()) &&
        		(t_citybox_start.getSelectionModel().getSelectedItem() == t_citybox_end.getSelectionModel().getSelectedItem()))return false;
    	if(t_countrybox_end.getSelectionModel().isEmpty() && t_citybox_end.getSelectionModel().isEmpty())return false;
    	return true;
    }
     
    public void updateDates()
    {
    	LocalDate startdate = TravelFramework.getInstance().getTravel().getStartDate();
    	LocalDate enddate = TravelFramework.getInstance().getTravel().getEndDate();
    	
    	curdate = startdate.plusDays((int)slider_tripday.getValue() - 1);

    	t_datepicker_start.setValue(curdate);
    	h_datepicker_start.setValue(curdate);
    	
    	label_travelday.setText("Dzie� podr�y: " + curdate.getDayOfMonth() + "-" + curdate.getMonthValue() + "-" + curdate.getYear());
    	
    	if(shouldUpdateTravelPlaces() == true )
    	{
    	   	a_countrybox.getSelectionModel().select(t_countrybox_end.getSelectionModel().getSelectedIndex());
        	a_citybox.getSelectionModel().select(t_citybox_end.getSelectionModel().getSelectedIndex());

        	h_countrybox.getSelectionModel().select(t_countrybox_end.getSelectionModel().getSelectedIndex());
        	h_citybox.getSelectionModel().select(t_citybox_end.getSelectionModel().getSelectedIndex());
    	}
    }
    
    public void clearAttractionComponents()
    {
    	a_textfield_name.setText("");
    	a_textfield_street.setText("");
    	a_textfield_zipcode.setText("");
    	a_textfield_number.setText("");
    	a_textfield_openfrom.setText("");
    	a_textfield_opentill.setText("");
    	a_textfield_price.setText("");
    	a_textarea_notes.setText("");
    	a_currencybox.getSelectionModel().clearSelection();
    }
    
    public void clearHotelComponents()
    {
    	h_textfield_name.setText("");
    	h_textfield_street.setText("");
    	h_textfield_zipcode.setText("");
    	h_textfield_number.setText("");
    	h_datepicker_start.setValue(null);
    	h_datepicker_end.setValue(null);
    	h_textfield_hour_start.setText("");
    	h_textfield_hour_end.setText("");
    	h_textfield_price.setText("");
    	h_currencybox.getSelectionModel().clearSelection();
    	h_textarea_notes.setText("");    	
    	h_textfield_reservation.setText("");
    }
    
    public void clearTransportComponents()
    {

    }
    
    public void populateVisitedHotels()
    {
    	List<VisitedHotels> hotelsfromdb = DatabaseHandlerHotelAdder.getInstance().getVisitedHotels();
    	for(VisitedHotels vh : hotelsfromdb)
    	{
    		h_myhotels.getItems().add(vh.getHotelName());
    	}
    }
    
    public void populateVisitedAttractions()
    {
		List<VisitedAttractions> attractionsfromdb = DatabaseHandlerAttractionAdder.getInstance().getVisitedAttractions();
		for(VisitedAttractions va : attractionsfromdb)
		{
			System.out.println("Attrname: " + va.getAttractionName());
			a_myattractions.getItems().add(va.getAttractionName());
		}
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		
		
		 populateVisitedAttractions();
		 populateVisitedHotels();
		
		hotelrating = new RatingBox();
		hotelrating.setDisable(true);
		h_hbox_ratingbox.getChildren().add(hotelrating);
		
		
		
		h_button_reservation.setOnAction(this);
		button_makeroute.setOnAction(this);
		t_datepicker_start.setOnAction(this);
		t_datepicker_end.setOnAction(this);
		h_datepicker_start.setOnAction(this);
		h_datepicker_end.setOnAction(this);
		t_button_findcities = new Button();
		t_button_findcities.setText("Znajd� miasta");
	   	t_button_findcities.setOnAction(this);
		a_countrybox = WindowMain.getCountryBox();
		a_citybox = WindowMain.getCityBox();
		a_currencybox = WindowMain.getCurrencyBox();
		h_countrybox = WindowMain.getCountryBox();
		h_citybox = WindowMain.getCityBox();
		h_currencybox = WindowMain.getCurrencyBox();
		t_countrybox_start = WindowMain.getCountryBox();
		t_citybox_start = WindowMain.getCityBox();
		t_countrybox_end = WindowMain.getCountryBox();
		t_citybox_end = WindowMain.getCityBox();
		t_currencybox = WindowMain.getCurrencyBox();
		a_hbox_countrybox.getChildren().add(a_countrybox);
		a_hbox_citybox.getChildren().add(a_citybox);
		a_hbox_currencybox.getChildren().add(a_currencybox);
		t_combobox_transport_category.getItems().setAll(DatabaseHandlerTransportAdder.getInstance().getCategories());
		
		h_hbox_countrybox.getChildren().add(h_countrybox);
		h_hbox_citybox.getChildren().add(h_citybox);
		h_vbox_currencybox.getChildren().add(h_currencybox);
		t_hbox_countrybox_start.getChildren().add(t_countrybox_start);
		t_hbox_citybox_start.getChildren().add(t_citybox_start);
		t_hbox_countrybox_end.getChildren().add(t_countrybox_end);
		t_hbox_countrybox_end.getChildren().add(t_button_findcities);
		t_hbox_citybox_end.getChildren().add(t_citybox_end);
		t_hbox_currencybox.getChildren().add(t_currencybox);
		SampleController.setOpeningLinksToBrowser(a_cityview);
		button_summary.setOnAction(this);
		button_cancel.setOnAction(this);
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);	
		t_vbox_mapbox.getChildren().add(mapView);
		a_button_addattraction.setOnAction(this);
		h_button_addhotel.setOnAction(this);
		t_button_addtransport.setOnAction(this);

		
		t_combobox_transport_category.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int catid = DatabaseHandlerTransportAdder.getInstance().getCategoryId(t_combobox_transport_category.getSelectionModel().getSelectedItem());
				
				t_combobox_transport_company.getItems().setAll(DatabaseHandlerTransportAdder.getInstance().getProviders(catid));
			}			
		});
		
		a_countrybox.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(a_countrybox.getSelectionModel().getSelectedItem());
				a_citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
			}			
		});
		
		h_countrybox.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(h_countrybox.getSelectionModel().getSelectedItem());
				h_citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
			}			
		});
		
		t_combobox_transport_category.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				String selectedcategory = t_combobox_transport_category.getSelectionModel().getSelectedItem();
				
				if(selectedcategory.equals("Car"))
				{
					t_label_transportpriceconsumptionfuckyou.setText("�rednie spalanie");
				}
				else
				{
					t_label_transportpriceconsumptionfuckyou.setText("Cena biletu");
				}
			}		
		});

		a_citybox.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				CityInformation cityinfo = CountryWarningsHandlerCommon.getInstance().getCityInformation(a_citybox.getSelectionModel().getSelectedItem());		
				a_cityview.getEngine().loadContent(cityinfo.getCityInformationHtml().toString());
			}			
		});
		
		t_countrybox_start.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(t_countrybox_start.getSelectionModel().getSelectedItem());
				int selectedcityid = DatabaseHandlerCommon.getInstance().getCityId(t_citybox_start.getSelectionModel().getSelectedItem());
				t_citybox_start.getSelectionModel().select(selectedcityid);
				a_citybox.getSelectionModel().select(t_citybox_start.getSelectionModel().getSelectedIndex());
			}			
		});
		
		slider_tripday.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
			{
					updateDates();
			}
		});
		
		t_button_calculate.setOnAction(this);
		setupInitialValues();
	}
	
	public boolean checkAttractionInput()
	{
		if(a_countrybox.getSelectionModel().isEmpty())return false;
		if(a_citybox.getSelectionModel().isEmpty())return false;
		if(a_textfield_name.getText().isEmpty())return false;
		if(a_textfield_price.getText().isEmpty())return false;
		if(a_currencybox.getSelectionModel().isEmpty())return false;
		return true;
	}
	
	public boolean checkHotelInput()
	{
		if(h_countrybox.getSelectionModel().isEmpty())return false;
		if(h_citybox.getSelectionModel().isEmpty())return false;
		if(h_textfield_name.getText().isEmpty())return false;
		if(h_datepicker_start.getValue() == null)return false;
		if(h_datepicker_end.getValue() == null)return false;
		if(h_textfield_price.getText().isEmpty())return false;
		if(h_currencybox.getValue().isEmpty())return false;
		return true;
	}
	
	public boolean checkTransportInput()
	{
		
		if(t_countrybox_start.getSelectionModel().isEmpty())return false;
		if(t_citybox_start.getSelectionModel().isEmpty())return false;
		if(t_countrybox_end.getSelectionModel().isEmpty())return false;
		if(t_citybox_end.getSelectionModel().isEmpty())return false;
		if(t_datepicker_start.getValue() == null)return false;
		if(t_datepicker_end.getValue() == null)return false;
		if(t_textfield_hour_start.getText().isEmpty())return false;
		if(t_textfield_hour_end.getText().isEmpty())return false;
		if(t_textfield_price.getText().isEmpty())return false;
		if(t_currencybox.getSelectionModel().isEmpty())return false;
		if(t_combobox_transport_category.getSelectionModel().getSelectedItem().equals("Car"))if(t_textfield_calcdprice.getText().isEmpty())return false;
		
		return true;
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	public void sanitizeTransportDatepickers()
	{
		if(t_datepicker_end.getValue() != null)
		{
			if(t_datepicker_end.getValue().compareTo(curdate) < 0)
			{
				t_datepicker_start.setValue(curdate);
			}

			if(t_datepicker_end.getValue().compareTo(t_datepicker_start.getValue()) < 0)
			{
				t_datepicker_end.setValue(t_datepicker_start.getValue());
			}
			if(t_datepicker_end.getValue().compareTo(maxdate) > 0)
			{
				t_datepicker_end.setValue(maxdate);
			}
		}
	}
	
	public void sanitizeHotelDatepickers()
	{
		if(h_datepicker_end.getValue() != null && h_datepicker_start.getValue() != null)
		{
			if(h_datepicker_end.getValue().compareTo(h_datepicker_start.getValue()) < 0)
			{
				h_datepicker_end.setValue(h_datepicker_start.getValue());
			}
			
			if(h_datepicker_end.getValue().compareTo(maxdate) > 0)
			{
				h_datepicker_end.setValue(maxdate);
			}
		}
	}
	
	
	public boolean checkHourInput(TextField field)
	{
		if(field.getText().isEmpty() == true)return false;
		else
		{
			String regex = "^\\d\\d:\\d\\d$";
			Pattern p = Pattern.compile(regex);
			Matcher match = p.matcher(field.getText());
		
			if(match.find() == true)return true;
			else return false;
		}
	}
	
	public int getHourFromTextField(TextField field)
	{
		String hhmm = field.getText();
		return Integer.parseInt(hhmm.substring(0, 1));
	}
	
	public int getMinutesFromTextField(TextField field)
	{
		String hhmm = field.getText();
		return Integer.parseInt(hhmm.substring(3, 4));
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == t_button_calculate)
		{
			if(t_combobox_transport_category.getSelectionModel().getSelectedItem().equals("Car"))
			{
				if(dist != null && dist.isEmpty() == false)
				{
					if(t_textfield_price.getText().isEmpty() == false && t_combobox_transport_category.getSelectionModel().getSelectedItem().isEmpty() == false)
					{
						System.out.println(dist);
						double spalanie = Double.parseDouble(t_textfield_price.getText().replaceAll(",", "."));	
						ResultsInformation information = new ResultsInformation(spalanie, distance, "e95");
						calctransportcost = information.getFuelCost();
						t_label_calcprice.setText("" + calctransportcost);
					}
					else
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Wyliczanie ceny transportu");
						alert.setHeaderText(null);
						alert.setContentText("Nie wype�niono wszystkich wymaganych p�l.");
						alert.showAndWait();
					}
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Wyliczanie ceny transportu");
					alert.setHeaderText(null);
					alert.setContentText("Wyznacz tras� dla transportu.");
					alert.showAndWait();
				}
			}
			else
			{
				if(t_textfield_price.getText().isEmpty() == false && t_combobox_transport_category.getSelectionModel().getSelectedItem().isEmpty() == false)
				{
					
					
					
				}
			}
		}
		else if(event.getSource() == t_datepicker_end)
		{
			sanitizeTransportDatepickers();
		}
		else if(event.getSource() == h_datepicker_start || event.getSource() == h_datepicker_end)
		{
			sanitizeHotelDatepickers();
		}
		else if(event.getSource() == button_makeroute)
		{
			if(t_countrybox_start.getSelectionModel().isEmpty() == false && t_citybox_start.getSelectionModel().isEmpty() == false)
			{
				if(t_countrybox_end.getSelectionModel().isEmpty() == false && t_citybox_end.getSelectionModel().isEmpty() == false)
				{
					selectRoute(t_citybox_start.getSelectionModel().getSelectedItem(), t_citybox_end.getSelectionModel().getSelectedItem());
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Wyznaczanie trasy transportu");
					alert.setHeaderText(null);
					alert.setContentText("Wybierz miejsce docelowe dla transportu.");
					alert.showAndWait();
				}
			}
		}
		else if(event.getSource() == t_button_findcities)
		{
			int countryid = DatabaseHandlerCommon.getInstance().getCountryId(t_countrybox_end.getSelectionModel().getSelectedItem());
			t_citybox_end.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
		}
		else if(event.getSource() == a_button_addattraction)
		{
			if(checkAttractionInput() == false)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Dodawanie atrakcji");
				alert.setHeaderText(null);
				alert.setContentText("Nie wype�niono wszystkich wymaganych p�l.");
				alert.showAndWait();
			}
			else
			{
				if(checkHourInput(a_textfield_openfrom) == false || checkHourInput(a_textfield_opentill) == false)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie atrakcji");
					alert.setHeaderText(null);
					alert.setContentText("Pola godzinowe wype�nione niepoprawnie.\nDozwolony format to hh:mm");
					alert.showAndWait();
				}
				else
				{
					Attraction at = new Attraction();
						at.country = a_countrybox.getSelectionModel().getSelectedItem();
						at.city = a_citybox.getSelectionModel().getSelectedItem();
					at.name = a_textfield_name.getText();
					at.street = a_textfield_street.getText();
					at.number = a_textfield_number.getText();
					at.zipcode = a_textfield_zipcode.getText();
					at.openfrom = a_textfield_openfrom.getText();
					at.opento = a_textfield_opentill.getText();
					at.date = t_datepicker_start.getValue();
					at.currency = a_currencybox.getSelectionModel().getSelectedItem();
					at.notes = a_textarea_notes.getText();
					if(at.notes.equals(""))at.notes = "Brak notatek";
					at.price = Float.parseFloat(a_textfield_price.getText());

					at.date = curdate;
					
					TravelFramework.getInstance().getTravel().addAttractionToDay(curdate, at);
	
					clearAttractionComponents();
				
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie atrakcji");
					alert.setHeaderText(null);
					alert.setContentText("Atrakcja zosta�a dodana pomy�lnie!");
					alert.showAndWait();

				}
			}
		}
		else if(event.getSource() == h_button_addhotel)
		{
			if(checkHotelInput() == false)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Dodawanie hotelu");
				alert.setHeaderText(null);
				alert.setContentText("Nie wype�niono wszystkich wymaganych p�l.");
				alert.showAndWait();
			}
			else
			{
				/*
				if(checkHourInput(h_textfield_hour_start) == false || checkHourInput(h_textfield_hour_end) == false)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie hotelu");
					alert.setHeaderText(null);
					alert.setContentText("Pola godzinowe wype�nione niepoprawnie.\nDozwolony format to hh:mm");
					alert.showAndWait();
				}
				else
				{
				*/
					Hotel hot = new Hotel();
					hot.country = h_countrybox.getSelectionModel().getSelectedItem();
					hot.city = h_citybox.getSelectionModel().getSelectedItem();
					hot.name = h_textfield_name.getText();
					hot.street = h_textfield_street.getText();
					hot.zipcode = h_textfield_zipcode.getText();
					hot.number = h_textfield_number.getText();
					hot.filepath = "" + h_textfield_reservation.getText();
					
					//LocalTime starttime = LocalTime.of(getHourFromTextField(h_textfield_hour_start), getMinutesFromTextField(h_textfield_hour_start));
					//LocalTime endtime = LocalTime.of(getHourFromTextField(h_textfield_hour_end), getMinutesFromTextField(h_textfield_hour_end));
					
					hot.accomodation_startdate = LocalDateTime.of(h_datepicker_start.getValue(), LocalTime.MIN);
					hot.accomodation_enddate = LocalDateTime.of(h_datepicker_end.getValue(), LocalTime.MIN);
					hot.pricepernite = Float.parseFloat(h_textfield_price.getText());
					hot.currency = h_currencybox.getSelectionModel().getSelectedItem();
					hot.notes = h_textarea_notes.getText();
					TravelFramework.getInstance().getTravel().setHotelToDay(curdate, hot);
				
					clearHotelComponents();
				
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie hotelu");
					alert.setHeaderText(null);
					alert.setContentText("Hotel zosta� dodany pomy�lnie!");
					alert.showAndWait();
				}
			//}
		}
		else if(event.getSource() == t_button_addtransport)
		{
			if(checkTransportInput() == false)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Dodawanie transportu");
				alert.setHeaderText(null);
				alert.setContentText("Nie wype�niono wszystkich wymaganych p�l.");
				alert.showAndWait();
			}
			else
			{
				if(checkHourInput(t_textfield_hour_start) == false || checkHourInput(t_textfield_hour_end) == false)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie transportu");
					alert.setHeaderText(null);
					alert.setContentText("Pola godzinowe wype�nione niepoprawnie.\nDozwolony format to hh:mm");
					alert.showAndWait();
				}
				else
				{
				
				
					Transport trans = new Transport();
					trans.country_start = t_countrybox_start.getSelectionModel().getSelectedItem();
					trans.city_start = t_citybox_start.getSelectionModel().getSelectedItem();
					trans.country_end = t_countrybox_end.getSelectionModel().getSelectedItem();
					trans.city_end = t_citybox_end.getSelectionModel().getSelectedItem();
					
					LocalTime starttime = LocalTime.of(getHourFromTextField(t_textfield_hour_start), getMinutesFromTextField(t_textfield_hour_start));
					LocalTime endtime = LocalTime.of(getHourFromTextField(t_textfield_hour_end), getMinutesFromTextField(t_textfield_hour_end));
					
					trans.startdatetime = LocalDateTime.of(t_datepicker_start.getValue(), starttime);
					trans.enddatetime = LocalDateTime.of(t_datepicker_end.getValue(), endtime);
					trans.transportcategory = t_combobox_transport_category.getSelectionModel().getSelectedItem();
					trans.provider = t_combobox_transport_company.getSelectionModel().getSelectedItem();
					trans.price = calctransportcost;
					trans.currency = t_currencybox.getSelectionModel().getSelectedItem();
					trans.notes = t_textarea_notes.getText();
			
					TravelFramework.getInstance().getTravel().setTransportToDay(curdate, trans);
			
					clearTransportComponents();
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Dodawanie transportu");
					alert.setHeaderText(null);
					alert.setContentText("Transport zosta� dodany pomy�lnie!");
					alert.showAndWait();
				
				}
			}
		}
		else if(event.getSource() == button_summary)
		{
			myController.loadScreen(WindowMain.TRAVEL_SUMMARY, WindowMain.TRAVEL_SUMMARY_FXML);
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
		else if(event.getSource() == button_cancel)
		{
			myController.setScreen(WindowMain.NEWTRAVELFIRST);
		}
	}
	
	public void selectRoute(String start, String end)
	{   
       	ds = new DirectionsService();
       	try
       	{     
       		dr = new DirectionsRequest( start, end, TravelModes.DRIVING);
       		ds.getRoute(dr, this, renderer);   
              
       		renderer.setMap(map);
       		renderer.setPanel(directions);       
       	}
       	catch (NullPointerException e2)
       	{
        	  System.out.println("MAP ERROR");
       	}	 
	}
	
	@Override
	public void directionsReceived(DirectionsResult results, DirectionStatus status) 
	{
		mapView.getMap().showDirectionsPane();
		List<DirectionsRoute> lista = results.getRoutes();
		List<DirectionsLeg> lista2 = lista.get(0).getLegs();
		List<DirectionsSteps> lista3 = lista2.get(0).getSteps();
        dist = lista2.get(0).getDistance().getText();
        distance = lista2.get(0).getDistance().getValue();
		System.out.println(lista3.get(0).getDuration().getText());
		System.out.println(directions.toString());
	}

	@Override
	public void mapInitialized() 
	{
	    //Set the initial properties of the map.
	    mapOptions = new MapOptions();       
	    mapOptions.center(new LatLong(52.232222, 21.008333))
	            .mapType(MapTypeIdEnum.ROADMAP)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .zoom(12);

	    map = mapView.createMap(mapOptions,true);
	    directions = mapView.getDirec();
	    renderer = new DirectionsRenderer(true, map, directions);	
	    mapView.getMap().hideDirectionsPane();
	}
}
