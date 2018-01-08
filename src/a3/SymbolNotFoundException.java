package a3;

/**
 * Exception to throw when the symbol of an investment is not found
 * @author Gavin Christie
 * @version 1.0
 * Created: November 26th 2017
 */
public class SymbolNotFoundException extends Exception {

    /**
     * Constructor to give error generic message
     */
    public SymbolNotFoundException() {
        super("Symbol not found");
    }

    /**
     * Constructor to add specific message to the exception
     * 
     * @param msg the detail message.
     */
    public SymbolNotFoundException(String msg) {
        super(msg);
    }
}

/* Last Modified: November 26th 2017 */
