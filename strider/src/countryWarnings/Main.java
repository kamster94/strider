package countryWarnings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbConnection.DbAccess;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import np.com.ngopal.control.AutoFillTextBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXMLLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;


import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.DirectionsWaypoint;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.sun.javafx.collections.MapAdapterChange;

public class Main extends Application implements MapComponentInitializedListener, DirectionsServiceCallback {
	
	
	 public static GoogleMapView mapView;
	 public static GoogleMap map;
	 protected DirectionsPane directions;
	 protected DirectionsService ds;
	 protected DirectionsRequest dr;
	 private Button wroc;
	 private TextField from;
	 private TextField to;
	 private Button wyznacz;
	
	 public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	 public static List<String> countryNames;
	 public static List<String> citiesOfCountries;
	 public static ArrayList<String> countryHtmls;
	 public static ComboBox countryBox ;
	 public static ComboBox cityBox ;
	 public static ObservableList countryData;
	 public static ObservableList cityData;
	 static public DbAccess dataBaseAccess;
	 public static Stage mainStage;
	 public static Scene scene2;
	 public static Scene scene;
	 public static LatLong latLong;
	 public static  MapOptions mapOptions;
	@Override

	
	public void start(Stage primaryStage) {
		
		mainStage = primaryStage;
		ToolBar tb = new ToolBar();
		BorderPane bp = new BorderPane();
		Document document;
		
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		 try {
			 
			 dataBaseAccess = new DbAccess("Artureczek","debil");
			 countryNames = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName")));
			 countryHtmls = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink")));
			 countryData = FXCollections.observableArrayList(); //Do sugestii wyszukiwania krajow
				
				for(int j=0; j<countryNames.size(); j++){
				countryData.add(countryNames.get(j));								
				}
				
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		try {		
			
			countryBox = new ComboBox();	
			countryBox.setItems(countryData);
			new AutoCompleteComboBoxListener(countryBox);
			countryBox.setLayoutX(37);
			countryBox.setLayoutY(40);
			countryBox.setPrefWidth(190);
			
			cityBox = new ComboBox();	
			new AutoCompleteComboBoxListener(cityBox);
			cityBox.setLayoutX(490);
			cityBox.setLayoutY(40);
			cityBox.setPrefWidth(190);
		
			mapView = new GoogleMapView();
			mapView.addMapInializedListener(this);
			
			wroc = new Button("Wroc do poprzedniego panelu");
	        wroc.setOnAction(e -> {
	        	mainStage.setScene(Main.scene);
	    		mainStage.show();
	        });
	        
	        wyznacz = new Button("Wyznacz trasê");
	        wyznacz.setOnAction(e -> {
	        	
	        map = mapView.createMap(mapOptions);
	                	
	        directions = mapView.getDirec();	       	    
	       	ds = new DirectionsService();
	        renderer = new DirectionsRenderer(true, map, directions);

	          try{     
	               dr = new DirectionsRequest(
	                       from.getText(),
	                       to.getText(),
	                       TravelModes.DRIVING
	                       );
	               
	               ds.getRoute(dr, this, renderer);
	          }catch (NullPointerException e2){
	        	  System.out.println("chujdupa");
	          }
	          
	               
	        });
	        
	        from = new TextField();
	        to = new TextField();
	        
	        tb.getItems().addAll(wroc, from, to, wyznacz);
	        bp.setTop(tb);	
	        bp.setCenter(mapView);
			scene2 = new Scene(bp);
			
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.getChildren().add(countryBox);
			root.getChildren().add(cityBox);
			scene = new Scene(root,1135,570);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	DirectionsRenderer renderer;
	public void mapInitialized() {
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
	    
	    DirectionsService ds = new DirectionsService();
        renderer = new DirectionsRenderer(true, map, directions);
        
        /*po drodze przystanki*/
        
    //    DirectionsWaypoint[] dw = new DirectionsWaypoint[2];
    //    dw[0] = new DirectionsWaypoint("SÃ£o Paulo - SP");
    //    dw[1] = new DirectionsWaypoint("Juiz de Fora - MG");
        
        /*poczatek i koniec*/
        
        DirectionsRequest dr = new DirectionsRequest(
                "Warszawa",
                "Kraków",
                TravelModes.DRIVING
                );
    //    ds.getRoute(dr, this, renderer);
/*
	    //Add a marker to the map
	    MarkerOptions markerOptions = new MarkerOptions();

	    markerOptions.position( new LatLong(47.6, -122.3) )
	                .visible(Boolean.TRUE)
	                .title("My Marker");

	    Marker marker = new Marker( markerOptions );

	    map.addMarker(marker);*/

	}
	
	
	public static void main(String[] args) {
	launch(args);
	}


	@Override
	public void directionsReceived(DirectionsResult results, DirectionStatus status) {
		// TODO Auto-generated method stub
		
	}
}
