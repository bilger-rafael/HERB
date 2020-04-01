package herb.server.ressources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import herb.server.ressources.core.ExceptionBase;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Game Not Found")
public class GameNotFoundException extends ExceptionBase {

}
