package herb.client.ui.lobbyCreater;

import java.util.Locale;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class LobbyCreaterView extends View<LobbyCreaterModel> {
	
	private BorderPane root; 
	private HBox bottomBox, topBox;
	
	private Button okButton, cancelButton;
	private TextField text;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	public LobbyCreaterView(Stage stage, LobbyCreaterModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass Lobby Creater");
		ServiceLocator.getInstance().getLogger().info("Application LobbyCreaterView initialized");
	}
	
	@Override
	protected Scene create_GUI() {
		
		ServiceLocator sl = ServiceLocator.getInstance();
		this.root = new BorderPane();
		
		menuBar = new MenuBar();
		menuLanguage = new Menu();
		menuLanguage.getItems().addAll();
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
		
		//Text field
		topBox = new HBox();
		text = new TextField();
		text.setId("textField");
		
		// Buttons - not needed in final version - just to open the GameView
		bottomBox = new HBox();
		okButton = new Button();
		cancelButton = new Button();
   
		bottomBox.setSpacing(10);
	    
	    cancelButton.setAlignment(Pos.BASELINE_CENTER);
	    okButton.setAlignment(Pos.BASELINE_CENTER);
	    
	    cancelButton.setPrefWidth(100);
	    okButton.setPrefWidth(100);
	    
	    topBox.getChildren().add(text);
	    topBox.setPadding(new Insets(2));
	    bottomBox.getChildren().addAll(okButton, cancelButton);
	    bottomBox.setPadding(new Insets(2));
		
		root.setId("background");
	    root.setTop(menuBar);
		root.setCenter(topBox);
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
}
