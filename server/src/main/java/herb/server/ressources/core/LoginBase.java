package herb.server.ressources.core;

import com.fasterxml.jackson.annotation.JsonCreator;

//Bilger
public abstract class LoginBase<Player extends PlayerBase> {
	private final String username;
	private final String password;
	
	@JsonCreator	
	public LoginBase(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public abstract void register() throws ExceptionBase;
	
	public abstract Player login() throws ExceptionBase;

}
