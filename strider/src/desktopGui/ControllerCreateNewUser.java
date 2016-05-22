package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import Model.NewUser;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerLogin;
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

	public int verifyData()
	{
		if(textfieldusername.getText().isEmpty())return 1;
		if(textfieldemail.getText().isEmpty())return 1;
		if(passwordfield.getText().isEmpty())return 1;
		if(passwordfieldrepeat.getText().isEmpty())return 1;
		if(passwordfield.getText().equals(passwordfieldrepeat.getText()) == false)return 2;
		if(passwordfield.getText().length() < 6 || passwordfield.getText().length() > 32)return 3;
		
		return 0;
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
			if(verifyData() == 0)
			{
				DatabaseHandlerRegister dhr = new DatabaseHandlerRegister();
				int countryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
				int cityid = DatabaseHandlerCommon.getInstance().getCityId(citybox.getSelectionModel().getSelectedItem());
				int currencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(currencybox.getSelectionModel().getSelectedItem());
				
	
				NewUser nu = new NewUser(textfieldusername.getText(), cityid, textfieldemail.getText(), countryid, passwordfield.getText(), currencyid);
				dhr.setUserCandidate(nu);
				int dataval = dhr.verifyDataValidity();
				
				if(dataval == 0)
				{
					boolean isemailfree = dhr.checkEmailAvailability();
					
					if(isemailfree == true)
					{
						dhr.sendToDb();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Tworzenie konta");
						alert.setHeaderText(null);
						alert.setContentText("Poprawnie utworzono konto");
						alert.showAndWait();
						
						DatabaseHandlerLogin dhl = new DatabaseHandlerLogin();
						dhl.loginUser(nu.getEmail(), nu.getPassword());
						
						clearComponents();
						myController.setScreen(WindowMain.MAIN_SCREEN);
					}
					else
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Tworzenie konta");
						alert.setHeaderText(null);
						alert.setContentText("Ten adres e-mail jest ju¿ zajêty.");
						alert.showAndWait();
					}
				}
				else if(dataval == 1)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tworzenie konta");
					alert.setHeaderText(null);
					alert.setContentText("D³ugoœæ nazwy u¿ytkownika wykracza poza limit 32 znaków.");
					alert.showAndWait();
				}	
				else if(dataval == 2)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tworzenie konta");
					alert.setHeaderText(null);
					alert.setContentText("Adres e-mail jest niepoprawny lub wykracza poza limit 32 znaków.");
					alert.showAndWait();
				}
				else if(dataval == 3)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tworzenie konta");
					alert.setHeaderText(null);
					alert.setContentText("D³ugoœæ has³a musi zawieraæ siê w przedziale od 6 do 32 znaków.");
					alert.showAndWait();
				}
			}
			else if(verifyData() == 1)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tworzenie konta");
				alert.setHeaderText(null);
				alert.setContentText("Wszystkie pola oprócz Pañstwa, Miasta i Waluty s¹ obowi¹zkowe.");
				alert.showAndWait();
			}
			else if(verifyData() == 2)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tworzenie konta");
				alert.setHeaderText(null);
				alert.setContentText("Wpisane has³a nie s¹ sobie równe.");
				alert.showAndWait();
			}
			else if(verifyData() == 3)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tworzenie konta");
				alert.setHeaderText(null);
				alert.setContentText("D³ugoœæ has³a musi zawieraæ siê w przedziale od 6 do 32 znaków.");
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
