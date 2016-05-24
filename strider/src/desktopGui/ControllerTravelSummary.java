package desktopGui;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Attraction;
import Model.AttractionDetails;
import Model.Day;
import Model.HotelDetails;

import Model.TravelFramework;
import Model.User;
import dbHandlers.DatabaseHandlerAttractionAdder;
import dbHandlers.DatabaseHandlerHotelAdder;
import dbHandlers.DatabaseHandlerStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerTravelSummary implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	static ScreensController myController;
	
    @FXML
    private VBox mainvbox;
    @FXML
    private Button button_back;
    
    @FXML
    private static Accordion accordionstages;
    
    public void addTitlePane()
    {
    	
    	
    	
    	
    }
    
    
 
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		button_back.setOnAction(this);
		
    	//windowtype = arg;


    		int curuser = User.getInstance().getId();
    		int curtravel = TravelFramework.getInstance().getTravel().getId();
    		System.out.println("I HAZ TRAVEL");
    		
    		List<Day> traveldays = TravelFramework.getInstance().getTravel().days;
    		
    		int daynum = 1;
    		
    		accordionstages.getPanes().setAll(new LinkedList<TitledPane>());
    		
    		for(Day day : traveldays)
    		{

    			TitledPane daytp = new TitledPane();
    			VBox dayvbox = new VBox();
    			daytp.setContent(dayvbox);
    			
    			daytp.setText("Day " + daynum + " | " + day.date.getDayOfMonth() + "-" + day.date.getMonthValue() +"-" + day.date.getYear());
    	
    			for(Attraction at : day.attractions)
    			{
    				TitledPane tp = new TitledPane();
    				tp.setText("ATRAKCJA | " + at.name);
    				VBox attrvbox = new VBox();
    				
    				Label atname = new Label("Nazwa: " + at.name);
    				Label athours = new Label("Godziny otwarcia: od " + at.openfrom + " do " + at.opento);
    				Label price = new Label("Cena: " + at.price);
    				
    				attrvbox.getChildren().add(atname);
    				attrvbox.getChildren().add(athours);
    				if(at.price > 0)attrvbox.getChildren().add(price);
    				
    				tp.setContent(attrvbox);
    				dayvbox.getChildren().add(tp);
    				
    			}
    			
    			if(day.hotel != null)
    			{
    				TitledPane tp = new TitledPane();
    				tp.setText("HOTEL | " + day.hotel.name);
    				VBox hotbox = new VBox();
    				
    				Label hotname = new Label("Nazwa: " + day.hotel.name);
    				Label price = new Label("Cena za dobê: " + day.hotel.pricepernite);
    				
    				hotbox.getChildren().add(hotname);
    				hotbox.getChildren().add(price);
    				if(day.hotel.pricepernite > 0)hotbox.getChildren().add(price);
    				
    				tp.setContent(hotbox);
    				dayvbox.getChildren().add(tp);
    				
    			}
    			
    			if(day.transport != null)
    			{
    				TitledPane tp = new TitledPane();
    				tp.setText("TRANSPORT | " + day.transport.country_start + "(" + day.transport.city_start + ") --> " + day.transport.country_end + "(" + day.transport.city_end + ")");
    				VBox transbox = new VBox();
    				
    				Label hotname = new Label("Nazwa: " + day.hotel.name);
    				Label price = new Label("Cena za dobê: " + day.hotel.pricepernite);
    				
    				transbox.getChildren().add(hotname);
    				transbox.getChildren().add(price);
    				if(day.hotel.pricepernite > 0)transbox.getChildren().add(price);
    				
    				tp.setContent(transbox);
    				dayvbox.getChildren().add(tp);
    				
    			}
    			
    			
    			accordionstages.getPanes().add(daytp);
    			daynum++;
    		}
    	
  
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_back)
		{
			myController.setScreen(WindowMain.NEWTRAVELSECOND);
		}
	}

}
