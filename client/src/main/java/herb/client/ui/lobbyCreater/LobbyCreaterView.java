package herb.client.ui.lobbyCreater;

import java.util.Locale;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LobbyCreaterView extends View<LobbyCreaterModel> {
	
	private BorderPane root, bottomBox; 
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
		text = new TextField();
		
		// Buttons - not needed in final version - just to open the GameView
		bottomBox = new BorderPane();
		okButton = new Button();
		cancelButton = new Button();

	    bottomBox.setRight(cancelButton);
	    bottomBox.setLeft(okButton);
	    
	    cancelButton.setAlignment(Pos.BASELINE_CENTER);
	    okButton.setAlignment(Pos.BASELINE_CENTER);
	    
	    cancelButton.setPrefWidth(100);
	    okButton.setPrefWidth(100);
		
	    root.setTop(menuBar);
		root.setCenter(text);
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
		text.setText(t.getString("program.lobbyCreater.text"));
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
