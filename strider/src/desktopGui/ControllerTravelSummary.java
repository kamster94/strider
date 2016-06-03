package desktopGui;


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
import Model.TravelFramework;
import Model.User;
import dbHandlers.DatabaseHandlerAttractionAdder;
import dbHandlers.DatabaseHandlerHotelAdder;
import dbHandlers.DatabaseHandlerStage;
import dbHandlers.DatabaseHandlerTripAdder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ControllerTravelSummary implements Initializable, ControlledScreen, EventHandler<ActionEvent>
{
	static ScreensController myController;
	
    @FXML
    private VBox mainvbox;
    @FXML
    private Button button_back;
    @FXML
    private Button button_apply;
    @FXML
    private Accordion accordionstages;
    
    private List<DayPane> daypanes;
    private List<Day> days;
    
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		daypanes = new LinkedList<DayPane>();
		days = TravelFramework.getInstance().getTravel().days;
		
		button_back.setOnAction(this);
		button_apply.setOnAction(this);
		
		accordionstages = new Accordion();
		mainvbox.getChildren().add(accordionstages);
		mainvbox.getChildren().get(1).toBack();
		VBox.setVgrow(accordionstages, Priority.ALWAYS);
    	int curuser = User.getInstance().getId();
    	int curtravel = TravelFramework.getInstance().getTravel().getId();
    	int daynum = 1;

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
 
    	accordionstages.getPanes().setAll(daypanes);
    		
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
        		trans_start.setContent(vbox_start);
        		trans_end.setContent(vbox_end);
        		
        		Label trans_start_label = new Label(trans_start_name);
        		Label trans_end_label = new Label(trans_end_name);
        		Label trans_start_type = new Label("Rodzaj : Wyjazd");
        		Label trans_end_type = new Label("Rodzaj : Przyjazd");
        		Label trans_start_time = new Label("Godzina: " + trans.startdatetime.getHour() + ":" + trans.startdatetime.getMinute());
        		Label trans_end_time = new Label("Godzina: " + trans.enddatetime.getHour() + ":" + trans.enddatetime.getMinute());
        		Label trans_start_cost = new Label("Cena: " + trans.price);
        		
        		LocalDate date_start = (trans.startdatetime).toLocalDate();
        		LocalDate date_end = (trans.enddatetime).toLocalDate();
        		
        		
        		
        		vbox_start.getChildren().add(trans_start_label);
        		vbox_start.getChildren().add(trans_start_type);
        		vbox_start.getChildren().add(trans_start_time);
        		vbox_start.getChildren().add(new Label("Œrodek transportu: " + trans.transportcategory));
        		vbox_start.getChildren().add(trans_start_cost);
    
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
        			
        		float hoteloverallprice = hot.pricepernite * (ChronoUnit.DAYS.between(date_arrival, date_leaving) + 1);
        			
        		Label price_arrival = new Label("Cena za pobyt: " + hoteloverallprice + " " + hot.currency);
        			
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
				Label athours = new Label("Godziny otwarcia: od " + at.openfrom + " do " + at.opento);
				Label price = new Label("Cena: " + at.price);

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
    	
    	
/*
    			/*
    			if(day.transport != null)
    			{
    				TitledPane tp = new TitledPane();
    				tp.setText("TRANSPORT | " + day.transport.country_start + "(" + day.transport.city_start + ") --> " + day.transport.country_end + "(" + day.transport.city_end + ")");
    				VBox transbox = new VBox();
    				
  
    				//Label hotname = new Label("Nazwa: " + day.transport.);
    				//Label price = new Label("Cena za dobê: " + day.hotel.pricepernite);
    				
    				
    				//transbox.getChildren().add(hotname);
    				//transbox.getChildren().add(price);
    				//if(day.hotel.pricepernite > 0)transbox.getChildren().add(price);
    				
    				tp.setContent(transbox);
    				dayvbox.getChildren().add(tp);
    				
    			}
    			*/
    			
    
  
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == button_back)
		{
			myController.setScreen(WindowMain.NEWTRAVELSECOND);
		}
		else if(arg0.getSource() == button_apply)
		{
			int tripid = DatabaseHandlerTripAdder.getInstance().pushTravelToDatabase();
			TravelFramework.getInstance().getTravel().setId(tripid);
			
			for(Day d : TravelFramework.getInstance().getTravel().days)
			{
				if(d.hotel != null)
				{
					DatabaseHandlerTripAdder.getInstance().pushHotelToDatabase(d.hotel);
				}
				
				for(Attraction a : d.attractions)
				{
					DatabaseHandlerTripAdder.getInstance().pushAttractionToDatabase(a);
				}
				
			}
		
			TravelFramework.getInstance().setTravel(null);
			myController.setScreen(WindowMain.MAIN_SCREEN);
		}
	}

}
