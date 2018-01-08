package a3;

/**
 * Exception to throw when a list needs to contain at least one item but is empty
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class EmptyListException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public EmptyListException() {
        super("The list is empty");
    }

    /**
     * Constructor to add specific message to the exception
     * 
     * @param msg the detail message.
     */
    public EmptyListException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
