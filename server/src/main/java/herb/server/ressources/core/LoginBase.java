package herb.server.ressources.core;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class LoginBase {
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
	
	public abstract PlayerBase login() throws ExceptionBase;
	
	public abstract void register() throws ExceptionBase;

}
