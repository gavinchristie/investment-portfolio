package a3;

/**
 * Exception to throw when the value of quantity is invalid
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class InvalidQuantityException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public InvalidQuantityException() {
        super("Quantity entered is invalid.");
    }

    /**
     * Constructor to add specific message to the exception
     *
     * @param msg the detail message.
     */
    public InvalidQuantityException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
