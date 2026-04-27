package uv.lis.professionalpracticesystem.exceptions;

/**
 *
 * @author Maria Jose
 */
public class DataIntegrityException extends DatabaseSystemException {
    public DataIntegrityException(String message) {
        super(message);
    }
    
    public DataIntegrityException(String message, Throwable cause) {
        super(message,cause);
    }
}
