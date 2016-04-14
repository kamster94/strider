package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class DraggableCalendarTestController implements Initializable, EventHandler<ActionEvent>
{
		@FXML
    	private VBox thinglist;

    	@FXML
    	private HBox thing;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1)
		{	
			
		}

		@Override
		public void handle(ActionEvent arg0)
		{
		}
	
}
