package desktopGui;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerTravelSummary implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private VBox travelbox;

    @FXML
    private Button buttonhome;

    @FXML
    private Button buttonprevious;

    @FXML
    private Button buttonfinish;
    
    final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_previous = new Image("desktopGui/textures/arrowleft.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		buttonprevious.setOnAction(this);
		buttonhome.setOnAction(this);
		
		ImageView homeico = new ImageView(icon_home);
		homeico.setFitWidth(35);
		homeico.setFitHeight(35);
		
		ImageView previco = new ImageView(icon_previous);
		previco.setFitWidth(35);
		previco.setFitHeight(35);
		
		buttonhome.setGraphic(homeico);
		buttonprevious.setGraphic(previco);
		
		
		
		//fuck the police
		//vboxcitybox.getChildren().add(WindowMain.getCityBox());
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == buttonhome)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == buttonprevious)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_2);
		}
	}

}
