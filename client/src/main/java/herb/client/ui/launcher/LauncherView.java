package herb.client.ui.launcher;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;

public class LauncherView extends View<LauncherModel> {
	
	private BorderPane root, bottomBox;
	private VBox centerBox;
	private Button joinButton, newLobbyButton;
	
	private MenuBar menuBar;
	private Menu menuLanguage;
	
	protected ListView<String> lobbyRoomCenter;
	

	
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
	     * list
	     * Herren
	     */
	    
	    lobbyRoomCenter = new ListView<String>();
	    lobbyRoomCenter.setPrefWidth(500);
		
		// Buttons
		joinButton = new Button("join");
		joinButton.setPrefSize(500, 30);
		bottomBox = new BorderPane();
		
		bottomBox.setRight(joinButton);
		root.setTop(menuBar);
		root.setCenter(lobbyRoomCenter);
		root.setBottom(bottomBox);
		
		Scene scene = new Scene(root);
		return scene;
	}
	
	public Button getJoinButton() {
		return joinButton;
	}

}
