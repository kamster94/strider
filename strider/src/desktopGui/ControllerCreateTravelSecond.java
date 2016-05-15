package desktopGui;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.jsoup.nodes.Document;

import dbConnection.DbAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import travel.TravelFramework;

public class ControllerCreateTravelSecond implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML private ControllerTabAttraction pageattraction;
    
    //@FXML private ControllerTabHotel pagehotel;
	
   // @FXML private ControllerTabTransport pagetransport;
	
    ////////////ATTRACTION
    @FXML
    private TabPane tabpane;

    @FXML
    private Tab tabattraction;

    @FXML
    private VBox a_vbox_country_from;

    @FXML
    private Button a_button_findcities;

    @FXML
    private VBox a_vbox_city_from;

    @FXML
    private Button a_button_findattractions;

    @FXML
    private TextField a_textfield_zipcode;

    @FXML
    private TextField a_textfield_name;

    @FXML
    private TextField a_textfield_street;

    @FXML
    private TextField a_textfieldnumber;

    @FXML
    private TextField a_textfield_open;

    @FXML
    private TextField a_textfield_closed;

    @FXML
    private TextField a_textfield_price;

    @FXML
    private ComboBox<String> a_comboboxmycurrency;

    @FXML
    private ComboBox<String> a_combobox_attrcurrency;

    @FXML
    private TextArea a_textarea_notes;

    @FXML
    private ListView<String> a_listview_attractions;

    @FXML
    private Button a_button_add;

    @FXML
    private Tab tabhotel;

    @FXML
    private Tab tabtransport;

    @FXML
    private Button button_previous;

    @FXML
    private Button button_summary;
    
    @FXML
    private ComboBox<String> a_combobox_countryfrom;
    @FXML
    private ComboBox<String> a_combobox_cityfrom;

    /////////////////////////////////////
    
    
    
    
    /*
    public static void getData()
    {
		if(TravelFramework.getInstance().hasTravel())
		{
			labeltraveltitle.setText(TravelFramework.getInstance().getCurrentTravel().getName());
		}
    }
    */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		a_combobox_countryfrom = WindowMain.getCountryBox();
		a_combobox_cityfrom = WindowMain.getCityBox();
		//a_combobox_countryfrom.setEditable(false);
		//a_combobox_cityfrom.setEditable(false);
		
		a_vbox_country_from.getChildren().add(a_combobox_countryfrom);
		a_vbox_city_from.getChildren().add(a_combobox_cityfrom);

		a_vbox_country_from.getChildren().get(1).toFront();
		a_vbox_city_from.getChildren().get(1).toFront();
		a_button_findcities.setOnAction(this);
		
		button_previous.setOnAction(this);
		button_summary.setOnAction(this);
		a_button_findattractions.setOnAction(this);
		//tabattraction.setOnSelectionChanged(this);
		//tabhotel.setOnSelectionChanged(this);
		//tabtransport.setOnSelectionChanged(this);
	}
	
	public void checkIfDisableControls()
	{
		if(a_combobox_countryfrom.getSelectionModel().isEmpty() == true)
		{
			a_combobox_cityfrom.setDisable(true);
			a_combobox_attrcurrency.setDisable(true);
		
		}
	}
	
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == button_previous)
		{
			myController.setScreen(WindowMain.NEWTRAVEL_1);
		}
		else if(event.getSource() == button_summary)
		{
			myController.setScreen(WindowMain.TRAVEL_SUMMARY);
		}
		else if(event.getSource() == a_button_findcities)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = a_combobox_countryfrom.getEditor().getText(); 
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
		   	 	     	a_combobox_cityfrom.setItems(cityData); 
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
		else if(event.getSource() == a_button_findattractions)
		{
			String sql = "Select * from DBA.Attraction where IDCountry = " + a_combobox_countryfrom.getSelectionModel().getSelectedIndex() + " and IDCity = " + a_combobox_cityfrom.getSelectionModel().getSelectedIndex();
			DbAccess dataBaseAccess = new DbAccess("adriank","debil");

			ArrayList<Integer> attractionsID = new ArrayList<Integer>(dataBaseAccess.getIntegersFromDb(sql, Arrays.asList("IDAttraction")));
		
			for(Integer i : attractionsID)
			System.out.println(i);
			
			//dataBaseAccess = new DbAccess("adriank","debil");
			
			ArrayList<String> attractionsNames = new ArrayList<String>();
			String sql2;
			
			for(int i = 0; i < attractionsID.size(); i++)
			{
				
				sql2 = "Select * from DBA.Attraction where IDAttraction = " + attractionsID.get(i);
				attractionsNames.add(dataBaseAccess.getSingeStringFromDb(sql2, "AttractionName"));
			}
			
			for(int i = 0; i < attractionsNames.size(); i++)
			{
				a_listview_attractions.getItems().add(attractionsNames.get(i));
			}
			
			
		}
		
	}



}
