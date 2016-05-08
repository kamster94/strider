package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button buttoncontinue;
    
	final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		imageviewlogo.setImage(imglogopalms);
		imageviewlogotitle.setImage(imglogotext);
		
		buttoncontinue.setOnAction(this);

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
		if(arg0.getSource() == buttoncontinue)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
	}
}

