package countryWarnings;


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
	        	   
	        		String textFromCountryNameBox = Main.box.getEditor().getText(); 
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        	    Document document;
	        	    String newLine = System.getProperty("line.separator");
	        	    int checkForMatchingCountry = 0;  
					int num=0;
	        	    	        	  	
	               try {
			
					
					for(int i = 0; i < Main.countryNames.size(); i++){ //petla do szukania wpisanego kraju
						
							if(textFromCountryNameBox.equals(Main.countryNames.get(i))){
							checkForMatchingCountry = 1;
							num = i;
						}
						
						
					}
					
					if(checkForMatchingCountry == 1){
					url+=Main.countryHtmls.get(num);
					document = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();
					
					Elements elems3 = document.select(("div[class=warning]"));//ostrzezenia o panstwie
					Elements elems4 = document.select(("div[class=item]")); //informacje o panstwie
					
					informationTextArea.setText(""); //czyszczenie poprzednich wyszukan
					
					for (Element link : elems3) {
						
						Elements linkChildren = link.children();
						
						for (Element elem : linkChildren) {

							if(elem.tagName().equals("a")){
							
							Elements linkGrandChildren = elem.children();
							for (Element elem2 : linkGrandChildren) {
 
								if(elem2.tagName().equals("h3"))
							    informationTextArea.appendText(elem2.text().toUpperCase() + newLine);
								
							}
			        
							}
					        if(elem.tagName().equals("div"))
						    informationTextArea.appendText(elem.text() + newLine);
					        
					        
					    }
					
				//	informationTextArea.appendText(link.text() + newLine); //odczytywanie tekstu ze zrodla do TextArea
					
						informationTextArea.appendText(newLine);
								
					
					}
					
					
					for (Element link : elems4) {
						
						Elements linkChildren = link.children();
						
						for (Element elem : linkChildren) {
					        
					        if(elem.tagName().equals("h3"))
					        informationTextArea.appendText(elem.text().toUpperCase() + newLine);
					        if(elem.tagName().equals("div"))
						    informationTextArea.appendText(elem.text() + newLine);
					        
					        
					    }
					
				//	informationTextArea.appendText(link.text() + newLine); //odczytywanie tekstu ze zrodla do TextArea
					
						informationTextArea.appendText(newLine);
								
					}
					
					
					
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
