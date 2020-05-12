package herb.client.ui.registration;

import herb.client.utils.ServiceLocator;
import herb.client.Main;
import herb.client.ressources.Login;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class RegistrationController extends Controller<RegistrationModel, RegistrationView> {
	ServiceLocator serviceLocator;
		
	public RegistrationController(RegistrationModel model, RegistrationView view) {
		super(model, view);
		
		// Action for CreateUserButton
		view.getRegistrationButton().setOnAction(e -> registration());
		
		/**
		 * action for cancelButton
		 * Herren
		 */
		view.getCancelButton().setOnAction(e -> getBackLoginView());
		
		/**
		 * register ourselves to handle window-closing event
		 */
		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				view.stop();
				Platform.exit();
			}
	});
		
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Application controller initialized");
	
	}


	private void registration() {
		String username = view.getNameTextField().getText();
		String password = view.getPasswordField().getText();
		/**
		 * new login
		 * Herren
		 */
		
		Login createLogin = new Login(username, password);
		

		try {
			model.register(username, password);
			this.view.stop();
			//reset textfield
			this.view.resetNameField();
			this.view.resetPasswordField();
		} catch (ExceptionBase e) {
			view.showError();
			serviceLocator.getLogger().info("Name oder Passwort falsch eingegeben.");
		}

	}
	/**
	 * Herren
	 */
	private void getBackLoginView() {
		this.view.stop();
		this.view.resetNameField();
		this.view.resetPasswordField();
		Main.getMainProgram().getLoginView().start();
	}

}
