package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private DatePicker datepickerstart;

    @FXML
    private DatePicker datepickerend;

    @FXML
    private VBox vboxcountrybox;

    @FXML
    private VBox vboxcitybox;

    @FXML
    private Spinner<Integer> spinnercompanions;

    @FXML
    private Button buttonprev;

    @FXML
    private Button buttonnext;
    
    @FXML
    private Button homebutton;
    
    final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_previous = new Image("desktopGui/textures/arrowleft.png");
	final Image icon_next = new Image("desktopGui/textures/arrowright.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		buttonprev.setOnAction(this);
		buttonnext.setOnAction(this);
		homebutton.setOnAction(this);
		
		ImageView homeico = new ImageView(icon_home);
		homeico.setFitWidth(35);
		homeico.setFitHeight(35);
		
		ImageView previco = new ImageView(icon_previous);
		previco.setFitWidth(35);
		previco.setFitHeight(35);
		
		ImageView nextico = new ImageView(icon_next);
		nextico.setFitWidth(35);
		nextico.setFitHeight(35);
		
		homebutton.setGraphic(homeico);
		buttonprev.setGraphic(previco);
		buttonnext.setGraphic(nextico);
		
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == homebutton)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == buttonprev)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_1);
		}
		
		
	}





}
