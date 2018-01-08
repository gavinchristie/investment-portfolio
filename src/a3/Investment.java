package a3;

import java.text.DecimalFormat;

/**
 * This class is the super class for stocks and mutual funds
 *
 * @author Gavin Christie
 * @version 3.0
 * Created: November 10th 2017
 */
public abstract class Investment {

    private String type; // The type of the investment
    private String symbol; // Unique symbol representing the stock
    private String name; // The name of the stock
    private int quantity; // The quantity of stock currently owned
    private double price; // Most recent price per share
    private double bookValue; // The price paid for current quantity
    private static final DecimalFormat df = new DecimalFormat("#0.00"); // Formating for decimal values

    /**
     * Constructor to make a new investment with a given type
     *
     * @param type The type of investment (stock or mutual fund)
     * @param symbol The unique symbol representing the stock
     * @param name Name of stock
     * @param quantity The quantity initially purchased
     * @param price The price per share at time of purchase
     * @throws a3.EmptyFieldException Exception thrown when a field is empty
     * @throws a3.LessThanZeroException Exception that is thrown when a field is less than zero
     */
    public Investment(String type, String symbol, String name, int quantity, double price) throws EmptyFieldException, LessThanZeroException {
        /* Check valid type */
        if (type.isEmpty()) {
            throw new EmptyFieldException("Type cannot be empty.");
        }
        /* Check valid symbol */
        if (symbol.isEmpty()) {
            throw new EmptyFieldException("Symbol cannot be empty.");
        }
        /* Check valid name */
        if (name.isEmpty()) {
            throw new EmptyFieldException("Name cannot be empty.");
        }
        /* Check valid quantity */
        if (quantity < 0) {
            throw new LessThanZeroException("The quantity cannot be less than zero.");
        }
        /* Check valid price */
        if (price < 0) {
            throw new LessThanZeroException("The price per share cannot be less than zero.");
        }
        /* Set all the values */
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    /**
     * Update the values of the investment after a purchase
     * @param quantity The number of stocks purchased
     * @param price  The price per stock
     * @return The cost of purchase
     */
    public double updateBuy(int quantity, double price) {
        double newCost;
        if (this instanceof Stock) {
            /* Calculate the purchased book value if it is a stock */
            newCost = Stock.calculateBookValue(price, quantity);
        } else if (this instanceof MutualFund) {
            /* Calculate the purchased book value if it is a mutual fund */
            newCost = MutualFund.calculateBookValue(price, quantity);
        } else {
            /* When it is not a stock or a mutual fund */
            System.out.println("Cannot calculate new book value");
            return -1;
        }
        /* Update the values */
        this.bookValue += newCost;
        this.quantity += quantity;
        this.price = price;
        return newCost;
    }
    
    /**
     * Updates the values of a stock or mutual fund after sell
     * @param quantity The quantity of shares sold
     * @param price The price per share sold
     * @return Returns the payment received for the sale
     */
    public double updateSell(int quantity, double price) {
        double sellValue = 0;
        if (this instanceof Stock) {
            /* Calculate the book value of a stock after sell */
            sellValue = ((Stock)this).updateSellStock(quantity, price);
        } else if (this instanceof MutualFund) {
            /* Calculate the book value of a mutual fund after sell */
            sellValue = ((MutualFund)this).updateSellMutualFund(quantity, price);
        }
        return sellValue;
    }

    /**
     * Get the gain of an investment
     *
     * @param i The investment being used to calculate the gain
     * @return Return the gain, or 0 if the investment is neither a mutual fund or a stock
     */
    public static double getGain(Investment i) {
        if (i instanceof Stock) {
            Stock s = (Stock)i;
            return (s.getPrice() * s.getQuantity() - 9.99) - s.getBookValue();
        } else if (i instanceof MutualFund) {
            MutualFund m = (MutualFund)i;
            return (m.getPrice() * m.getQuantity() - 45) - m.getBookValue();
        }
        
        return 0;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the bookValue
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * @param bookValue the bookValue to set
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * @return the df
     */
    public static DecimalFormat getDf() {
        return df;
    }
    
    public boolean equals(Object o) {
        Investment i = (Investment)o;
        if (this.name.equals(i.name) && this.symbol.equals(i.symbol)) {
            return true;
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "Type: " + this.type + " Symbol: " + this.symbol + " Name: " + this.name + " Price: $" + df.format(this.price) + " Quantity: " + this.quantity + " Book Value: $" + df.format(this.bookValue);
    }
}

/* Last Modified: November 26th 2017 */