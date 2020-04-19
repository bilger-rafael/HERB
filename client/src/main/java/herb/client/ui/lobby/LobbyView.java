package herb.client.ui.lobby;

import javafx.stage.Stage;

import java.util.Locale;

import herb.client.ressources.Player;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LobbyView extends View<LobbyModel> {
	
	private BorderPane root, bottomBox; 
	private Button cancelButton;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	protected ListView <Player> playerOverview;
	
	private Player player;
	
	public LobbyView(Stage stage, LobbyModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Lobby: " + model.getLobby() );
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
		menuLanguage = new Menu();
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
	     * list
	     * Herren
	     */
   
	    playerOverview = new ListView<>(model.getPlayers());
	    
	    StackPane stPane = new StackPane();
	    stPane.getChildren().add(playerOverview);
	    
	    playerOverview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 

	    playerOverview.getSelectionModel().selectIndices(1, 2);
	    
	    playerOverview.setPrefWidth(300);
		
		/**
		 * Buttons with bottomBox
		 * Herren
		 */
		bottomBox = new BorderPane();
		cancelButton = new Button("cancel");
		

		bottomBox.setLeft(cancelButton);

		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		
		cancelButton.setPrefWidth(100);
		
		root.setTop(menuBar);
		root.setCenter(playerOverview);
		root.setBottom(bottomBox);
		
        updateLabels();  
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		return scene;
	}
	
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		cancelButton.setText(t.getString("program.lobby.cancelButton"));
		menuLanguage.setText(t.getString("program.lobby.menuLanguage"));
		
	}


	
	public Button getCancelButton() {
		return cancelButton;
	}
}
