package herb.client.ui.registration;

import herb.client.utils.ServiceLocator;
import herb.client.Main;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import javafx.application.Platform;
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
		
		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
			}
	});
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Application controller initialized");
	
	}


	private void registration() {
		String username = view.getNameTextField().getText();
		String password = view.getPasswordField().getText();


		try {
			model.register(username, password);
			this.view.stop();
		} catch (ExceptionBase e) {
			// TODO show error message
		}

	}
	/**
	 * 
	 */
	private void getBackLoginView() {
		this.view.stop();
		Main.getMainProgram().getLoginView().start();
	}

}
