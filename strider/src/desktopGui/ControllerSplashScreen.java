package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerSplashScreen implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private ImageView imageviewlogo;

    @FXML
    private ImageView imageviewlogotitle;

    @FXML
    private TextField textfieldusername;

    @FXML
    private PasswordField passwordfieldpassword;

    @FXML
    private Button buttonlogin;
    
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		imageviewlogo.setImage(imglogopalms);
		imageviewlogotitle.setImage(imglogotext);
		
		buttonlogin.setOnAction(this);

		/*
		ImageView continueico = new ImageView(icon_next);
		continueico.setFitWidth(35);
		continueico.setFitHeight(35);
		buttoncontinue.setGraphic(nextico);
		*/
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == buttonlogin)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
	}
}

