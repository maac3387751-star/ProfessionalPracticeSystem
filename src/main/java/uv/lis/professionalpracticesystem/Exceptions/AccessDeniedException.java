package uv.lis.professionalpracticesystem.exceptions;

/**
 *
 * @author Maria Jose
 */
public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message){
        super(message);
    }
    
    public AccessDeniedException(String message, Throwable cause){
        super(message,cause);
    }
}
