package desktopGui;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Model.AttractionDetails;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

    private static int windowtype;
    
    public static void lateInitialize(int arg)
    {
    	windowtype = arg;

    	accordionstages.getPanes().removeAll(accordionstages.getPanes());
    	
    	if(TravelFramework.getInstance().hasTravel() == true)
    	{
    		int curuser = User.getInstance().getId();
    		int curtravel = TravelFramework.getInstance().getCurrentTravel().getId();
    		System.out.println("Cur travel id : " + TravelFramework.getInstance().getCurrentTravel().getId());
    	
    		List<Integer> ids = DatabaseHandlerStage.getInstance().getStageIdentifiers(curuser, curtravel);

    		for(Integer integer : ids)
    		{
    			int stagetype = DatabaseHandlerStage.getInstance().getStageType(curuser, curtravel, integer.intValue());
    			TitledPane tp = new TitledPane();
    			
    			if(stagetype == 1)
    			{
    				int id_attr_det = DatabaseHandlerStage.getInstance().getAttractionDetailsId(curtravel, integer.intValue());
    				tp.setText("AttractionDetails_ID : " + id_attr_det);
    				
    			}
    			else if(stagetype == 2)
    			{
    				int id_hotel_det = DatabaseHandlerStage.getInstance().getHotelDetailsId(curtravel, integer.intValue());
    				tp.setText("HotelDetails_ID : " + id_hotel_det);
    			}
    			else if(stagetype == 3)
    			{
    				int id_transp_det = DatabaseHandlerStage.getInstance().getTransportDetailsId(curtravel, integer.intValue());
    				tp.setText("TransportDetails_ID : " + id_transp_det);
    			} 
    			accordionstages.getPanes().add(tp);
    		}
    	}
    	else
    	{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Travel summary");
			alert.setHeaderText(null);
			alert.setContentText("No current travel to display!");
			alert.showAndWait();
			myController.setScreen(WindowMain.MAIN_SCREEN);
    	}
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
	  	accordionstages = new Accordion();
	  	VBox.setVgrow(accordionstages, Priority.ALWAYS);
	  	mainvbox.getChildren().add(accordionstages);
	  	mainvbox.getChildren().get(1).toBack();
		button_back.setOnAction(this);
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
			if(windowtype == 0)
			{
				myController.setScreen(WindowMain.NEWTRAVEL_2);
			}
			else if(windowtype == 1)
			{
				myController.setScreen(WindowMain.MAIN_SCREEN);
			}
		}
	}

}
