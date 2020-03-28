package herb.client.ui.registration;

import javafx.stage.Stage;

import java.util.logging.Logger;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RegistrationView extends View<RegistrationModel> {
	//Herren
	private RegistrationModel model;
	
	private BorderPane root;
	private BorderPane bottomBox;
	private VBox centerBox;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	private Button cancelButton, registrationButton;
	private Label nameLabel, passwordLabel, errorLabel;
	private TextField nameTextField;
	private PasswordField passwordField;
	
	
	public RegistrationView(Stage stage, RegistrationModel model) {
		super(stage, model);
		//Herren
		stage.setTitle("HERB-Jass > Registration");
//		ServiceLocator.getInstance().getLogger().info("Registration window initialized");
		

	}

	@Override
	protected Scene create_GUI() {
		//Herren
//		ServiceLocator sl = ServiceLocator.getInstance();
//	    Logger logger = sl.getLogger();
	    

		this.root = new BorderPane();
	    /**
	     * Top/menu
	     */
	    menuBar = new MenuBar();
	    menuLanguage = new Menu();
	    menuLanguage.getItems().addAll();
	    menuBar.getMenus().add(menuLanguage);
	    
	    /**
	     * Center with centerBox
	     */
	    centerBox = new VBox();
	    nameLabel = new Label("Benutzername");
	    passwordLabel = new Label("Passwort");
	    nameTextField = new TextField();
	    passwordField = new PasswordField();
	    /**
	     * space for each item in the vbox
	     */
	    centerBox.setSpacing(10);
	    
	    /**
	     * Bottom with bottomBox
	     */
	    bottomBox = new BorderPane();
	    cancelButton = new Button("Abbrechen");
	    registrationButton = new Button("Registrieren");
	    
	    /**
	     * add buttons in bottomBox
	     */
	    bottomBox.setLeft(cancelButton);
	    bottomBox.setRight(registrationButton);
	    
	    /**
	     * place buttons in bottomBox
	     */
	    cancelButton.setAlignment(Pos.BASELINE_CENTER);
	    registrationButton.setAlignment(Pos.BASELINE_CENTER);
	    
	    /**
	     * set witdh 
	     */
	    nameTextField.setPrefWidth(250);
	    passwordField.setPrefWidth(250);
	    cancelButton.setPrefWidth(100);
	    registrationButton.setPrefWidth(100);
	    /**
	     * add items in centerBox
	     */
	    centerBox.getChildren().addAll(nameLabel, nameTextField, passwordLabel, passwordField, registrationButton, cancelButton);
	    
	    /**
	     * if registration/login fails
	     */
	    errorLabel = new Label("Error");
		errorLabel.setOpacity(0);
	    
		root.setCenter(centerBox);
		Scene scene = new Scene(root);
		return scene;
	}

}
