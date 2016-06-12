package desktopGui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class RatingBox extends HBox implements EventHandler<ActionEvent>
{
	ToggleButton rating1;
	ToggleButton rating2;
	ToggleButton rating3;
	ToggleButton rating4;
	ToggleButton rating5;
	ToggleButton[] ratings;
	int rating;
	
	public RatingBox() 
	{
		ratings = new ToggleButton[5];
		rating1 = new ToggleButton();
		rating2 = new ToggleButton();
		rating3 = new ToggleButton();
		rating4 = new ToggleButton();
		rating5 = new ToggleButton();
		ratings[0] = rating1;
		ratings[1] = rating2;
		ratings[2] = rating3;
		ratings[3] = rating4;
		ratings[4] = rating5;
		rating1.setOnAction(this);
		rating2.setOnAction(this);
		rating3.setOnAction(this);
		rating4.setOnAction(this);
		rating5.setOnAction(this);
		rating1.setPrefSize(32, 32);
		rating2.setPrefSize(32, 32);
		rating3.setPrefSize(32, 32);
		rating4.setPrefSize(32, 32);
		rating5.setPrefSize(32, 32);
		getChildren().add(rating1);
		getChildren().add(rating2);
		getChildren().add(rating3);
		getChildren().add(rating4);
		getChildren().add(rating5);
		setPrefWidth(USE_COMPUTED_SIZE);
		setPrefHeight(USE_COMPUTED_SIZE);
		setAlignment(Pos.CENTER);
		setStyle(".ratingbox");
	}
	
	boolean isAnyStarAboveIsSelected(int id)
	{
		if(id < 4)for(int i = id + 1; i <= 4; i++)if(ratings[i].isSelected())return true;
		return false;
	}
	
	public void setValue(int val)
	{
		if(val >= 0 && val <= 5)
		{
			for(int i = 0; i <= 4; i++)ratings[i].setSelected(false);
			if(val != 0)
			{
				for(int i = 0; i < val; i++)
				{
					ratings[i].setSelected(true);
				}
			}
		}
	}
	
	public int getValue()
	{
		int val = 0;
		for(int i = 0; i <= 4; i++)
		{
			if(ratings[i].isSelected())val++;
		}
		return val;
	}
	
	public void selectProperStars(ToggleButton activator)
	{
		int id = 0;
		if(activator.equals(rating1))id = 0;
		else if(activator.equals(rating2))id = 1;
		else if(activator.equals(rating3))id = 2;
		else if(activator.equals(rating4))id = 3;
		else if(activator.equals(rating5))id = 4;

		if(activator.isSelected())
		{
			for(int i = 0; i <= id; i++)
			{
					ratings[i].setSelected(true);
			}
		}
		else
		{
			if(id == 0)
			{
				if(isAnyStarAboveIsSelected(id) == true)
				{
					for(int i = 0; i <= 4; i++)
					{
						ratings[i].setSelected(false);
					}
					ratings[0].setSelected(true);
				}
			}
			else
			{
				for(int i = id; i <= 4; i++)
				{
					ratings[i].setSelected(false);
					ratings[id].setSelected(true);
				}
			}
		}
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource().getClass() == ToggleButton.class)
		{
			selectProperStars((ToggleButton)event.getSource());
		}
	}
}
