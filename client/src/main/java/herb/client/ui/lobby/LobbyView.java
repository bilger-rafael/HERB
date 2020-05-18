package herb.client.ui.lobby;

import javafx.stage.Stage;

import java.util.Locale;

import herb.client.ressources.Player;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LobbyView extends View<LobbyModel> {
	
	private BorderPane root; 
	private VBox centerBox;
	private HBox bottomBox;
	private StackPane messageStackPane;
	private Button cancelButton, botsButton;
	private Label lobbyName, message;
	
	private Region zero;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	protected ListView <Player> playerOverview;
	
	private Player player;
		
	public LobbyView(Stage stage, LobbyModel model) {
		super(stage, model);
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
	    
	    playerOverview.setPrefWidth(300);
	    playerOverview.setMaxHeight(125);
	    
	    message = new Label();
	    messageStackPane = new StackPane();
	    messageStackPane.getChildren().add(message);
		message.setId("message");
		message.setVisible(false);
		
	    zero = new Region();
		zero.setMinWidth(20);
	    lobbyName = new Label(model.getLobby().getName());
	    centerBox  = new VBox();
	    centerBox.setPadding(new Insets(35, 50, 10, 50));
	    centerBox.getChildren().addAll(lobbyName, playerOverview, messageStackPane);
	    centerBox.setSpacing(10);

		/**
		 * Buttons with bottomBox
		 * Herren
		 */
		bottomBox = new HBox();
		bottomBox.setSpacing(10);
		cancelButton = new Button();
		botsButton = new Button();
	    cancelButton.setPrefSize(220, 50);
	    botsButton.setPrefSize(220, 50);  
		
	    bottomBox.getChildren().addAll(cancelButton, zero,botsButton );
		bottomBox.setPadding(new Insets(5, 50, 15, 50));
	    
		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		botsButton.setAlignment(Pos.BASELINE_CENTER);
		
		
//		cancelButton.setPrefWidth(100);


		root.setId("background");
		root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);
		
        updateLabels();  
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		return scene;
	}
	
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		cancelButton.setText(t.getString("program.lobby.cancelButton"));
		botsButton.setText(t.getString("program.lobby.botsButton"));
		menuLanguage.setText(t.getString("program.lobby.menuLanguage"));
		message.setText(t.getString("program.lobby.message"));
		stage.setTitle(t.getString("program.lobby.titel"));
		if (message.getText()!="") {
			message.setText(t.getString("program.lobby.message"));
		}
		
	}

	public Button getCancelButton() {
		return cancelButton;
	}
	
	public Button getBotsButton() {
		return botsButton;
	}
	
	public Label getLobbyName() {
		return lobbyName;
	}
	
	public Label getMessage() {
		return message;
	}
	//To show the error message in GUI if Login fails
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.lobby.message"));
	}
}
