package desktopGui;

import java.net.URL;
import java.util.ResourceBundle;
import dbHandlers.DatabaseHandlerLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerSplashScreen implements Initializable, ClearableScreen, ControlledScreen, EventHandler<ActionEvent>
{
    @FXML
    private ImageView imageviewlogo;
    @FXML
    private ImageView imageviewlogotitle;
    @FXML
    private TextField textfieldemail;
    @FXML
    private PasswordField passwordfieldpassword;
    @FXML
    private Button buttonlogin;
    @FXML
    private Button buttoncreatenewaccount;
    @FXML
    private Label labelkektext;
    
	private ScreensController myController;
	private final Image imglogotext = new Image("desktopGui/textures/ta_title.png");
	private final Image imglogopalms = new Image("desktopGui/textures/ta_palms.png");
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		textfieldemail.setText("adriankepa@exception.com");
		passwordfieldpassword.setText("dankmemes");
		imageviewlogo.setImage(imglogopalms);
		imageviewlogotitle.setImage(imglogotext);
		buttonlogin.setOnAction(this);
		buttoncreatenewaccount.setOnAction(this);
		textfieldemail.setPromptText("default@email.com");
		passwordfieldpassword.setPromptText("Password");
		labelkektext.setText("\"This is the best thing since sliced bread.\" - George W. Bush");
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) 
	{
		myController = screenParent; 
	}
	
	@Override
	public void handle(ActionEvent arg0) 
	{
		if(arg0.getSource() == buttonlogin)
		{
			textfieldemail.getText();
			passwordfieldpassword.getText();
			int dataverification = DatabaseHandlerLogin.getInstance().verifyDataValidity(textfieldemail.getText(), passwordfieldpassword.getText());
			
			if(dataverification == 0)
			{
				boolean userexists = DatabaseHandlerLogin.getInstance().checkEmailAvailability(textfieldemail.getText());
				if(userexists == true)
				{
					int loginstatus = DatabaseHandlerLogin.getInstance().loginUser(textfieldemail.getText(), passwordfieldpassword.getText());
					if(loginstatus == 1)
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Zaloguj");
						alert.setHeaderText(null);
						alert.setContentText("Zalogowano pomyœlnie.");
						alert.showAndWait();
						
						clearComponents();
						myController.loadScreenAndSet(WindowMain.MAIN_SCREEN, WindowMain.MAIN_SCREEN_FXML);
					}
					else if(loginstatus == 0)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Zaloguj");
						alert.setHeaderText(null);
						alert.setContentText("Nie istnieje u¿ytkownik dla podanego adresu e-mail.");
						alert.showAndWait();
					}
					else if(loginstatus == -1)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Zaloguj");
						alert.setHeaderText(null);
						alert.setContentText("Podano z³e has³o dla u¿ytkownika " + textfieldemail.getText());
						alert.showAndWait();
					}
				}
				else
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Zaloguj");
					alert.setHeaderText(null);
					alert.setContentText("Nie istnieje u¿ytkownik dla podanego adresu e-mail.");
					alert.showAndWait();
				}
			}
			else if(dataverification == 1)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Zaloguj");
				alert.setHeaderText(null);
				alert.setContentText("Niepoprawny adres e-mail.");
				alert.showAndWait();
			}
			else if(dataverification == 2)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Zaloguj");
				alert.setHeaderText(null);
				alert.setContentText("D³ugoœæ has³a musi zawieraæ siê w przedziale od 6 do 32 znaków.");
				alert.showAndWait();
			}
		}
		else if(arg0.getSource() == buttoncreatenewaccount)
		{
			clearComponents();
			myController.loadScreenAndSet(WindowMain.CREATEACCOUNT_SCREEN, WindowMain.CREATEACCOUNT_SCREEN_FXML);
		}
	}

	@Override
	public void clearComponents() 
	{
		textfieldemail.setText("");
		passwordfieldpassword.setText("");
	}
}
