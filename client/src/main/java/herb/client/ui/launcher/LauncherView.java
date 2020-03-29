package herb.client.ui.launcher;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;

public class LauncherView extends View<LauncherModel> {
	
	private BorderPane root, bottomBox; 
	private Button joinButton;

	
	public LauncherView(Stage stage, LauncherModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Launcher");
		// can we make different titles for all three languages? TODO
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		this.root = new BorderPane();
		
		// TODO program 
		
		
		
		// Buttons
		joinButton = new Button("join, join, join");
		joinButton.setPrefSize(500, 30);
		bottomBox = new BorderPane();
		
		bottomBox.setRight(joinButton);
		root.setBottom(bottomBox);
		
		Scene scene = new Scene(root);
		return scene;
	}
	
	public Button getJoinButton() {
		return joinButton;
	}

}
