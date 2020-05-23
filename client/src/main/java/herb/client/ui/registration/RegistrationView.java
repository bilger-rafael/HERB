
package herb.client.ui.registration;

import javafx.stage.Stage;

import java.util.Locale;
import java.util.logging.Logger;

import herb.client.utils.Translator;
import herb.client.ui.core.View;
import herb.client.ui.login.LoginModel;
import herb.client.utils.ServiceLocator;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//Herren
public class RegistrationView extends View<RegistrationModel> {

	private RegistrationModel model;

	private BorderPane root, bottomBox;
	private StackPane messageStackPane;
	private VBox centerBox, labelBox, textFieldBox;
	private HBox fieldBox;

	private Region zero, one, two, three, four;

	private TextField nameField;
	private PasswordField pwField;

	private Button registrationButton, cancelButton;

	private Label nameLabel, pwLabel;
	private Label message;

	private MenuBar headMenu;
	private Menu menuLanguage;

	public RegistrationView(Stage stage, RegistrationModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		Logger logger = sl.getLogger();
		this.root = new BorderPane();

		/**
		 * menu
		 */
		headMenu = new MenuBar();
		menuLanguage = new Menu();
		headMenu.getMenus().addAll(menuLanguage);

		/**
		 * link to Locale
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
		 * items
		 */
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
		registrationButton = new Button();
		cancelButton = new Button();

		// panes
		labelBox = new VBox();
		textFieldBox = new VBox();
		fieldBox = new HBox();
		messageStackPane = new StackPane();
		centerBox = new VBox();
		bottomBox = new BorderPane();

		// get children
		labelBox.getChildren().addAll(nameLabel, zero, pwLabel);
		textFieldBox.getChildren().addAll(nameField, one, pwField);
		fieldBox.getChildren().addAll(four, labelBox, two, textFieldBox);
		messageStackPane.getChildren().add(message);
		centerBox.getChildren().addAll(fieldBox, messageStackPane);
		bottomBox.setLeft(registrationButton);
		bottomBox.setCenter(three);
		bottomBox.setRight(cancelButton);

		// size
		nameField.setPrefSize(130, 60);
		pwField.setPrefSize(130, 60);
		zero.setPrefHeight(40);
		one.setPrefHeight(48);
		two.setPrefWidth(20);
		three.setPrefWidth(20);
		four.setPrefWidth(20);
		labelBox.setPrefSize(130, 100);
		textFieldBox.setPrefSize(230, 90);
		registrationButton.setPrefSize(220, 50);
		cancelButton.setPrefSize(220, 50);

		// spacing and padding
		bottomBox.setPadding(new Insets(20, 50, 15, 50));
		centerBox.setPadding(new Insets(35, 20, 0, 20));
		centerBox.setSpacing(10);
		centerBox.setSpacing(10);

		// position
		registrationButton.setAlignment(Pos.BASELINE_CENTER);
		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		centerBox.setAlignment(Pos.BASELINE_CENTER);

		// css
		pwField.setId("textField");
		nameField.setId("textField");
		message.setId("message");
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
		nameLabel.setText(t.getString("program.registration.nameLabel"));
		pwLabel.setText(t.getString("program.registration.pwLabel"));
		registrationButton.setText(t.getString("program.registration.registrationButton"));
		cancelButton.setText(t.getString("program.registration.cancelButton"));
		stage.setTitle(t.getString("program.registration.stage"));
		// if label is not visible, do not update
		if (message.getText() != "") {
			message.setText(t.getString("program.registration.message"));
		}

	}

	/**
	 * getter
	 */
	public Button getRegistrationButton() {
		return registrationButton;
	}

	public Button getCancelButton() {
		return cancelButton;
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

	/**
	 * To show the error message in GUI if Login fails
	 */
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.registration.message"));
	}
}
