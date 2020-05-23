package herb.client.ui.login;

import herb.client.Main;
import herb.client.ressources.Login;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.ServiceLocator;

//Herren
public class LoginController extends Controller<LoginModel, LoginView> {

	private ServiceLocator serviceLocator;

	private LoginModel loginModel;

	public LoginController(LoginModel model, LoginView view) {
		super(model, view);

		//action for loginButton
		view.getLoginButton().setOnAction(e -> login());
		//action for createUserButton
		view.getCreateUserButton().setOnAction(e -> createUserView());
		//if nameField and pwField are empty loginButton is disabled
		view.getLoginButton().disableProperty().bind(view.getNameField().textProperty().isEmpty());
		view.getLoginButton().disableProperty().bind(view.getPwField().textProperty().isEmpty());

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Login controller initialized");
	}
	//methode for loginButton
	private void login() {
		//get username and password
		String username = view.getNameField().getText();
		String password = view.getPwField().getText();
		
		//try catch for exception handling
		try {
			//if user already exist
			model.login(username, password);
			goToLauncher();
			serviceLocator.getLogger().info("Login war erfolgreich.");
			
		} catch (ExceptionBase e) {
			//if user do not exist
			view.showError();
			view.getMessage().setVisible(true);
			serviceLocator.getLogger().info("Name oder Passwort falsch eingegeben.");
			}

	}

	// Connector to Launcher, reset text fields and sets message invisible in LoginView
	private void goToLauncher() {
		this.view.resetPasswordField();
		this.view.resetNameField();
		this.view.getMessage().setVisible(false);
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

	// forwards to the RegistraionView, reset text fields and sets message invisible in LoginView
	private void createUserView() {
		this.view.resetPasswordField();
		this.view.resetNameField();
		this.view.getMessage().setVisible(false);
		Main.getMainProgram().startRegistration();
	}
}