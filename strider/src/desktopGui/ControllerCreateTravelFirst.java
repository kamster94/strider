package desktopGui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import Model.Travel;
import Model.TravelFramework;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class ControllerCreateTravelFirst implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
	ScreensController myController;

    @FXML
    private TextField textfield_tripname;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_next;
    
    @FXML
    private DatePicker datepicker_start;

    @FXML
    private DatePicker datepicker_end;
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		textfield_tripname.setPromptText("Nazwa podró¿y");
		datepicker_start.setPromptText("Pierwszy dzieñ");
		datepicker_end.setPromptText("Ostatni dzieñ");
		
		button_cancel.setOnAction(this);
		button_next.setOnAction(this);
		
		datepicker_start.setOnAction(this);
		datepicker_end.setOnAction(this);
		
		datepicker_start.setValue(LocalDate.now());
		datepicker_end.setValue(LocalDate.now().plusDays(1));
	}
	
	public boolean checkInputCompletion()
	{
		if(textfield_tripname.getText().isEmpty() == true)return false;
		if(datepicker_start.getValue() == null)return false;
		if(datepicker_end.getValue() == null)return false;
		return true;
	}
	
	public void sanitazeDatepickers()
	{
		if(datepicker_start.getValue().compareTo(LocalDate.now()) < 0)
		{
			datepicker_start.setValue(LocalDate.now());
		}

		if(datepicker_end.getValue().compareTo(datepicker_start.getValue()) < 0)
		{
			datepicker_end.setValue(datepicker_start.getValue().plusDays(1));
		}
	}
	
	
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_next)
		{
			if(checkInputCompletion() == true)
			{
				LocalDateTime startdate = LocalDateTime.of(datepicker_start.getValue(), LocalTime.MIN);
				LocalDateTime enddate = LocalDateTime.of(datepicker_end.getValue(), LocalTime.MIN);
				
				long difference = ChronoUnit.DAYS.between(startdate, enddate);
				
				if(difference > 28)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tworzenie podró¿y");
					alert.setHeaderText(null);
					alert.setContentText("Podró¿ jest zbyt d³uga (maksimum 28 dni).");
					alert.showAndWait();
				}
				else
				{
					Travel trav = new Travel(textfield_tripname.getText(), startdate, enddate);
				
					TravelFramework.getInstance().setTravel(trav);

					myController.loadScreen(WindowMain.NEWTRAVELSECOND, WindowMain.NEWTRAVELSECOND_FXML);
					myController.setScreen(WindowMain.NEWTRAVELSECOND);
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tworzenie podró¿y");
				alert.setHeaderText(null);
				alert.setContentText("Nale¿y wype³niæ wszystkie pola.");
				alert.showAndWait();
			}
		}
		else if(arg0.getSource() == button_cancel)
		{
			myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		}
		else if(arg0.getSource().getClass() == DatePicker.class)
		{
			sanitazeDatepickers();
		}
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent)
	{
		// TODO Auto-generated method stub
		myController = screenParent; 
	}
	
	@Override
	public void clearComponents()
	{

		
	}
}
