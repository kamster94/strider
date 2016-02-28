package presentationLayer;


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
    private Button znajdzButton;

    @FXML
    private TextField krajTextField;

    @FXML
    private TextArea ostrzezenieTextArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ostrzezenieTextArea.setWrapText(true);

		znajdzButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent arg0) {
	        	   
	        		String textFromkrajTextField = krajTextField.getText(); 
	        		//String, ktory wpisaliscie do TextFielda - poprawnie: Z duzej litery nazwa dowolnego panstwa
	        		String url = "http://www.polakzagranica.msz.gov.pl";	   
	        	    Document document;
			
	               try {
					document = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();					
					Elements elems = document.select("option");
					String newLine = System.getProperty("line.separator");
					
					//jak wejdziecie sobie na pierwszy url i w zrodlo strony, bedzie tam lista opcji z panstwami/
					//ta liste kopiuje do tych dwoch tablic - nazwe panstwa oraz koncowke adresu, ktora
					//pozniej dodaje do urla i wyszukuje na jej podstawie info o panstwach
					
					 String[] countryNames = new String[elems.size()];
					 String[] countryHtmls = new String[elems.size()];
					 
					int i = 0;					
					for (Element link : elems) {
						countryNames[i] = link.text();
						countryHtmls[i] = link.attr("value");
						i++;
						}
													
					int check = 0;  //jak tekscik, ktory wpisaliscie bedzie poprawna nazwa panstwa, check na 1
					int num=0;
					
					for(i = 0; i < countryNames.length; i++){
						
						if(textFromkrajTextField.equals(countryNames[i])){
							check = 1;
							num = i;
						}
						
						
					}
					
					if(check == 1){
					url+=countryHtmls[num];
					document = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")		
					.get();
					Elements elems2 = document.select("p");
					ostrzezenieTextArea.setText(""); //czyszczenie poprzednich wyszukan
					
					for (Element link : elems2) 
					ostrzezenieTextArea.appendText(link.text() + newLine); //zczytywanie tekstu ze zrodla do TextArea
					
					ostrzezenieTextArea.selectPositionCaret(0); //przewiniecie do poczatku tekstu w TextArea
						
					}else
						ostrzezenieTextArea.setText("Prosze wpisac poprawnie nazwe Panstwa");
				
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	              
	        }
	    });
		
	

    
	}
    
}
