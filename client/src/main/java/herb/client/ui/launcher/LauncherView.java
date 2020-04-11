package herb.client.ui.launcher;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;

import java.util.Locale;

import herb.client.utils.Translator;
import herb.client.ressources.Lobby;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;

public class LauncherView extends View<LauncherModel> {
	
	private BorderPane root, bottomBox;
	private VBox centerBox;
	private Button joinButton,createButton, newLobbyButton;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	protected ListView<String> lobbyRoomCenter;
	
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
	    
	    lobbyRoomCenter = new ListView<String>();
	    lobbyRoomCenter.setPrefWidth(500);
	    
	    Lobby lobby1 = new Lobby("new");
	    Lobby lobby2 = new Lobby("new2");
	    Lobby lobby3 = new Lobby("new3");
	    
	    ObservableList<String> lobbys = FXCollections.observableArrayList(lobby1.getName(),lobby2.getName(),lobby3.getName());
	    
	    ListView<String> lobbyRoomCenter = new ListView<String>(lobbys);
	    
	    StackPane stPane = new StackPane();
	    stPane.getChildren().add(lobbyRoomCenter);
	    
	    lobbyRoomCenter.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 

	    lobbyRoomCenter.getSelectionModel().selectIndices(1, 2);
	    
	    /**
	     * Buttons with bottomBox
	     * Herren
	     */
		bottomBox = new BorderPane();
		joinButton = new Button("join");		
		createButton = new Button("create");
		
		bottomBox.setRight(joinButton);
		bottomBox.setLeft(createButton);
		
		joinButton.setAlignment(Pos.BASELINE_CENTER);
		createButton.setAlignment(Pos.BASELINE_CENTER);
		
		joinButton.setPrefWidth(100);
		createButton.setPrefWidth(100);
		

		root.setTop(menuBar);
		root.setCenter(lobbyRoomCenter);
		root.setBottom(bottomBox);
		
		Scene scene = new Scene(root);
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
		menuLanguage.setText(t.getString("program.launcher.menuLanguage"));
	}
	
	public Button getJoinButton() {
		return joinButton;
	}
	
	public Button getCreateButton() {
		return createButton;
	}

}
