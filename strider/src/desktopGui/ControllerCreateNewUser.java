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
import javafx.scene.layout.HBox;

public class ControllerCreateNewUser implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	@FXML
	private TextField textfieldusername;
	@FXML
	private TextField textfieldemail;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private PasswordField passwordfieldrepeat;
	@FXML
	private HBox hbox_countrybox;
	@FXML
	private HBox hbox_citybox;
	@FXML
	private HBox hbox_currencybox;
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
	@FXML
	private ComboBox<String> currencybox;
	
	private ScreensController myController;
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		String DEFAULT_COUNTRY = "Polska";
		String DEFAULT_CITY = "Warszawa";
		
		button_findcities = new Button();
		button_findcities.setText("ZnajdŸ");
		
		countrybox = WindowMain.getCountryBox();
		citybox = WindowMain.getCityBox();
		currencybox = WindowMain.getCurrencyBox();
		
		countrybox.valueProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				citybox.getSelectionModel().clearSelection();
			}
		});
		
		button_cancel.setOnAction(this);
		button_create.setOnAction(this);
		button_findcities.setOnAction(this);

		hbox_countrybox.getChildren().add(countrybox);
		hbox_countrybox.getChildren().add(button_findcities);
		hbox_citybox.getChildren().add(citybox);
		hbox_currencybox.getChildren().add(currencybox);

		textfieldusername.setPromptText("Nazwa u¿ytkownika");
		textfieldemail.setPromptText("jankowalski@gmail.com");
		passwordfield.setPromptText("Has³o");
		passwordfieldrepeat.setPromptText("Powtórz has³o");
		countrybox.setPromptText("Pañstwo");
		citybox.setPromptText("Miasto");
		currencybox.setPromptText("Waluta");
		
		int selectedid = DatabaseHandlerCommon.getInstance().getCountryId(DEFAULT_COUNTRY);
		countrybox.getSelectionModel().select(DEFAULT_COUNTRY);
		citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(selectedid));
		citybox.getSelectionModel().select(DEFAULT_CITY);
		currencybox.getSelectionModel().select(DatabaseHandlerCommon.getInstance().getCurrencyNameForGivenCountry(selectedid));
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
		if(arg0.getSource() == button_findcities)
		{
			int countryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
			citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
		}
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
						DatabaseHandlerLogin.getInstance().loginUser(nu.getEmail(), nu.getPassword());
						clearComponents();
						myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
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
