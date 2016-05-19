package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import dbHandlers.DatabaseHandlerLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerSplashScreen implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
	
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
    @FXML
    private Button buttoncreatenewaccount;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		imageviewlogo.setImage(imglogopalms);
		imageviewlogotitle.setImage(imglogotext);
		
		buttonlogin.setOnAction(this);
		buttoncreatenewaccount.setOnAction(this);
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
			
			if(status == -2)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login");
				alert.setHeaderText(null);
				alert.setContentText("Invalid password for user " + textfieldemail.getText());
				alert.showAndWait();
			}
			else if(status == -1)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login");
				alert.setHeaderText(null);
				alert.setContentText("User doesn't exist.");
				alert.showAndWait();
			}
			else if(status == 0)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login");
				alert.setHeaderText(null);
				alert.setContentText("Login succesfull!");
				alert.showAndWait();
				
				clearComponents();
				myController.setScreen(WindowMain.MAIN_SCREEN);
			}
		}
		else if(arg0.getSource() == buttoncreatenewaccount)
		{
			clearComponents();
			myController.setScreen(WindowMain.CREATEACCOUNT_SCREEN);
		}
	}

	@Override
	public void clearComponents() 
	{
		textfieldemail.setText("");
		passwordfieldpassword.setText("");
	}
}
