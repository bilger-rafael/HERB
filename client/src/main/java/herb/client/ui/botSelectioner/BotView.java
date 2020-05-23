package herb.client.ui.botSelectioner;

import java.io.InputStream;
import java.util.Locale;

import javax.imageio.stream.ImageInputStream;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Herren
public class BotView extends View<BotModel> {

	private BorderPane root;
	private VBox labelBox, radioButtonBox;
	private HBox centerBox, bottomBox, topBox;
	private StackPane messageStackPane;

	private RadioButton easyRadioButton, heavyRadioButton;
	private Button cancelButton, okButton;
	private Label easyLabel, heavyLabel, info, message;

	private Region zero, one, two, three, four;

	private ToggleGroup toggleGroup;

	private MenuBar menuBar;
	private Menu menuLanguage;

	public BotView(Stage stage, BotModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();

		this.root = new BorderPane();

		menuBar = new MenuBar();
		menuLanguage = new Menu();
		menuBar.getMenus().add(menuLanguage);

		/**
		 * set local for language
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
		 * menu, labels, textfield, regions, buttons, radiobutton, toogle group
		 */
		message = new Label();
		easyLabel = new Label();
		heavyLabel = new Label();
		info = new Label();
		zero = new Region();
		one = new Region();
		two = new Region();
		three = new Region();
		four = new Region();
		cancelButton = new Button();
		okButton = new Button();
		easyRadioButton = new RadioButton();
		heavyRadioButton = new RadioButton();
		toggleGroup = new ToggleGroup();
		/**
		 * panes
		 */
		topBox = new HBox();
		centerBox = new HBox();
		labelBox = new VBox();
		radioButtonBox = new VBox();
		messageStackPane = new StackPane();
		bottomBox = new HBox();

		/**
		 * size
		 */

		zero.setMinWidth(20);
		one.setPrefHeight(10);
		two.setPrefHeight(20);
		three.setPrefWidth(35);
		four.setPrefHeight(50);
		cancelButton.setPrefSize(220, 50);
		okButton.setPrefSize(220, 50);

		/**
		 * spacing and padding
		 */
		bottomBox.setPadding(new Insets(5, 50, 15, 50));
		radioButtonBox.setPadding(new Insets(53, 50, 15, 50));
		messageStackPane.setPadding(new Insets(5, 0, 0, 0));
		centerBox.setPadding(new Insets(35, 50, 15, 50));
		centerBox.setSpacing(10);
		bottomBox.setSpacing(10);

		/**
		 * position
		 */
		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		okButton.setAlignment(Pos.BASELINE_CENTER);
		easyRadioButton.setAlignment(Pos.CENTER_RIGHT);
		heavyRadioButton.setAlignment(Pos.CENTER_RIGHT);

		// toggle group
		easyRadioButton.setToggleGroup(toggleGroup);
		heavyRadioButton.setToggleGroup(toggleGroup);
//		easyRadioButton.setSelected(true);

		/*
		 * get children
		 */
		topBox.getChildren().addAll(info, four);
		messageStackPane.getChildren().add(message);
		labelBox.getChildren().addAll(topBox, easyLabel, one, heavyLabel, messageStackPane);
		radioButtonBox.getChildren().addAll(easyRadioButton, two, heavyRadioButton);
		centerBox.getChildren().addAll(labelBox, three, radioButtonBox);
		bottomBox.getChildren().addAll(okButton, zero, cancelButton);

		/**
		 * css
		 */
		message.setId("message");
		message.setVisible(false);

		root.setId("background");
		root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);

		updateLabels();
		Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		return scene;
	}

	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		easyLabel.setText(t.getString("program.botSelectioner.easyLabel"));
		heavyLabel.setText(t.getString("program.botSelectioner.heavyLabel"));
		okButton.setText(t.getString("program.botSelectioner.okButton"));
		cancelButton.setText(t.getString("program.botSelectioner.cancelButton"));
		info.setText(t.getString("program.botSelectioner.info"));
		message.setText(t.getString("program.botSelectioner.message"));
		menuLanguage.setText(t.getString("program.menu.file.language"));
		stage.setTitle(t.getString("program.botSelectioner.titel"));
		if (message.getText() != "") {
			message.setText(t.getString("program.botSelectioner.message"));
		}
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getOkButton() {
		return okButton;
	}

	public RadioButton getEasyRadioButton() {
		return easyRadioButton;
	}

	public RadioButton getHeavyRadioButton() {
		return heavyRadioButton;
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	public Label getMessage() {
		return message;
	}

	/**
	 * To show the error message in GUI if Login fails
	 */
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.botSelectioner.message"));
	}

}
