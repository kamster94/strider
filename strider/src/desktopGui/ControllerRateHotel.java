package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import dbHandlers.DatabaseHandlerCommon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class ControllerRateHotel implements Initializable, ControlledScreen
{
	ScreensController myController;

	RatingBox rating_clean;
	RatingBox rating_comfort;
	RatingBox rating_localization;
	RatingBox rating_udogodnienia;
	RatingBox rating_personel;
	RatingBox rating_qualityprice;
	RatingBox rating_average;
	
    @FXML
    private ComboBox<String> combobox_hotel;

    @FXML
    private VBox vbox_rateclean;

    @FXML
    private VBox vbox_ratecomfort;

    @FXML
    private VBox vbox_ratelocalization;

    @FXML
    private VBox vbox_rateudogothings;

    @FXML
    private VBox vbox_ratepersonel;

    @FXML
    private VBox vbox_ratequalitytoprice;

    @FXML
    private TextFlow textflow_hoteldata;

    @FXML
    private TextFlow textflow_notes;

    @FXML
    private VBox vbox_rateaverage;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_rate;

	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;
	}

	private int calculateAverage()
	{
		double avg = 0;
		
		avg = rating_clean.getValue() + rating_comfort.getValue() + rating_localization.getValue() + rating_personel.getValue() + rating_qualityprice.getValue() + rating_udogodnienia.getValue();
		avg = avg / 6;
		System.out.println("AVG : " + avg);
		return (int)avg;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		//Wype³niæ hotelami z HotelDetails (z tych hoteli w których by³ u¿ytkownik) na zasadzie Pañstwo | Miasto | Nazwa hotelu
		combobox_hotel.getItems().setAll(DatabaseHandlerCommon.getInstance().getHotelsList());
		
		
		
		
		rating_clean = new RatingBox();
		rating_comfort = new RatingBox();
		rating_localization = new RatingBox();
		rating_udogodnienia = new RatingBox();
		rating_personel = new RatingBox();
		rating_qualityprice = new RatingBox();
		rating_average = new RatingBox();
		vbox_rateclean.getChildren().add(rating_clean);
		vbox_ratecomfort.getChildren().add(rating_comfort);
		vbox_ratelocalization.getChildren().add(rating_localization);
		vbox_rateudogothings.getChildren().add(rating_udogodnienia);
		vbox_ratepersonel.getChildren().add(rating_personel);
		vbox_ratequalitytoprice.getChildren().add(rating_qualityprice);
		vbox_rateaverage.getChildren().add(rating_average);
		
		for(Node n : rating_clean.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}
		
		for(Node n : rating_comfort.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}

		for(Node n : rating_localization.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}
		
		for(Node n : rating_udogodnienia.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}
		
		for(Node n : rating_personel.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}
		
		for(Node n : rating_qualityprice.getChildren())
		{
			if(n.getClass().equals(ToggleButton.class))
			{
				((ToggleButton)n).selectedProperty().addListener(new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) 
					{
						rating_average.setValue(calculateAverage());
					}
				});
			}
		}
	
		
		rating_average.setDisable(true);
		rating_average.setValue(calculateAverage());
	}
}
