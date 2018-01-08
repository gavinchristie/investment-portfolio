package a3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a portfolio which stores a list of investments.
 *
 * @author Gavin Christie
 * @version 3.0 Created: November 10th 2017
 */
public class Portfolio {

    private String filePath;
    private ArrayList<Investment> investments;
    private HashMap<String, ArrayList<Integer>> keywordMap;
    private HashMap<String, Integer> symbolMap;
    private static final DecimalFormat df = new DecimalFormat("#0.00"); // Formating for decimal places
    private String delim = " \\t\\n\\r\\f,.:;-"; // Delimiters used to break strings 

    /**
     * Creates a new instance of a portfolio
     */
    public Portfolio() {
        this.investments = new ArrayList<Investment>();
        this.keywordMap = new HashMap<String, ArrayList<Integer>>();
        this.symbolMap = new HashMap<String, Integer>();
        this.filePath = "No File Name";
    }

    /**
     * Creates a new instance of a portfolio
     *
     * @param path String with path to the file the user wants to use
     */
    public Portfolio(String path) {
        this.investments = new ArrayList<Investment>();
        this.keywordMap = new HashMap<String, ArrayList<Integer>>();
        this.symbolMap = new HashMap<String, Integer>();
        this.filePath = new String(path);
    }

    /**
     * Method to buy more stock or mutual funds
     *
     * @param type The type of investment being purchased (Stock or Mutual Fund)
     * @param symbol The symbol of the investment being purchased
     * @param name The name of the investment being purchased
     * @param price The price per share of the investment being purchased
     * @param quantity The number of shares being purchased
     * @return Returns string representing the purchase order
     * @throws a3.LessThanZeroException When quantity or price is less than
     * zero, the variable is specified in the error message
     * @throws a3.EmptyFieldException When type, symbol, or name is empty, field
     * is specified in the error message
     */
    public String buy(String type, String symbol, String name, String price, String quantity) throws LessThanZeroException, EmptyFieldException {
        /* Declare variables */
        int index;
        int numShares;
        double pricePer, newCost = 0;
        String purchaseOrder;

        /* Check if the type is empty */
        if (type.isEmpty()) {
            throw new EmptyFieldException("No type entered. Please try again");
        }

        /* Check if the symbol is empty */
        if (symbol.isEmpty()) {
            throw new EmptyFieldException("No symbol entered. Please try again.");
        }

        /* Parse the quantity as an integer */
        try {
            numShares = (int) Double.parseDouble(quantity);
        } catch (NumberFormatException e) {
            /* Throw number format exception when it won't parse */
            throw new NumberFormatException("\"" + quantity + "\"" + " is not a valid quantity. Please try again.");
        }

        /* Parse the price as a double */
        try {
            pricePer = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            /* Throw number format exception when it won't parse */
            throw new NumberFormatException("\"" + price + "\"" + " is not a valid price. Please try again.");
        }

        /* If the pricePer share is less than zero throw exception */
        if (pricePer < 0) {
            throw new LessThanZeroException("Price is less than zero. Please try again.");
        }

        /* If the quantity is less than zero throw exception */
        if (numShares < 0) {
            throw new LessThanZeroException("Quantity is less than zero. Please try again.");
        }

        /* Find the index of the symbol in the arraylist */
        index = alreadyInvested(symbol);

        if (index != -1) {
            /* Update the book value to simulate buy and get the cost */
            newCost = this.investments.get(index).updateBuy(numShares, pricePer);
        } else {
            /* Checking if the name is empty before continuing */
            if (name.isEmpty()) {
                throw new EmptyFieldException("No name entered. Please try again.");
            }

            /* Add the new investment */
            if (type.equalsIgnoreCase("Stock")) {
                /* Add new stock */
                investments.add(new Stock(symbol, name, numShares, pricePer));
            } else {
                /* Add new mutual fund */
                investments.add(new MutualFund(symbol, name, numShares, pricePer));
            }

            /* Get the index of the new investment, which will always be the last element in the list size .add() is used */
            index = this.investments.size() - 1;

            /* Get the cost of the purchase */
            newCost = this.investments.get(index).getBookValue();

            /* Add symbol to the symbols hashmap */
            this.symbolMap.put(symbol, index);

            /* Declare variables to StringTokenize the name of the investment */
            String nextElement;
            StringTokenizer nameToken = new StringTokenizer(name, delim);
            ArrayList<Integer> indexs;

            /* While there are more parts of the name keep adding to the hashmap */
            while (nameToken.hasMoreElements()) {
                nextElement = nameToken.nextToken();
                indexs = this.keywordMap.get(nextElement);
                if (indexs != null) {
                    indexs.add(index);
                    this.keywordMap.replace(nextElement, indexs);
                } else {
                    indexs = new ArrayList<Integer>();
                    indexs.add(index);
                    this.keywordMap.put(nextElement, indexs);
                }
            }
        }

        /* Return the purchase order */
        return "Purchase order:\nSymbol: " + symbol + " Name: " + name + " Quantity: " + quantity + " Price: $" + Investment.getDf().format(pricePer) + "\nTotal cost: $" + newCost;
    }

    /**
     * Method to sell stocks or mutual funds
     *
     * @param symbol The symbol of the investment being sold
     * @param price The price of the investment being sold
     * @param quantity The number of shares being sold
     * @return Returns the sale order as a string
     * @throws a3.SymbolNotFoundException Exception thrown when no investment
     * has a matching symbol
     * @throws a3.EmptyFieldException Exception thrown when one of the fields is
     * empty
     * @throws a3.InvalidQuantityException Exception thrown when the quantity is
     * to big or to small
     * @throws a3.EmptyListException Exception thrown when the list of
     * investments is empty
     */
    public String sell(String symbol, String price, String quantity) throws SymbolNotFoundException, EmptyFieldException, InvalidQuantityException, EmptyListException {
        /* Declare variables */
        int index = 0, numSharesToSell;
        double payment = 0, pricePerShare;
        String returnInfo;

        /* If the list is empty throw exception */
        if (this.investments.isEmpty()) {
            throw new EmptyListException("There are no investements to sell.");
        }

        /* Check if the symbol is empty */
        if (symbol.isEmpty()) {
            throw new EmptyFieldException("No symbol entered. Please try again.");
        }

        /* Check if there is an investment with that symbol, get its index */
        try {
            index = this.symbolMap.get(symbol);
        } catch (NullPointerException e) {
            /* If the symbol doesn't exist throw exception*/
            throw new SymbolNotFoundException("The ssymbol \"" + symbol + "\" was not found in portfolio");
        }

        /* Parse the quantity as an integer */
        try {
            numSharesToSell = (int) Double.parseDouble(quantity);
        } catch (NumberFormatException e) {
            /* Throw number format exception when it won't parse */
            throw new NumberFormatException("\"" + quantity + "\"" + " is not a valid quantity. Please try again.");
        }

        /* Check that the quantity is less than the number of owned shares */
        if (numSharesToSell > this.investments.get(index).getQuantity()) {
            throw new InvalidQuantityException("The number of shares to sell must be less than: " + this.investments.get(index).getQuantity());
        }

        /* Check that the number of shares to be sold is greater than zero */
        if (numSharesToSell < 0) {
            throw new InvalidQuantityException("The number of shares to sell must be greater than zero.");
        }

        /* Parse the price as a double */
        try {
            pricePerShare = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            /* Throw number format exception when it won't parse */
            throw new NumberFormatException("\"" + price + "\"" + " is not a valid price. Please try again.");
        }

        /* Check that the price is bigger than zero */
        if (pricePerShare < 0) {
            throw new NumberFormatException("Price must be greatere than zero.");
        }

        /* Updating the investment */
        payment = this.investments.get(index).updateSell(numSharesToSell, pricePerShare);

        /* Set the string to be returned */
        returnInfo = "Sale order:\nSymbol: " + symbol + " Name: " + this.investments.get(index).getName() + " Quantity: " + quantity + " Price: $" + df.format(pricePerShare) + "\nPayment received: $" + df.format(payment);

        /* If all shares are sold remove from list and update both HashMaps */
        if (this.investments.get(index).getQuantity() == 0) {
            returnInfo = returnInfo + "\nAll shares sold. Investment has been removed from the list.";
            this.investments.remove(index);
            updateAfterSell(index);
        }

        return returnInfo;
    }

    /**
     * Method that controls the searching of stocks and mutual funds
     *
     * @param symbol The symbol being searched for
     * @param keywords The keywords being searched for
     * @param priceLow The lower bound on the price
     * @param priceHigh The upper bound on the price
     * @return Returns string with formated results of the search
     * @throws a3.LessThanZeroException Exception thrown when a price is less
     * than zero
     * @throws a3.SymbolNotFoundException Exception thrown when the symbol is
     * not found in the search
     * @throws a3.SearchEmptyException Exception thrown when the search returns
     * no results
     * @throws a3.EmptyListException Exception thrown when the list of
     * investments is empty
     */
    public String searchControl(String symbol, String keywords, String priceLow, String priceHigh) throws LessThanZeroException, SymbolNotFoundException, SearchEmptyException, EmptyListException {
        /* Declare variables */
        double lowPrice, highPrice;
        ArrayList<Integer> indexs;
        String returnMessage = "";

        /* Throw exception if the list is empty */
        if (this.investments.isEmpty()) {
            throw new EmptyListException("No investments to search");
        }

        if (priceLow.isEmpty()) {
            lowPrice = 0;
        } else {
            /* Cast the lower price bound as a double */
            try {
                lowPrice = Double.parseDouble(priceLow);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("\"" + priceLow + "\" is not a valid lowest price");
            }
            /* Check that lowest price is above zero */
            if (lowPrice < 0) {
                throw new LessThanZeroException("Lower price must be greater than zero");
            }
        }

        if (priceHigh.isEmpty()) {
            highPrice = Double.MAX_VALUE;
        } else {
            /* Cast the upper price bound as a double */
            try {
                highPrice = Double.parseDouble(priceHigh);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("\"" + priceLow + "\" is not a valid highest price");
            }
            /* Check that the upper price is above zero */
            if (highPrice < 0) {
                throw new LessThanZeroException("Upper price must be greater than zero");
            }
        }

        /* Check that lower price is less than or equal to the upper price */
        if (lowPrice > highPrice) {
            throw new NumberFormatException("Lower price must be less than upper price.");
        }

        try {
            /* Get the indexs that match the search parameters */
            indexs = search(symbol, keywords, lowPrice, highPrice);
        } catch (SymbolNotFoundException | SearchEmptyException ex) {
            throw ex;
        }

        /* If no indexs are returned, tell the user */
        if (indexs.isEmpty()) {
            returnMessage = "No results found.\n";
        } else {
            /* For every index in the ArrayList add the investments information to the return string */
            for (int i : indexs) {
                returnMessage = returnMessage + "\n" + this.investments.get(i).toString();
            }
        }

        /* Return the string */
        return returnMessage;
    }

    /**
     * Method to find all of the investments that meet the searching
     * requirements
     *
     * @param symbol The symbol being searched for
     * @param name The name being searched for
     * @param lowerBound The lower bound of the price
     * @param upperBound The upper bound of the price
     * @return Returns an ArrayList of integers represented the indexs of the
     * investments
     * @throws a3.SymbolNotFoundException Thrown when there is no matching
     * symbol
     * @throws a3.SearchEmptyException Thrown when search has no results
     */
    private ArrayList<Integer> search(String symbol, String name, double lowerBound, double upperBound) throws SymbolNotFoundException, SearchEmptyException {
        ArrayList<Integer> indexs = new ArrayList<Integer>();
        int symIndex = -1;
        boolean hasSymbol = false;

        /* If there is a symbol to search for */
        if (!symbol.isEmpty()) {
            try {
                /* Try and find the symbol in the hashmap */
                symIndex = this.symbolMap.get(symbol);
            } catch (NullPointerException e) {
                /* Catch when the symbol cannot be found */
 /* Return the empty arraylist if there is not matching symbol */
                throw new SymbolNotFoundException("Symbol \"" + symbol + "\" not found in the list.");
            }
            hasSymbol = true;
        }

        /* If there is a symbol, only the index of the symbol should be in the list */
        if (hasSymbol) {
            indexs.add(symIndex);
        } else {
            /* Otherwise add every index to indexs ArrayList */
            for (int i = 0; i < this.investments.size(); i++) {
                indexs.add(i);
            }
        }

        /* If there are keywords search for them */
        if (!name.isEmpty()) {
            /* Declare variables to StringTokenize the name of the investment */
            String nextElement;
            StringTokenizer nameToken = new StringTokenizer(name, delim);
            while (nameToken.hasMoreElements()) {
                /* Get the next keyword and check if it is in the hashmap */
                nextElement = nameToken.nextToken();
                ArrayList<Integer> temp = this.keywordMap.get(nextElement);
                if (temp != null) {
                    /* For every index in ArrayList check if the keyword has the same index */
                    for (int i = 0; i < indexs.size(); i++) {
                        boolean add = false;
                        for (int j = 0; j < temp.size(); j++) {
                            if (indexs.get(i) == temp.get(j)) {
                                add = true;
                                break;
                            }
                        }
                        /* Remove any none common members in indexs ArrayList */
                        if (!add) {
                            indexs.remove(i);
                            i--;
                        }
                    }
                }
                /* If the list is empty return null */
                if (indexs.isEmpty()) {
                    throw new SearchEmptyException("No matching results found.");
                }
            }
        }

        /* Iterate through every index saved so far */
        Iterator<Integer> iterator = indexs.iterator();

        /* While there is a next element */
        while (iterator.hasNext()) {
            /* Get the next index to check and the price at that index */
            int current = iterator.next();
            double currentPrice = this.investments.get(current).getPrice();
            if (currentPrice < lowerBound || currentPrice > upperBound) {
                /* If the price is outside of the bounds remove it from the list */
                iterator.remove();
            }
        }

        /* If there is no indexs return null */
        if (indexs.isEmpty()) {
            throw new SearchEmptyException("No matching results found.");
        }

        return indexs;
    }

    /**
     * Method used to get the symbol and name of an investment
     *
     * @param index The index of the information being looked for
     * @return Returns string with name and symbol of investment delimited by
     * ",,"
     */
    public String displayInvestment(int index) {
        /* Declare variables */
        String returnInfo = "";
        Investment toBeDisplayed;

        /* If the user tries to access an negative index throw exception */
        if (index < 0) {
            throw new IndexOutOfBoundsException("No more previous investments in list.");
        }

        /* If the user tries to access index greater than size of the list throw an exception */
        if (index >= this.investments.size()) {
            throw new IndexOutOfBoundsException("No more next investments in the list.");
        }

        /* Get the investment from the list */
        toBeDisplayed = this.investments.get(index);

        /* Get the required information from the investment */
        if (toBeDisplayed != null) {
            returnInfo = toBeDisplayed.getSymbol() + ",," + toBeDisplayed.getName();
        } else {
            returnInfo = "No information,,No Information";
        }

        return returnInfo;
    }

    /**
     * Update the price of every stock and mutual fund
     *
     * @param currentIndex The index of the investment being updated
     * @param price The new price for the investment
     * @return String with the information of the updated investment
     */
    public String update(int currentIndex, String price) {
        /* Declare variables */
        String returnInfo;
        double pricePer;

        /* If the user tries to access an negative index throw exception */
        if (currentIndex < 0) {
            throw new IndexOutOfBoundsException("No more previous investments in list.");
        }

        /* If the user tries to access index greater than size of the list throw an exception */
        if (currentIndex >= this.investments.size()) {
            throw new IndexOutOfBoundsException("No more next investments in the list.");
        }

        /* Convert price from string to double */
        try {
            pricePer = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"" + price + "\" is not a valid price.");
        }

        /* Check that the price is bigger than zero */
        if (pricePer < 0) {
            throw new NumberFormatException("Price must be greater than zero.");
        }

        this.investments.get(currentIndex).setPrice(pricePer);

        returnInfo = "New Price: $" + pricePer + "\n" + this.investments.get(currentIndex).toString();

        return returnInfo;
    }

    /**
     * Get the total gain on everything user owns based on current prices
     *
     * @return Return the net gain as double
     * @throws a3.EmptyListException Exception thrown when the list is empty
     */
    public String[] getTotalGain() throws EmptyListException {
        double totalGain = 0;
        String[] returnValue = new String[2];

        /* If the list is empty throw exception */
        if (this.investments.isEmpty()) {
            throw new EmptyListException("There are no investments to calculate the gain for.");
        }

        /* For every stock and mutual fund calculate the gain */
        for (int i = 0; i < this.investments.size(); i++) {
            /* Get the current investments gain and add it to the total */
            double currentGain = Investment.getGain(this.investments.get(i));
            totalGain += currentGain;
            returnValue[1] += "\n" + this.investments.get(i).getSymbol() + ": $" + df.format(currentGain);
        }

        returnValue[0] = df.format(totalGain);

        return returnValue;
    }

    /**
     * Method to update the indexs in the keyword hashmap and the symbol hashmap
     * after all the shares of an investment are sold.
     *
     * @param index The index in the investment ArrayList that the investment
     * was stored at
     */
    private void updateAfterSell(int index) {
        /* Create iterator for the keywords hashmap */
        Iterator iterator = this.keywordMap.entrySet().iterator();
        /* Loop through each entry */
        while (iterator.hasNext()) {
            /* Get the ArrayList of indexs and iterator through all of those */
            Map.Entry entry = (Map.Entry) iterator.next();
            ArrayList<Integer> indexs = (ArrayList<Integer>) entry.getValue();
            for (int i = 0; i < indexs.size(); i++) {
                if (indexs.get(i) == index) {
                    /* If the stored index is equal to index being search remove it */
                    indexs.remove(i);
                    i--;
                } else if (indexs.get(i) > index) {
                    /* If the index is bigger than the index searched subtract one */
                    int wrongIndex = indexs.get(i);
                    wrongIndex--;
                    indexs.set(i, wrongIndex);
                }
            }
            /* Set the ArrayList in the hashmap to the updated version */
            entry.setValue(indexs);
        }

        /* Create an iterator for the symbol hashmap and loop through all entries */
        iterator = this.symbolMap.entrySet().iterator();
        while (iterator.hasNext()) {
            /* Get the index stored */
            Map.Entry entry = (Map.Entry) iterator.next();
            int wrongIndex = (int) entry.getValue();
            if (wrongIndex == index) {
                /* If the index is equal to index being searched remove it */
                iterator.remove();
            } else if (wrongIndex > index) {
                /* If the index is > index being searched subtract one */
                wrongIndex--;
                entry.setValue(wrongIndex);
            }
        }
    }

    /**
     * This method checks if the user has an investment with the provided symbol
     *
     * @param symbol The symbol being searched for
     * @return Returns index in list if the symbol already exists or -1 if it
     * doesn't
     */
    private int alreadyInvested(String symbol) {
        int index;
        /* Find the symbol in the hashmap and get the corresponding index */
        try {
            index = this.symbolMap.get(symbol);
        } catch (NullPointerException e) {
            return -1;
        }
        /* Return the index given */
        return index;
    }

    /**
     * Method to read from the file name provided by the user
     *
     * @throws java.io.FileNotFoundException
     */
    public void readFromFile() throws FileNotFoundException {
        Scanner inputStream;
        boolean add;

        if (this.filePath.equals("No File Name")) {
            throw new FileNotFoundException();
        }

        /* Try to open the file for reading */
        try {
            inputStream = new Scanner(new FileInputStream(this.filePath));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Something went wrong opening: " + this.filePath);
        }

        /* While there is another line in the file to get */
        while (inputStream.hasNext()) {
            add = true;
            /* Initialize variables */
            Investment invest = null;
            String type, symbol, name, shares, price, book;
            int numShares;
            double pricePerShare, bookValue;

            /* Get all the variables for a new investment */
            type = inputStream.nextLine();
            symbol = inputStream.nextLine();
            name = inputStream.nextLine();
            shares = inputStream.nextLine();
            price = inputStream.nextLine();
            book = inputStream.nextLine();

            numShares = Integer.parseInt(shares);
            pricePerShare = Double.parseDouble(price);
            bookValue = Double.parseDouble(book);

            /* Create new invesetment based on the type */
            if (type.equalsIgnoreCase("stock")) {
                try {
                    invest = new Stock(symbol, name, numShares, pricePerShare, bookValue); // New stock
                } catch (EmptyFieldException | LessThanZeroException ex) {
                    add = false;
                }
            } else if (type.equalsIgnoreCase("mutualfund")) {
                try {
                    invest = new MutualFund(symbol, name, numShares, pricePerShare, bookValue);// New mutual fund
                } catch (EmptyFieldException | LessThanZeroException ex) {
                    add = false;
                }
            }
            if (add) {
                this.investments.add(invest); // Add the new investment to the list

                /* Get the index of the newly added investment */
                int investmentIndex = this.investments.indexOf(invest);

                /* Declare variables to StringTokenize the name of the investment */
                String nextElement;
                StringTokenizer nameToken = new StringTokenizer(name, delim);
                ArrayList<Integer> indexs;

                /* While there are more parts of the name keep adding to the hashmap */
                while (nameToken.hasMoreElements()) {
                    nextElement = nameToken.nextToken();
                    indexs = this.keywordMap.get(nextElement);
                    if (indexs != null) {
                        indexs.add(investmentIndex);
                        this.keywordMap.replace(nextElement, indexs);
                    } else {
                        indexs = new ArrayList<Integer>();
                        indexs.add(investmentIndex);
                        this.keywordMap.put(nextElement, indexs);
                    }
                }
                /* Add to symbol map */
                this.symbolMap.put(symbol, investmentIndex);
            }
        }
        /* Close the file */
        inputStream.close();
    }

    /**
     * Method to write all the information stored in the ArrayList to the file
     *
     * @throws a3.NoFilePathException
     * @throws java.io.FileNotFoundException
     */
    public void writeToFile() throws NoFilePathException, FileNotFoundException {
        /* If the path is empty prompt the user for one */
        if (this.filePath.isEmpty() || this.filePath.equals("No File Name")) {
            throw new NoFilePathException("No file path was provided.");
        }

        /* Create the print writer to write to the file */
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(this.filePath));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("There was a problem opening the file: " + this.filePath);
        }

        /* Create an iterator to loop through whole ArrayList */
        Iterator<Investment> iterator = this.investments.iterator();
        /* Write all the information from the ArrayList to the file */
        while (iterator.hasNext()) {
            /* Write all the current investments information to the file */
            Investment write = iterator.next();
            pw.println(write.getType());
            pw.println(write.getSymbol());
            pw.println(write.getName());
            pw.println(write.getQuantity());
            pw.println(write.getPrice());
            pw.println(df.format(write.getBookValue()));
        }
        /* Close the file once everything is written */
        pw.close();
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
