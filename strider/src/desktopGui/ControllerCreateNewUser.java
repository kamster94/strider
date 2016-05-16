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

import Model.NewUser;
import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ControllerCreateNewUser implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
		
	@FXML
	private TextField textfieldusername;
	@FXML
	private VBox vbox_countrybox;
	@FXML
	private TextField textfieldemail;
	@FXML
	private VBox vbox_citybox;
	@FXML
	private TextField textfieldpassword;
	@FXML
	private TextField textfieldpasswordrepeat;
	@FXML
	private ComboBox<String> comboboxcurrency;
	@FXML
	private Button button_cancel;
	@FXML
	private Button button_create;
    @FXML
    private Button button_findcities;
    
	@FXML
	private ComboBox<String> countrybox;
	@FXML
	private ComboBox<String> citybox;
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
			myController = screenParent; 
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		button_cancel.setOnAction(this);
		button_create.setOnAction(this);
		button_findcities.setOnAction(this);
		
		countrybox = WindowMain.getCountryBox();
		citybox = WindowMain.getCityBox();
		
		vbox_countrybox.getChildren().add(countrybox);
		vbox_citybox.getChildren().add(citybox);
		
		vbox_countrybox.getChildren().get(1).toFront();
		
		ArrayList <String> currencies = new ArrayList<String>(DbAccess.getInstance().getStringsFromDb("SELECT * FROM DBA.Currency", Arrays.asList("CurrencyShortcut")));
		comboboxcurrency.getItems().addAll(currencies);
	}

	public int checkInput()
	{
		if(textfieldemail.getText().isEmpty() == false &&
			textfieldpassword.getText().isEmpty() == false &&
			textfieldpasswordrepeat.getText().isEmpty() == false &&
			textfieldpassword.getText().equals(textfieldpasswordrepeat.getText()) &&
			countrybox.getValue() != null &&
			citybox.getValue() != null &&
			comboboxcurrency.getValue() != null)
		{
			return 0;
		}
		else if(textfieldemail.getText().isEmpty() == false &&
				textfieldpassword.getText().isEmpty() == false &&
				textfieldpasswordrepeat.getText().isEmpty() == false &&
				textfieldpassword.getText().equals(textfieldpasswordrepeat.getText())  == false &&
				countrybox.getValue() != null &&
				citybox.getValue() != null &&
				comboboxcurrency.getValue() != null)
		{
			return 1;
		}
		else return 2;
	}
	
	
	
	
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_cancel)
		{
			myController.setScreen(WindowMain.SPLASH_SCREEN);
		}
		else if(arg0.getSource() == button_create)
		{
			if(checkInput() == 0)
			{
				DatabaseHandlerRegister dhr = new DatabaseHandlerRegister();
				NewUser nu = new NewUser(textfieldusername.getText(), citybox.getSelectionModel().getSelectedIndex(), textfieldemail.getText(), countrybox.getSelectionModel().getSelectedIndex(), textfieldpassword.getText(), comboboxcurrency.getSelectionModel().getSelectedIndex());
				dhr.setUserCandidate(nu);
				int dataval = dhr.verifyDataValidity();
				
				if(dataval == 1)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Create new account");
					alert.setHeaderText(null);
					alert.setContentText("Username exceeds 32 character limit.");
					alert.showAndWait();
				}
				else if(dataval == 2)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Create new account");
					alert.setHeaderText(null);
					alert.setContentText("Invalid e-mail structure.");
					alert.showAndWait();
				}
				else if(dataval == 3)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Create new account");
					alert.setHeaderText(null);
					alert.setContentText("Password length must be between 6 and 32 characters.");
					alert.showAndWait();
				}
				else if(dataval == 0)
				{
					if(dhr.checkEmailAvailability() == false)
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Create new account");
						alert.setHeaderText(null);
						alert.setContentText("This e-mail is already in use.");
						alert.showAndWait();
					}
					else
					{
						System.out.println(dhr.sendToDb());
						myController.setScreen(WindowMain.SPLASH_SCREEN);
					}
				}
			}
			else if(checkInput() == 1)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Create new account");
				alert.setHeaderText(null);
				alert.setContentText("Password fields don't match.");
				alert.showAndWait();
			}
			else if(checkInput() == 2)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Create new account");
				alert.setHeaderText(null);
				alert.setContentText("All fields except 'Username' are mandatory.");
				alert.showAndWait();
			}
		}
		else if(arg0.getSource() == button_findcities)
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
    			//zaladuj html do WebView'
		}
	}
}
