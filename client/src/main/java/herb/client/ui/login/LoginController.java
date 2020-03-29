package herb.client.ui.login;


import ch.qos.logback.core.net.server.Client;
import herb.client.Main;
import herb.client.ressources.Login;
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

		// register ourselves to handle window-closing event
//		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
//		@Override
//		public void handle(WindowEvent event) {
//		Platform.exit();
//		}
//	});
		
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Application controller initialized");

		}

		private void login() {
			String username = view.getNameField().getText();
			String password = view.getPwField().getText();

			Login login = new Login(username, password);
			
//			Client.getClient().addMsgListener(new MessageListener() {
//				@Override
//				public void receive(Message msg) {
//					if (msg instanceof Result) {
//						Result r = (Result) msg;
//						if (r.getType() == ResultType.Token) {
//							if (r.getBoolean()) {
//								serviceLocator.getLogger().info("eingeloggt");
//								Client.getClient().setToken(r.getToken());
//								Client.getClient().setUsername(username);
//								Platform.runLater(() -> {
									goToLauncher();
//								});
//							} else {
//
//							}
//							Client.getClient().removeMsgListener(this);
//						}
//						//Fehlermeldung anzeigen
//						if( r.getType() == ResultType.Simple && r.getBoolean() == false) {
//						view.showError();
//						serviceLocator.getLogger().info("Login-Informationen falsch");
//						}
//					}
//					
//				}
//				
//			});
//
//			Client.getClient().send(login);
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