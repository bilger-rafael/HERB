package herb.client.ui.login;

import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.Datastore;

public class LoginModel extends Model{
	private Player player;

    public LoginModel() {
    	super();
    }
    
    public void login(String username, String password) throws ExceptionBase {
    	Login login = new Login(username, password);
    	
    	this.player = (Player) login.login();
    	
    	Datastore.getInstance().setMainPlayer(this.player);
    }

	public Player getPlayer() {
		return player;
	}
    
}
