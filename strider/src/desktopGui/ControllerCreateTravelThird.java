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

public class ControllerCreateTravelThird implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private TextField textfieldname;

    @FXML
    private TextField textfieldstreet;

    @FXML
    private TextField textfieldnumber;

    @FXML
    private TextField textfieldzipcode;

    @FXML
    private VBox vboxcitybox;

    @FXML
    private ComboBox<String> comboboxopen;

    @FXML
    private ComboBox<String> comboboxclosed;

    @FXML
    private TextField textfieldprice;

    @FXML
    private ComboBox<String> comboboxcurrency;

    @FXML
    private TextArea textareanotes;

    @FXML
    private ImageView imageviewmediawiki;

    @FXML
    private WebView cityWebView;

    @FXML
    private ListView<String> listviewattractions;

    @FXML
    private Button buttonaddattraction;

    @FXML
    private Button buttonhome;

    @FXML
    private Button buttonprev;

    @FXML
    private Button buttonsummary;
    
    @FXML
    private ComboBox<String> citybox;
    
    final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_previous = new Image("desktopGui/textures/arrowleft.png");
	final Image icon_next = new Image("desktopGui/textures/arrowright.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		buttonprev.setOnAction(this);
		buttonsummary.setOnAction(this);
		buttonhome.setOnAction(this);
		
		ImageView homeico = new ImageView(icon_home);
		homeico.setFitWidth(35);
		homeico.setFitHeight(35);
		
		ImageView previco = new ImageView(icon_previous);
		previco.setFitWidth(35);
		previco.setFitHeight(35);
		
		buttonhome.setGraphic(homeico);
		buttonprev.setGraphic(previco);
		
		//fuck the police
		citybox = WindowMain.getCityBox();

		vboxcitybox.getChildren().add(citybox);
		
		

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
		else if(arg0.getSource() == buttonprev)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_2);
		}
		else if(arg0.getSource() == buttonsummary)
		{
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
		
		
	}

}
