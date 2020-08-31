package bowling.exception;

/**
 * Bussiness bowling exception class.
 */
public class BowlingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constr.
     * @param message message
     */
    public BowlingException(String message){
        super(message);
    }

    /**
     * Constr2.
     * @param message message
     * @param error error
     */
    public BowlingException(String message, Exception error){
        super(message, error);
    }
}
