package bowling.exception;

public class BowlingException extends RuntimeException {
    /**
     * Serial.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Cons.
     * @param message message
     */
    public BowlingException(String message){
        super(message);
    }

    /**
     * Cons2.
     * @param message message
     * @param error error
     */
    public BowlingException(String message, Exception error){
        super(message, error);
    }
}
