package desktopGui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.TravelFramework;
import Model.User;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerTripAdder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControllerCreateTravelFirst implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;

    @FXML
    private TextField textfieldtravelname;
    @FXML
    private DatePicker datepickerstart;
    @FXML
    private DatePicker datepickerend;
    @FXML
    private VBox vbox_countrybox;
    @FXML
    private VBox vbox_citybox;
    @FXML
    private Spinner<Integer> spinnercompanions;
    @FXML
    private Button button_back;
    @FXML
    private Button button_next;
    
    @FXML
    private static ComboBox<String> citybox;
    @FXML
    private static ComboBox<String> countrybox;
	
    public static void lateInitialize()
    {
		//Preferowane miasto i panstwo startowe usera
		System.out.println("Pref country : " + User.getInstance().getCountryId());
		System.out.println("Pref city : " + User.getInstance().getCityId());
		countrybox.getSelectionModel().select(User.getInstance().getCountryId());
		citybox.getSelectionModel().select(User.getInstance().getCityId());
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_next.setOnAction(this);
		button_back.setOnAction(this);

		citybox = WindowMain.getCityBox();
		countrybox = WindowMain.getCountryBox();

		vbox_countrybox.getChildren().add(countrybox);
		vbox_citybox.getChildren().add(citybox);
	
		datepickerstart.setEditable(false);
		datepickerend.setEditable(false);
		datepickerstart.setValue(LocalDate.now());
		datepickerstart.setOnAction(this);
		datepickerend.setOnAction(this);
		
		SpinnerValueFactory<Integer> svf= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10);
		spinnercompanions.setValueFactory(svf);
		spinnercompanions.setEditable(false);
		
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
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}

	public void sanitizeDatePickers()
	{
		if(datepickerstart.getValue() != null)
		{
			if(datepickerstart.getValue().isBefore(LocalDate.now()))
			{
				datepickerstart.setValue(LocalDate.now());
			}
		}
		
		if((datepickerend.getValue() != null) && (datepickerstart.getValue() != null))
		{
			if(datepickerend.getValue().isBefore(LocalDate.now()))
			{
				datepickerend.setValue(datepickerstart.getValue().plusDays(1));
			}
			if(datepickerend.getValue().isBefore(datepickerstart.getValue()))
			{
				datepickerend.setValue(datepickerstart.getValue().plusDays(1));
			}
		}
	}
	
	public boolean checkInputCompletion()
	{
		if((textfieldtravelname.getText().isEmpty() == false) &&
			(datepickerstart.getValue() != null) &&
			(datepickerend.getValue() != null) &&
			(countrybox.getValue() != null) &&
			(citybox.getValue() != null) &&
			(spinnercompanions.getValue() != null))
		{
			return true;
		}
		else return false;
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if((arg0.getSource() == datepickerstart) || (arg0.getSource() == datepickerend))
		{
			sanitizeDatePickers();
		}
		else if(arg0.getSource() == button_back)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == button_next)
		{
			if(checkInputCompletion() == true)
			{
				
				if(TravelFramework.getInstance().hasTravel())
				{
					if(textfieldtravelname.getText() != TravelFramework.getInstance().getCurrentTravel().getName())
					{
						TravelFramework.getInstance().getCurrentTravel().setName(textfieldtravelname.getText());
					}
					if(datepickerstart.getValue() != TravelFramework.getInstance().getCurrentTravel().getStartDate())
					{
						TravelFramework.getInstance().getCurrentTravel().setStartDate(datepickerstart.getValue());
					}
					if(datepickerend.getValue() != TravelFramework.getInstance().getCurrentTravel().getEndDate())
					{
						TravelFramework.getInstance().getCurrentTravel().setEndDate(datepickerend.getValue());
					}
					if(countrybox.getSelectionModel().getSelectedIndex() != TravelFramework.getInstance().getCurrentTravel().getCountryOriginId())
					{
						TravelFramework.getInstance().getCurrentTravel().setCountryOriginId(countrybox.getSelectionModel().getSelectedIndex());
					}
					if(citybox.getSelectionModel().getSelectedIndex() != TravelFramework.getInstance().getCurrentTravel().getCityOriginId())
					{
						TravelFramework.getInstance().getCurrentTravel().setCityOriginId(citybox.getSelectionModel().getSelectedIndex());
					}
				}
				else
				{
					TravelFramework.getInstance().createNewTravel(textfieldtravelname.getText(), datepickerstart.getValue(), datepickerend.getValue(), countrybox.getSelectionModel().getSelectedIndex(), citybox.getSelectionModel().getSelectedIndex(), spinnercompanions.getValue().intValue());
					
					//Nie spamujmy bazy póki co
					DatabaseHandlerTripAdder dbhta = new DatabaseHandlerTripAdder();
					dbhta.setTravel(TravelFramework.getInstance().getCurrentTravel());
					TravelFramework.getInstance().getCurrentTravel().setId(dbhta.pushTravelToDatabase());
					
					
					TravelFramework.getInstance().print();
					System.out.println(TravelFramework.getInstance().getCurrentTravel().getId());
				}
				myController.setScreen(WindowMain.NEWTRAVEL_2);	
				ControllerCreateTravelSecond.lateInitialize();
			}
		}
	}

	@Override
	public void clearComponents()
	{

		
	}
}
