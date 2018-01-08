package a3;

/**
 * Main class for A3
 * @author Gavin Christie
 * @version 3.0
 */
public class A3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = null;
        if (args.length > 0) {
            gui = new GUI(args[0]);
        } else {
            gui = new GUI("");
        }
        gui.setVisible(true);
    }
    
}

/* Last Modified: November 26th 2017 */
