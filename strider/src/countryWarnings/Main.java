package countryWarnings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	 public static final String url2 = "https://pl.wikipedia.org";
	 public static ArrayList<String> countryNames;
	 public static ArrayList<String> countryNames2;
	 public static ArrayList<String> countryHtmls;
	 public static ArrayList<String> wikiCitiesList;
	 public static ArrayList<String> wikiCitiesListHtmls;
	 public static ComboBox box ;
	 public static ComboBox box2 ;
	 public static AutoCompleteComboBoxListener countryNameBox2;
	 public static ObservableList data;
	 public static ObservableList data2;
	 public static WebView browser;
	 public static WebEngine webEngine;
	
	@Override
	public void start(Stage primaryStage) {
		Document document;
		data = FXCollections.observableArrayList();
		 try {
				//Ponizsze linijki sa do sciagniecia listy Krajow z mszu
			    //Dostawanie sie do ostrzezen informacji odbywa sie w klasie SampleController.java
			 
			 	document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")		
				.get();					
				
				Elements elems = document.select("option");

				countryNames = new ArrayList();
				countryHtmls = new ArrayList();
				wikiCitiesListHtmls = new ArrayList();
								
				for (Element link : elems) {
					countryNames.add(link.text());
					countryHtmls.add(link.attr("value"));
		
					}			

				int rozmiar = countryNames.size();
				rozmiar = rozmiar/2;
				
				//Z jakiegos powodu sciaga do obu list dwa razy to samo, mimo ze w htmlu
				//sie nie powta¿a. Tymczasowo na betonie po prostu usune polowe listy
				for(int i = countryNames.size()-1; i >= rozmiar; i--){
				countryNames.remove(i);
				countryHtmls.remove(i);
				}
				countryNames.remove(0);//pierwsza pozycja jest zawsze blank
				countryHtmls.remove(0);
												
				data = FXCollections.observableArrayList(); //Do sugestii wyszukiwania krajow
				
				for(int j=0; j<countryNames.size(); j++)
				data.add(countryNames.get(j));
				
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*	    
		 try {
			 
			 File file = new File("miasta.txt");
			 
			 try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				    for(String line; (line = br.readLine()) != null; ) {
				       wikiCitiesListHtmls.add(line.substring(line.lastIndexOf(" ")+1));
				    }
				}
			 					
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		
		try {		
			
			box = new ComboBox();	
			box.setItems(data);
			new AutoCompleteComboBoxListener(box);
			box.setLayoutX(37);
			box.setLayoutY(40);
			box.setPrefWidth(190);
			
			box2 = new ComboBox();	
			//box2.setItems(data);
			new AutoCompleteComboBoxListener(box2);
			box2.setLayoutX(490);
			box2.setLayoutY(40);
			box2.setPrefWidth(190);
		
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.getChildren().add(box);
			//root.getChildren().add(scrollPane);
			//root.getChildren().add(box2);
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
