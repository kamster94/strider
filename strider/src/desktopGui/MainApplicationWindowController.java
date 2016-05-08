package desktopGui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import countryWarnings.AutoCompleteComboBoxListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainApplicationWindowController implements Initializable, EventHandler<ActionEvent> 
{
    @FXML
    private BorderPane mainborderpane;

    @FXML
    private ScrollPane changeme;
    
    @FXML
    private Label mwlabelwelcomemessage;

    @FXML
    private Label mwlabelaccountstatus;

    @FXML
    private Label mwlabelversion;

    @FXML
    private ToggleButton mwtogglebuttoncreatenewtravel;

    @FXML
    private ToggleButton mwtogglebuttonviewcurrenttravel;

    @FXML
    private ToggleButton mwtogglebuttonadditionalinformation;

    @FXML
    private ToggleButton mwtogglebuttontravelhistory;

    @FXML
    private ToggleButton mwtogglebuttonreviewhotels;

    @FXML
    private Button mwbuttonexit;
    
    final ToggleGroup tg = new ToggleGroup();
    

	public static ObservableList<String> countryData;
	public static ObservableList<String> cityData;
	public static ComboBox<String> cityBox;
	public static ComboBox<String> countryBox;
	
	public static AdditionalInformationsController addinfocontroller;
	private VBox AdditionalInformationVBox;
	public  MainApplicationWindow myparent;
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		mwtogglebuttoncreatenewtravel.setOnAction(this);
		mwtogglebuttonviewcurrenttravel.setOnAction(this);
		mwtogglebuttonadditionalinformation.setOnAction(this);
		mwtogglebuttontravelhistory.setOnAction(this);
		mwtogglebuttonreviewhotels.setOnAction(this);
		mwbuttonexit.setOnAction(this);

		mwtogglebuttoncreatenewtravel.setToggleGroup(tg);
		mwtogglebuttonviewcurrenttravel.setToggleGroup(tg);
		mwtogglebuttonadditionalinformation.setToggleGroup(tg);
		mwtogglebuttontravelhistory.setToggleGroup(tg);
		mwtogglebuttonreviewhotels.setToggleGroup(tg);	
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/fxml_mainwindow_additionalinformation.fxml"));
		try 
		{
			AdditionalInformationVBox = (VBox)fxmlLoader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		addinfocontroller = (AdditionalInformationsController) fxmlLoader.getController();
	}

	public VBox getVboxCitybox()
	{
		return addinfocontroller.vboxcitybox;
	}
	
	public VBox getVboxCountrybox()
	{
		return addinfocontroller.vboxcountrybox;
	}
	
    
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == mwbuttonexit)
		{
			MainApplicationWindow.closeWindow();
		}
		else if(arg0.getSource() == mwtogglebuttoncreatenewtravel ||
				arg0.getSource() == mwtogglebuttonviewcurrenttravel ||
				arg0.getSource() == mwtogglebuttonadditionalinformation ||
				arg0.getSource() == mwtogglebuttontravelhistory ||
				arg0.getSource() == mwtogglebuttonreviewhotels)
		{
			changeme.setContent(null);
			
			if(mwtogglebuttonadditionalinformation.isSelected())
			{
				AdditionalInformationVBox.setFillWidth(true);
				changeme.setContent(AdditionalInformationVBox);
			}
		}
	}
}
