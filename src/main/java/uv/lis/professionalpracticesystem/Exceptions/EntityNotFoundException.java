package uv.lis.professionalpracticesystem.exceptions;

/**
 *
 * @author Maria Jose
 */
public class EntityNotFoundException extends DatabaseSystemException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
