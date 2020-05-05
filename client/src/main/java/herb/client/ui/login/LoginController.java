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

		// Event on CreateUserButton
		view.getCreateUserButton().setOnAction(e -> createUserView());
		
		view.getLoginButton().disableProperty().bind(view.getNameField().textProperty().isEmpty());
		
		view.getLoginButton().disableProperty().bind(view.getPwField().textProperty().isEmpty());

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Login controller initialized");
	}

	private void login() {
		String username = view.getNameField().getText();
		String password = view.getPwField().getText();

		try {
			model.login(username, password);
			goToLauncher();
			serviceLocator.getLogger().info("Login war erfolgreich.");
			
		} catch (ExceptionBase e) {
			view.showError();
			serviceLocator.getLogger().info("Name oder Passwort falsch eingegeben.");
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