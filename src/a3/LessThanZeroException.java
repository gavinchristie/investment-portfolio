package a3;

/**
 * Exception to throw when a required value is less than zero
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class LessThanZeroException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public LessThanZeroException() {
        super("The number entered is less than zero.");
    }

    /**
     * Constructor to add specific message to the exception
     *
     * @param msg the detail message.
     */
    public LessThanZeroException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
