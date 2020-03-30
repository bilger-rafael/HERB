package herb.client.ui.login;

import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;

public class LoginModel extends Model{
	private Player player;

    public LoginModel() {
    	super();
    }
    
    public void login(String username, String password) throws ExceptionBase {
    	Login login = new Login(username, password);
    	
    	this.player = (Player) login.login();
    }

	public Player getPlayer() {
		return player;
	}
    
}
