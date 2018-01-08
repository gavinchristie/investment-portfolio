package a3;

/**
 * This class describes a stock that can be purchased
 *
 * @author Gavin Christie
 * @version 2.0
 * Created: November 10th 2017
 */
public class Stock extends Investment {

    private String symbol;

    /**
     * This creates a new stock
     * @param symbol The symbol of the stock
     * @param name The name of the stock
     * @param quantity The quantity of the stock
     * @param price The price per share
     * @throws a3.EmptyFieldException Exception thrown when a field is empty
     * @throws a3.LessThanZeroException Exception that is thrown when a field is less than zero
     */
    public Stock(String symbol, String name, int quantity, double price) throws EmptyFieldException, LessThanZeroException {
        super("stock", symbol, name, quantity, price);
        super.setBookValue(calculateBookValue(price, quantity));
    }

    /**
     * This creates a new stock
     * @param symbol The symbol of the stock
     * @param name The name of the stock
     * @param quantity The quantity of the stock
     * @param price The price per share
     * @param bookValue The book value already calculated
     * @throws a3.EmptyFieldException Exception thrown when a field is empty
     * @throws a3.LessThanZeroException Exception that is thrown when a field is less than zero
     */
    public Stock(String symbol, String name, int quantity, double price, double bookValue) throws EmptyFieldException, LessThanZeroException {
        super("stock", symbol, name, quantity, price);
        super.setBookValue(bookValue);
    }

    /**
     * This function is used to calculate the book value of a stock
     *
     * @param price The price per stock
     * @param quantity The quantity of stock being purchased
     * @return Returns total price of that quantity of stock
     */
    public static double calculateBookValue(double price, int quantity) {
        return (price * (double) (quantity)) + 9.99;
    }

    /**
     * Update the values of the stock after a sale
     *
     * @param quantity The quantity of stocks sold
     * @param price The price per stock
     * @return Returns the payment received
     */
    public double updateSellStock(int quantity, double price) {
        double sellValue = getBookValue() - getBookValue() * ((double) (getQuantity() - quantity) / getQuantity());
        /* If the quantity sold equals current number of shares set quantity and bookValue to zero */
        if (quantity == getQuantity()) {
            setQuantity(0);
            setBookValue(0);
            return sellValue;
        }
        /* Calculate values */
        setBookValue(getBookValue() * ((double) (getQuantity() - quantity) / getQuantity()));
        setQuantity(this.getQuantity() - quantity);
        setPrice(price);
        return sellValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!Stock.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        
        final Stock s = (Stock)o;
        if (!super.equals(s)) {
            return false;
        }
        return true;
    }
}

/* Last Modified: November 10th 2017 */