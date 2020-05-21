package herb.client.ui.lobbyCreater;

import java.util.Locale;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//Herren
public class LobbyCreaterView extends View<LobbyCreaterModel> {
	
	private BorderPane root; 
	private HBox bottomBox, labelBox, textFieldBox;
	private StackPane messageStackPane;
	private VBox centerBox, topBox;
	
	private Button okButton, cancelButton;
	private TextField text;
	private Label message, info;
	
	private Region zero;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	public LobbyCreaterView(Stage stage, LobbyCreaterModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("Application LobbyCreaterView initialized");
	}
	
	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		this.root = new BorderPane();
		
		/**
		 * menu
		 */
		menuBar = new MenuBar();
		menuLanguage = new Menu();
	    menuBar.getMenus().add(menuLanguage);
		
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
		okButton = new Button();
		cancelButton = new Button();
		textFieldBox = new HBox();
		text = new TextField();
		info = new Label();
	    zero = new Region();
		message = new Label();
		
		//panes
		topBox = new VBox();
	    centerBox = new VBox();
		bottomBox = new HBox();
		labelBox = new HBox();
		messageStackPane = new StackPane();
		
		//get children
		labelBox.getChildren().add(info);
		textFieldBox.getChildren().add(text);
	    topBox.getChildren().addAll(labelBox, textFieldBox);
	    bottomBox.getChildren().addAll(okButton,zero ,cancelButton);
		messageStackPane.getChildren().add(message);
	    centerBox.getChildren().addAll(topBox,messageStackPane);

		//position
	    cancelButton.setAlignment(Pos.BASELINE_CENTER);
	    okButton.setAlignment(Pos.BASELINE_CENTER);
	    
		//spacing and padding
		bottomBox.setSpacing(10);
	    centerBox.setSpacing(10);
		bottomBox.setPadding(new Insets(10, 50, 15, 50));
	    topBox.setPadding(new Insets(35, 50, 10, 50));
	    text.setPrefWidth(470);
		zero.setMinWidth(20);
		
		//size
	    cancelButton.setPrefSize(220, 50);
	    okButton.setPrefSize(220, 50);   

	    //css
		message.setId("message");
		message.setVisible(false);
		text.setId("textField");
		root.setId("background");

	    root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);
		
		updateLabels();
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		return scene;
	}
	
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		cancelButton.setText(t.getString("program.lobbyCreater.cancelButton"));
		okButton.setText(t.getString("program.lobbyCreater.okButton"));
		menuLanguage.setText(t.getString("program.lobbyCreater.menuLanguage"));
		info.setText(t.getString("program.lobbyCreater.info"));
		stage.setTitle(t.getString("program.lobbyCreater.titel"));
		if (message.getText()!="") {
			message.setText(t.getString("program.lobbyCreater.message"));
		}
		
	}
	public TextField getTextField() {
		return text;
	}
	
	public void resetTextField() {
		text.setText("");
	}

	public Button getOkButton() {
		return okButton;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}
	
	public Label getMessage() {
		return message;
	}
	//To show the error message in GUI if Login fails
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.lobbyCreater.message"));
	}
	
}
