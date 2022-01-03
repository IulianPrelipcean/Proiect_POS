package pos.examples.soap.stateless.Exception;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class TokenException {
    private Boolean tokenExpire = null;
    private Boolean missingToken = null;
    private Boolean invalidToken = null;
}
