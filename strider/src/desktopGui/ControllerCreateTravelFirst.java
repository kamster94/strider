package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerCreateTravelFirst implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private ListView<String> travelslist;

    @FXML
    private Button backtohome;

    @FXML
    private Button createnewtravel;
    
	final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_next = new Image("desktopGui/textures/arrowright.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		backtohome.setOnAction(this);
		createnewtravel.setOnAction(this);
		
		ImageView homeico = new ImageView(icon_home);
		homeico.setFitWidth(35);
		homeico.setFitHeight(35);
		
		ImageView nextico = new ImageView(icon_next);
		nextico.setFitWidth(35);
		nextico.setFitHeight(35);
		
		backtohome.setGraphic(homeico);
		createnewtravel.setGraphic(nextico);
		
		
		
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == backtohome)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == createnewtravel)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_2);
		}
	}





}
