package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import dbConnection.CreateUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewAccountWindowController implements Initializable, EventHandler<ActionEvent>
{
	@FXML
	private Button buttoncancel;

	@FXML
	private Button buttoncreate;

	@FXML
	private Label labelusername;

	@FXML
	private TextField textfieldusername;

	@FXML
	private TextField textfieldemail;

	@FXML
	private Label labelemailtaken;

	@FXML
	private Label labelpassword;

	@FXML
	private PasswordField textfieldpassword;

	@FXML
	private Label labelrepeatpassword;

	@FXML
	private PasswordField textfieldrepeatpassword;

	@FXML
	private Label labelcity;

	@FXML
	private Label labelcountry;

	@FXML
	private TextField textfieldcountry;

	@FXML
	private Label labelcurrency;

	@FXML
	private ChoiceBox<String> choiceboxcurrency;
	
	@FXML
	private ChoiceBox<String> choiceboxcountry;
	
	@FXML
	private ChoiceBox<String> choiceboxcity;
	
	private CreateUser cuser;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{	
		//Ukrywamy label o zajetosci maila
		labelemailtaken.setVisible(false);
		
		cuser = new CreateUser();
		
		choiceboxcountry.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue ov, Number value, Number new_value)
			{
				choiceboxcity.getItems().removeAll();
				choiceboxcity.getItems().addAll(cuser.getCities(choiceboxcountry.getSelectionModel().getSelectedIndex()));
			}
		});
			
		//Wypelnianie choiceboxow od waluty i panstw
		choiceboxcurrency.getItems().addAll(cuser.getCurrencies()); 
		choiceboxcountry.getItems().addAll(cuser.getCountries());
		choiceboxcity.getItems().addAll(cuser.getCities(197)); // miasta dla Polszy jak by co
		
		//Poczatkowe zaznaczenie w choiceboxach
		choiceboxcurrency.setValue("PLN");
		choiceboxcountry.setValue("Polska");
		choiceboxcity.setValue("Warszawa");
		
		buttoncancel.setOnAction(this);
		buttoncreate.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent arg0)
	{
		
		if(arg0.getSource() == buttoncreate)
		{
			//TODO: Dodac sprawdzenie zeby zaden textfield nie byl pusty top kek
			
			System.out.println(choiceboxcurrency.getValue());
			System.out.println(choiceboxcurrency.getSelectionModel().getSelectedIndex());
			//CreateUser cuser = new CreateUser(, , choiceboxcountry.getValue().toString(), textfieldpassword.getText(), choiceboxcurrency.getValue().toString());
			
			cuser.setData(textfieldusername.getText(), 
					      choiceboxcity.getValue(), 
					      choiceboxcity.getSelectionModel().getSelectedIndex(), 
					      textfieldemail.getText(), 
					      choiceboxcountry.getSelectionModel().getSelectedIndex(), 
					      textfieldpassword.getText(), 
					      choiceboxcurrency.getValue(), 
					      choiceboxcurrency.getSelectionModel().getSelectedIndex());
				
			if(cuser.verifyDataValidity())
			{
				if(cuser.checkEmailAvailability())
				{
					//email git wiec usuwamy napis
					labelemailtaken.setVisible(false);
						
					boolean addstatus = false;
					addstatus = cuser.sendToDb();
						
					if(addstatus)
					{
						//Dodalismy usera do bazy poprawnie
						System.out.println("Correctly added new user to DB.");
					}
					else
					{
						System.out.println("Couldn't add new user to DB.");
						//dobrywieczor, cos sie, cos sie popsulo
					}
					NewAccountWindow.closeWindow();
				}
				else
				{
					//Email zajety, pokazujemy napis
					labelemailtaken.setVisible(true);
				}
			}
			else
			{
				//TODO: Wywal okno z bledem	
			}
		}
		else if(arg0.getSource() == buttoncancel)
		{
			NewAccountWindow.closeWindow();
		}
		

	}
}
