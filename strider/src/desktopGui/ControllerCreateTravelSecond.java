package desktopGui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;

import Model.Attraction;
import Model.Hotel;
import Model.Transport;
import Model.TravelFramework;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>, MapComponentInitializedListener, DirectionsServiceCallback
{
	ScreensController myController;
	
    @FXML
    private Slider slider_tripday;
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
    @FXML
    private VBox t_vbox_mapbox;
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
	
	LocalDate curdate;
	
    DirectionsRenderer renderer;
    
    public void setupInitialValues()
    {
    	long daysnum = TravelFramework.getInstance().getTravel().getDaysNumber();
    	LocalDateTime startdate = TravelFramework.getInstance().getTravel().getStartDate();
    	LocalDateTime enddate = TravelFramework.getInstance().getTravel().getEndDate();
    	
    	slider_tripday.setMax(daysnum);
  
    	System.out.println("Numdays in travel: " + TravelFramework.getInstance().getTravel().getDaysNumber());
    }
    
    public void updateDates()
    {
    	LocalDate startdate = TravelFramework.getInstance().getTravel().getStartDate().toLocalDate();
    	LocalDate enddate = TravelFramework.getInstance().getTravel().getEndDate().toLocalDate();
    	
    	curdate = startdate.plusDays((int)slider_tripday.getValue() - 1);

    	t_datepicker_start.setValue(curdate);
    	h_datepicker_start.setValue(curdate);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		a_countrybox = WindowMain.getCountryBox();
		a_citybox = WindowMain.getCityBox();
		a_currencybox = WindowMain.getCityBox();
		
		h_countrybox = WindowMain.getCountryBox();
		h_citybox = WindowMain.getCityBox();
		h_currencybox = WindowMain.getCurrencyBox();
		
		t_countrybox_start = WindowMain.getCountryBox();
		t_citybox_start = WindowMain.getCityBox();
		t_countrybox_end = WindowMain.getCountryBox();
		t_citybox_end = WindowMain.getCityBox();
		
		
		
		
		a_button_findcities = new Button();
		a_button_findcities.setText("Znajdü miasta");
		a_button_findattractions = new Button();
		a_button_findattractions.setText("Znajdü atrakcje");
		
		a_hbox_countrybox.getChildren().add(a_countrybox);
		a_hbox_countrybox.getChildren().add(a_button_findcities);
		a_hbox_citybox.getChildren().add(a_citybox);
		a_hbox_citybox.getChildren().add(a_button_findattractions);
		a_hbox_currencybox.getChildren().add(a_currencybox);

		h_button_findcities = new Button();
		h_button_findcities.setText("Znajdü miasta");
		
		h_hbox_countrybox.getChildren().add(h_countrybox);
		h_hbox_countrybox.getChildren().add(h_button_findcities);
		h_hbox_citybox.getChildren().add(h_citybox);
		
		t_button_findcities = new Button();
		t_button_findcities.setText("Znajdü miasta");
		
		t_hbox_countrybox_start.getChildren().add(t_countrybox_start);
		t_hbox_citybox_start.getChildren().add(t_citybox_start);
		t_hbox_countrybox_end.getChildren().add(t_countrybox_end);
		t_hbox_countrybox_end.getChildren().add(t_button_findcities);
		t_hbox_citybox_end.getChildren().add(t_citybox_end);
		
		
		
		
		button_summary.setOnAction(this);

		
		
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);	
		
		t_vbox_mapbox.getChildren().add(mapView);
		
		a_button_addattraction.setOnAction(this);
		h_button_addhotel.setOnAction(this);
		t_button_addtransport.setOnAction(this);
		
		
		slider_tripday.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
			{
				//This ensures that the shit underneath gets called ONLY when the value finally sets.
				//if(slider_tripday.isValueChanging() == false)
				//{
					updateDates();
				//}
			}
		});
		setupInitialValues();
	}
	

	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	
	
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == a_button_addattraction)
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
			at.datetime = LocalDateTime.of(t_datepicker_start.getValue(), LocalTime.MIN);
			at.currency = a_currencybox.getSelectionModel().getSelectedItem();
			at.notes = a_textarea_notes.getText();
			at.price = Float.parseFloat(a_textfield_price.getText());

			TravelFramework.getInstance().getTravel().addAttractionToDay(LocalDateTime.of(curdate,LocalTime.MIN), at);
			
			//selectRoute("Warszawa", "KrakÛw");
		}
		else if(event.getSource() == h_button_addhotel)
		{
			Hotel hot = new Hotel();
			hot.country = h_countrybox.getSelectionModel().getSelectedItem();
			hot.city = h_citybox.getSelectionModel().getSelectedItem();
			hot.name = h_textfield_name.getText();
			hot.street = h_textfield_street.getText();
			hot.zipcode = h_textfield_zipcode.getText();
			hot.number = h_textfield_number.getText();
			hot.accomodation_startdate = LocalDateTime.of(h_datepicker_start.getValue(), LocalTime.MIN);
			hot.accomodation_enddate = LocalDateTime.of(h_datepicker_end.getValue(), LocalTime.MIN);
			hot.pricepernite = Float.parseFloat(h_textfield_price.getText());
			hot.currency = h_currencybox.getSelectionModel().getSelectedItem();
			hot.notes = h_textarea_notes.getText();
			
			TravelFramework.getInstance().getTravel().setHotelToDay(LocalDateTime.of(curdate,LocalTime.MIN), hot);
			
			
		}
		else if(event.getSource() == t_button_addtransport)
		{
			Transport trans = new Transport();
			trans.country_start = t_countrybox_start.getSelectionModel().getSelectedItem();
			trans.city_start = t_citybox_start.getSelectionModel().getSelectedItem();
			trans.country_end = t_countrybox_end.getSelectionModel().getSelectedItem();
			trans.city_end = t_citybox_end.getSelectionModel().getSelectedItem();
			trans.startdatetime = LocalDateTime.of(t_datepicker_start.getValue(), LocalTime.MIN);
			trans.enddatetime = LocalDateTime.of(t_datepicker_end.getValue(), LocalTime.MIN);
			trans.transportcategory = t_choicebox_transport_category.getSelectionModel().getSelectedItem();
			trans.price = Float.parseFloat(a_textfield_price.getText());
			trans.currency = t_currencybox.getSelectionModel().getSelectedItem();
			trans.notes = a_textarea_notes.getText();
			
			TravelFramework.getInstance().getTravel().setTransportToDay(LocalDateTime.of(curdate, LocalTime.MIN), trans);
			
		}
		
		
		else if(event.getSource() == button_summary)
		{
			myController.loadScreen(WindowMain.TRAVEL_SUMMARY, WindowMain.TRAVEL_SUMMARY_FXML);
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
	}
	
	
	public void selectRoute(String start, String end)
	{   
		ds = new DirectionsService();

		try
		{     
			dr = new DirectionsRequest(start, end, TravelModes.DRIVING);
			ds.getRoute(dr, this, renderer);   
		               
		}
		catch (NullPointerException e2)
		{
			e2.printStackTrace();
		}  
	}
	
	
	
	@Override
	public void directionsReceived(DirectionsResult arg0, DirectionStatus arg1) 
	{
		/*
		List<DirectionsRoute> lista = results.getRoutes();
		List<DirectionsLeg> lista2 = lista.get(0).getLegs();
		List<DirectionsSteps> lista3 = lista2.get(0).getSteps();
		
		for(int i = 0; i < lista3.size(); i++)
			System.out.println(i + " " + lista3.get(i).getInstructions());
	        
        distanceTextField.setText(lista2.get(0).getDistance().getText());
        distance = lista2.get(0).getDistance().getValue();
		System.out.println(lista3.get(0).getDuration().getText());
		System.out.println(directions.toString());
		*/
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
	    
	    map = mapView.createMap(mapOptions, true);
	    directions = mapView.getDirec();
	}
}
