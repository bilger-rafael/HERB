package herb.client.ui.launcher;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
import java.util.logging.Logger;

import herb.client.utils.Translator;
import herb.client.ressources.HighScore;
import herb.client.ressources.Lobby;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//Herren
public class LauncherView extends View<LauncherModel> {
	
	private Lobby lobby;
	
	private BorderPane root;
	private VBox lobbyListBox, highscoreListBox;
	private HBox centerBox,bottomBox;
	
	private Button joinButton,createButton, refreshButton;
	private Label lobbyLabel, highscoreLabel;
	private Region zero, one;
	
	private MenuBar menuBar;
	private Menu menuLanguage, menuFile;
	private MenuItem logoutMenuItem;
	
	protected ListView<Lobby> lobbyRoomCenter;
	protected ListView<HighScore> highscoreList;
	
	public LauncherView(Stage stage, LauncherModel model) {
		super(stage, model);
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		Logger logger = sl.getLogger();	
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
	    highscoreList = new ListView<>(model.getHighScore());
	    StackPane stPane1 = new StackPane();
	    stPane1.getChildren().add(highscoreList);

	    /**
	     * Label
	     */
	    lobbyLabel = new Label();
	    highscoreLabel = new Label();
		joinButton = new Button();	
		refreshButton = new Button();
		createButton = new Button();
		zero = new Region();
		one = new Region();
	    
		//pane
	    lobbyListBox = new VBox();
	    highscoreListBox = new VBox();
	    centerBox = new HBox();
		bottomBox = new HBox();
	    
		//get children in panes
	    lobbyListBox.getChildren().addAll(lobbyLabel, lobbyRoomCenter);
	    highscoreListBox.getChildren().addAll(highscoreLabel,highscoreList );
	    centerBox.getChildren().addAll(lobbyListBox, highscoreListBox);
	    bottomBox.getChildren().addAll(joinButton,zero, createButton,one, refreshButton);

		//spacing
	    lobbyLabel.setAlignment(Pos.BASELINE_CENTER);
	    highscoreLabel.setAlignment(Pos.BASELINE_CENTER);
	    centerBox.setAlignment(Pos.BASELINE_CENTER);
		joinButton.setAlignment(Pos.BASELINE_CENTER);
		createButton.setAlignment(Pos.BASELINE_CENTER);
		refreshButton.setAlignment(Pos.BASELINE_CENTER);
		bottomBox.setAlignment(Pos.BASELINE_CENTER);
		centerBox.setSpacing(10);
	    centerBox.setPadding(new Insets(35, 50, 15, 55));
		bottomBox.setPadding(new Insets(20, 50, 15, 50));
		
		//set size
		joinButton.setPrefSize(220, 50);
		createButton.setPrefSize(220, 50);
		refreshButton.setPrefSize(220, 50);
		zero.setMinWidth(20);
		one.setMinWidth(20);
		highscoreList.setPrefWidth(300);
		lobbyRoomCenter.setPrefWidth(300);

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
	// update items 
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		joinButton.setText(t.getString("program.launcher.joinButton"));
		createButton.setText(t.getString("program.launcher.createButton"));
		lobbyLabel.setText(t.getString("program.launcher.lobbyLabel"));
		highscoreLabel.setText(t.getString("program.launcher.highscoreLabel"));
		menuLanguage.setText(t.getString("program.launcher.menuLanguage"));
		menuFile.setText(t.getString("program.launcher.menuFile"));
		stage.setTitle(t.getString("program.launcher.titel"));
		logoutMenuItem.setText(t.getString("program.launcher.logoutMenuItem"));
		refreshButton.setText(t.getString("program.launcher.refreshButton"));
	}
	//getter
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

	public ListView<Lobby> getLobbyRoomCenter() {
		return lobbyRoomCenter;
	}
}
