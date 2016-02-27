package application;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SampleController implements Initializable{

    @FXML
    private Button findButton;

    @FXML
    private TextArea informationTextArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		informationTextArea.setWrapText(true);

		findButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	   
	        		String textFromCountryNameBox = Main.countryNameBox.getText(); 
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        	    Document document;
	        	    String newLine = System.getProperty("line.separator");
	        	    int checkForMatchingCountry = 0;  
					int num=0;
	        	    	        	  	
	               try {
			
					
					for(int i = 0; i < Main.countryNames.length; i++){
						
						System.out.println(Main.countryNames[i]);
						
						if(textFromCountryNameBox.equals(Main.countryNames[i])){
							checkForMatchingCountry = 1;
							num = i;
						}
						
						
					}
					
					if(checkForMatchingCountry == 1){
					url+=Main.countryHtmls[num];
					document = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();
					Elements elems2 = document.select("p");
					informationTextArea.setText(""); //czyszczenie poprzednich wyszukan
					
					for (Element link : elems2) 
					informationTextArea.appendText(link.text() + newLine); //zczytywanie tekstu ze zrodla do TextArea
					
					informationTextArea.selectPositionCaret(0); //przewiniecie do poczatku tekstu w TextArea
						
					}else
					informationTextArea.setText("Prosze wpisac poprawnie nazwe Panstwa");
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	              
	        }
	    });
		
	

    
	}
    
}
