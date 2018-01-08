package a3;

/**
 * Exception to throw when a field is empty
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class EmptyFieldException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public EmptyFieldException() {
        super("Required field is empty\n");
    }

    /**
     * Constructor to add specific message to the exception
     *
     * @param msg the detail message.
     */
    public EmptyFieldException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
