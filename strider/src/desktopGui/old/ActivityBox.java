package desktopGui.old;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActivityBox extends HBox 
{
	Activity parent;
	
	public ActivityBox(Activity act) 
	{
		this.parent = act;

		this.setPrefHeight((50 * parent.getDuration() + 10 * (parent.getDuration() - 1)));
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("hbox");

		Label activityname = new Label("Activity : " + parent.getName());
		this.getChildren().add(activityname);
		VBox vb = new VBox();
		
		Label starttime = new Label("Start Time : " + act.getStartTime());
		Label endtime = new Label("End Time : " + act.getEndTime());
		vb.getChildren().add(starttime);
		vb.getChildren().add(endtime);
		this.getChildren().add(vb);
		
		Label activityduration = new Label("Duration : " + parent.getDuration() + " hours.");

		this.getChildren().add(activityduration);
		
	}
	
	public Activity getActivity()
	{
		return parent;
	}
}
