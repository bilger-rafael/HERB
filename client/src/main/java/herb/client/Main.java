package herb.client;

import java.util.UUID;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import herb.client.ressources.Game;
import herb.client.ressources.Lobby;
import herb.client.ressources.Round;
import herb.client.ui.botSelectioner.BotController;
import herb.client.ui.botSelectioner.BotModel;
import herb.client.ui.botSelectioner.BotView;
import herb.client.ui.core.View;
import herb.client.ui.game.GameController;
import herb.client.ui.game.GameModel;
import herb.client.ui.game.GameView;
import herb.client.ui.launcher.LauncherController;
import herb.client.ui.launcher.LauncherModel;
import herb.client.ui.launcher.LauncherView;
import herb.client.ui.lobby.LobbyController;
import herb.client.ui.lobby.LobbyModel;
import herb.client.ui.lobby.LobbyView;
import herb.client.ui.lobbyCreater.LobbyCreaterController;
import herb.client.ui.lobbyCreater.LobbyCreaterModel;
import herb.client.ui.lobbyCreater.LobbyCreaterView;
import herb.client.ui.login.LoginController;
import herb.client.ui.login.LoginModel;
import herb.client.ui.login.LoginView;
import herb.client.ui.registration.RegistrationController;
import herb.client.ui.registration.RegistrationModel;
import herb.client.ui.registration.RegistrationView;
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
	private LoginView loginView;
	private RegistrationView registrationView;
	private LauncherView launcherView;
	private LobbyView loView;
	private GameView gameView;
	private LobbyCreaterView lobbyCreaterView;
	private BotView botView;

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
	public void startMain() {
		
		// Initialize the application MVC components. Note that these components
		// can only be initialized now, because they may depend on the
		// resources initialized by the splash screen
		
		// create LoginView
		View view = getLoginView();

		// Resources are now initialized
		serviceLocator = ServiceLocator.getInstance();	
		
		// Close the splash screen, and set the reference to null, so that all
		// Splash_XXX objects can be garbage collected
		splashView.stop();
		splashView = null;

		view.start();		
	}
	
	// roesti - call login view	
	public LoginView getLoginView() {
		if (loginView == null) {
			Stage stage = new Stage();
			LoginModel loginModel = new LoginModel();
			loginView = new LoginView(stage, loginModel);
			new LoginController(loginModel, loginView);
		}
		return loginView;
		//}
		/*
		String url = serviceLocator.getConfiguration().getOption("rootURL");
		Player player = restTemplate.getForObject(url.concat("/player"), Player.class);
		System.out.println(player.getName() + player.getRank());
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
		
	// roesti - open RegistrationWindow
	public void startRegistration() {
		Stage stage = new Stage();
		RegistrationModel regModel = new RegistrationModel();
		if (registrationView == null) {
			registrationView = new RegistrationView(stage, regModel);
		}
		new RegistrationController(regModel, registrationView);
		//loginView.stop();
		registrationView.start();
	}

	// roesti - open Launcher
	public LauncherView getLauncher() {
		if (launcherView == null) {
			Stage stage = new Stage();
			LauncherModel lauModel = new LauncherModel();
			launcherView = new LauncherView(stage, lauModel);
			new LauncherController(lauModel, launcherView);
			serviceLocator.getLogger().info("Launcher started");
			}
		return launcherView;
	}
	
	public LobbyCreaterView getLobbyCreater() {
		if (lobbyCreaterView == null) {
			Stage stage = new Stage();
			LobbyCreaterModel lobbyCreaterModel = new LobbyCreaterModel();
			lobbyCreaterView = new LobbyCreaterView(stage, lobbyCreaterModel);
			new LobbyCreaterController(lobbyCreaterModel, lobbyCreaterView);
			serviceLocator.getLogger().info("LobbyCreater started");
			}
		return lobbyCreaterView;
	}

	public LobbyView getLobbyView(Lobby lobby) {
		// TODO correct, when lobbies work => multiple Lobbies possible
		
		//if(!getlobbyViews().containsKey(loobyName)) {
		Stage stage = new Stage();
		LobbyModel model = new LobbyModel(lobby);
		loView = new LobbyView(stage, model);
		// LobbyView loView = new LobbyView(stage, model);
		new LobbyController(model, loView);
		launcherView.stop();
		
		//getLobbyViews().put(lobbyName, loView);
		
		return loView; 
		// return getLobbyViews().get(lobbyName);
	}
	

	public GameView getGameView() {
			// TODO correct, when everything works
			Stage stage = new Stage();
			GameModel model = new GameModel();
			gameView = new GameView(stage, model);
			new GameController(model, gameView);
			//loView.stop();
			
			return gameView;
	}
	
	public BotView getBotView(Lobby lobby) {
		if(botView == null) {
		Stage stage = new Stage();
		BotModel botModel = new BotModel(lobby);
		botView = new BotView(stage, botModel);
		new BotController(botModel, botView);
		serviceLocator.getLogger().info("BotView started");
		}
		return botView;
	}
	
	public void clearBotView() {
		this.botView = null;
	}
}
