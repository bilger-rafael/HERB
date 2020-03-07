package herb.client.ui.game;

import herb.client.ui.base.View;
import javafx.stage.Stage;
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
