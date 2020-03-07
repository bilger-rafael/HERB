package herb.client.ui.lobby;

import herb.client.ui.base.View;
import javafx.stage.Stage;
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
