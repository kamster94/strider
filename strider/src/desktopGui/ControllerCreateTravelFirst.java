package desktopGui;

import java.net.URL;
import java.time.LocalDate;
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

public class ControllerCreateTravelFirst implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
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

    private ScreensController myController;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		textfield_tripname.setPromptText("Nazwa podr�y");
		datepicker_start.setPromptText("Pierwszy dzie�");
		datepicker_end.setPromptText("Ostatni dzie�");
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
				long difference = ChronoUnit.DAYS.between(datepicker_start.getValue(), datepicker_end.getValue());
				
				if(difference > 28)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Tworzenie podr�y");
					alert.setHeaderText(null);
					alert.setContentText("Podr� jest zbyt d�uga (maksimum 28 dni).");
					alert.showAndWait();
				}
				else
				{
					Travel trav = new Travel(textfield_tripname.getText(), datepicker_start.getValue(), datepicker_end.getValue());
					TravelFramework.getInstance().setTravel(trav);					
					myController.loadScreenAndSet(WindowMain.NEWTRAVELSECOND, WindowMain.NEWTRAVELSECOND_FXML);
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tworzenie podr�y");
				alert.setHeaderText(null);
				alert.setContentText("Nale�y wype�ni� wszystkie pola.");
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
		myController = screenParent; 
	}
}
