package a3;

/**
 * Exception thrown when there is no provided file name
 * @author Gavin Christie
 */
public class NoFilePathException extends Exception {

    /**
     * Creates new exception with generic message
     */
    public NoFilePathException() {
        super("No path to the file provided.");
    }

    /**
     * Creates new exception
     *
     * @param msg the detail message.
     */
    public NoFilePathException(String msg) {
        super(msg);
    }
}
