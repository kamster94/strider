package desktopGui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import Model.Attraction;
import Model.Day;
import Model.Hotel;
import Model.Transport;
import Model.Travel;
import Model.User;
import countryWarnings.CurrencyInformation;
import dbHandlers.DatabaseHandlerCommon;
import dbHandlers.DatabaseHandlerTravelHistory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ControllerTravelHistory implements ControlledScreen, Initializable, EventHandler<ActionEvent>
{
    @FXML
    private Label label_transportcost;
    @FXML
    private Label label_hotelcost;
    @FXML
    private Label label_attractioncost;
    @FXML
    private Label label_allcost;
    @FXML
    private Label label_numdays;
    @FXML
    private ListView<String> listviewtravels;
    @FXML
    private Accordion accordionsummary;
    @FXML
    private Button button_cancel;
    @FXML
    private ComboBox<String> combobox_file;
    @FXML
    private Button button_open;
    
    private ScreensController myController; 
    private List<Travel> travellist;
    private List<DayPane> daypanes;
    private List<Day> days;
    private List<String> pathlist;
    
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;		
	}

	public String makeFuckingStringTime(String shitsql)
	{
		String time = "";
		String hour = shitsql.substring(11, 13);
		String minute = shitsql.substring(14, 16);
		time = "" + hour + ":" + minute;
		return time;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_open.setOnAction(this);
		button_cancel.setOnAction(this);
		travellist = DatabaseHandlerTravelHistory.getInstance().getUserTravels();
		
		String tname = "";
		
		if(travellist != null && travellist.size() > 0)
		{
			for(Travel t : travellist)
			{
				tname = t.getStartDate() + " do " + t.getEndDate() + " | " + t.getName();
				listviewtravels.getItems().add(tname);
			}
		
			listviewtravels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
				{
					if(listviewtravels.getSelectionModel().isEmpty())
					{
						accordionsummary.getPanes().clear();
					}
					else
					{
						combobox_file.getItems().clear();
						
						daypanes = new LinkedList<DayPane>();
						Travel selected = travellist.get(listviewtravels.getSelectionModel().getSelectedIndex());
						pathlist = selected.getFilePathList();
						combobox_file.getItems().setAll(pathlist);
						days = selected.days;
						for(Day d : days)
						{
							DayPane dp = new DayPane(d);
							dp.myday = d;
							dp.setText("Dzieñ " + dp.myday.date.getDayOfMonth() + "-" + dp.myday.date.getMonthValue() + "-" + dp.myday.date.getYear());
							VBox daypanevbox = new VBox();
							dp.myvbox = daypanevbox;
							dp.setContent(dp.myvbox);
							daypanes.add(dp);
						}
						accordionsummary.getPanes().setAll(daypanes);
						populateSummary();
					}	
				}
			});
		}
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_open)
		{
			if(combobox_file.getSelectionModel().isEmpty() == false)
			{
				try 
				{
					Desktop.getDesktop().open(new File(pathlist.get(combobox_file.getSelectionModel().getSelectedIndex())));
				} 
				catch (IOException | IllegalArgumentException e ) 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Potwierdzenie rezerwacji");
					alert.setHeaderText(null);
					alert.setContentText("Wyst¹pi³ problem przy otwarciu pliku.");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Potwierdzenie rezerwacji");
				alert.setHeaderText(null);
				alert.setContentText("Nie wybrano pliku do otwarcia.");
				alert.showAndWait();
			}
		}
		else if(arg0.getSource() == button_cancel)
		{
			myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
		}
		
	}
	private class DayPane extends TitledPane
    {
    	public Day myday;
    	public VBox myvbox;
    	
    	public DayPane(Day d)
    	{
    		myday = d;
    	}
    }
    
    private DayPane getDayPaneByDate(LocalDate date)
	{
		DayPane founddaypane = null;
		for(DayPane dp : daypanes)
		{
			if(dp.myday.date.equals(date))founddaypane = dp;
		}
		return founddaypane;
	}

    private double calculateTransportCost()
    {
    	double cost = 0;	
    	String usercurrency = DatabaseHandlerCommon.getInstance().getCurrencyName(User.getInstance().getCurrencyId());
    	for(DayPane dp : daypanes)
    	{
    		if(dp.myday.transport != null)
    		{
    			cost += (CurrencyInformation.getUserCurrencyCost(dp.myday.transport.calcdcost, dp.myday.transport.currency, usercurrency));
    		}
    	}
    	return cost;
    }
    
    private double calculateHotelCost()
    {
    	double cost = 0;
    	String usercurrency = DatabaseHandlerCommon.getInstance().getCurrencyName(User.getInstance().getCurrencyId());
    	for(DayPane dp : daypanes)
    	{
    		if(dp.myday.hotel != null)
    		{
    			//Rzutowanie z long -> int - bo i tak zak³adamy ¿e najd³u¿szy pobyt w hotelu czy tam d³ugoœc podró¿y to 30 dni.
    			int numdays = (int)ChronoUnit.DAYS.between(dp.myday.hotel.accomodation_startdate, dp.myday.hotel.accomodation_enddate) + 1;
    			double subcost = numdays * dp.myday.hotel.pricepernite;
    			cost += (CurrencyInformation.getUserCurrencyCost(subcost, dp.myday.hotel.currency, usercurrency));
    		}
    	}
    	return cost;
    }
    
    private double calculateAttractionCost()
    {
    	double cost = 0;
    	String usercurrency = DatabaseHandlerCommon.getInstance().getCurrencyName(User.getInstance().getCurrencyId());
    	
    	for(DayPane dp : daypanes)
    	{
    		if(dp.myday.attractions.isEmpty() == false)
    		{
    			for(Attraction a : dp.myday.attractions)
    			{
    				cost += (CurrencyInformation.getUserCurrencyCost(a.price, a.currency, usercurrency));
    			}
    		}
    	}
    	return cost;
    }
    
	public String makeFuckingStringTime2(String shitsql)
	{
		String time = "";
		String hour = shitsql.substring(0, 2);
		String minute = shitsql.substring(3, 5);
		time = "" + hour + ":" + minute;
		return time;
	}
	
	public void populateSummary()
	{
		String usercurrency = DatabaseHandlerCommon.getInstance().getCurrencyName(User.getInstance().getCurrencyId());
		double transportcost = new BigDecimal(calculateTransportCost()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    	double hotelcost = new BigDecimal(calculateHotelCost()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    	double attractioncost = new BigDecimal(calculateAttractionCost()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    	double allcost = new BigDecimal(transportcost + hotelcost + attractioncost).setScale(2, RoundingMode.HALF_UP).doubleValue();
    	int numdays = daypanes.size();
    	
    	label_transportcost.setText("Koszt transportów: " + transportcost + " " + usercurrency);
    	label_hotelcost.setText("Koszt noclegów: " + hotelcost + " " + usercurrency);
    	label_attractioncost.setText("Koszt atrakcji: " + attractioncost + " " + usercurrency);
    	label_allcost.setText("£¹czny koszt: " + allcost + " " + usercurrency);
    	label_numdays.setText("Czas podró¿y (w dniach): " + numdays);
    	
    	
    	for(DayPane dp : daypanes)
    	{
    		dp.myvbox.getChildren().removeAll(dp.myvbox.getChildren());
    	}
    	
        for(DayPane dp : daypanes)
        {
        	if(dp.myday.transport != null)
        	{
        		Transport trans = dp.myday.transport;
        		TitledPane trans_start = new TitledPane();
        		TitledPane trans_end = new TitledPane();
        		String trans_start_name = "Wyjazd z " + trans.country_start + ", " + trans.city_start;
        		String trans_end_name = "Przyjazd do " + trans.country_end + ", " + trans.city_end;
        		trans_start.setText("TRANSPORT | " + trans_start_name);
        		trans_end.setText("TRANSPORT | " + trans_end_name);
        		VBox vbox_start = new VBox();
        		VBox vbox_end = new VBox();
        		vbox_start.setSpacing(10);
        		vbox_end.setSpacing(10);
        		trans_start.setContent(vbox_start);
        		trans_end.setContent(vbox_end);
        		
        		Label trans_start_label = new Label(trans_start_name);
        		Label trans_end_label = new Label(trans_end_name);
        		Label trans_start_type = new Label("Rodzaj : Wyjazd");
        		Label trans_end_type = new Label("Rodzaj : Przyjazd");
        		Label trans_start_time = new Label("Godzina: " + makeFuckingStringTime(trans.startdatetime.toString()));
        		Label trans_end_time = new Label("Godzina: " + makeFuckingStringTime(trans.enddatetime.toString()));
        		
        		double recalculated_cost = (CurrencyInformation.getUserCurrencyCost(trans.calcdcost, trans.currency, usercurrency));
        		recalculated_cost = new BigDecimal(recalculated_cost).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		trans.calcdcost = new BigDecimal(trans.calcdcost).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		Label trans_start_cost = new Label("Cena: " + trans.calcdcost + " " + trans.currency + " (" + recalculated_cost + " " + usercurrency + ")");
        		
        		LocalDate date_start = (trans.startdatetime).toLocalDate();
        		LocalDate date_end = (trans.enddatetime).toLocalDate();	
        		
        		vbox_start.getChildren().add(trans_start_label);
        		vbox_start.getChildren().add(trans_start_type);
        		vbox_start.getChildren().add(trans_start_time);
        		vbox_start.getChildren().add(new Label("Œrodek transportu: " + trans.transportcategory));
        		vbox_start.getChildren().add(trans_start_cost);
        		
        		vbox_end.getChildren().add(trans_end_label);
        		vbox_end.getChildren().add(trans_end_type);
        		vbox_end.getChildren().add(trans_end_time);
        		vbox_end.getChildren().add(new Label("Œrodek transportu: " + trans.transportcategory));
 
        		if(getDayPaneByDate(date_start) != null)
        		{			
        			getDayPaneByDate(date_start).myvbox.getChildren().add(trans_start);
        		}
        			
        		if(getDayPaneByDate(date_end) != null)
        		{
        			getDayPaneByDate(date_end).myvbox.getChildren().add(trans_end);
        		}
        	}

        	if(dp.myday.hotel != null)
        	{
        		Hotel hot = dp.myday.hotel;
        		
        		TitledPane hotelpane_arrival = new TitledPane();
        		TitledPane hotelpane_leaving = new TitledPane();
        		hotelpane_arrival.setText("HOTEL | " + hot.name + " | Zameldowanie");
        		hotelpane_leaving.setText("HOTEL | " + hot.name + " | Wymeldowanie");

        		LocalDate date_arrival = (hot.accomodation_startdate).toLocalDate();
        		LocalDate date_leaving = (hot.accomodation_enddate).toLocalDate();
  
        		VBox hotelvbox_arrival = new VBox();
        		VBox hotelvbox_leaving = new VBox();
        		hotelvbox_arrival.setSpacing(10);
        		hotelvbox_leaving.setSpacing(10);
        			
        		Label type_arrival = new Label("Rodzaj : Zameldowanie.");
        		Label type_leaving = new Label("Rodzaj : Wymeldowanie.");
        			
        		double hoteloverallprice = hot.pricepernite * (ChronoUnit.DAYS.between(date_arrival, date_leaving) + 1);
        		double recalculated_overallprice = (CurrencyInformation.getUserCurrencyCost(hoteloverallprice, hot.currency, usercurrency));
        		double recalculated_niteprice = (CurrencyInformation.getUserCurrencyCost(hot.pricepernite, hot.currency, usercurrency));
        		double pricepernite_cut = new BigDecimal(hot.pricepernite).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		
        		hoteloverallprice = new BigDecimal(hoteloverallprice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		recalculated_overallprice = new BigDecimal(recalculated_overallprice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		recalculated_niteprice = new BigDecimal(recalculated_niteprice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		
        		Label price_arrival = new Label("Cena za pobyt: " + hoteloverallprice + " " + hot.currency + " (" + recalculated_overallprice + " " + usercurrency + ")");
        		Label price_nite = new Label("Cena za noc: " + pricepernite_cut + " " + hot.currency + " (" + recalculated_niteprice + " " + usercurrency + ")");
        		
        		TitledPane tpnotes1 = new TitledPane();
        		tpnotes1.setExpanded(false);
        		tpnotes1.setText("Poka¿ notatkê");
        		TextArea notesarea1 = new TextArea();
        		notesarea1.setEditable(false);
        		notesarea1.setText(hot.notes);
        		tpnotes1.setContent(notesarea1);
    				
        		TitledPane tpnotes2 = new TitledPane();
        		tpnotes2.setExpanded(false);
        		tpnotes2.setText("Poka¿ notatkê");
        		TextArea notesarea2 = new TextArea();
        		notesarea2.setEditable(false);
        		notesarea2.setText(hot.notes);
        		tpnotes2.setContent(notesarea2);

        		hotelvbox_arrival.getChildren().add(new Label("Nazwa hotelu: " + hot.name));
        		hotelvbox_arrival.getChildren().add(type_arrival);
        		hotelvbox_arrival.getChildren().add(price_nite);
        		hotelvbox_arrival.getChildren().add(price_arrival);
        		hotelvbox_arrival.getChildren().add(tpnotes1);
        		hotelpane_arrival.setContent(hotelvbox_arrival);		
        		
        		hotelvbox_leaving.getChildren().add(new Label("Nazwa hotelu: " + hot.name));
        		hotelvbox_leaving.getChildren().add(type_leaving);
        		hotelvbox_leaving.getChildren().add(tpnotes2);
        		hotelpane_leaving.setContent(hotelvbox_leaving);
    				
        		if(getDayPaneByDate(date_arrival) != null)
        		{			
        			getDayPaneByDate(date_arrival).myvbox.getChildren().add(hotelpane_arrival);
        		}
        			
        		if(getDayPaneByDate(date_leaving) != null)
        		{
        			getDayPaneByDate(date_leaving).myvbox.getChildren().add(hotelpane_leaving);
        		}
        	}
        	
			for(Attraction at : dp.myday.attractions)
			{
				TitledPane tp = new TitledPane();
				tp.setText("ATRAKCJA | " + at.name);
				VBox attrvbox = new VBox();
				attrvbox.setSpacing(10);
				Label atname = new Label("Nazwa atrakcji: " + at.name);
				Label athours = new Label("");
				
				at.openfrom = makeFuckingStringTime2(at.openfrom);
				at.opento = makeFuckingStringTime2(at.opento);
				
				if(!(at.openfrom.equals("00:00") && at.opento.equals("00:00")))
				athours.setText("Godziny otwarcia: od " + at.openfrom + " do " + at.opento);
				
        		double recalculated_cost = (CurrencyInformation.getUserCurrencyCost(at.price, at.currency, usercurrency));
				Label price = new Label("Cena: " + at.price + " " + at.currency + " (" + recalculated_cost + " " + usercurrency + ")");

				TitledPane tpnotes = new TitledPane();
				tpnotes.setExpanded(false);
				tpnotes.setText("Poka¿ notatkê");
				TextArea notesarea = new TextArea();
				notesarea.setEditable(false);
				notesarea.setText(at.notes);
				tpnotes.setContent(notesarea);
				
				attrvbox.getChildren().add(atname);
				attrvbox.getChildren().add(athours);
				if(at.price > 0)attrvbox.getChildren().add(price);
				attrvbox.getChildren().add(tpnotes);
				
				tp.setContent(attrvbox);
				dp.myvbox.getChildren().add(tp);
			}
        }
	}
}
