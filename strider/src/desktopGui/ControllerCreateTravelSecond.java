package desktopGui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private DatePicker datepickerstart;

    @FXML
    private DatePicker datepickerend;

    @FXML
    private VBox vboxcountrybox;

    @FXML
    private VBox vboxcitybox;

    @FXML
    private Spinner<Integer> spinnercompanions;

    @FXML
    private Button buttonprev;

    @FXML
    private Button buttonnext;
    
    @FXML
    private Button homebutton;
    
    @FXML
    private Button buttonfindcities;
    
    @FXML
    private ComboBox<String> citybox;
    
    @FXML
    private ComboBox<String> countrybox;
    
    final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_previous = new Image("desktopGui/textures/arrowleft.png");
	final Image icon_next = new Image("desktopGui/textures/arrowright.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		buttonprev.setOnAction(this);
		buttonnext.setOnAction(this);
		homebutton.setOnAction(this);
		buttonfindcities.setOnAction(this);
		
		ImageView homeico = new ImageView(icon_home);
		homeico.setFitWidth(35);
		homeico.setFitHeight(35);
		
		ImageView previco = new ImageView(icon_previous);
		previco.setFitWidth(35);
		previco.setFitHeight(35);
		
		ImageView nextico = new ImageView(icon_next);
		nextico.setFitWidth(35);
		nextico.setFitHeight(35);
		
		homebutton.setGraphic(homeico);
		buttonprev.setGraphic(previco);
		buttonnext.setGraphic(nextico);

		//fuck the police
		citybox = WindowMain.getCityBox();
		countrybox = WindowMain.getCountryBox();
		vboxcitybox.getChildren().add(citybox);
		vboxcountrybox.getChildren().add(countrybox);
		
		//Przesuwamy guzik findcities pod dodany wy¿ej cumbobox
		vboxcountrybox.getChildren().get(1).toFront();
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == homebutton)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == buttonprev)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_1);
		}
		else if(arg0.getSource() == buttonnext)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_3);
		}
		else if(arg0.getSource() == buttonfindcities)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = countrybox.getEditor().getText(); 
    		String url = "http://www.polakzagranica.msz.gov.pl";	   
    		Document document;
    		String cityNamesSql = "SELECT C.CityName FROM DBA.City C inner join DBA.Country Cr on C.IDCountry = Cr.IDCountry "
    							+ "where Cr.CountryName = '" +  textFromCountryNameBox +"' order by 1 asc";
    		int checkForMatchingCountry = 0;
    		int num = 0;

 						
    			for(int i = 0; i < WindowMain.countryNames.size(); i++)
    			{ //petla do szukania wpisanego kraju
    				if(textFromCountryNameBox.equals(WindowMain.countryNames.get(i)))
    				{
    					checkForMatchingCountry = 1;
    					num = i;							
					}											
    			}
    			//jesli wybrano miasto z listy dostepnych panstw
    			if(checkForMatchingCountry == 1)
    			{
    				//Laczenie z baza danych do wyciagniecia listy miast na podstawie wybranego panstwa  	
    				try 
    				{	    
    					//String connectionString = "jdbc:sqlanywhere:uid=DBA;pwd=sql";
		   	   	      	String connectionString = "jdbc:sqlanywhere:uid="+"Artureczek"+";pwd="+"debil"+";eng=traveladvisordb;database=traveladvisordb;host=5.134.69.28:15144";
		   	 	     	Connection con = DriverManager.getConnection(connectionString);					 	         			  
		   	 	     	Statement stmt = con.createStatement();
		   	 	     	ResultSet rs = stmt.executeQuery(cityNamesSql);
		   	 	     	ObservableList cityData = FXCollections.observableArrayList();  
		   	 	       
		   	 	     	while (rs.next())
		   	 	     		cityData.add(rs.getString("CityName"));
		   	
		   	 	     	rs.close();
		   	 	     	stmt.close();
		   	 	     	con.close();
		   	 	     	citybox.setItems(cityData); 
    				} 
    				catch (SQLException e1) 
    				{
    					e1.printStackTrace();
		   			}	

    			}
    			else
    			{
    				//jesli wpisano cos innego niz nazwe panstwa z listy
    				finalmente ="<b>Prosze wpisac poprawnie nazwe Panstwa<b>";
    			}
    			//zaladuj html do WebView
    		} 
		}
	
	
	private static String readAll(Reader rd) throws IOException 
	{
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) 
	    {
	    	sb.append((char) cp);
	    }
	    return sb.toString();
	}
}
