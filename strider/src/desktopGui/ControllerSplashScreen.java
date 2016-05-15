package desktopGui;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerLogin;
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
    private TextField textfieldemail;

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
			textfieldemail.getText();
			passwordfieldpassword.getText();
			DatabaseHandlerLogin dhl = new DatabaseHandlerLogin();
			
			int status = dhl.loginUser(textfieldemail.getText(), passwordfieldpassword.getText());
			System.out.println("Login Status: " + status);
			
			//-1 U¿ytkownik istnieje, z³e has³o!
			
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
	}
}

