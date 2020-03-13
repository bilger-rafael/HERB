package herb.client.ui.login;

import javafx.stage.Stage;
import herb.client.ui.core.View;
import javafx.scene.Scene;

public class LoginView extends View<LoginModel> {
	
	public LoginView(Stage stage, LoginModel model) {
		super(stage, model);
	}

	@Override
	protected Scene create_GUI() {
		//Scene scene = new Scene(root);
		return scene;
	}

}
