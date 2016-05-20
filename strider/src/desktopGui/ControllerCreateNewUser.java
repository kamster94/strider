package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import Model.NewUser;
import Model.User;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerRegister;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ControllerCreateNewUser implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	private ScreensController myController;

	@FXML
	private TextField textfieldusername;
	@FXML
	private TextField textfieldemail;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private PasswordField passwordfieldrepeat;
	@FXML
	private VBox vbox_countrybox;
	@FXML
	private VBox vbox_citybox;
	@FXML
	private VBox vbox_currencybox;
	@FXML
	private Button button_cancel;
	@FXML
	private Button button_create;
    
    //Uzupelniajki, nie ma ich w FXML'u
	@FXML
	private ComboBox<String> countrybox;
	@FXML
	private ComboBox<String> citybox;
	@FXML
	private ComboBox<String> currencybox;
	
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
		
		countrybox = WindowMain.getCountryBox();
		citybox = WindowMain.getCityBox();
		currencybox = WindowMain.getCurrencyBox();
		
		vbox_countrybox.getChildren().add(countrybox);
		vbox_citybox.getChildren().add(citybox);
		vbox_currencybox.getChildren().add(currencybox);
		
		vbox_countrybox.getChildren().get(1).toFront();
		
		countrybox.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				citybox.getSelectionModel().clearSelection();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
				citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
			}
		});
		
		textfieldusername.setPromptText("Username");
		textfieldemail.setPromptText("default@email.com");
		passwordfield.setPromptText("Password");
		passwordfieldrepeat.setPromptText("Repeat password");
		countrybox.setPromptText("Country");
		citybox.setPromptText("City");
		currencybox.setPromptText("Currency");
	}

	public int checkInput()
	{
		if(textfieldemail.getText().isEmpty() == false &&
			passwordfield.getText().isEmpty() == false &&
			passwordfieldrepeat.getText().isEmpty() == false &&
			passwordfield.getText().equals(passwordfieldrepeat.getText()) &&
			countrybox.getValue() != null &&
			citybox.getValue() != null &&
			currencybox.getValue() != null)
		{
			return 0;
		}
		else if(textfieldemail.getText().isEmpty() == false &&
				passwordfield.getText().isEmpty() == false &&
				passwordfieldrepeat.getText().isEmpty() == false &&
				passwordfield.getText().equals(passwordfieldrepeat.getText())  == false &&
				countrybox.getValue() != null &&
				citybox.getValue() != null &&
				currencybox.getValue() != null)
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
			clearComponents();
			myController.setScreen(WindowMain.SPLASH_SCREEN);
		}
		else if(arg0.getSource() == button_create)
		{
			if(checkInput() == 0)
			{
				DatabaseHandlerRegister dhr = new DatabaseHandlerRegister();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(citybox.getSelectionModel().getSelectedItem());
				int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(currencybox.getSelectionModel().getSelectedItem());
				
	
				NewUser nu = new NewUser(textfieldusername.getText(), cityid, textfieldemail.getText(), countryid, passwordfield.getText(), currencyid);
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
						int status = dhr.sendToDb();
							
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Create new account");
						alert.setHeaderText(null);
							
						if(status == 1)alert.setContentText("Account created succesfully! \n You may now log in.");
						else if(status == 0)alert.setContentText("Couldn't create the account! \n Please try again.");
	
						alert.showAndWait();
						
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
	}
	
	@Override
	public void clearComponents() 
	{
		textfieldusername.setText("");
		textfieldemail.setText("");
		passwordfield.setText("");
		passwordfieldrepeat.setText("");
		currencybox.getSelectionModel().clearSelection();
		countrybox.getSelectionModel().clearSelection();
		citybox.getSelectionModel().clearSelection();
	}
}
