package herb.client.ui.registration;

import herb.client.utils.ServiceLocator;
import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
//Herren
public class RegistrationModel extends Model{
    ServiceLocator serviceLocator;

    public RegistrationModel() {
    	super();
    
        serviceLocator = ServiceLocator.getInstance();        
        serviceLocator.getLogger().info("Application model initialized");
    	
    }
    /**
     * registration save as new login
     */
    public void register(String username, String password) throws ExceptionBase {
    	Login login = new Login(username, password);
    	
    	login.register();
    }
    
    
}
