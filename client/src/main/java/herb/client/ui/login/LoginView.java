package herb.client.ui.login;

import javafx.stage.Stage;

import java.util.Locale;
import java.util.logging.Logger;

import herb.client.ui.core.View;
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

public class LoginView extends View<LoginModel> {
	
	private LoginModel model;
	private BorderPane root;
	private TextField nameField;
	private PasswordField pwField;
	private Button loginButton, createUserButton;
	private Region zero;
	private VBox leftBox;
	private VBox rightBox;
	
	private VBox centerBox;
	private BorderPane bottomBox;
	
	private Label nameLabel, pwLabel, connectedLabel;
	private Label message;
//	private FadeTransition transition;
//	private HBox messageBox;
	
	public LoginView(Stage stage, LoginModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass");
		//ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		
		/*
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		*/
		
		this.root = new BorderPane();

		// Top Menuleiste auch so machen?

		/*
		// Locale setzen
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			this.menuLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}
		 */

		// Center
		centerBox = new VBox();
		nameLabel = new Label("Nick-Name");
		nameField = new TextField();
		pwLabel = new Label("Passwort");
		pwField = new PasswordField();
		
		// Left and Right
		zero = new Region();
	 leftBox = new VBox();
	 rightBox = new VBox();

		// Bottom HBox
		loginButton = new Button();
		createUserButton = new Button();

		bottomBox = new BorderPane();

		bottomBox.setLeft(createUserButton);
		bottomBox.setRight(loginButton);

		centerBox.setSpacing(10);

		loginButton.setAlignment(Pos.BASELINE_CENTER);
		createUserButton.setAlignment(Pos.BASELINE_CENTER);

//		centerBox.getChildren().addAll(nameLabel, getNameField(), pwLabel, getPwField(), bottomBox);
//		leftBox.add(zero);
//		rightBox.add(zero);

//		messageBox = new HBox();
		connectedLabel = new Label();
		connectedLabel.setId("connectedLabel");
		connectedLabel.setOpacity(0);
//		messageBox.getChildren().add(connectedLabel);

		
		//Nachricht, falls Login fehlgeschlagen ist
		message = new Label("");
		message.setId("message");
		message.setOpacity(0);
//		messageBox.getChildren().add(message);
		
		
		// Borderpane anordnen
	//	root.setTop(headMenu);
		root.setCenter(centerBox);
//		root.setBottom(messageBox);

//		updateTexts();

		Scene scene = new Scene(root);
		
		
//		scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		
		
		return scene;
	}

}
