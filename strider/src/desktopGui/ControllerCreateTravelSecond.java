package desktopGui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>
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
    private ComboBox<String> a_countrybox;
    private ComboBox<String> a_citybox;
    private ComboBox<String> a_currencybox;
    private Button a_button_findcities;
    private Button a_button_findattractions;
    
    public void setupInitialValues()
    {
    	long daysnum = TravelFramework.getInstance().getTravel().getDaysNumber();
    	LocalDateTime startdate = TravelFramework.getInstance().getTravel().getStartDate();
    	LocalDateTime enddate = TravelFramework.getInstance().getTravel().getEndDate();
    	
    	slider_tripday.setMax(daysnum - 1);
  
    	System.out.println("Numdays in travel: " + TravelFramework.getInstance().getTravel().getDaysNumber());
    }
    
    public void updateDates()
    {
    	LocalDate startdate = TravelFramework.getInstance().getTravel().getStartDate().toLocalDate();
    	LocalDate enddate = TravelFramework.getInstance().getTravel().getEndDate().toLocalDate();
    	
    	LocalDate curdate = startdate.plusDays((int)slider_tripday.getValue() - 1);
    	
    	
    	t_datepicker_start.setValue(curdate);
    	h_datepicker_start.setValue(curdate);
    	//t_datepicker_end.setValue(enddate);
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		a_countrybox = WindowMain.getCountryBox();
		a_citybox = WindowMain.getCityBox();
		a_currencybox = WindowMain.getCityBox();
		a_button_findcities = new Button();
		a_button_findcities.setText("Znajdü miasta");
		a_button_findattractions = new Button();
		a_button_findattractions.setText("Znajdü atrakcje");
		
		a_hbox_countrybox.getChildren().add(a_countrybox);
		a_hbox_countrybox.getChildren().add(a_button_findcities);
		a_hbox_citybox.getChildren().add(a_citybox);
		a_hbox_citybox.getChildren().add(a_button_findattractions);
		a_hbox_currencybox.getChildren().add(a_currencybox);

		button_summary.setOnAction(this);
		a_textfield_zipcode.setDisable(true);
		a_textfield_name.setDisable(true);
		a_textfield_street.setDisable(true);
		
		slider_tripday.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
			{
				//This ensures that the shit underneath gets called ONLY when the value finally sets.
				if(slider_tripday.isValueChanging() == false)
				{
					updateDates();
			
				}
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
		if(event.getSource() == button_summary)
		{
			myController.loadScreen(WindowMain.TRAVEL_SUMMARY, WindowMain.TRAVEL_SUMMARY_FXML);
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
	}
	
}
