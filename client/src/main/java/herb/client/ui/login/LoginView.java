package herb.client.ui.login;

import herb.client.ui.base.View;
import javafx.stage.Stage;
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
