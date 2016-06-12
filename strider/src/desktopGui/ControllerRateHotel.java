package desktopGui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Model.Review;
import Model.VisitedHotels;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerHotelAdder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ControllerRateHotel implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
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
    private Label label_hotelname;
    @FXML
    private Label label_streetnameandnumber;
    @FXML
    private Label label_zipcitycountry;
    @FXML
    private TextArea textarea_notes;
    @FXML
    private VBox vbox_rateaverage;
    @FXML
    private Button button_cancel;
    @FXML
    private Button button_rate;
    
	private ScreensController myController;
	private RatingBox rating_clean;
	private RatingBox rating_comfort;
	private RatingBox rating_localization;
	private RatingBox rating_udogodnienia;
	private RatingBox rating_personel;
	private RatingBox rating_qualityprice;
	private RatingBox rating_average;
	private List<VisitedHotels> hotelsfromdb;

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
	
	private boolean checkInput()
	{
		if(rating_clean.getValue() == 0) return false;
		if(rating_comfort.getValue() == 0)return false;
		if(rating_localization.getValue() == 0)return false;
		if(rating_udogodnienia.getValue() == 0)return false;
		if(rating_personel.getValue() == 0)return false;
		if(rating_qualityprice.getValue() == 0)return false;
		if(rating_average.getValue() == 0)return false;
		return true;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_cancel.setOnAction(this);
		button_rate.setOnAction(this);
		label_hotelname.setText("Nazwa:");
		label_streetnameandnumber.setText("Adres:");
		label_zipcitycountry.setText("Lokacja:");
		
		hotelsfromdb = DatabaseHandlerHotelAdder.getInstance().getVisitedHotels();
		
		if(hotelsfromdb != null && hotelsfromdb.size() > 0)
		{
			for(VisitedHotels vh : hotelsfromdb)
			{
				combobox_hotel.getItems().add(vh.getHotelName());
			}

			combobox_hotel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
				{
					if(combobox_hotel.getSelectionModel().isEmpty() == false)
					{
						int id = combobox_hotel.getSelectionModel().getSelectedIndex();
						label_hotelname.setText("Nazwa: " + hotelsfromdb.get(id).getHotelName());
						label_streetnameandnumber.setText("Adres: " + hotelsfromdb.get(id).getStreetName() + " " + hotelsfromdb.get(id).getStreetNumber());
					
						String country = DatabaseHandlerCommon.getInstance().getCountryName(hotelsfromdb.get(id).getCountryId());
						String city = DatabaseHandlerCommon.getInstance().getCityName(hotelsfromdb.get(id).getCountryId(), hotelsfromdb.get(id).getCityId());
				
						label_zipcitycountry.setText("Lokacja: " + hotelsfromdb.get(id).getZipCode() + " " + city + " " + country);
					}
					else
					{
						label_hotelname.setText("Nazwa:");
						label_streetnameandnumber.setText("Adres:");
						label_zipcitycountry.setText("Lokacja:");
					}
				}		
			});
		}

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

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_cancel)
		{
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
		else if(arg0.getSource() == button_rate)
		{
			if(combobox_hotel.getSelectionModel().isEmpty() == false)
			{
				if(checkInput() == true)
				{
					int ratclean = rating_clean.getValue();
					int ratcom = rating_comfort.getValue();
					int ratloc = rating_localization.getValue();
					int ratamen = rating_udogodnienia.getValue();
					int ratpers = rating_personel.getValue();
					int ratvalmone = rating_qualityprice.getValue();
					int rataverage = rating_average.getValue();
					String notes = textarea_notes.getText();
				
					Review rev = new Review(ratclean, ratcom, ratloc, ratamen, ratpers, ratvalmone, rataverage, notes);
					DatabaseHandlerHotelAdder.getInstance().pushHotelReview(hotelsfromdb.get(combobox_hotel.getSelectionModel().getSelectedIndex()), rev);
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Ocena hotelu");
					alert.setHeaderText(null);
					alert.setContentText("Pomyœlnie oceniono wybrany hotel.");
					alert.showAndWait();
					
					myController.setScreen(WindowMain.MAIN_SCREEN);
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Ocena hotelu");
					alert.setHeaderText(null);
					alert.setContentText("Nie oceniono wszystkich kryteriów (Minimalna ocena to 1).");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Ocena hotelu");
				alert.setHeaderText(null);
				alert.setContentText("Nie wybrano hotelu do oceny.");
				alert.showAndWait();
			}
		}
	}
}
