package herb.client.ui.registration;

import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;

public class RegistrationController extends Controller<RegistrationModel, RegistrationView> {

	public RegistrationController(RegistrationModel model, RegistrationView view) {
		super(model, view);
	}

	private void registration() {
		// String username = view.getNameField().getText();
		// String password = view.getPwField().getText();

		try {
			model.register("", "");
			// model.register(username, password);
			// goToLogin();
		} catch (ExceptionBase e) {
			// TODO show error message
		}

	}

}
