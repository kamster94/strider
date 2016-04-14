package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import countryWarnings.AutoCompleteComboBoxListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainApplicationWindowController implements Initializable, EventHandler<ActionEvent>
{
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

	@FXML
	private Label mwailabelpanetype;

	@FXML
	private Label mwailabelcountry;

	@FXML
	private ImageView mwaiimageviewmszlogo;

	@FXML
	private ScrollPane mwaiscrollpanemsz;

	@FXML
	private Label mwailabelcity;

	@FXML
	private Label mwailabelcitydoesntexist;

	@FXML
	private ImageView mwaiimageviewmediawikilogo;

	@FXML
	private ScrollPane mwaiscrollpanemediawiki;

	@FXML
	private Button mwaibuttonok;

	@FXML
	private Label mwailabelcurrencyname;

	@FXML
	private Label mwailabelcurrencystate;

    @FXML
    private ScrollPane mwpaneadditionalinformation;
    
    @FXML
    private ScrollPane mwpanecreatenewtravel;
    
    @FXML
    private VBox vboxleftside;
    
    @FXML
    private VBox vboxrightside;
    
    @FXML
    private ComboBox<?> countryBox;
    
    @FXML
    private ComboBox<?> cityBox;
	
    @FXML
    private BorderPane mainborderpane;
    
    @FXML
    private VBox newtravelwizard_2;

    @FXML
    private VBox newtravelwizard_1;

    @FXML
    private VBox newtravelwizard_3;
    
    @FXML
    private Button newtravelwizard_1_createnewtravel;

    @FXML
    private Button newtravelwizard_2_cancel;

    @FXML
    private Button newtravelwizard_2_next;
    
    @FXML
    private Button newtravelwizard_3_back;
    
	final ToggleGroup tg = new ToggleGroup();
	
	final Image mszlogoimage = new Image("desktopGui/textures/ta_msz.png");
	final Image mwikilogoimage = new Image("desktopGui/textures/ta_mwiki.png");
	
	private static ObservableList countryData;
	private static ObservableList cityData;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		mwtogglebuttoncreatenewtravel.setOnAction(this);
		mwtogglebuttonviewcurrenttravel.setOnAction(this);
		mwtogglebuttonadditionalinformation.setOnAction(this);
		mwtogglebuttontravelhistory.setOnAction(this);
		mwtogglebuttonreviewhotels.setOnAction(this);
		mwbuttonexit.setOnAction(this);

		newtravelwizard_1_createnewtravel.setOnAction(this);
		newtravelwizard_2_cancel.setOnAction(this);
		newtravelwizard_2_next.setOnAction(this);
		newtravelwizard_3_back.setOnAction(this);
		
		mwtogglebuttoncreatenewtravel.setToggleGroup(tg);
		mwtogglebuttonviewcurrenttravel.setToggleGroup(tg);
		mwtogglebuttonadditionalinformation.setToggleGroup(tg);
		mwtogglebuttontravelhistory.setToggleGroup(tg);
		mwtogglebuttonreviewhotels.setToggleGroup(tg);
		
		mwaiimageviewmszlogo.setImage(mszlogoimage);
		mwaiimageviewmediawikilogo.setImage(mwikilogoimage);
		
		mwpaneadditionalinformation.setDisable(true);
		mwpaneadditionalinformation.setVisible(false);
		
		mwpanecreatenewtravel.setDisable(true);
		mwpanecreatenewtravel.setVisible(false);
		
		countryBox.setItems(countryData); 
	}
	
	//TODO: Ogarn¹æ jak dodaæ te pierdolone uzupe³niajki ¿eby javafx nie sra³o errorami
	/*
	public void doshit()
	{
		new AutoCompleteComboBoxListener(countryBox);
		new AutoCompleteComboBoxListener(cityBox);
	}
	 */
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == mwbuttonexit)
		{
			MainApplicationWindow.closeWindow();
		}
		else if(arg0.getSource() == newtravelwizard_1_createnewtravel)
		{
			newtravelwizard_1.setDisable(true);
			newtravelwizard_1.setVisible(false);
			
			newtravelwizard_2.setDisable(false);
			newtravelwizard_2.setVisible(true);
			
			
		}
		else if(arg0.getSource() == newtravelwizard_2_cancel)
		{
			newtravelwizard_1.setDisable(false);
			newtravelwizard_1.setVisible(true);
			
			newtravelwizard_2.setDisable(true);
			newtravelwizard_2.setVisible(false);
		}
		else if(arg0.getSource() == newtravelwizard_2_next)
		{
			newtravelwizard_2.setDisable(true);
			newtravelwizard_2.setVisible(false);
			
			newtravelwizard_3.setDisable(false);
			newtravelwizard_3.setVisible(true);
		}
		else if(arg0.getSource() == newtravelwizard_3_back)
		{			
			newtravelwizard_3.setDisable(true);
			newtravelwizard_3.setVisible(false);
			
			newtravelwizard_2.setDisable(false);
			newtravelwizard_2.setVisible(true);
		}
		else if(arg0.getSource() == mwtogglebuttoncreatenewtravel ||
				arg0.getSource() == mwtogglebuttonviewcurrenttravel ||
				arg0.getSource() == mwtogglebuttonadditionalinformation ||
				arg0.getSource() == mwtogglebuttontravelhistory ||
				arg0.getSource() == mwtogglebuttonreviewhotels)
		{
			if(mwtogglebuttonadditionalinformation.isSelected())
			{
				mwpaneadditionalinformation.setDisable(false);
				mwpaneadditionalinformation.setVisible(true);
			}
			else
			{
				mwpaneadditionalinformation.setDisable(true);
				mwpaneadditionalinformation.setVisible(false);
			}
			
			if(mwtogglebuttoncreatenewtravel.isSelected())
			{
				mwpanecreatenewtravel.setDisable(false);
				mwpanecreatenewtravel.setVisible(true);
				newtravelwizard_1.setDisable(false);
				newtravelwizard_1.setVisible(true);
			}
			else
			{
				mwpanecreatenewtravel.setDisable(true);
				mwpanecreatenewtravel.setVisible(false);
				
				newtravelwizard_1.setDisable(true);
				newtravelwizard_1.setVisible(false);
				
				newtravelwizard_2.setDisable(true);
				newtravelwizard_2.setVisible(false);
				
				newtravelwizard_3.setDisable(true);
				newtravelwizard_3.setVisible(false);
			}
		}
	}
}
