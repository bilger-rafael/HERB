package herb.client.ui.lobby;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class LobbyView extends View<LobbyModel> {
	
	private BorderPane root, bottomBox; 
	private Button waitButton;
	
	public LobbyView(Stage stage, LobbyModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Lobby XY");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		
		ServiceLocator sl = ServiceLocator.getInstance();
		this.root = new BorderPane();
		
		// TODO program 
		
		
		
		// Buttons - not needed in final version - just to open the GameView
		waitButton = new Button("wait, wait, wait");
		waitButton.setPrefSize(500, 30);
		bottomBox = new BorderPane();
		
		bottomBox.setRight(waitButton);
		root.setBottom(bottomBox);
		
		Scene scene = new Scene(root);
		return scene;
	}
	
	public Button getWaitButton() {
		return waitButton;
	}
}
