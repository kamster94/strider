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
import java.time.LocalDate;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import travel.TravelFramework;

public class ControllerCreateTravelFirst implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private TextField textfieldtravelname;

    @FXML
    private DatePicker datepickerstart;

    @FXML
    private DatePicker datepickerend;

    @FXML
    private VBox vboxcountryboxsource;

    @FXML
    private Button buttonfindcitiessource;

    @FXML
    private VBox vboxcountryboxdestination;

    @FXML
    private Button buttonfindcitiesdestination;

    @FXML
    private VBox vboxcityboxsource;

    @FXML
    private VBox vboxcityboxdestination;

    @FXML
    private Spinner<Integer> spinnercompanions;

    @FXML
    private Button homebutton;

    @FXML
    private Button buttonnext;
    
    @FXML
    private ComboBox<String> citybox_source;
    
    @FXML
    private ComboBox<String> countrybox_source;
    
    @FXML
    private ComboBox<String> citybox_target;
    
    @FXML
    private ComboBox<String> countrybox_target;
    
    
    final Image icon_home = new Image("desktopGui/textures/button_home.png");
	final Image icon_previous = new Image("desktopGui/textures/arrowleft.png");
	final Image icon_next = new Image("desktopGui/textures/arrowright.png");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		buttonnext.setOnAction(this);
		homebutton.setOnAction(this);
		buttonfindcitiessource.setOnAction(this);
		buttonfindcitiesdestination.setOnAction(this);
		
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
		buttonnext.setGraphic(nextico);

		//fuck the police
		citybox_source = WindowMain.getCityBox();
		countrybox_source = WindowMain.getCountryBox();
		
		citybox_target = WindowMain.getCityBox();
		countrybox_target = WindowMain.getCountryBox();
		
		vboxcountryboxsource.getChildren().add(countrybox_source);
		vboxcityboxsource.getChildren().add(citybox_source);
		
		vboxcountryboxdestination.getChildren().add(countrybox_target);
		vboxcityboxdestination.getChildren().add(citybox_target);
		

		//Przesuwamy guzik findcities pod dodany wy¿ej cumbobox
		vboxcountryboxsource.getChildren().get(1).toFront();
		vboxcountryboxdestination.getChildren().get(1).toFront();
	
		datepickerstart.setEditable(false);
		datepickerend.setEditable(false);
		datepickerstart.setValue(LocalDate.now());
		datepickerstart.setOnAction(this);
		datepickerend.setOnAction(this);
		
		spinnercompanions.setEditable(false);
		SpinnerValueFactory svf= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10);
		spinnercompanions.setValueFactory(svf);
		
		
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	public void sanitizeDatePickers()
	{
		if(datepickerstart.getValue().isBefore(LocalDate.now()))
		{
			datepickerstart.setValue(LocalDate.now());
		}
		if(datepickerend.getValue().isBefore(LocalDate.now()))
		{
			datepickerend.setValue(datepickerstart.getValue().plusDays(1));
		}
		if(datepickerend.getValue().isBefore(datepickerstart.getValue()))
		{
			datepickerend.setValue(datepickerstart.getValue().plusDays(1));
		}
	}
	
	public boolean checkInputCompletion()
	{
		if((textfieldtravelname.getText().isEmpty() == false) &&
			(datepickerstart.getValue() != null) &&
			(datepickerend.getValue() != null) &&
			(countrybox_source.getValue() != null) &&
			(citybox_source.getValue() != null) &&
			(countrybox_target.getValue() != null) &&
			(citybox_target.getValue() != null) &&
			(spinnercompanions.getValue() != null))
		{
			return true;
		}
		else return false;
	}
	
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if((arg0.getSource() == datepickerstart) || (arg0.getSource() == datepickerend))
		{
			sanitizeDatePickers();
		}
		else if(arg0.getSource() == homebutton)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == buttonnext)
		{
			if(checkInputCompletion() == true)
			{
				if(TravelFramework.getInstance().hasTravel())
				{
				
				
				}
				else
				{
					TravelFramework.getInstance().createNewTravel(textfieldtravelname.getText(), datepickerstart.getValue(), datepickerend.getValue(), countrybox_source.getSelectionModel().getSelectedIndex(), citybox_source.getSelectionModel().getSelectedIndex(), countrybox_target.getSelectionModel().getSelectedIndex(), citybox_target.getSelectionModel().getSelectedIndex(), spinnercompanions.getValue().intValue());
				}
				myController.setScreen(WindowMain.NEWTRAVEL_2);
			}
		}
		else if(arg0.getSource() == buttonfindcitiessource)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = countrybox_source.getEditor().getText(); 
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
		   	 	     	citybox_source.setItems(cityData); 
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
		else if(arg0.getSource() == buttonfindcitiesdestination)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = countrybox_target.getEditor().getText(); 
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
		   	 	     	citybox_target.setItems(cityData); 
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
