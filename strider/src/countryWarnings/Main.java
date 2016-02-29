package countryWarnings;

import java.io.IOException;
import java.util.ArrayList;

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
	 public static ArrayList<String> countryNames;
	 public static ArrayList<String> countryHtmls;
	 public static ComboBox box ;
	 public static AutoCompleteComboBoxListener countryNameBox2;
	 
	
	@Override
	public void start(Stage primaryStage) {
		Document document;
		ObservableList data = FXCollections.observableArrayList();
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
								
				for (Element link : elems) {
					countryNames.add(link.text());
					countryHtmls.add(link.attr("value"));
		
					}			

				int rozmiar = countryNames.size();
				rozmiar = rozmiar/2;
				
				//Z jakiegos powodu sciaga do obu list dwa razy to samo, mimo ze w htmlu
				//sie nie powtarza. Tymczasowo na betonie po prostu usune polowe listy
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
			    
		

		
		try {		
			
			box = new ComboBox();	
			box.setItems(data);
			new AutoCompleteComboBoxListener(box);
			box.setLayoutX(37);
			box.setLayoutY(40);
			box.setPrefWidth(190);
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.getChildren().add(box);
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
