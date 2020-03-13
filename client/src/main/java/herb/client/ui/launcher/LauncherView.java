package herb.client.ui.launcher;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import javafx.scene.Scene;

public class LauncherView extends View<LauncherModel> {
	
	public LauncherView(Stage stage, LauncherModel model) {
		super(stage, model);
	}

	@Override
	protected Scene create_GUI() {
		//Scene scene = new Scene(root);
		return scene;
	}

}
