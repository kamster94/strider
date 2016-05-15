package desktopGui;

import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class ScreensController extends StackPane
{
	private HashMap<String, Node> screens = new HashMap<>(); 
	
	public ScreensController() 
	{
		super();
	}

	public void addScreen(String name, Node screen) 
	{
		screens.put(name, screen);
	} 
	
	public boolean loadScreen(String name, String resource) 
	{
		try 
		{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
			myScreenControler.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		}
		catch(Exception e) 
		{
	       e.printStackTrace();
	       return false;
		}
	} 
	
	public boolean setScreen(final String name) 
	{
		if(screens.get(name) != null) 
		{
			//Is there is more than one screen
			if(!getChildren().isEmpty())
			{
				getChildren().remove(0); 
				
				FadeTransition ft = new FadeTransition(Duration.millis(500), screens.get(name));
				ft.setFromValue(0.0);
				ft.setToValue(1.0);
				ft.play();
				
				getChildren().add(0, screens.get(name));
				getScene().getWindow().sizeToScene();
			} 
			else 
			{
				
				FadeTransition ft = new FadeTransition(Duration.millis(2000), screens.get(name));
				ft.setFromValue(0.0);
				ft.setToValue(1.0);
				ft.play();
				getChildren().add(screens.get(name));
				//getScene().getWindow().sizeToScene();
			}
			return true;
		} 
		else 
		{
			System.out.println("screen hasn't been loaded!\n");
			return false;
		} 
	}
	
	public boolean unloadScreen(String name) 
	{
		if(screens.remove(name) == null) 
		{
			System.out.println("Screen didn't exist");
			return false;
		} 
		else 
		{
			return true;
		}
	} 
}
