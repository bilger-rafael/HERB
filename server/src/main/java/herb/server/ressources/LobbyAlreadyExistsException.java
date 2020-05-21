package herb.server.ressources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import herb.server.ressources.core.ExceptionBase;

//Bilger
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server Error")
public class LobbyAlreadyExistsException extends ExceptionBase {

}
