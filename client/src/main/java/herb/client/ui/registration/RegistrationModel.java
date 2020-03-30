package herb.client.ui.registration;

import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;

public class RegistrationModel extends Model{

    public RegistrationModel() {
    	super();
    }
    
    public void register(String username, String password) throws ExceptionBase {
    	Login login = new Login(username, password);
    	
    	login.register();
    }
    
}
