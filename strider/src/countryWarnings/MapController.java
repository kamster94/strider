package countryWarnings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsLeg;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsRoute;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;

import desktopGui.ControlledScreen;
import desktopGui.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class MapController implements Initializable, ControlledScreen, MapComponentInitializedListener, DirectionsServiceCallback{

	ScreensController myController; 
    
	@FXML
    private ToolBar toolBar;

    @FXML
    private Button backButton;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private Button findButton;

    @FXML
    private TextField distaneTextField;
    
    @FXML
    private BorderPane pane;
    
    public static  MapOptions mapOptions;
    public static GoogleMapView mapView;
	public static GoogleMap map;
	protected DirectionsPane directions;
	protected DirectionsService ds;
	protected DirectionsRequest dr;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);		
		pane.setCenter(mapView);
		
		
		findButton.setOnAction(new EventHandler<ActionEvent>() 
		{@Override
	        public void handle(ActionEvent arg0) 
	        {
	        	
	        	selectRoute();   	
	        }
	    });

		backButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {        	
	        	 myController.setScreen(Main.WARNINGS); 	
	        }
	    });    
		
	}

	public void selectRoute()
	{
		map = mapView.createMap(mapOptions);	                	
        directions = mapView.getDirec();	       	    
       	ds = new DirectionsService();
        renderer = new DirectionsRenderer(true, map, directions);

          try{     
               dr = new DirectionsRequest(   
            		   fromTextField.getText(),
            		   toTextField.getText(),
                     TravelModes.DRIVING
                      );
               
               ds.getRoute(dr, this, renderer);   
    	               	               	              
          }catch (NullPointerException e2){
        	  System.out.println("chujdupa");
          }	              
	}
	
	DirectionsRenderer renderer;
	public void mapInitialized()
	{
	    //Set the initial properties of the map.
	    mapOptions = new MapOptions();       
	    mapOptions.center(new LatLong(52.1356, 21.0030))
	            .mapType(MapTypeIdEnum.ROADMAP)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .zoom(12);

	    map = mapView.createMap(mapOptions);
	    directions = mapView.getDirec();
        renderer = new DirectionsRenderer(true, map, directions);
	}
	

	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 	
	}


	@Override
	public void directionsReceived(DirectionsResult results, DirectionStatus status) {
		List<DirectionsRoute> lista = results.getRoutes();
		List<DirectionsLeg> lista2 = lista.get(0).getLegs();
	   
        distaneTextField.setText(lista2.get(0).getDistance().getText());
		System.out.println("lol");
	}


	
	
	
}
