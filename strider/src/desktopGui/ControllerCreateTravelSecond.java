package desktopGui;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import travel.TravelFramework;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<Event>
{
	ScreensController myController;
	
    @FXML private ControllerTabAttraction pageattraction;
    
    //@FXML private ControllerTabHotel pagehotel;
	
   // @FXML private ControllerTabTransport pagetransport;
	
    ////////////ATTRACTION
    @FXML
    private TabPane tabpane;
    @FXML
    private Tab tabattraction;
    @FXML
    private VBox a_vbox_country_from;
    @FXML
    private VBox a_vbox_city_from;
    @FXML
    private TextField a_textfield_zipcode;
    @FXML
    private TextField a_textfield_name;
    @FXML
    private TextField a_textfield_street;
    @FXML
    private TextField textfieldnumber;
    @FXML
    private TextField a_textfield_open;
    @FXML
    private TextField a_textfield_closed;
    @FXML
    private TextField a_textfield_price;
    @FXML
    private ComboBox<?> a_comboboxmycurrency;
    @FXML
    private ComboBox<?> a_combobox_attrcurrency;
    @FXML
    private TextArea a_textarea_notes;
    @FXML
    private ListView<?> a_listview_attractions;
    @FXML
    private Button a_button_add;
    @FXML
    private Tab tabhotel;
    @FXML
    private VBox vboxcountrybox1;
    @FXML
    private VBox vboxcitybox1;
    @FXML
    private TextField textfieldzipcode1;
    @FXML
    private TextField textfieldname1;
    @FXML
    private TextField textfieldstreet1;
    @FXML
    private TextField textfieldnumber1;
    @FXML
    private TextField textfieldprice1;
    @FXML
    private ComboBox<?> comboboxcurrency1;
    @FXML
    private TextArea textareanotes1;
    @FXML
    private ListView<?> listviewattractions1;
    @FXML
    private Tab tabtransport;
    @FXML
    private VBox vboxcountrybox2;
    @FXML
    private VBox vboxcitybox2;
    @FXML
    private TextField textfieldprice2;
    @FXML
    private ComboBox<?> comboboxcurrency2;
    @FXML
    private TextArea textareanotes2;
    @FXML
    private ListView<?> listviewattractions2;
    @FXML
    private Button button_previous;
    @FXML
    private Button button_summary;
    
    /////////////////////////////////////
    
    
    
    
    /*
    public static void getData()
    {
		if(TravelFramework.getInstance().hasTravel())
		{
			labeltraveltitle.setText(TravelFramework.getInstance().getCurrentTravel().getName());
		}
    }
    */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_previous.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{
				myController.setScreen(WindowMain.NEWTRAVEL_1);
			}
		});
		
		button_summary.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{
				myController.setScreen(WindowMain.TRAVEL_SUMMARY);
			}
		});
		
		//tabattraction.setOnSelectionChanged(this);
		//tabhotel.setOnSelectionChanged(this);
		//tabtransport.setOnSelectionChanged(this);
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(Event arg0) 
	{

		
	}


}
