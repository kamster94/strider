package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ControllerMain implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	
	ScreensController myController; 

    @FXML
    private Button button_createtravel;

    @FXML
    private Button button_viewcurtravel;

    @FXML
    private Button button_additionalinfo;

    @FXML
    private Button button_history;

    @FXML
    private Button button_exit;
	
    @FXML
    private ImageView imageviewlogotext;
    
    @FXML
    private ImageView imageviewlogobottom;
	
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		imageviewlogotext.setImage(imglogotext);
		imageviewlogobottom.setImage(imglogopalms);
		
		button_exit.setOnAction(this);
		button_additionalinfo.setOnAction(this);
		button_createtravel.setOnAction(this);
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == button_additionalinfo)
		{
			System.out.println("KEK");
			myController.setScreen(WindowMain.ADDINFO_SCREEN);
		}
		else if(arg0.getSource() == button_createtravel)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_1);
		}
		else if(arg0.getSource() == button_exit)
		{
			WindowMain.closeWindow();
		}
	}



}
