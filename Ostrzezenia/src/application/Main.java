package application;
	
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import np.com.ngopal.control.AutoFillTextBox;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Main extends Application {
	
	 public static final String url = "http://www.polakzagranica.msz.gov.pl";	   
	 public static String[] countryNames;
	 public static String[] countryHtmls;
	 public static AutoFillTextBox countryNameBox;
	
	
	@Override
	public void start(Stage primaryStage) {
		Document document;
		ObservableList data = FXCollections.observableArrayList();
		 try {
				document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")		
				.get();					
				Elements elems = document.select("option");
				String newLine = System.getProperty("line.separator");
			
				 countryNames = new String[elems.size()];
				 countryHtmls = new String[elems.size()];
				 
				int i = 0;					
				for (Element link : elems) {
					countryNames[i] = link.text();
					countryHtmls[i] = link.attr("value");
					i++;
					}
			
				data = FXCollections.observableArrayList();
				for(int j=0; j<countryNames.length; j++)
				data.add(countryNames[j]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
		

		
		try {		
			countryNameBox = new AutoFillTextBox(data);
			countryNameBox.setFilterMode(true);
			countryNameBox.setLayoutX(50);
			countryNameBox.setLayoutY(40);
			countryNameBox.setPrefWidth(130);

			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.getChildren().add(countryNameBox);
			Scene scene = new Scene(root,590,520);
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
