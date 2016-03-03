package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import dbConnection.CreateUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private TextField textfieldcity;

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		//Ukrywamy label o zajetosci maila
		labelemailtaken.setVisible(false);
		
		
		//Wypelnianie choiceboxow od waluty i panstw
		//TODO: Wypelnic danymi z bazy?
		choiceboxcurrency.getItems().addAll("PLN", "USD", "EURO"); 
		choiceboxcountry.getItems().addAll("Albania", "Polska", "Uganda");
		
		
		//Poczatkowe zaznaczenie w choiceboxach
		choiceboxcurrency.setValue("PLN");
		choiceboxcountry.setValue("Polska");
		
		buttoncancel.setOnAction(this);
		buttoncreate.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent arg0)
	{
		
		if(arg0.getSource() == buttoncreate)
		{
			//TODO: Dodac sprawdzenie zeby zaden textfield nie byl pusty top kek
			
			CreateUser cuser = new CreateUser(textfieldusername.getText(), textfieldcity.getText(), textfieldemail.getText(), choiceboxcountry.getValue().toString(), textfieldpassword.getText(), choiceboxcurrency.getValue().toString());
				
			/*
			System.out.println("Username : " + textfieldusername.getText());
			System.out.println("City : " + textfieldcity.getText());
			System.out.println("Email : " + textfieldemail.getText());
			System.out.println("Country : " + choiceboxcountry.getValue().toString());
			System.out.println("Password : " + textfieldpassword.getText());
			System.out.println("Currency : " + choiceboxcurrency.getValue().toString());
			*/
				
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
					}
					else
					{
						//dobrywieczor, cos sie, cos sie popsulo
					}
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
