package herb.client.ui.botSelectioner;

import herb.client.Main;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;

//Anahi
public class BotController extends Controller<BotModel, BotView> {

	public BotController(BotModel model, BotView view) {
		super(model, view);
		
		view.getCancelButton().setOnAction(e -> getBackLobbyView());
		
		view.getOkButton().setOnAction(e -> startSelectedBot());
		

	}
	
	private void getBackLobbyView() {
		this.view.stop();
	}
	
	private void startSelectedBot() {
		if(view.getEasyRadioButton().isSelected()) {
			getEasyBot();
		}
		if(view.getHeavyRadioButton().isSelected()) {
			getAdvancedBot();
		}
	}
	
	private void getEasyBot() {
		try{
			this.model.getLobby().addBot(false);
		}catch (Exception e){
			// TODO show error message
		}
		this.view.stop();
	}
	
	private void getAdvancedBot() {
		try{
			this.model.getLobby().addBot(true);
		}catch (Exception e){
			// TODO show error message
		}
		this.view.stop();
	}
}
