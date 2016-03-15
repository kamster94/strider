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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
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

public class Main extends Application {
	
	 public static final String url = "http://www.polakzagranica.msz.gov.pl";	
	 public static List<String> countryNames;
	 public static List<String> citiesOfCountries;
	 public static ArrayList<String> countryHtmls;
	 public static ComboBox countryBox ;
	 public static ComboBox cityBox ;
	 public static ObservableList countryData;
	 public static ObservableList cityData;
	 static public DbAccess dataBaseAccess;
	@Override
	public void start(Stage primaryStage) {
		Document document;
		citiesOfCountries = new ArrayList<String>();
		countryData = FXCollections.observableArrayList();
		 try {
			 
			 dataBaseAccess = new DbAccess("Artureczek","debil");
			 countryNames = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("CountryName")));
			 countryHtmls = new ArrayList<String>(dataBaseAccess.getStringsFromDb("SELECT * FROM DBA.Country", Arrays.asList("MSZlink")));
		//	 System.out.println("");
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
		
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.getChildren().add(countryBox);
			root.getChildren().add(cityBox);
			Scene scene = new Scene(root,935,520);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
	launch(args);
	}
}
