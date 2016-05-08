package desktopGui.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dbConnection.CreateUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
		cuser.start();
		
		choiceboxcountry.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue ov, Number value, Number new_value)
			{
				choiceboxcity.getItems().removeAll(choiceboxcity.getItems());
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
			//System.out.println(choiceboxcurrency.getValue());
			//System.out.println(choiceboxcurrency.getSelectionModel().getSelectedIndex());
			
			cuser.setData(textfieldusername.getText(), 
					      choiceboxcity.getValue(), 
					      choiceboxcity.getSelectionModel().getSelectedIndex(), 
					      textfieldemail.getText(), 
					      choiceboxcountry.getSelectionModel().getSelectedIndex(), 
					      textfieldpassword.getText(), 
					      choiceboxcurrency.getValue(), 
					      choiceboxcurrency.getSelectionModel().getSelectedIndex());
				
			if(cuser.verifyDataValidity() == 0)
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
						try 
						{
							Stage primaryStage = new Stage();
							BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("fxml/TravelAdvisorMainWindow.fxml"));
						
							Scene scene = new Scene(root, 800, 600);
							scene.getStylesheets().add(getClass().getResource("fxml/ta_mainwindow.css").toExternalForm());
							primaryStage.setScene(scene);
							primaryStage.setTitle("Travel Advisor");
							primaryStage.show();
						} 
						catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally
						{
							NewAccountWindow.closeWindow();
						}
					}
					else
					{
						Alert alertuseraddfailure = new Alert(AlertType.WARNING);
						alertuseraddfailure.setTitle("Couldn't add a new user");
						alertuseraddfailure.setHeaderText("Couldn't add a new user to the database.");
						alertuseraddfailure.setContentText("Please check if provided user details are correct.");

						alertuseraddfailure.showAndWait();
					}
					NewAccountWindow.closeWindow();
				}
				else
				{
					//Email zajety, pokazujemy napis
					//labelemailtaken.setVisible(true); //Madziula tego nie chce? Jak chce to mozna to z powrotem wykorzystac
					
					Alert alertemailtaken = new Alert(AlertType.WARNING);
					alertemailtaken.setTitle("Email already taken");
					alertemailtaken.setHeaderText("This email is already used by another account.");
					alertemailtaken.setContentText("Please make sure your email is unique.");

					alertemailtaken.showAndWait();
				}
			}
			else if(cuser.verifyDataValidity() == 1)
			{
				Alert alertuseraddfailure = new Alert(AlertType.WARNING);
				alertuseraddfailure.setTitle("Invalid username");
				alertuseraddfailure.setHeaderText("Your username is too long.");
				alertuseraddfailure.setContentText("Please make sure your username does not exceed 32 characters.");

				alertuseraddfailure.showAndWait();
			}
			else if(cuser.verifyDataValidity() == 2)
			{
				Alert alertuseraddfailure = new Alert(AlertType.WARNING);
				alertuseraddfailure.setTitle("Invalid email");
				alertuseraddfailure.setHeaderText("Your username is invalid.");
				alertuseraddfailure.setContentText("Please make sure your email is in correct format and does not exceed 32 characters.");

				alertuseraddfailure.showAndWait();
			}
			else if(cuser.verifyDataValidity() == 3)
			{
				Alert alertuseraddfailure = new Alert(AlertType.WARNING);
				alertuseraddfailure.setTitle("Invalid password");
				alertuseraddfailure.setHeaderText("Your password length is invalid.");
				alertuseraddfailure.setContentText("Please make sure your password is between 6 and 32 characters.");

				alertuseraddfailure.showAndWait();
			}
			else if(cuser.verifyDataValidity() == 4)
			{
				Alert alertuseraddfailure = new Alert(AlertType.WARNING);
				alertuseraddfailure.setTitle("Invalid city");
				alertuseraddfailure.setHeaderText("Your city name is too long.");
				alertuseraddfailure.setContentText("Please make sure your city name does not exceed 32 characters.");

				alertuseraddfailure.showAndWait();
			}
			else if(cuser.verifyDataValidity() == 5)
			{
				Alert alertuseraddfailure = new Alert(AlertType.WARNING);
				alertuseraddfailure.setTitle("Invalid currenct");
				alertuseraddfailure.setHeaderText("Your currency length is invalid.");
				alertuseraddfailure.setContentText("Please make sure your currency name is exactly 3 characters.");

				alertuseraddfailure.showAndWait();
			}
		}
		else if(arg0.getSource() == buttoncancel)
		{
			NewAccountWindow.closeWindow();
		}
		

	}
}
