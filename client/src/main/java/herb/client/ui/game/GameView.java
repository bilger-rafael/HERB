package herb.client.ui.game;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameView extends View<GameModel> {
	
	private BorderPane root, bottomBox; 
	private Label trickLabel;
	private Label playerLabel;
// etc.
	
	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		this.root = new BorderPane();
		
		// TODO program 
		
		
		
		// Labels
		trickLabel = new Label("Here comes the table...");
		trickLabel.setPrefSize(500, 200);
		playerLabel = new Label("Here is the hand of the player...");
		playerLabel.setPrefSize(500, 200);

		root.setCenter(trickLabel);
		root.setBottom(playerLabel);
		
		Scene scene = new Scene(root);
		return scene;
	}

}
