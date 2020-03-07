package herb.client.ui.launcher;

import herb.client.ui.base.View;
import javafx.stage.Stage;
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
