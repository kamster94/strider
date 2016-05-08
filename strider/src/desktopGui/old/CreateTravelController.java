package desktopGui.old;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CreateTravelController implements Initializable, EventHandler<ActionEvent> 
{
	@FXML
	private VBox newtravelwizard_1;
	
    @FXML
    private VBox summarybox;
    
	@FXML
	private ListView<String> travelslist;

	@FXML
	private Button createnewtravel;

	private boolean extended;
	
	private VBox TravelSetupVBox;
	private CreateTravelSetupController createtravelsetupcontroller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		//Sciagnac liste traveli z bazy i wsadzic do travelslist
		createnewtravel.setOnAction(this);
		
	    List<String> values = Arrays.asList("one", "two", "three");
	    travelslist.setItems(FXCollections.observableList(values));
		
		extended = false;
		
		travelslist.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
	        @Override
	        public void handle(MouseEvent event) 
	        {	
	        	if(extended == false)
	        	{
	        		System.out.println("Extending");
	        	}		
	        	else
	        	{
	        		System.out.println("Retracting");
	        	}
	        }
	    });
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/fxml_mainwindow_newtravel_2.fxml"));
		try 
		{
			TravelSetupVBox = (VBox)fxmlLoader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		createtravelsetupcontroller = (CreateTravelSetupController) fxmlLoader.getController();
	}
	
	public VBox getCityBox()
	{
		return createtravelsetupcontroller.getCityBox();
	}
	
	public VBox getCountryBox()
	{
		return createtravelsetupcontroller.getCountryBox();
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == createnewtravel)
		{

			
		}
		
	}


	

}
