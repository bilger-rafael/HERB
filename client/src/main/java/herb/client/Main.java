package herb.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import herb.client.ui.splash.SplashController;
import herb.client.ui.splash.SplashModel;
import herb.client.ui.splash.SplashView;
import herb.client.utils.ServiceLocator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {

	private static Main main; // singleton
	private SplashView splashView;

	private ServiceLocator serviceLocator; // resources, after initialization

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Note: This method is called on the main thread, not the JavaFX Application
	 * Thread. This means that we cannot display anything to the user at this point.
	 * Since we want to show a splash screen, this means that we cannot do any real
	 * initialization here.
	 * 
	 * This implementation ensures that the application is a singleton; only one per
	 * JVM-instance. On client installations this is not necessary (each application
	 * runs in its own JVM). However, it can be important on server installations.
	 * 
	 * Why is it important that only one instance run in the JVM? Because our
	 * initialized resources are a singleton - if two programs instances were
	 * running, they would use (and overwrite) each other's resources!
	 */
	@Override
	public void init() {
		if (main == null) {
			main = this;
		} else {
			Platform.exit();
		}
	}

	/**
	 * This method is called after init(), and is called on the JavaFX Application
	 * Thread, so we can display a GUI. We have two GUIs: a splash screen and the
	 * application. Both of these follow the MVC model.
	 * 
	 * We first display the splash screen. The model is where all initialization for
	 * the application takes place. The controller updates a progress-bar in the
	 * view, and (after initialization is finished) calls the startApp() method in
	 * this class.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Create and display the splash screen and model
		SplashModel splashModel = new SplashModel();
		splashView = new SplashView(primaryStage, splashModel);
		new SplashController(splashModel, splashView);
		splashView.start();

		// Display the splash screen and begin the initialization
		splashModel.initialize();
	}

	/**
	 * This method is called when the splash screen has finished initializing the
	 * application. The initialized resources are in a ServiceLocator singleton. Our
	 * task is to now create the application MVC components, to hide the splash
	 * screen, and to display the application GUI.
	 * 
	 * Multitasking note: This method is called from an event-handler in the
	 * Splash_Controller, which means that it is on the JavaFX Application Thread,
	 * which means that it is allowed to work with GUI components.
	 * http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
	 */
	public void startLogin() {
		/*
		// Initialize the application MVC components. Note that these components
		// can only be initialized now, because they may depend on the
		// resources initialized by the splash screen

		// LoginView
		View view = getLoginView();

		// Resources are now initialized
		serviceLocator = ServiceLocator.getServiceLocator();

		// Close the splash screen, and set the reference to null, so that all
		// Splash_XXX objects can be garbage collected
		splashView.stop();
		splashView = null;

		view.start();
		*/
	}

	/**
	 * The stop method is the opposite of the start method. It provides an
	 * opportunity to close down the program, including GUI components. If the start
	 * method has never been called, the stop method may or may not be called.
	 * 
	 * Make the GUI invisible first. This prevents the user from taking any actions
	 * while the program is ending.
	 */
	@Override
	public void stop() {
		serviceLocator.getConfiguration().save();
		serviceLocator.getLogger().info("Application terminated");
	}

	// Static getter for a reference to the main program object
	public static Main getMainProgram() {
		return main;
	}

	/*
	public static void main(String[] args) {
		
		//SpringApplication.run(ClientApplication.class, args);

		//WebClient client = WebClient.create("http://localhost:8080");

		final String uri = "http://127.0.0.1:8080";
		RestTemplate restTemplate = new RestTemplate();

		Player player = restTemplate.getForObject(uri.concat("/player"), Player.class);

		System.out.println(player.getName() + player.getRank());
	}
	*/

}
