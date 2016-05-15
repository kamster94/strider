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
	
    @FXML
    private TabPane tabpane;

    @FXML
    private Tab tabattraction;

    @FXML
    private Tab tabhotel;

    @FXML
    private Tab tabtransport;

    @FXML
    private Button button_previous;

    @FXML
    private Button button_summary;
    
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
		
		tabattraction.setOnSelectionChanged(this);
		tabhotel.setOnSelectionChanged(this);
		tabtransport.setOnSelectionChanged(this);
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(Event arg0) 
	{
		if(tabattraction.isSelected())
		{
			System.out.println("Changen attraction");
		}
		else if(tabhotel.isSelected())
		{
			System.out.println("changehn hotel");
		}
		else if(tabtransport.isSelected())
		{
			System.out.println("changen transpor");
		}
		
	}


}
