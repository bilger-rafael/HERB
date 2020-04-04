package herb.client.ui.login;

import java.util.logging.Logger;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;

import java.util.Locale;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginView extends View<LoginModel> {
	
	private LoginModel model;
	private BorderPane root;
	private TextField nameField;
	private PasswordField pwField;
	private Button loginButton, createUserButton;
	private Region zero, one, two;	
	private VBox centerBox;
	private BorderPane bottomBox;
	private HBox topBox;	
	private Label nameLabel, pwLabel, connectedLabel;
	private Label message;
//	private FadeTransition transition;
//	private HBox messageBox;
	private MenuBar headMenu;
	private Menu menuLanguage;
	//private Menu menuFile, menuEdit, menuLanguage, menuHelp;
	
	public LoginView(Stage stage, LoginModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Login");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		Logger logger = sl.getLogger();		
		
		this.root = new BorderPane();

		// Top menu for language, TODO for passwordChange
		headMenu = new MenuBar();
		
		menuLanguage = new Menu();
		menuLanguage.getItems().addAll();

		
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
		
		headMenu.getMenus().addAll(menuLanguage);

		//roesti > Center VBox
		centerBox = new VBox();
		nameLabel = new Label();
		nameField = new TextField();
		pwLabel = new Label();
		pwField = new PasswordField();
		zero = new Region();
		one = new Region();
		two = new Region();
	
		nameLabel.setPrefSize(100, 20);
		pwLabel.setPrefSize(100, 20);
		zero.setPrefSize(80, 20);
		one.setPrefSize(80,20);
		two.setPrefSize(80,5);

		HBox un = new HBox();
		HBox pw = new HBox();		

		un.getChildren().addAll(one, nameLabel, nameField);
		pw.getChildren().addAll(zero, pwLabel, pwField);
		
		// roesti > Bottom HBox
		loginButton = new Button();
		createUserButton = new Button();
		loginButton.setPrefSize(200, 30);
		createUserButton.setPrefSize(200, 30);

		bottomBox = new BorderPane();
		bottomBox.setLeft(createUserButton);
		bottomBox.setRight(loginButton);

		loginButton.setAlignment(Pos.BASELINE_CENTER);
		createUserButton.setAlignment(Pos.BASELINE_CENTER);
		
		//roesti - fill CenterBox
		centerBox.getChildren().addAll(two, un, pw, bottomBox);
//		centerBox.getChildren().addAll(nameLabel, getNameField(), pwLabel, getPwField(), bottomBox);
		centerBox.setSpacing(10);
		
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
		
		
	// 	roesti - fill LoginWindow
		root.setTop(headMenu);
		root.setCenter(centerBox);
//		root.setBottom(messageBox);

		updateLabels();
		Scene scene = new Scene(root);	
//		scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());	
		return scene;
	}
	
	// roesti 
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		// language settings
		menuLanguage.setText(t.getString("program.menu.file.language"));
		
		// screen labels
		nameLabel.setText(t.getString("program.login.nameLabel"));
		pwLabel.setText(t.getString("program.login.pwLabel"));
		loginButton.setText(t.getString("program.login.loginButton"));
		createUserButton.setText(t.getString("program.login.createUserButton"));
		stage.setTitle(t.getString("program.name"));
		
	}

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
}
