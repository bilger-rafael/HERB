package herb.client.ui.login;

import herb.client.Main;
import herb.client.ressources.Login;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.ServiceLocator;

public class LoginController extends Controller<LoginModel, LoginView> {

	ServiceLocator serviceLocator;

	private LoginModel loginModel;

	public LoginController(LoginModel model, LoginView view) {
		super(model, view);

		// Event on LoginButton
		view.getLoginButton().setOnAction(e -> login());

		// Action for CreateUserButton
		view.getCreateUserButton().setOnAction(e -> createUserView());

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Application controller initialized");

	}

	private void login() {
		String username = view.getNameField().getText();
		String password = view.getPwField().getText();

		try {
			model.login(username, password);
			goToLauncher();
		} catch (ExceptionBase e) {
			// TODO show error message
		}

	}

	private void goToLauncher() {
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

	// forwards to the CreatUserView
	private void createUserView() {
		Main.getMainProgram().startRegistration();
	}
//	}
}