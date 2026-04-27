package uv.lis.professionalpracticesystem.exceptions;

/**
 *
 * @author Maria Jose
 */
public class DatabaseSystemException extends Exception {
    
    public DatabaseSystemException(String message) {
        super(message);
    }
    
    public DatabaseSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
