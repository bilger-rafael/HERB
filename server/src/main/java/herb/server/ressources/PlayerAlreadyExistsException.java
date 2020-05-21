package herb.server.ressources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import herb.server.ressources.core.ExceptionBase;
//Bilger
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Player Already Exists")
public class PlayerAlreadyExistsException extends ExceptionBase {

}
