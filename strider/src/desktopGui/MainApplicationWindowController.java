package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	private ChoiceBox<?> mwaichoiceboxcountry;

	@FXML
	private ImageView mwaiimageviewmszlogo;

	@FXML
	private ScrollPane mwaiscrollpanemsz;

	@FXML
	private Label mwailabelcity;

	@FXML
	private TextField mwaitextfieldcity;

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
	
	final ToggleGroup tg = new ToggleGroup();
	
	final Image mszlogoimage = new Image("desktopGui/textures/ta_msz.png");
	final Image mwikilogoimage = new Image("desktopGui/textures/ta_mwiki.png");
	
	
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
		
		mwaiimageviewmszlogo.setImage(mszlogoimage);
		mwaiimageviewmediawikilogo.setImage(mwikilogoimage);
		
		mwpaneadditionalinformation.setDisable(true);
		mwpaneadditionalinformation.setVisible(false);
		
	}

	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == mwbuttonexit)
		{
			MainApplicationWindow.closeWindow();
		}
		else if(arg0.getSource() == mwtogglebuttonadditionalinformation)
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
		
		}
	}
}
