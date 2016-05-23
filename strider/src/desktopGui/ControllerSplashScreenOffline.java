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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerSplashScreenOffline implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
	
    @FXML
    private ImageView imageviewlogo;
    @FXML
    private ImageView imageviewlogotitle;
    @FXML
    private Button button_exit;
    @FXML
    private Label labelkektext;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		imageviewlogo.setImage(imglogopalms);
		imageviewlogotitle.setImage(imglogotext);
		button_exit.setOnAction(this);
		labelkektext.setText("\"This is the best thing since sliced bread.\" - George W. Bush");
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_exit)
		{
			WindowMain.closeWindow();
		}
	}

	@Override
	public void clearComponents() {}
}
