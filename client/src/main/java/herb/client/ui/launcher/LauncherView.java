package herb.client.ui.launcher;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;


import java.util.Locale;

import herb.client.utils.Translator;
import herb.client.ressources.Lobby;
import herb.client.ressources.Round;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

public class LauncherView extends View<LauncherModel> {
	
	private BorderPane root, bottomBox;
	private VBox lobbyListBox, highscoreListBox;
	private HBox centerBox;
	private Button joinButton,createButton, newLobbyButton, refreshButton;
	private Label lobbyLabel, highscoreLabel;

	
	private MenuBar menuBar;
	private Menu menuLanguage, menuFile;
	private MenuItem logoutMenuItem;
	
	protected ListView<Lobby> lobbyRoomCenter;
	protected ListView<Round> highscoreList;
	
	private Lobby lobby;
	

	
	public LauncherView(Stage stage, LauncherModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Launcher");
		// can we make different titles for all three languages? TODO
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
		menuFile = new Menu();
		logoutMenuItem = new MenuItem();
		menuFile.getItems().add(logoutMenuItem);
	    menuBar.getMenus().addAll(menuLanguage, menuFile);
	    
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
	     * lobby list
	     * Herren
	     */
   
	    lobbyRoomCenter = new ListView<>(model.getLobbys());
	    
	    StackPane stPane = new StackPane();
	    stPane.getChildren().add(lobbyRoomCenter);
	    lobbyRoomCenter.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	    
	    /**
	     * highscore list
	     */
	    highscoreList = new ListView<>();
	    StackPane stPane1 = new StackPane();
	    stPane1.getChildren().add(highscoreList);

	    /**
	     * Label
	     * Herren
	     */
	    lobbyLabel = new Label();
	    highscoreLabel = new Label();

	    lobbyLabel.setAlignment(Pos.BASELINE_CENTER);
	    highscoreLabel.setAlignment(Pos.BASELINE_CENTER);
	    
	    lobbyListBox = new VBox();
	    lobbyListBox.getChildren().addAll(lobbyLabel, lobbyRoomCenter);
	    
	    highscoreListBox = new VBox();
	    highscoreListBox.getChildren().addAll(highscoreLabel,highscoreList );
	    
	    centerBox = new HBox();
	    centerBox.getChildren().addAll(lobbyListBox, highscoreListBox);
		centerBox.setSpacing(10);
	    centerBox.setPadding(new Insets(20));
	    centerBox.setAlignment(Pos.BASELINE_CENTER);
	    
	    /**
	     * Buttons with bottomBox
	     * Herren
	     */
		bottomBox = new BorderPane();
		joinButton = new Button();	
		refreshButton = new Button();
		createButton = new Button();
		
		
		bottomBox.setRight(joinButton);
		bottomBox.setLeft(createButton);
		bottomBox.setCenter(refreshButton);
		
		
		joinButton.setAlignment(Pos.BASELINE_CENTER);
		createButton.setAlignment(Pos.BASELINE_CENTER);
		refreshButton.setAlignment(Pos.BASELINE_CENTER);
		
		joinButton.setPrefWidth(150);
		createButton.setPrefWidth(150);
		refreshButton.setPrefWidth(150);

		root.setId("background");
		root.setTop(menuBar);
		root.setCenter(centerBox);
		root.setBottom(bottomBox);
		
		
        updateLabels();     
		Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		return scene;
		
	/**
	 * names given by translator
	 * Herren
	 */
		
	}

	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		joinButton.setText(t.getString("program.launcher.joinButton"));
		createButton.setText(t.getString("program.launcher.createButton"));
		lobbyLabel.setText(t.getString("program.launcher.lobbyLabel"));
		highscoreLabel.setText(t.getString("program.launcher.highscoreLabel"));
		menuLanguage.setText(t.getString("program.launcher.menuLanguage"));
		menuFile.setText(t.getString("program.launcher.menuFile"));
		logoutMenuItem.setText(t.getString("program.launcher.logoutMenuItem"));
		refreshButton.setText(t.getString("program.launcher.refreshButton"));
	}
	
	public Button getJoinButton() {
		return joinButton;
	}
	
	public Button getCreateButton() {
		return createButton;
	}
	
	
	public Button getRefreshButton() {
		return refreshButton;
	}
	
	public MenuItem getLogoutMenuItem() {
		return logoutMenuItem;
	}


}
