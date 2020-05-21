package herb.client.ui.login;

import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.Datastore;
//Herren
public class LoginModel extends Model{
	private Player player;

    public LoginModel() {
    	super();
    }
    
    //if login action 
    public void login(String username, String password) throws ExceptionBase {
    	//a "new" login
    	Login login = new Login(username, password);
    	//does already a player exist with this username and password
    	this.player = (Player) login.login();
    	//does user exist in datastore
    	Datastore.getInstance().setMainPlayer(this.player);
    }
    //if player exist (if player do not exist, throw exception)
	public Player getPlayer() {
		return player;
	}
    
}
