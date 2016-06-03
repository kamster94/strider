package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import Model.User;
import dbConnection.DbAccess;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ControllerChangeUserOptions implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;
	
    @FXML
    private VBox vbox_countrybox;

    @FXML
    private VBox vbox_citybox;

    @FXML
    private VBox vbox_currency;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_apply;

    ComboBox<String> countrybox;
    ComboBox<String> citybox;
    ComboBox<String> currencybox;
    Button button_findcities;
    
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_findcities = new Button("ZnajdŸ");
		
		button_findcities.setOnAction(this);
		button_cancel.setOnAction(this);
		button_apply.setOnAction(this);
		countrybox = WindowMain.getCountryBox();
		citybox = WindowMain.getCityBox();
		currencybox = WindowMain.getCurrencyBox();
		
		vbox_countrybox.getChildren().add(countrybox);
		vbox_countrybox.getChildren().add(button_findcities);

		vbox_citybox.getChildren().add(citybox);
		vbox_currency.getChildren().add(currencybox);
		
		countrybox.getSelectionModel().select(User.getInstance().getCountryId());
		citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(User.getInstance().getCountryId()));
		citybox.getSelectionModel().select(User.getInstance().getCityId());
		currencybox.getSelectionModel().select(User.getInstance().getCurrencyId());
		
		
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_cancel)
		{
			myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		}
		else if(arg0.getSource() == button_findcities)
		{
			int countryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
			citybox.getItems().setAll(DatabaseHandlerCommon.getInstance().getCities(countryid));
		}
		else if(arg0.getSource() == button_apply)
		{
			if(countrybox.getSelectionModel().isEmpty() == false && citybox.getSelectionModel().isEmpty() == false && currencybox.getSelectionModel().isEmpty() == false)
			{
				int newcountryid = DatabaseHandlerCommon.getInstance().getCountryId(countrybox.getSelectionModel().getSelectedItem());
				int newcityid = DatabaseHandlerCommon.getInstance().getCityId(citybox.getSelectionModel().getSelectedItem());
				int newcurrencyid = DatabaseHandlerCommon.getInstance().getCurrencyId(currencybox.getSelectionModel().getSelectedItem());
				
				boolean status = DatabaseHandlerLogin.getInstance().updateUserPreferences(newcountryid, newcityid, newcurrencyid);
				
			
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Zmieñ ustawienia u¿ytkownika");
				alert.setHeaderText(null);
				alert.setContentText("Zmiany zosta³y pomyœlnie zapisane.");
				alert.showAndWait();
				
				myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
			}
		}
	}



}
