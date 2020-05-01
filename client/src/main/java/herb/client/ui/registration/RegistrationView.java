
package herb.client.ui.registration;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Locale;
import java.util.logging.Logger;

import herb.client.utils.Translator;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RegistrationView extends View<RegistrationModel> {
	//Herren
	private RegistrationModel model;
	
	private BorderPane root, bottomBox;
	private VBox centerBox;
	private HBox topBox;
	private HBox messageBox;
	private Region zero, one, two;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	private Button cancelButton, registrationButton;

	private Label nameLabel, passwordLabel, connectedLabel;
	private Label message;
	private TextField nameTextField;
	private PasswordField passwordField;
	
//	private FadeTransition transition;

	public RegistrationView(Stage stage, RegistrationModel model) {
		super(stage, model);
		//Herren
		ServiceLocator.getInstance().getLogger().info("Registration window initialized");
		

	}

	@Override
	protected Scene create_GUI() {
		//Herren
		ServiceLocator sl = ServiceLocator.getInstance();
	    Logger logger = sl.getLogger();
	  
		this.root = new BorderPane();
	    /**
	     * Top/menu
	     * Herren
	     */
	    menuBar = new MenuBar();
	    menuLanguage = new Menu();
	    menuLanguage.getItems().addAll();
	    menuBar.getMenus().add(menuLanguage);
	    /**
	     * set local
	     * Herren
	     */
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			this.menuLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateLabels();
			});
		}
	    /**
	     * Center with centerBox
	     * Herren
	     */
	    centerBox = new VBox();
	    nameLabel = new Label();
	    passwordLabel = new Label();
	    nameTextField = new TextField();
	    passwordField = new PasswordField();
	    nameTextField.setId("textField");
	    passwordField.setId("textField");
	    /**
	     * spacing
	     */
		zero = new Region();
		one = new Region();
		two = new Region();
		zero.setPrefSize(80, 20);
		one.setPrefSize(80,20);
		two.setPrefSize(80,5);
	    /**
	     * Bottom with bottomBox
	     * Herren
	     */
	    bottomBox = new BorderPane();
	    cancelButton = new Button();
	    registrationButton = new Button();
	    /**
	     * add buttons in bottomBox
	     * Herren
	     */
	    bottomBox.setRight(cancelButton);
	    bottomBox.setLeft(registrationButton);	
	    /**
	     * place buttons in bottomBox
	     * Herren
	     */
	    cancelButton.setAlignment(Pos.BASELINE_CENTER);
	    registrationButton.setAlignment(Pos.BASELINE_CENTER);
	    
	    /**
	     * set witdh
	     * Herren
	     */
		nameLabel.setPrefSize(100, 20);
		passwordLabel.setPrefSize(100, 20);
	    nameTextField.setPrefSize(200, 30);
	    passwordField.setPrefSize(200, 30);
	    cancelButton.setPrefSize(200, 30);
	    registrationButton.setPrefSize(200, 30);
	    
	    /**
	     * hbox
	     */
		HBox username = new HBox();
		HBox password = new HBox();	
		
		username.getChildren().addAll(one, nameLabel, nameTextField);
		password.getChildren().addAll(zero, passwordLabel, passwordField);
		
	    /**
	     * add items in centerBox
	     * Herren
	     */
	    centerBox.getChildren().addAll(two, username, password, bottomBox);
	    /**
	     * space for each item in the vbox
	     * Herren
	     */
	    centerBox.setSpacing(10);
		
	    /**
	     * messages
	     */
		messageBox = new HBox();
		connectedLabel = new Label();
//		connectedLabel.setId("connectedLabel");
//		connectedLabel.setOpacity(0);
		messageBox.getChildren().add(connectedLabel);	
		// message for failed login
		message = new Label();
		message.setPrefHeight(40);
//		message.setId("message");
//		message.setOpacity(0);
		messageBox.getChildren().add(message);
	    
	    /**
	     * if registration/login fails
	     * Herren
	     */
//	    errorLabel = new Label();
//		errorLabel.setId("errorLabel");
//		errorLabel.setOpacity(0);
	    
		root.setId("background");
		root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(messageBox);
        
        updateLabels();     
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());	
		return scene;
	}
	
	/**
	 * names given by translator
	 * Herren
	 */
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		cancelButton.setText(t.getString("program.registration.cancelButton"));
		registrationButton.setText(t.getString("program.registration.registrationButton"));
		
		nameLabel.setText(t.getString("program.registration.nameLabel"));
		passwordLabel.setText(t.getString("program.registration.passwordLabel"));
//		errorLabel.setText(t.getString("program.registration.errorLabel"));
		
		menuLanguage.setText(t.getString("program.menu.file.language"));
		
        stage.setTitle(t.getString("program.registration.stage"));
	}
		
	/**
	 * 
	 */
	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getRegistrationButton() {
		return registrationButton;
	}
	
	public TextField getNameTextField() {
		return nameTextField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}
	
//	public void showError() {
//		Translator t = ServiceLocator.getInstance().getTranslator();
//		errorLabel.setText(t.getString("Programm.newUser.errorLabel"));
//		
//		if( transition == null ) {
//			transition = new FadeTransition(Duration.millis(2000), errorLabel);
//			transition.setFromValue(1.0);
//			transition.setToValue(0);
//			transition.setDelay(Duration.millis(2000));
//		}
//		
//		transition.stop();
//		errorLabel.setOpacity(1);
//		transition.play();
//	}
	public void resetPasswordField() {
		passwordField.setText("");
	}
	
	public void resetNameField() {
		nameTextField.setText("");
	}
	
}

