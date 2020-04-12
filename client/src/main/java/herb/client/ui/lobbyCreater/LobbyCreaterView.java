package herb.client.ui.lobbyCreater;

import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LobbyCreaterView extends View<LobbyCreaterModel> {
	
	private BorderPane root, bottomBox; 
	private Button okButton;
	private TextField text;
	
	public LobbyCreaterView(Stage stage, LobbyCreaterModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass Lobby Creater");
		ServiceLocator.getInstance().getLogger().info("Application LobbyCreaterView initialized");
	}
	
	@Override
	protected Scene create_GUI() {
		
		ServiceLocator sl = ServiceLocator.getInstance();
		this.root = new BorderPane();
		
		// TODO program 
		
		
		//Text field
		text = new TextField();
		
		// Buttons - not needed in final version - just to open the GameView
		okButton = new Button("ok");
		okButton.setPrefSize(500, 30);
		bottomBox = new BorderPane();
		
		bottomBox.setLeft(text);
		bottomBox.setRight(okButton);
		root.setBottom(bottomBox);
		
		Scene scene = new Scene(root);
		return scene;
	}
	
	public TextField getTextField() {
		return text;
	}

	public Button getOkButton() {
		return okButton;
	}
}
