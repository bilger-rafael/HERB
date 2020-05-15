package herb.client.ui.login;

import java.util.logging.Logger;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;

import java.util.Locale;

import javafx.geometry.Insets;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//Herren
public class LoginView extends View<LoginModel> {
	
	private LoginModel model;
	
	private BorderPane root, bottomBox;
	private StackPane messageStackPane;
	private VBox centerBox, labelBox, textFieldBox;
	private HBox fieldBox;
	
	private Region zero, one, two, three, four;	
	
	private TextField nameField;
	private PasswordField pwField;
	
	private Button loginButton, createUserButton;

	private Label nameLabel, pwLabel;
	private Label message;

	private MenuBar headMenu;
	private Menu menuLanguage;
	
	public LoginView(Stage stage, LoginModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		Logger logger = sl.getLogger();		
		this.root = new BorderPane();
		
		/**
		 * menubar
		 */
		headMenu = new MenuBar();
		menuLanguage = new Menu();
		menuLanguage.getItems().addAll();
		headMenu.getMenus().addAll(menuLanguage);
		// Top menu for language, TODO for passwordChange
		
		// link to Locale
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			this.menuLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateLabels();
			});
		}
		//panes
		// Herren - messages
		labelBox = new VBox();
		textFieldBox = new VBox();
		fieldBox = new HBox();
		bottomBox = new BorderPane();
		centerBox = new VBox();
		messageStackPane = new StackPane();
		
		//items
		nameLabel = new Label();
		nameField = new TextField();
		pwLabel = new Label();
		pwField = new PasswordField();
		message = new Label();
		
		zero = new Region();
		one = new Region();
		two = new Region();
		three = new Region();
		four = new Region();
		
		loginButton = new Button();
		createUserButton = new Button();

		//get children in panes
		labelBox.getChildren().addAll(nameLabel,zero,pwLabel);
		textFieldBox.getChildren().addAll(nameField,one, pwField);
		fieldBox.getChildren().addAll(four, labelBox,two,textFieldBox);
		messageStackPane.getChildren().add(message);
		centerBox.getChildren().addAll(fieldBox,messageStackPane);
		bottomBox.setLeft(createUserButton);
		bottomBox.setCenter(three);
		bottomBox.setRight(loginButton);

		//set size
		nameField.setPrefSize(130, 60);
		pwField.setPrefSize(130, 60);
		zero.setPrefHeight(40);
		one.setPrefHeight(48);
		two.setPrefWidth(20);
		three.setPrefWidth(20);
		four.setPrefWidth(20);
		labelBox.setPrefSize(130, 100);
		textFieldBox.setPrefSize(230, 90);
		loginButton.setPrefSize(220, 50);
		createUserButton.setPrefSize(220, 50);
		
		//spacing		
		bottomBox.setPadding(new Insets(20, 20, 15, 20));
		centerBox.setPadding(new Insets(35, 20, 0, 20));
		centerBox.setSpacing(20);
		centerBox.setSpacing(10);
		loginButton.setAlignment(Pos.BASELINE_CENTER);
		createUserButton.setAlignment(Pos.BASELINE_CENTER);
		centerBox.setAlignment(Pos.BASELINE_CENTER);
	
		//css id
		pwField.setId("textField");
		nameField.setId("textField");
		message.setId("message");
		message.setVisible(false);
		root.setId("background");
		

		root.setTop(headMenu);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);

		updateLabels();
		Scene scene = new Scene(root);		
		return scene;
	}
	
	// update items 
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		// language settings
		menuLanguage.setText(t.getString("program.menu.file.language"));
		
		// screen labels
		nameLabel.setText(t.getString("program.login.nameLabel"));
		pwLabel.setText(t.getString("program.login.pwLabel"));
		loginButton.setText(t.getString("program.login.loginButton"));
		createUserButton.setText(t.getString("program.login.createUserButton"));
		stage.setTitle(t.getString("program.menu.file.titel"));
		//if label is not visible, do not update
		if (message.getText()!="") {
			message.setText(t.getString("program.login.message"));
		}

	}
	//getter
	public Button getLoginButton() {
		return loginButton;
	}

	public Button getCreateUserButton() {
		return createUserButton;
}
	public TextField getNameField() {
		return nameField;
	}
	
	public PasswordField getPwField() {
		return pwField;
	}
	
	public void resetPasswordField() {
		pwField.setText("");
	}
	
	public void resetNameField() {
		nameField.setText("");
	}
	
	public Label getMessage() {
		return message;
	}
	
	public void resetMessageLabel() {
		message.setText("");
	}

	//To show the error message in GUI if Login fails
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.login.message"));
	}
}
