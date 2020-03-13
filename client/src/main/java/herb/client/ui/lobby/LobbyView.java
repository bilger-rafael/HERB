package herb.client.ui.lobby;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import javafx.scene.Scene;

public class LobbyView extends View<LobbyModel> {
	
	public LobbyView(Stage stage, LobbyModel model) {
		super(stage, model);
	}

	@Override
	protected Scene create_GUI() {
		//Scene scene = new Scene(root);
		return scene;
	}

}
