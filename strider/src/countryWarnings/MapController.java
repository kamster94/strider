package countryWarnings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

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
import com.lynden.gmapsfx.service.directions.DirectionsSteps;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

import desktopGui.ControlledScreen;
import desktopGui.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class MapController implements Initializable, ControlledScreen, MapComponentInitializedListener, 
ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback{

	ScreensController myController; 
    
    @FXML
    private BorderPane pane;

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
    private TextField distanceTextField;

    @FXML
    private Button goToResultsButton;
    
    
    public static  MapOptions mapOptions;
    public static GoogleMapView mapView;
    public static double distance;
	public static GoogleMap map;
	protected DirectionsPane directions;
	protected DirectionsService ds;
	protected DirectionsRequest dr;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		fromTextField.setPromptText("lokacja startowa");
		toTextField.setPromptText("lokacja docelowa");
		distanceTextField.setPromptText("odleglosc");
		
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);	

		pane.setCenter(mapView);
		goToResultsButton.setDisable(true);
		
		
		findButton.setOnAction(new EventHandler<ActionEvent>() 
		{   @Override
	        public void handle(ActionEvent arg0) 
	        {	
			    
	        	selectRoute();   
	        	goToResultsButton.setDisable(false);
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
		
		goToResultsButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {        	
	        	 myController.setScreen(Main.RESULTS); 	
	        }
	    });
		
	}

	public void selectRoute()
	{          	    

	    renderer = new DirectionsRenderer();	
       	ds = new DirectionsService();

       	try{     
               dr = new DirectionsRequest(   
            		   fromTextField.getText(),
            		   toTextField.getText(),
                     TravelModes.DRIVING
                      );
               
               ds.getRoute(dr, this, renderer);   
               renderer.setMap(map);
               renderer.setPanel(directions);
               
          }catch (NullPointerException e2){
        	  System.out.println("chujdupa");
          }	 
          
	}
	
	DirectionsRenderer renderer;
	public void mapInitialized()
	{
	    //Set the initial properties of the map.
	    mapOptions = new MapOptions();       
	    mapOptions.center(new LatLong(52.232222, 21.008333))
	            .mapType(MapTypeIdEnum.ROADMAP)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .zoom(12);

	    map = mapView.createMap(mapOptions,true);
	    directions = mapView.getDirec();
	    
	    
        
	    
	}
	

	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 	
	}


	@Override
	public void directionsReceived(DirectionsResult results, DirectionStatus status)
	{
		//mapView.getMap().showDirectionsPane();
		
		/*
		List<DirectionsRoute> lista = results.getRoutes();
		List<DirectionsLeg> lista2 = lista.get(0).getLegs();
		List<DirectionsSteps> lista3 = lista2.get(0).getSteps();
		
		
		for(int i = 0; i < lista3.size(); i++)
			System.out.println(i + " " + lista3.get(i).getInstructions());
	         
	   
        distanceTextField.setText(lista2.get(0).getDistance().getText());
        distance = lista2.get(0).getDistance().getValue();
		System.out.println(lista3.get(0).getDuration().getText());
		System.out.println(directions.toString());
		*/
		
	}

	@Override
	public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}