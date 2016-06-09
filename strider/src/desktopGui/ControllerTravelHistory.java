package desktopGui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Model.Travel;
import dbHandlers.DatabaseHandlerTravelHistory;
import dbHandlers.DatabaseHandlerTripAdder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerTravelHistory implements ControlledScreen, Initializable, EventHandler<ActionEvent>
{
	ScreensController myController; 
	
    @FXML
    private ListView<String> listviewtravels;

    @FXML
    private Accordion accordionsummary;

    @FXML
    private Button button_cancel;

	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;		
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_cancel.setOnAction(this);
		
		List<Travel> travellist = DatabaseHandlerTravelHistory.getInstance().getUserTravels();
		
		for(Travel t : travellist)
		{
			listviewtravels.getItems().add(t.getName());
		}
		
		
		
		
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_cancel)
		{
			myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		}
		
	}
}
