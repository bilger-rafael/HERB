package herb.server.ressources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import herb.server.ressources.core.ExceptionBase;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Login Failed")
public class PlayerLoginFailedException extends ExceptionBase {

}
