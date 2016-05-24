package countryWarnings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class ResultsController implements Initializable, ClearableScreen, ControlledScreen{

	ScreensController myController; 
	

		@FXML
		private AnchorPane pane;
	
	   @FXML
	    private TextField litersPerHourTextField;

	    @FXML
	    private Label instructionLabel;

	    @FXML
	    private Button okButton;

	    @FXML
	    private WebView resultsWebView;

	    @FXML
	    private Button warningsBackButton;

	    @FXML
	    private Button mapBackButton;

	    @FXML
	    private Label warnigsBackLabel;

	    @FXML
	    private Label mapBackLabel;

	    @FXML
	    private ComboBox<?> typeOfFuelComboBox;

	    @FXML
	    private Label typeOfFuelLabel;
	    
	    public static Label distanceLabel;
	    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList fuels = FXCollections.observableArrayList();
		fuels.addAll("e95","s98", "LPG", "ON");
		typeOfFuelComboBox.setItems(fuels);
		typeOfFuelComboBox.getSelectionModel().select(0);
	//	distanceLabel.setText(distanceLabel.getText() + MapController.distance);
		
		distanceLabel = new Label();
		distanceLabel.setText("Obliczona D³ugoœæ trasy: ");
		distanceLabel.setLayoutX(594);
		distanceLabel.setLayoutY(128);
		pane.getChildren().add(distanceLabel);
		
		
		okButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {        	
	        	double lol = Double.parseDouble(litersPerHourTextField.getText().replaceAll(",", "."));	
	        	ResultsInformation information = new ResultsInformation(lol, MapController.distance, (String) typeOfFuelComboBox.getValue());
	        	resultsWebView.getEngine().loadContent(information.getResultsInformationHtml().toString());
	        	//resultsWebView.getEngine().load("https://www.yahoo.com/news/weather/country/state/city-638242/");
	        }
	    });
		
		
		mapBackButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {        	
	        	 myController.setScreen(Main.MAP); 	
	        }
	    });
		
		
		warningsBackButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {        	
	        	 myController.setScreen(Main.WARNINGS); 	
	        }
	    });
		
		
	}
	

	@Override
	public void setScreenParent(ScreensController screenPage) {
		
		myController = screenPage; 	
		
	}

	@Override
	public void clearComponents() {
		// TODO Auto-generated method stub
		
	}



}
