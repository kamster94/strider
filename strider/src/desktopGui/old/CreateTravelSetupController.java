package desktopGui.old;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class CreateTravelSetupController implements Initializable, EventHandler<ActionEvent> 
{

    @FXML
    private VBox newtravelwizard_2;

    @FXML
    private DatePicker datepickerstart;

    @FXML
    private DatePicker datepickerend;

    @FXML
    private Spinner<Integer> spinnercompanions;

    @FXML
    private Button buttoncancel;

    @FXML
    private Button buttonnext;
    
    @FXML
    private VBox vboxcountrybox;

    @FXML
    private VBox vboxcitybox;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
	}
	
	public VBox getCountryBox()
	{
		return vboxcountrybox;
	}
	
	public VBox getCityBox()
	{
		return vboxcitybox;
	}
    
	@Override
	public void handle(ActionEvent arg0) 
	{
		
	}



}
