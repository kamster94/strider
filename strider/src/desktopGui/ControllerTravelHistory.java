package desktopGui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControllerTravelHistory implements ControlledScreen, Initializable, EventHandler<ActionEvent>
{
	ScreensController myController; 
	List<Travel> travellist;
    private List<DayPane> daypanes;
    private List<Day> days;
    
    @FXML
    private ListView<String> listviewtravels;

    @FXML
    private Accordion accordionsummary;

    @FXML
    private Button button_cancel;

	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;		
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		button_cancel.setOnAction(this);
		
		travellist = DatabaseHandlerTravelHistory.getInstance().getUserTravels();
		
		String tname = "";
		
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
					daypanes = new LinkedList<DayPane>();
					Travel selected = travellist.get(listviewtravels.getSelectionModel().getSelectedIndex());
					days = selected.days;
			    	for(Day d : days)
			    	{
			    		DayPane dp = new DayPane(d);
			    		dp.myday = d;
			    		dp.setText("Dzie� " + dp.myday.date.getDayOfMonth() + "-" + dp.myday.date.getMonthValue() + "-" + dp.myday.date.getYear());
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

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_cancel)
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
    			//Rzutowanie z long -> int - bo i tak zak�adamy �e najd�u�szy pobyt w hotelu czy tam d�ugo�c podr�y to 30 dni.
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
    
	public void populateSummary()
	{
		String usercurrency = DatabaseHandlerCommon.getInstance().getCurrencyName(User.getInstance().getCurrencyId());
		double transportcost = calculateTransportCost();
    	double hotelcost = calculateHotelCost();
    	double attractioncost = calculateAttractionCost();
    	double allcost = transportcost + hotelcost + attractioncost;
    	int numdays = daypanes.size();
    	
    	/*
    	label_transportcost.setText("Koszt transport�w: " + transportcost + " " + usercurrency);
    	label_hotelcost.setText("Koszt nocleg�w: " + hotelcost + " " + usercurrency);
    	label_attractioncost.setText("Koszt atrakcji: " + attractioncost + " " + usercurrency);
    	label_allcost.setText("��czny koszt: " + allcost + " " + usercurrency);
    	label_numdays.setText("Czas podr�y (w dniach): " + numdays);
    	*/
    	
    	for(DayPane dp : daypanes)
    	{
    		dp.myvbox.getChildren().removeAll(dp.myvbox.getChildren());
    	}
    	
        for(DayPane dp : daypanes)
        {
        	if(dp.myday.transport != null)
        	{
        		
        		/*
        		Button button_delete = new Button("Usu�");
        		
        		button_delete.setOnAction(new EventHandler<ActionEvent>()
        		{
					@Override
					public void handle(ActionEvent arg0) 
					{
						dp.myday.transport = null;
						populateContent();
					}
        			
        		});
        		*/
        		
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
        		Label trans_start_time = new Label("Godzina: " + trans.startdatetime.getHour() + ":" + trans.startdatetime.getMinute());
        		Label trans_end_time = new Label("Godzina: " + trans.enddatetime.toLocalTime().toString());
        		
        		double recalculated_cost = (CurrencyInformation.getUserCurrencyCost(trans.calcdcost, trans.currency, usercurrency));
        		recalculated_cost = new BigDecimal(recalculated_cost).setScale(2, RoundingMode.HALF_UP).doubleValue();
        		Label trans_start_cost = new Label("Cena: " + trans.calcdcost + " " + trans.currency + " (" + recalculated_cost + " " + usercurrency + ")");
        		
        		LocalDate date_start = (trans.startdatetime).toLocalDate();
        		LocalDate date_end = (trans.enddatetime).toLocalDate();	
        		
        		vbox_start.getChildren().add(trans_start_label);
        		vbox_start.getChildren().add(trans_start_type);
        		vbox_start.getChildren().add(trans_start_time);
        		vbox_start.getChildren().add(new Label("�rodek transportu: " + trans.transportcategory));
        		vbox_start.getChildren().add(trans_start_cost);
        		//vbox_start.getChildren().add(button_delete);
        		
        		vbox_end.getChildren().add(trans_end_label);
        		vbox_end.getChildren().add(trans_end_type);
        		vbox_end.getChildren().add(trans_end_time);
        		vbox_end.getChildren().add(new Label("�rodek transportu: " + trans.transportcategory));
 
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
        		/*
        		Button button_delete = new Button("Usu�");
        		
        		button_delete.setOnAction(new EventHandler<ActionEvent>()
        		{
					@Override
					public void handle(ActionEvent arg0) 
					{
						dp.myday.hotel = null;
						populateContent();
					}
        			
        		});
        		*/
        		
        		Hotel hot = dp.myday.hotel;
        		
        		TitledPane hotelpane_arrival = new TitledPane();
        		TitledPane hotelpane_leaving = new TitledPane();
        		hotelpane_arrival.setText("HOTEL | " + hot.name + " | Przyjazd");
        		hotelpane_leaving.setText("HOTEL | " + hot.name + " | Wyjazd");

        		LocalDate date_arrival = (hot.accomodation_startdate).toLocalDate();
        		LocalDate date_leaving = (hot.accomodation_enddate).toLocalDate();
  
        		VBox hotelvbox_arrival = new VBox();
        		VBox hotelvbox_leaving = new VBox();
        		hotelvbox_arrival.setSpacing(10);
        		hotelvbox_leaving.setSpacing(10);
        			
        		Label type_arrival = new Label("Rodzaj : Przyjazd do hotelu.");
        		Label type_leaving = new Label("Rodzaj : Wyjazd z hotelu.");
        			
        		double hoteloverallprice = hot.pricepernite * (ChronoUnit.DAYS.between(date_arrival, date_leaving) + 1);
        			
        		double recalculated_overallprice = (CurrencyInformation.getUserCurrencyCost(hoteloverallprice, hot.currency, usercurrency));
        		double recalculated_niteprice = (CurrencyInformation.getUserCurrencyCost(hot.pricepernite, hot.currency, usercurrency));
        		
        		Label price_arrival = new Label("Cena za pobyt: " + hoteloverallprice + " " + hot.currency + " (" + recalculated_overallprice + " " + usercurrency + ")");
        		Label price_nite = new Label("Cena za noc: " + hot.pricepernite + " " + hot.currency + " (" + recalculated_niteprice + " " + usercurrency + ")");
        		
        		TitledPane tpnotes1 = new TitledPane();
        		tpnotes1.setExpanded(false);
        		tpnotes1.setText("Poka� notatk�");
        		TextArea notesarea1 = new TextArea();
        		notesarea1.setEditable(false);
        		notesarea1.setText(hot.notes);
        		tpnotes1.setContent(notesarea1);
    				
        		TitledPane tpnotes2 = new TitledPane();
        		tpnotes2.setExpanded(false);
        		tpnotes2.setText("Poka� notatk�");
        		TextArea notesarea2 = new TextArea();
        		notesarea2.setEditable(false);
        		notesarea2.setText(hot.notes);
        		tpnotes2.setContent(notesarea2);

        		hotelvbox_arrival.getChildren().add(new Label("Nazwa hotelu: " + hot.name));
        		hotelvbox_arrival.getChildren().add(type_arrival);
        		hotelvbox_arrival.getChildren().add(price_nite);
        		hotelvbox_arrival.getChildren().add(price_arrival);
        		hotelvbox_arrival.getChildren().add(tpnotes1);
        		//hotelvbox_arrival.getChildren().add(button_delete);
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
				/*
        		Button button_delete = new Button("Usu�");
        		
        		button_delete.setOnAction(new EventHandler<ActionEvent>()
        		{
					@Override
					public void handle(ActionEvent arg0) 
					{
						for(Attraction atx : dp.myday.attractions)
						{
							if(atx.equals(at))dp.myday.attractions.remove(atx);
						}
						populateContent();
					}
        			
        		});
				 */
				
				TitledPane tp = new TitledPane();
				tp.setText("ATRAKCJA | " + at.name);
				VBox attrvbox = new VBox();
				attrvbox.setSpacing(10);
				Label atname = new Label("Nazwa atrakcji: " + at.name);
				Label athours = new Label("Godziny otwarcia: od " + at.openfrom + " do " + at.opento);
				
        		double recalculated_cost = (CurrencyInformation.getUserCurrencyCost(at.price, at.currency, usercurrency));
				Label price = new Label("Cena: " + at.price + " " + at.currency + " (" + recalculated_cost + " " + usercurrency + ")");

				TitledPane tpnotes = new TitledPane();
				tpnotes.setExpanded(false);
				tpnotes.setText("Poka� notatk�");
				TextArea notesarea = new TextArea();
				notesarea.setEditable(false);
				notesarea.setText(at.notes);
				tpnotes.setContent(notesarea);
				
				attrvbox.getChildren().add(atname);
				attrvbox.getChildren().add(athours);
				if(at.price > 0)attrvbox.getChildren().add(price);
				attrvbox.getChildren().add(tpnotes);
				//attrvbox.getChildren().add(button_delete);
				
				tp.setContent(attrvbox);
				dp.myvbox.getChildren().add(tp);
			}
        }
	}
}
