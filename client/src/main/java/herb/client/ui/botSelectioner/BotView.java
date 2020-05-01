package herb.client.ui.botSelectioner;

import java.util.Locale;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BotView extends View<BotModel>{
	
	private BorderPane root, bottomBox;
	private VBox vbox;
	private HBox easyHbox, heavyHbox;
	
	private RadioButton easyRadioButton, heavyRadioButton;
	private Button cancelButton, okButton;
	private Label easyLabel, heavyLabel;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	public BotView(Stage stage, BotModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Bot Selectioner: ");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		this.root = new BorderPane();
		
		/**
		 * Top/menu
		 * Herren
		 */
		menuBar = new MenuBar();
		menuLanguage = new Menu("Sprache");
	    menuBar.getMenus().add(menuLanguage);
		
	    /**
	     * set local
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
		 * Buttons with bottomBox
		 * Herren
		 */
		bottomBox = new BorderPane();
		cancelButton = new Button("cancel");
		okButton = new Button("ok");
		
		bottomBox.setRight(cancelButton);
		bottomBox.setLeft(okButton);
		
		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		okButton.setAlignment(Pos.BASELINE_CENTER);
		
		/*
		 * CenterBox
		 */
		easyHbox = new HBox();
		heavyHbox = new HBox();
		
		easyLabel = new Label("Easy Bot");
		heavyLabel = new Label("Heavy Bot");
		easyRadioButton = new RadioButton();
		heavyRadioButton = new RadioButton();
		
		easyRadioButton.setAlignment(Pos.CENTER_RIGHT);
		heavyRadioButton.setAlignment(Pos.CENTER_RIGHT);
		
		easyHbox.getChildren().addAll(easyLabel, easyRadioButton);
		heavyHbox.getChildren().addAll(heavyLabel, heavyRadioButton);
		
		vbox = new VBox();

		vbox.getChildren().addAll(easyHbox, heavyHbox);
		
		root.setId("background");
		root.setTop(menuBar);
		root.setCenter(vbox);
		root.setBottom(bottomBox);
		
        updateLabels();  
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());		
		return scene;
	}
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}
	
	public Button getOkButton() {
		return okButton;
	}

}
