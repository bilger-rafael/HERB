package herb.client.ui.lobby;

import javafx.stage.Stage;

import java.util.Locale;
import java.util.logging.Logger;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//Herren
public class LobbyView extends View<LobbyModel> {
	
	private BorderPane root; 
	private VBox centerBox;
	private HBox bottomBox;
	private StackPane stPane, messageStackPane;
	
	private Button cancelButton, botsButton;
	private Label lobbyName, message, messageRefresh;
	
	private Region zero;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	protected ListView <Player> playerOverview;
	
	private Player player;
		
	public LobbyView(Stage stage, LobbyModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("LobbyView initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		Logger logger = sl.getLogger();
		this.root = new BorderPane();

		//menu
		menuBar = new MenuBar();
		menuLanguage = new Menu();
	    menuBar.getMenus().add(menuLanguage);
	    
	    //link to local
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			this.menuLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateLabels();
			});
		}
		//label, button, region, list
	    message = new Label();
	    messageRefresh = new Label();
	    lobbyName = new Label(model.getLobby().getName());
		cancelButton = new Button();
		botsButton = new Button();
	    zero = new Region();
	    playerOverview = new ListView<>(model.getPlayers());
	   
	    //panes
	    centerBox  = new VBox();
		bottomBox = new HBox();
	    messageStackPane = new StackPane();
	    stPane = new StackPane();
	    
	    //get children
	    stPane.getChildren().add(playerOverview);
	    messageStackPane.getChildren().addAll(message, messageRefresh);
	    centerBox.getChildren().addAll(lobbyName, playerOverview, messageStackPane);
	    bottomBox.getChildren().addAll(cancelButton, zero,botsButton );


	    //size
	    cancelButton.setPrefSize(220, 50);
	    botsButton.setPrefSize(220, 50);  
	    playerOverview.setPrefWidth(300);
	    playerOverview.setMaxHeight(125);
		zero.setMinWidth(20);
		
		//spacing and padding
		bottomBox.setPadding(new Insets(5, 50, 15, 50));
	    centerBox.setPadding(new Insets(35, 50, 10, 50));
		bottomBox.setSpacing(10);
	    centerBox.setSpacing(10);

	    //position
		cancelButton.setAlignment(Pos.BASELINE_CENTER);
		botsButton.setAlignment(Pos.BASELINE_CENTER);
		
		//css
		message.setId("message");
		message.setVisible(false);
		messageRefresh.setId("message");
		messageRefresh.setVisible(false);
		root.setId("background");

		//list not selectable: just to look at
	    playerOverview.setMouseTransparent(true);
	    playerOverview.setFocusTraversable(false);
		
		root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);
		
        updateLabels();  
		Scene scene = new Scene(root);
		return scene;
	}
	//update items
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		cancelButton.setText(t.getString("program.lobby.cancelButton"));
		botsButton.setText(t.getString("program.lobby.botsButton"));
		menuLanguage.setText(t.getString("program.lobby.menuLanguage"));
		message.setText(t.getString("program.lobby.message"));
		messageRefresh.setText(t.getString("program.lobby.messageRefresh"));
		stage.setTitle(t.getString("program.lobby.titel"));
		if (message.getText()!="") {
			message.setText(t.getString("program.lobby.message"));
		}
		if (messageRefresh.getText()!="") {
			messageRefresh.setText(t.getString("program.lobby.messageRefresh"));
		}
		
	}
	//getter
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
	
	public Label getMessageRefresh() {
		return messageRefresh;
	}
	//To show the error message in GUI if Login fails
	public void showError() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		message.setText(t.getString("program.lobby.message"));
	}
	public void showErrorRefresh() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		messageRefresh.setText(t.getString("program.lobby.messageRefresh"));
	}
}
