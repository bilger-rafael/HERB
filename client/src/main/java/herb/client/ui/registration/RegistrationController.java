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
//Herren
public class RegistrationController extends Controller<RegistrationModel, RegistrationView> {
	ServiceLocator serviceLocator;
		
	public RegistrationController(RegistrationModel model, RegistrationView view) {
		super(model, view);
		
		// action for registrationButton
		view.getRegistrationButton().setOnAction(e -> registration());
		
		//if nameField and pwField is empty registrationButton is disabled
		view.getRegistrationButton().disableProperty().bind(view.getNameField().textProperty().isEmpty());
		view.getRegistrationButton().disableProperty().bind(view.getPwField().textProperty().isEmpty());
		
		//action for cancelButton
		view.getCancelButton().setOnAction(e -> getBackLoginView());
		
		
		// register ourselves to handle window-closing event
//		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
//			@Override
//			public void handle(WindowEvent event) {
//				view.stop();
//				Platform.exit();
//			}
//	});
		
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Application controller initialized");
	
	}

	//methode for getRegistrationButton
	private void registration() {
		//get username and password and save as new login
		String username = view.getNameField().getText();
		String password = view.getPwField().getText();
		
		Login createLogin = new Login(username, password);
		
		//try catch for exception handling
		try {
			//if username and password do not exist
			model.register(username, password);
			this.view.stop();
			//reset textfield
			this.view.resetNameField();
			this.view.resetPasswordField();
			this.view.getMessage().setVisible(false);
		} catch (ExceptionBase e) {
			//if username and password already exist
			view.showError();
			view.getMessage().setVisible(true);
			serviceLocator.getLogger().info("Name oder Passwort existiert bereits.");
		}

	}
	//methode for getCancelButton
	private void getBackLoginView() {
		this.view.stop();
		this.view.resetNameField();
		this.view.resetPasswordField();
		this.view.getMessage().setVisible(false);
		Main.getMainProgram().getLoginView().start();
	}

}
