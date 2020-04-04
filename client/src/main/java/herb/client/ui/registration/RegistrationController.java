package herb.client.ui.registration;

import herb.client.Main;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;

public class RegistrationController extends Controller<RegistrationModel, RegistrationView> {

	public RegistrationController(RegistrationModel model, RegistrationView view) {
		super(model, view);
		
		// Action for CreateUserButton
		view.getRegistrationButton().setOnAction(e -> registration());
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

}
