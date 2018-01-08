package a3;

/**
 * This class describes a mutual fund
 * @author Gavin Chritie
 * @version 2.0
 * Created: November 10th 2017
 */
public class MutualFund extends Investment {
    
    /**
     * This creates a new mutual fund
     * @param symbol The symbol of the mutual fund
     * @param name The name of the mutual fund
     * @param quantity The quantity of the mutual fund
     * @param price The price per share
     * @throws a3.EmptyFieldException Exception thrown when a field is empty
     * @throws a3.LessThanZeroException Exception that is thrown when a field is less than zero
     */
    public MutualFund(String symbol, String name, int quantity, double price) throws EmptyFieldException, LessThanZeroException {
        super("mutualfund", symbol, name, quantity, price);
        super.setBookValue(calculateBookValue(price, quantity));
    }
    
    /**
     * This creates a new mutual fund
     * @param symbol The symbol of the mutual fund
     * @param name The name of the mutual fund
     * @param quantity The quantity of the mutual
     * @param price The price per share
     * @param bookValue The book value that has been previously calculated
     * @throws a3.EmptyFieldException Exception thrown when a field is empty
     * @throws a3.LessThanZeroException Exception that is thrown when a field is less than zero
     */
    public MutualFund(String symbol, String name, int quantity, double price, double bookValue) throws EmptyFieldException, LessThanZeroException {
        super("mutualfund", symbol, name, quantity, price);
        super.setBookValue(bookValue);
    }
    
    /**
     * This function is used to calculate the book value of a stock
     * @param price The price per stock
     * @param quantity The quantity of stock being purchased
     * @return Returns total price of that quantity of stock
     */
    public static double calculateBookValue (double price, int quantity) {
        return (price * (double)(quantity));
    }
    
    /**
     * Update the mutual fund after the purchase of shares
     * @param quantity The quantity purchased
     * @param price The price per share at time of purchase
     */
    public void updateBuyMF(int quantity, double price) {
        double newCost = calculateBookValue(price, quantity); // Calculate the cost of the new shares
        setBookValue(getBookValue() + newCost);
        setQuantity(getQuantity() + quantity);
        setPrice(price);
    }
    
    /**
     * Update the mutual fund after selling shares
     * @param quantity The quantity of shares sold
     * @param price The price at the time of the sale
     * @return 
     */
    public double updateSellMutualFund(int quantity, double price) {
        double sellValue = getBookValue() - getBookValue() * ((double)(getQuantity() - quantity) / getQuantity());
        /* If selling remaining shares set new quantity and book value to zero */
        if (quantity == getQuantity()) {
            setQuantity(0);
            setBookValue(0);
            return sellValue;
        }
        /* Calculating new values */
        setBookValue(getBookValue() * ((double)(getQuantity() - quantity) / getQuantity()));
        setQuantity(quantity);
        setPrice(price);
        return sellValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!MutualFund.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        
        final MutualFund m = (MutualFund)o;
        if (!super.equals(m)) {
            return false;
        }
        return true;
    }
}

/* Last Modified: November 10th 2017 */