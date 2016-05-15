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
import dbHandlers.DatabaseHandlerAttractionAdder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import travel.AttractionDetails;
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
    private VBox h_vboxcountry;

    @FXML
    private Button h_button_findcities;

    @FXML
    private VBox h_vboxcity;

    @FXML
    private Button h_button_findhotels;

    @FXML
    private TextField h_textfield_zipcode;

    @FXML
    private TextField h_textfield_name;

    @FXML
    private TextField h_textfield_street;

    @FXML
    private TextField h_textfield_number;

    @FXML
    private DatePicker h_datepicker_arrival;

    @FXML
    private DatePicker h_datepicker_departure;

    @FXML
    private TextField h_textfield_pricepernite;

    @FXML
    private ComboBox<String> h_combobox_mycurrency;

    @FXML
    private ComboBox<String> h_combobox_currency;

    @FXML
    private TextArea h_textarea_notes;

    @FXML
    private ListView<String> h_listview_hotels;

    @FXML
    private ComboBox<String> h_combobox_book;

    @FXML
    private ComboBox<String> h_combobox_rated;

    @FXML
    private Tab tabtransport;

    @FXML
    private VBox t_vbox_countryfrom;

    @FXML
    private Button t_button_findcities_from;

    @FXML
    private VBox t_vbox_countryto;

    @FXML
    private Button t_button_findcities_to;

    @FXML
    private VBox t_vbox_cityfrom;

    @FXML
    private VBox t_vbox_cityto;

    @FXML
    private DatePicker t_datepicker_start;

    @FXML
    private TextField t_textfield_starttime;

    @FXML
    private DatePicker t_datepicker1;

    @FXML
    private TextField t_textfield_endtime;

    @FXML
    private TextField t_textfield_price;

    @FXML
    private ComboBox<String> t_combobox_mycurrency;

    @FXML
    private ComboBox<String> t_combobox_transportcurrency;

    @FXML
    private TextArea t_textarea_notes;

    @FXML
    private ComboBox<String> t_combobox_transportcategory;

    @FXML
    private ListView<String> t_listview_companies;

    @FXML
    private Button t_add;

    @FXML
    private Button button_previous;

    @FXML
    private Button button_summary;
    
    
    
    @FXML
    private ComboBox<String> a_combobox_countryfrom;
    @FXML
    private ComboBox<String> a_combobox_cityfrom;
    @FXML
    private ComboBox<String> h_combobox_country;
    @FXML
    private ComboBox<String> h_combobox_city;
    @FXML
    private ComboBox<String> t_combobox_country_from;
    @FXML
    private ComboBox<String> t_combobox_city_from;
    @FXML
    private ComboBox<String> t_combobox_country_to;
    @FXML
    private ComboBox<String> t_combobox_city_to;
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
		
		a_listview_attractions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				String sql2 = "Select * from DBA.Attraction where AttractionName = '" + newValue + "'";
				String zipcode = DbAccess.getInstance().getSingeStringFromDb(sql2, "ZipCode");
				String streetName = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetName");
				String streetNumber = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetNumber");
				String openTime = DbAccess.getInstance().getSingeStringFromDb(sql2, "OpeningTime");
				String closeTime = DbAccess.getInstance().getSingeStringFromDb(sql2, "ClosingTime");
				
				a_textfield_name.setText(newValue);
				a_textfield_zipcode.setText(zipcode);
				a_textfield_street.setText(streetName);
				a_textfieldnumber.setText(streetNumber);
				a_textfield_open.setText(openTime);
				a_textfield_closed.setText(closeTime);	
			}
		});
		
		h_listview_hotels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldvalue, String newvalue) 
			{
				String sql2 = "Select * from DBA.Hotel where HotelName =" +"'" + newvalue + "'";
				String zipcode = DbAccess.getInstance().getSingeStringFromDb(sql2, "ZipCode");
				String streetName = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetName");
				String streetNumber = DbAccess.getInstance().getSingeStringFromDb(sql2, "StreetNumber");
				
				h_textfield_name.setText(newvalue);
				h_textfield_zipcode.setText(zipcode);
				h_textfield_street.setText(streetName);
				h_textfield_number.setText(streetNumber);
			}
		});
		
		t_combobox_city_to = WindowMain.getCityBox();
		t_combobox_city_from = WindowMain.getCityBox();
		t_combobox_country_to = WindowMain.getCountryBox();
		t_combobox_country_from = WindowMain.getCountryBox();
		
		t_vbox_countryfrom.getChildren().add(t_combobox_country_from);
		t_vbox_countryto.getChildren().add(t_combobox_country_to);
		t_vbox_cityfrom.getChildren().add(t_combobox_city_from);
		t_vbox_cityto.getChildren().add(t_combobox_city_to);
		
		t_vbox_countryfrom.getChildren().get(1).toFront();
		t_vbox_countryto.getChildren().get(1).toFront();
		t_vbox_cityfrom.getChildren().get(1).toFront();
		t_vbox_cityto.getChildren().get(1).toFront();
		
		t_button_findcities_from.setOnAction(this);
		t_button_findcities_to.setOnAction(this);
		
		ArrayList <String> transport = new ArrayList<String>(DbAccess.getInstance().getStringsFromDb("SELECT * FROM DBA.Transport", Arrays.asList("TransportName")));
		ArrayList <String> transportCategory = new ArrayList<String>(DbAccess.getInstance().getStringsFromDb("SELECT * FROM DBA.TransportCategory", Arrays.asList("TransportCategoryName")));
		
		t_combobox_transportcategory.getItems().addAll(transportCategory);
		
		t_combobox_transportcategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldvalue, String newvalue) 
			{
				t_listview_companies.getItems().removeAll(t_listview_companies.getItems());
				String sql = "Select * from DBA.Transport where IDTransportCategory = " + t_combobox_transportcategory.getSelectionModel().getSelectedIndex();
				ArrayList transports = new ArrayList<String>(DbAccess.getInstance().getStringsFromDb(sql, Arrays.asList("TransportName")));
				t_listview_companies.getItems().addAll(transports);
			}
		});

		a_combobox_countryfrom = WindowMain.getCountryBox();
		a_combobox_cityfrom = WindowMain.getCityBox();
		
		h_combobox_country = WindowMain.getCountryBox();
		h_combobox_city = WindowMain.getCityBox();
		
		h_vboxcountry.getChildren().add(h_combobox_country);
		h_vboxcity.getChildren().add(h_combobox_city);
		
		h_vboxcountry.getChildren().get(1).toFront();
		h_vboxcity.getChildren().get(1).toFront();
		
		h_button_findcities.setOnAction(this);
		h_button_findhotels.setOnAction(this);
		
		
		
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
		a_button_add.setOnAction(this);
		//tabattraction.setOnSelectionChanged(this);
		//tabhotel.setOnSelectionChanged(this);
		//tabtransport.setOnSelectionChanged(this);
	}
	
	private boolean acheckInputCompletion()
	{
		if(a_combobox_countryfrom.getValue() != null &&
			a_combobox_cityfrom.getValue() != null &&
			a_textfield_price.getText() != null &&
			a_comboboxmycurrency.getValue() != null &&
			a_combobox_attrcurrency.getValue() != null)
		{
			return true;
		}
		else return false;
	}
	
	private void aclearUserInput()
	{
		a_combobox_countryfrom.getSelectionModel().clearSelection();
		a_combobox_cityfrom.getSelectionModel().clearSelection();
		a_textfield_zipcode.setText("");
		a_textfield_name.setText("");
		a_textfield_street.setText("");
		a_textfieldnumber.setText("");
		a_textfield_closed.setText("");
		a_textfield_open.setText("");
		a_textfield_price.setText("");
		a_comboboxmycurrency.getSelectionModel().clearSelection();
		a_combobox_attrcurrency.getSelectionModel().clearSelection();
		a_textarea_notes.setText("");
		
		a_listview_attractions.getItems().removeAll(a_listview_attractions.getItems());
		a_listview_attractions.getSelectionModel().clearSelection();
	}
	

	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == t_button_findcities_to)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = t_combobox_country_to.getEditor().getText(); 
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
		   	 	     	t_combobox_city_to.setItems(cityData); 
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
    			//zaladuj html do WebView'
		}
		else if(event.getSource() == t_button_findcities_from)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = t_combobox_country_from.getEditor().getText(); 
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
		   	 	     	t_combobox_city_from.setItems(cityData); 
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
    			//zaladuj html do WebView'
		}
		else if(event.getSource() == a_button_add)
		{
			if(acheckInputCompletion() == true)
			{
				AttractionDetails ad = new AttractionDetails(a_listview_attractions.getSelectionModel().getSelectedIndex(), a_combobox_countryfrom.getSelectionModel().getSelectedIndex(), a_combobox_cityfrom.getSelectionModel().getSelectedIndex(), a_combobox_attrcurrency.getSelectionModel().getSelectedIndex(), Integer.parseInt(a_textfield_price.getText()), a_textarea_notes.getText());
				DatabaseHandlerAttractionAdder dhaa = new DatabaseHandlerAttractionAdder();
				dhaa.setAttraction(ad);
				dhaa.pushAttractionToDatabase();
				
				ad = null;
				aclearUserInput();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("New attraction added");
				alert.setHeaderText(null);
				alert.setContentText("Attraction added succesfully!");

				alert.showAndWait();
				
				
			}
		}
		else if(event.getSource() == button_previous)
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
    			//zaladuj html do WebView'
    			
    			ArrayList <String> currencies = new ArrayList<String>(DbAccess.getInstance().getStringsFromDb("SELECT * FROM DBA.Currency", Arrays.asList("CurrencyShortcut")));
    			a_comboboxmycurrency.getItems().addAll(currencies);
    			a_combobox_attrcurrency.getItems().addAll(currencies);
    			
		}
		else if(event.getSource() == h_button_findcities)
		{
			String newLine = System.getProperty("line.separator");
    		String htmlString = "";
    	    String finalmente = "<font face='WildWest'>";        	
    		String textFromCountryNameBox = h_combobox_country.getEditor().getText(); 
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
		   	 	     	h_combobox_city.setItems(cityData); 
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
		else if(event.getSource() == h_button_findhotels)
		{
			String sql = "Select * from DBA.Hotel where IDCountry = " + h_combobox_country.getSelectionModel().getSelectedIndex() + " and IDCity = " + h_combobox_city.getSelectionModel().getSelectedIndex();
					
			ArrayList<Integer> HotelsID = new ArrayList<Integer>(DbAccess.getInstance().getIntegersFromDb(sql, Arrays.asList("IDHotel")));
			ArrayList<String> hotelsNames = new ArrayList<String>();
			String sql2;
			
			for(int i = 0; i < HotelsID.size(); i++)
			{
				sql2 = "Select * from DBA.Hotel where IDHotel = " + HotelsID.get(i);
				hotelsNames.add(DbAccess.getInstance().getSingeStringFromDb(sql2, "HotelName"));
			}
			
			for(int i = 0; i < HotelsID.size(); i++)
			{
				h_listview_hotels.getItems().add(hotelsNames.get(i));
			}
			
			
			
		}
		else if(event.getSource() == a_button_findattractions)
		{
			String sql = "Select * from DBA.Attraction where IDCountry = " + a_combobox_countryfrom.getSelectionModel().getSelectedIndex() + " and IDCity = " + a_combobox_cityfrom.getSelectionModel().getSelectedIndex();

			ArrayList<Integer> attractionsID = new ArrayList<Integer>(DbAccess.getInstance().getIntegersFromDb(sql, Arrays.asList("IDAttraction")));
		
			for(Integer i : attractionsID)
			System.out.println(i);
			
			ArrayList<String> attractionsNames = new ArrayList<String>();
			String sql2;
			
			for(int i = 0; i < attractionsID.size(); i++)
			{
				sql2 = "Select * from DBA.Attraction where IDAttraction = " + attractionsID.get(i);
				attractionsNames.add(DbAccess.getInstance().getSingeStringFromDb(sql2, "AttractionName"));
			}
			
			for(int i = 0; i < attractionsNames.size(); i++)
			{
				a_listview_attractions.getItems().add(attractionsNames.get(i));
			}
		}
		
	}
}
