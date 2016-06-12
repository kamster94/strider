package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import Model.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ControllerMain implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	
	ScreensController myController; 

    @FXML
    private Button button_createtravel;
    
    @FXML
    private Button button_changeusersettings;

    @FXML
    private Button button_additionalinfo;

    @FXML
    private Button button_travelhistory;
    
    @FXML
    private Button button_exit;
    
    @FXML
    private Button button_ratehotel;
	
    @FXML
    private ImageView imageviewlogotext;
    
    @FXML
    private Label label_welcome;
    
    @FXML
    private ImageView imageviewlogobottom;
	
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		label_welcome.setText("Witaj, " + User.getInstance().getUserName());
		imageviewlogotext.setImage(imglogotext);
		imageviewlogobottom.setImage(imglogopalms);
	
		button_travelhistory.setOnAction(this);
		button_ratehotel.setOnAction(this);
		button_exit.setOnAction(this);
		button_additionalinfo.setOnAction(this);
		button_createtravel.setOnAction(this);
		button_changeusersettings.setOnAction(this);
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_additionalinfo)
		{
			System.out.println("KEK");
			myController.loadScreenAndSet(WindowMain.ADDINFO_SCREEN, WindowMain.ADDINFO_SCREEN_FXML);
		}
		else if(arg0.getSource() == button_createtravel)
		{
			myController.loadScreenAndSet(WindowMain.NEWTRAVELFIRST, WindowMain.NEWTRAVELFIRST_FXML);
		}
		else if(arg0.getSource() == button_ratehotel)
		{
			myController.loadScreenAndSet(WindowMain.RATE_HOTEL, WindowMain.RATE_HOTEL_FXML);
		}
		else if(arg0.getSource() == button_changeusersettings)
		{
			myController.loadScreenAndSet(WindowMain.OPTIONS, WindowMain.OPTIONS_FXML);
		}
		else if(arg0.getSource() == button_travelhistory)
		{
			myController.loadScreenAndSet(WindowMain.TRAVEL_HISTORY, WindowMain.TRAVEL_HISTORY_FXML);
		}
		else if(arg0.getSource() == button_exit)
		{
			WindowMain.closeWindow();
		}
	}



}
