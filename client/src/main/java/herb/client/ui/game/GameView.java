package herb.client.ui.game;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import javafx.scene.Scene;

public class GameView extends View<GameModel> {
	
	public GameView(Stage stage, GameModel model) {
		super(stage, model);
	}

	@Override
	protected Scene create_GUI() {
		//Scene scene = new Scene(root);
		return scene;
	}

}
