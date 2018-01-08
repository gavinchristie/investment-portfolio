package a3;

/**
 * Exception to throw when a search yields no results
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class SearchEmptyException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public SearchEmptyException() {
        super("No results found for search.");
    }

    /**
     * Constructor to add specific message to the exception
     *
     * @param msg the detail message.
     */
    public SearchEmptyException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
