package herb.server.ressources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import herb.server.ressources.core.ExceptionBase;

//Bilger
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Lobby Not Found")
public class LobbyNotFoundException extends ExceptionBase {

}
