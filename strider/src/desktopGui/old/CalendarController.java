package desktopGui.old;

import java.net.URL;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.GroupLayout.Alignment;

import com.sun.javafx.css.Style;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CalendarController implements Initializable, EventHandler<ActionEvent> 
{

    @FXML
    private VBox daybox;

    @FXML
    private VBox hourbox;

    @FXML
    private VBox activitybox;

    @FXML
    private Button buttonadd;

    @FXML
    private Spinner<Integer> spinnerduration;

    private static final int baseblocksize = 50;
    private static final int daystart = 7;
    private static final int dayend = 21;
    private static final int timetouse = dayend - daystart + 1;
    private List<Activity> activitylist;
    

    private Travel mytravel;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		buttonadd.setOnAction(this);
		
		activitylist = new LinkedList<Activity>();
		
		IntegerSpinnerValueFactory ivf = new IntegerSpinnerValueFactory(1, timetouse);
		spinnerduration.setValueFactory(ivf);
		
		setupDaysBox();
		//setupGestureTarget(rightvbox);
		
		Date start = Date.valueOf("2016-05-08");
		Date end = Date.valueOf("2016-05-20");
		
		Travel t = new Travel(start, end, "Poland", "Pruszków", "Russia", 1);
		
		System.out.println("Start : " + t.startdate);
		System.out.println("End : " + t.enddate);
		System.out.println("Travel duration : " + t.getTravelDuration() + " days.");
	}
	
	public void setupHoursVBox()
	{
		for(int i = daystart; i <= dayend; i++)
		{
			Label hour = new Label();
			hour.setText("" + i + ":00 - " + (i + 1) + ":00");
			hour.setMinHeight(baseblocksize);

			hour.setStyle(".label");
			//leftvbox.getChildren().add(hour);
		}
	}
	
	public void setupDaysBox()
	{
		for(int i = 1; i <= 10; i++)
		{
			ToggleButton daybutton = new ToggleButton("Day " + i);
			daybox.getChildren().add(daybutton);
		}
		
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == buttonadd)
		{
			if((getHoursLeft() > 0) && (spinnerduration.getValue() <= getHoursLeft()))
			{
				addActivity("Activity", spinnerduration.getValue(), 0);
			}
		}
	}
	
	public void setupGestureTarget(VBox container)
	{
		container.setOnDragOver(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent event)
			{
				Dragboard db = event.getDragboard();
				
				//if something
				event.acceptTransferModes(TransferMode.MOVE);
				event.consume();
			}
			
			
		});
		
		container.setOnDragDropped(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent event)
			{
				System.out.println("Got " + event.getDragboard().getString());
					
				Activity tempactivity;
				Label templabel;
				int id = 0;
				
				for(Activity a : activitylist)
				{
					//if(a == event.getDragboard().getContent(Data))
					{
						tempactivity = a;
						id = activitylist.indexOf(a);
						activitylist.remove(a);
					}
				}
				System.out.println("Id = " + id);
			//	rightvbox.getChildren().remove(id);
					
					
				event.setDropCompleted(true);
				event.consume();
			}
			
			
			
		});
		
		
		
	}
	
	public void setupGestureSource(HBox obj)
	{
		obj.setOnDragDetected(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event)
			{
				Dragboard dg = obj.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent cc = new ClipboardContent();
				//cc.putString(obj.getText());
				
				dg.setContent(cc);
				event.consume();
			}
			
			
			
		});
		
	}
	
	public void addActivity(String name, int actstart, int duration)
	{
		Activity act = new Activity(name + (activitylist.size() + 1), actstart, duration);
		ActivityBox actbox = new ActivityBox(act);
		
		activitybox.getChildren().add(actbox);
		activitylist.add(act);
	}
	
	
//	public int getAttractionNumber()
	{
		//return rightvbox.getChildren().size();
	}
	
	public int getHoursLeft()
	{
		int timeleft = timetouse;
		int timeuseD = 0;
		for (Activity a : activitylist)
		{
			timeuseD += a.getDuration();
		}
		return (timeleft - timeuseD);
	}
}
