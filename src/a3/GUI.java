package a3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/******** TO DO **********
 * 1. ADD SCROLL BARS TO ALL TEXT AREAS
 * 3. FIX LAYOUT OF GET GAIN
 */

/**
 * Class to implement the Graphical User Interface for the Investment Portfolio
 * assignment
 * 
 * @author Gavin Christie
 * @version 1.0
 * Create: November 20th 2017
 */
public class GUI extends JFrame {
    
    /* Create a new instance of a portfolio */
    private Portfolio portfolio;
    
    /* Text for combobox */
    private final String[] comboBoxOptions = {"Stock", "Mutual Fund"};
    
    /* Declare all the main panels */
    private JPanel welcomePanel;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel updatePanel;
    private JPanel gainPanel;
    private JPanel searchPanel;
    
    /* Common labels */
    JLabel typeLabel = new JLabel("Type");
    JLabel buySymLabel = new JLabel("Symbol");
    JLabel sellSymLabel = new JLabel("Symbol");
    JLabel searchSymLabel = new JLabel("Symbol");
    JLabel symLabel = new JLabel("Symbol");
    JLabel nameLabel = new JLabel("Name");
    JLabel sellQuantLabel = new JLabel("Quantity");
    JLabel quantLabel = new JLabel("Quantity");
    JLabel buyPriceLabel = new JLabel("Price");
    JLabel sellPriceLabel = new JLabel("Price");
    JLabel priceLabel = new JLabel("Price");
    JLabel lowLabel = new JLabel("Low Price");
    JLabel highLabel = new JLabel("High Price");
    JLabel searchKeywordLabel = new JLabel("Name Keywords");
    JLabel keywordLabel = new JLabel("Name Keywords");
    JLabel gainLabel = new JLabel("Total Gain");
    
    /* Componenets of the menu */
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem buyMenuItem;
    private JMenuItem sellMenuItem;
    private JMenuItem searchMenuItem;
    private JMenuItem updateMenuItem;
    private JMenuItem gainMenuItem;
    private JMenuItem quitMenuItem;
    
    /* Components of buy screen */
    private JPanel buyTextFieldsPanel;
    private JPanel buyButtonsPanel;
    private JPanel buyMessagePanel;
    private JTextField buySymText;
    private JTextField buyNameText;
    private JTextField buyQuantText;
    private JTextField buyPriceText;
    private JComboBox buyTypeBox;
    private JButton buyButton;
    private JButton buyResetButton;
    private JTextArea buyMessagesArea;
    private JScrollPane buyMessageScroll;
    
    /* Components of the sell screen */
    private JPanel sellTextFieldsPanel;
    private JPanel sellButtonsPanel;
    private JPanel sellMessagePanel;
    private JTextField sellSymText;
    private JTextField sellQuantText;
    private JTextField sellPriceText;
    private JButton sellButton;
    private JButton sellResetButton;
    private JTextArea sellMessagesArea;
    private JScrollPane sellMessageScroll;
    
    /* Components of the search screen */
    private JPanel searchTextFieldsPanel;
    private JPanel searchButtonsPanel;
    private JPanel searchMessagePanel;
    private JTextField searchSymText;
    private JTextField searchNameText;
    private JTextField searchLowText;
    private JTextField searchHighText;
    private JButton searchButton;
    private JButton searchResetButton;
    private JTextArea searchMessageArea;
    private JScrollPane searchMessageScroll;
    
    /* Components of the update screen */
    private JPanel updateTextFieldsPanel;
    private JPanel updateButtonsPanel;
    private JPanel updateMessagePanel;
    private JTextField updateSymText;
    private JTextField updateNameText;
    private JTextField updatePriceText;
    private JButton updateNextButton;
    private JButton updatePrevButton;
    private JButton updateButton;
    private JTextArea updateMessageArea;
    private JScrollPane updateMessageScroll;
    
    /* Components of the total gain screen */
    private JPanel gainTextFieldsPanel;
    private JPanel gainMessagePanel;
    private JTextField gainText;
    private JTextArea gainMessageArea;
    private JScrollPane gainMessageScroll;
    
    /* Other instance variables */
    private int currentInvestmentIndex = 0; // Index to keep track of currently displayed investment
    private String[] totalGain; // The total gain of each stock
    
    /**
     * Method for creating the graphical user interface
     * 
     * @param path The path to the file
     */
    public GUI(String path) {
        /* Initialize the new portfolio */
        if (path.isEmpty()) {
            portfolio = new Portfolio();
        } else {
            portfolio = new Portfolio(path);
            try {
                portfolio.readFromFile();
            } catch (FileNotFoundException ex) {
                /* If reading from the file breaks do nothing */
            }
        }
        /* Create the window and set the parameters for the JFrame */
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Investment Portfolio");
        
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                writeOnClose();
            }
        };
        
        this.addWindowListener(exitListener);
        
        
        /************ Create the Sell screen ************/
        /****** Add the text fields to sell screen ******/
        this.sellPanel = new JPanel();
        this.sellPanel.setLayout(new BorderLayout());
        this.sellPanel.setVisible(false);
        this.sellTextFieldsPanel = new JPanel();
        this.sellTextFieldsPanel.setPreferredSize(new Dimension(500, 400));
        this.sellTextFieldsPanel.setLayout(new GridLayout(5,2));
        this.sellTextFieldsPanel.setBorder(BorderFactory.createTitledBorder("Selling an investment"));
        this.sellSymText = new JTextField(15);
        this.sellQuantText = new JTextField(15);
        this.sellPriceText = new JTextField(15);
        this.sellTextFieldsPanel.add(this.sellSymLabel);
        this.sellTextFieldsPanel.add(this.sellSymText);
        this.sellTextFieldsPanel.add(this.sellQuantLabel);
        this.sellTextFieldsPanel.add(this.sellQuantText);
        this.sellTextFieldsPanel.add(this.sellPriceLabel);
        this.sellTextFieldsPanel.add(this.sellPriceText);
        this.sellPanel.add(this.sellTextFieldsPanel, BorderLayout.WEST);
        
        /****** Add the buttons to sell screen ******/
        this.sellButton = new JButton("Sell");
        this.sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String saleOrder;
                try {
                    saleOrder = GUI.this.portfolio.sell(GUI.this.sellSymText.getText(), GUI.this.sellPriceText.getText(), GUI.this.sellQuantText.getText());
                } catch (SymbolNotFoundException | EmptyFieldException | InvalidQuantityException | NumberFormatException | EmptyListException ex) {
                    saleOrder = ex.getMessage();
                }
                GUI.this.sellMessagesArea.setText(saleOrder);
                resetSell();
            } 
        });
        this.sellResetButton = new JButton("Reset");
        this.sellResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetSell();
            }
        });
        this.sellButtonsPanel = new JPanel();
        this.sellButtonsPanel.setPreferredSize(new Dimension(300,400));
        this.sellButtonsPanel.add(this.sellResetButton);
        this.sellButtonsPanel.add(this.sellButton);
        this.sellPanel.add(this.sellButtonsPanel, BorderLayout.EAST);
        
        /****** Add the message text to sell screen ******/
        this.sellMessagesArea = new JTextArea(11,65);
        this.sellMessagesArea.setEditable(false);
        this.sellMessageScroll = new JScrollPane(this.sellMessagesArea);
        this.sellMessageScroll.setVisible(true);
        this.sellMessagePanel = new JPanel();
        this.sellMessagePanel.setPreferredSize(new Dimension(800,200));
        this.sellMessagePanel.setBorder(BorderFactory.createTitledBorder("Messages"));
        this.sellMessagePanel.add(this.sellMessageScroll);
        this.sellMessagePanel.setVisible(true);
        this.sellPanel.add(this.sellMessagePanel, BorderLayout.SOUTH);
        /************ End of creating Sell screen ************/
        

        
        /************ Create the Buy screen ************/
        /****** Add the text fields to buy screen ******/
        this.buyPanel = new JPanel();
        this.buyPanel.setLayout(new BorderLayout());
        this.buyPanel.setVisible(false);
        this.buyTextFieldsPanel = new JPanel();
        this.buyTextFieldsPanel.setPreferredSize(new Dimension(500, 400));
        this.buyTextFieldsPanel.setLayout(new GridLayout(5,2,-175,25));
        this.buyTextFieldsPanel.setBorder(BorderFactory.createTitledBorder("Buy an investment"));
        this.buyNameText = new JTextField(15);
        this.buySymText = new JTextField(15);
        this.buyQuantText = new JTextField(15);
        this.buyPriceText = new JTextField(15);
        this.buyTypeBox = new JComboBox(this.comboBoxOptions);
        this.buyTextFieldsPanel.add(this.typeLabel);
        this.buyTextFieldsPanel.add(this.buyTypeBox);
        this.buyTextFieldsPanel.add(this.buySymLabel);
        this.buyTextFieldsPanel.add(this.buySymText);
        this.buyTextFieldsPanel.add(this.nameLabel);
        this.buyTextFieldsPanel.add(this.buyNameText);
        this.buyTextFieldsPanel.add(this.quantLabel);
        this.buyTextFieldsPanel.add(this.buyQuantText);
        this.buyTextFieldsPanel.add(this.buyPriceLabel);
        this.buyTextFieldsPanel.add(this.buyPriceText);
        this.buyPanel.add(this.buyTextFieldsPanel, BorderLayout.WEST);
        this.add(this.buyPanel);
        
        /****** Add the buttons to buy screen ******/
        this.buyButton = new JButton("Buy");
        this.buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String printOrder;
                try {
                    printOrder = GUI.this.portfolio.buy(GUI.this.buyTypeBox.getSelectedItem().toString(), GUI.this.buySymText.getText(), GUI.this.buyNameText.getText(), GUI.this.buyPriceText.getText(), GUI.this.buyQuantText.getText());
                } catch (LessThanZeroException | NumberFormatException | EmptyFieldException e) {
                    printOrder = e.getMessage();
                }
                GUI.this.buyMessagesArea.setText(printOrder);
                resetBuy();
            } 
        });
        this.buyResetButton = new JButton("Reset");
        this.buyResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetBuy();
            }
        });
        this.buyButtonsPanel = new JPanel();
        this.buyButtonsPanel.setPreferredSize(new Dimension(300,400));
        this.buyButtonsPanel.add(this.buyResetButton);
        this.buyButtonsPanel.add(this.buyButton);
        this.buyPanel.add(this.buyButtonsPanel, BorderLayout.EAST);
        
        /****** Add the message text to buy screen ******/
        this.buyMessagesArea = new JTextArea(11,65);
        this.buyMessagesArea.setEditable(false);
        this.buyMessageScroll = new JScrollPane(this.buyMessagesArea);
        this.buyMessageScroll.setVisible(true);
        this.buyMessagePanel = new JPanel();
        this.buyMessagePanel.setPreferredSize(new Dimension(800,200));
        this.buyMessagePanel.setBorder(BorderFactory.createTitledBorder("Messages"));
        this.buyMessagePanel.add(this.buyMessageScroll);
        this.buyPanel.add(this.buyMessagePanel, BorderLayout.SOUTH);
        /************ End of creating Buy screen ************/
        
        
        
        /************ Create the Search screen ************/
        /****** Add the text fields to search screen ******/
        this.searchPanel = new JPanel();
        this.searchPanel.setLayout(new BorderLayout());
        this.searchPanel.setVisible(false);
        this.searchTextFieldsPanel = new JPanel();
        this.searchTextFieldsPanel.setPreferredSize(new Dimension(500, 400));
        this.searchTextFieldsPanel.setLayout(new GridLayout(5,2,-175,25));
        this.searchTextFieldsPanel.setBorder(BorderFactory.createTitledBorder("Searching Investments"));
        this.searchSymText = new JTextField(15);
        this.searchNameText = new JTextField(15);
        this.searchLowText = new JTextField(15);
        this.searchHighText = new JTextField(15);
        this.searchTextFieldsPanel.add(this.searchSymLabel);
        this.searchTextFieldsPanel.add(this.searchSymText);
        this.searchTextFieldsPanel.add(this.searchKeywordLabel);
        this.searchTextFieldsPanel.add(this.searchNameText);
        this.searchTextFieldsPanel.add(this.lowLabel);
        this.searchTextFieldsPanel.add(this.searchLowText);
        this.searchTextFieldsPanel.add(this.highLabel);
        this.searchTextFieldsPanel.add(this.searchHighText);
        this.searchPanel.add(this.searchTextFieldsPanel, BorderLayout.WEST);
        
        /****** Add the buttons to search screen ******/
        this.searchButton = new JButton("Search");
        this.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String outputInformation;
                try {
                    outputInformation = GUI.this.portfolio.searchControl(GUI.this.searchSymText.getText(), GUI.this.searchNameText.getText(), GUI.this.searchLowText.getText(), GUI.this.searchHighText.getText());
                } catch (LessThanZeroException | SymbolNotFoundException | SearchEmptyException | NumberFormatException | EmptyListException ex) {
                    outputInformation = ex.getMessage();
                }
                GUI.this.searchMessageArea.setText(outputInformation);
                resetSearch();
            } 
        });
        this.searchResetButton = new JButton("Reset");
        this.searchResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetSearch();
            }
        });
        this.searchButtonsPanel = new JPanel();
        this.searchButtonsPanel.setPreferredSize(new Dimension(300,400));
        this.searchButtonsPanel.add(this.searchResetButton);
        this.searchButtonsPanel.add(this.searchButton);
        this.searchPanel.add(this.searchButtonsPanel, BorderLayout.EAST);
        
        /****** Add the message text to search screen ******/
        this.searchMessageArea = new JTextArea(11,65);
        this.searchMessageArea.setEditable(false);
        this.searchMessageScroll = new JScrollPane(this.searchMessageArea);
        this.searchMessageScroll.setVisible(true);
        this.searchMessagePanel = new JPanel();
        this.searchMessagePanel.setPreferredSize(new Dimension(800,200));
        this.searchMessagePanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        this.searchMessagePanel.add(this.searchMessageScroll);
        this.searchMessagePanel.setVisible(true);
        this.searchPanel.add(this.searchMessagePanel, BorderLayout.SOUTH);
        /************ End of creating Search screen ************/
        
        
        
        /************ Create the Update screen ************/
        /****** Add the text fields to Update screen ******/
        this.updatePanel = new JPanel();
        this.updatePanel.setLayout(new BorderLayout());
        this.updatePanel.setVisible(false);
        this.updateTextFieldsPanel = new JPanel();
        this.updateTextFieldsPanel.setPreferredSize(new Dimension(500, 400));
        this.updateTextFieldsPanel.setLayout(new GridLayout(5,2,-175,25));
        this.updateTextFieldsPanel.setBorder(BorderFactory.createTitledBorder("Searching Investments"));
        this.updateSymText = new JTextField(15);
        this.updateSymText.setEditable(false);
        this.updateNameText = new JTextField(15);
        this.updateNameText.setEditable(false);
        this.updatePriceText = new JTextField(15);
        this.updateTextFieldsPanel.add(this.symLabel);
        this.updateTextFieldsPanel.add(this.updateSymText);
        this.updateTextFieldsPanel.add(this.keywordLabel);
        this.updateTextFieldsPanel.add(this.updateNameText);
        this.updateTextFieldsPanel.add(this.priceLabel);
        this.updateTextFieldsPanel.add(this.updatePriceText);
        this.updatePanel.add(this.updateTextFieldsPanel, BorderLayout.WEST);
        
        /****** Add the buttons to Update screen ******/
        this.updateNextButton = new JButton("Next");
        this.updateNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] info;
                GUI.this.currentInvestmentIndex++;
                try {
                    info = GUI.this.portfolio.displayInvestment(GUI.this.currentInvestmentIndex).split(",,");
                    if (info.length == 2) {
                        GUI.this.updateSymText.setText(info[0]);
                        GUI.this.updateNameText.setText(info[1]);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    String error = ex.getMessage();
                    GUI.this.updateMessageArea.setText(error);
                }
            } 
        });
        this.updatePrevButton = new JButton("Prev");
        this.updatePrevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] info;
                GUI.this.currentInvestmentIndex--;
                try {
                    info = GUI.this.portfolio.displayInvestment(GUI.this.currentInvestmentIndex).split(",,");
                    if (info.length == 2) {
                        GUI.this.updateSymText.setText(info[0]);
                        GUI.this.updateNameText.setText(info[1]);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    String error = ex.getMessage();
                    GUI.this.updateMessageArea.setText(error);
                }
            } 
        });
        this.updateButton = new JButton("Save");
        this.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String info;
                try {
                info = GUI.this.portfolio.update(GUI.this.currentInvestmentIndex, GUI.this.updatePriceText.getText());
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    info = ex.getMessage();
                }
                GUI.this.updateMessageArea.setText(info);
                GUI.this.updatePriceText.setText("");
            }
        });
        this.updateButtonsPanel = new JPanel();
        this.updateButtonsPanel.setPreferredSize(new Dimension(300,400));
        this.updateButtonsPanel.add(this.updatePrevButton);
        this.updateButtonsPanel.add(this.updateNextButton);
        this.updateButtonsPanel.add(this.updateButton);
        this.updatePanel.add(this.updateButtonsPanel, BorderLayout.EAST);
        
        /****** Add the message text to update screen ******/
        this.updateMessageArea = new JTextArea(11,65);
        this.updateMessageArea.setEditable(false);
        this.updateMessageScroll = new JScrollPane(this.updateMessageArea);
        this.updateMessageScroll.setVisible(true);
        this.updateMessagePanel = new JPanel();
        this.updateMessagePanel.setPreferredSize(new Dimension(800,200));
        this.updateMessagePanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        this.updateMessagePanel.add(this.updateMessageScroll);
        this.updateMessagePanel.setVisible(true);
        this.updatePanel.add(this.updateMessagePanel, BorderLayout.SOUTH);
        /************ End of creating Update screen ************/
        
        
        
        /************ Create the Total Gain screen ************/
        /****** Add the text fields to total gain screen ******/
        this.gainPanel = new JPanel();
        this.gainPanel.setLayout(new BorderLayout());
        this.gainPanel.setVisible(false);
        this.gainTextFieldsPanel = new JPanel();
        this.gainTextFieldsPanel.setPreferredSize(new Dimension(800, 125));
        this.gainTextFieldsPanel.setLayout(new GridLayout(2,2));
        this.gainTextFieldsPanel.setBorder(BorderFactory.createTitledBorder("Getting Total Gain"));
        this.gainText = new JTextField(15);
        this.gainText.setEditable(false);
        this.gainTextFieldsPanel.add(this.gainLabel);
        this.gainTextFieldsPanel.add(this.gainText);
        this.gainPanel.add(this.gainTextFieldsPanel, BorderLayout.WEST);
        
        /****** Add the message text to total gain screen ******/
        this.gainMessageArea = new JTextArea(29,70);
        this.gainMessageArea.setEditable(false);
        this.gainMessageScroll = new JScrollPane(this.gainMessageArea);
        this.gainMessageScroll.setVisible(true);
        this.gainMessagePanel = new JPanel();
        this.gainMessagePanel.setPreferredSize(new Dimension(800,475));
        this.gainMessagePanel.setBorder(BorderFactory.createTitledBorder("Individual Gains"));
        this.gainMessagePanel.add(this.gainMessageScroll);
        this.gainMessagePanel.setVisible(true);
        this.gainPanel.add(this.gainMessagePanel, BorderLayout.SOUTH);
        /************ End of creating Update screen ************/
        
        
        
        /************ Create the menu ************/
        /* Create the menu items */
        this.buyMenuItem = new JMenuItem("Buy");
        this.sellMenuItem = new JMenuItem("Sell");
        this.searchMenuItem = new JMenuItem("Search");
        this.updateMenuItem = new JMenuItem("Update");
        this.gainMenuItem = new JMenuItem("Get Gain");
        this.quitMenuItem = new JMenuItem("Quit");
        /******* Add actions to the menu items *******/
        /* Quit menu item action */
        this.quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                writeOnClose();
                System.exit(0);
            }
        });
        /* Buy menu item action */
        this.buyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUI.this.buyPanel.setVisible(true);
                GUI.this.add(GUI.this.buyPanel);
                GUI.this.buyMessagesArea.setText("");
                GUI.this.gainPanel.setVisible(false);
                GUI.this.searchPanel.setVisible(false);
                GUI.this.welcomePanel.setVisible(false);
                GUI.this.sellPanel.setVisible(false);
                GUI.this.updatePanel.setVisible(false);
            }
        });
        /* Sell menu item action */
        this.sellMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUI.this.sellPanel.setVisible(true);
                GUI.this.add(GUI.this.sellPanel);
                GUI.this.sellMessagesArea.setText("");
                GUI.this.buyPanel.setVisible(false);
                GUI.this.gainPanel.setVisible(false);
                GUI.this.searchPanel.setVisible(false);
                GUI.this.welcomePanel.setVisible(false);
                GUI.this.updatePanel.setVisible(false);
            }
        });
        /* Search menu item action */
        this.searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUI.this.searchPanel.setVisible(true);
                GUI.this.add(GUI.this.searchPanel);
                GUI.this.searchMessageArea.setText("");
                GUI.this.buyPanel.setVisible(false);
                GUI.this.gainPanel.setVisible(false);
                GUI.this.welcomePanel.setVisible(false);
                GUI.this.sellPanel.setVisible(false);
                GUI.this.updatePanel.setVisible(false);
            }
        });
        /* Update menu item action */
        this.updateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUI.this.currentInvestmentIndex = 0;
                String[] info;
                info = GUI.this.portfolio.displayInvestment(GUI.this.currentInvestmentIndex).split(",,");
                GUI.this.updateSymText.setText(info[0]);
                GUI.this.updateNameText.setText(info[1]);
                GUI.this.updatePanel.setVisible(true);
                GUI.this.add(GUI.this.updatePanel);
                GUI.this.updateMessageArea.setText("");
                GUI.this.updatePriceText.setText("");
                GUI.this.buyPanel.setVisible(false);
                GUI.this.gainPanel.setVisible(false);
                GUI.this.searchPanel.setVisible(false);
                GUI.this.welcomePanel.setVisible(false);
                GUI.this.sellPanel.setVisible(false);
            }
        });
        /* Gain menu item action */
        this.gainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    GUI.this.totalGain = GUI.this.portfolio.getTotalGain();
                    GUI.this.gainText.setText(GUI.this.totalGain[0]);
                    GUI.this.gainMessageArea.setText(GUI.this.totalGain[1]);
                } catch (EmptyListException ex) {
                    String error = ex.getMessage();
                    GUI.this.gainMessageArea.setText(error);
                }
                GUI.this.gainPanel.setVisible(true);
                GUI.this.add(GUI.this.gainPanel);
                GUI.this.buyPanel.setVisible(false);
                GUI.this.searchPanel.setVisible(false);
                GUI.this.welcomePanel.setVisible(false);
                GUI.this.sellPanel.setVisible(false);
                GUI.this.updatePanel.setVisible(false);
            }
        });
        /* Add menu items to the menu */
        this.menu = new JMenu("Commands");
        this.menu.add(buyMenuItem);
        this.menu.add(sellMenuItem);
        this.menu.add(gainMenuItem);
        this.menu.add(updateMenuItem);
        this.menu.add(searchMenuItem);
        this.menu.add(quitMenuItem);
        /* Create the menu bar */
        this.menuBar = new JMenuBar();
        this.menuBar.add(menu);
        /* Add the menu bar to the JFrame */
        this.setJMenuBar(menuBar);
        /************ End of creating the menu ************/
        
        
        /************ Create the welcome screen ************/
        this.welcomePanel = new JPanel();
        this.welcomePanel.setLayout(new GridLayout(2,1));
        JTextArea welcomeText1 = new JTextArea();
        welcomeText1.setEditable(false);
        welcomeText1.setText("Welcome to Investment Portfolio.");
        JTextArea welcomeText2 = new JTextArea();
        welcomeText2.setEditable(false);
        welcomeText2.setLineWrap(true);
        welcomeText2.setWrapStyleWord(true);
        welcomeText2.setText("Choose a command from \"Commands\" menu to buy or sell an investment, update prices for all investments,get gain for the portfolio, search for relevant investments, or quit the program");
        this.welcomePanel.add(welcomeText1);
        this.welcomePanel.add(welcomeText2);
        this.welcomePanel.setVisible(true);
        this.add(this.welcomePanel);
        /************ End of creating welcome screen ************/
    }
    
    /**
     * Function to reset all the fields on the buy GUI
     */
    private void resetBuy() {
        GUI.this.buyTypeBox.setSelectedIndex(0);
        GUI.this.buySymText.setText("");
        GUI.this.buyNameText.setText("");
        GUI.this.buyQuantText.setText("");
        GUI.this.buyPriceText.setText("");
    }
    
    /**
     * Function to reset all the fields on the sell GUI
     */
    private void resetSell() {
        GUI.this.sellSymText.setText("");
        GUI.this.sellQuantText.setText("");
        GUI.this.sellPriceText.setText("");
    }
    
    /**
     * Function to reset all the fields on the search GUI
     */
    private void resetSearch() {
        GUI.this.searchSymText.setText("");
        GUI.this.searchNameText.setText("");
        GUI.this.searchLowText.setText("");
        GUI.this.searchHighText.setText("");
    }
    
    /**
     * Function to write to a file on closing
     */
    private void writeOnClose() {
        /* Declare variables */
        String filePath;
        boolean writeComplete = false;
        /* While there is no valid file name keep cycling */
        while (!writeComplete) {
            try {
                /* Try to write to the file */
                this.portfolio.writeToFile();
                writeComplete = true;
            } catch (NoFilePathException | FileNotFoundException ex) {
                /* If there is a problem with the file get a new name */
                filePath = (String)JOptionPane.showInputDialog(null, "File Name","Enter", JOptionPane.PLAIN_MESSAGE, null, null, null);
                this.portfolio.setFilePath(filePath);
            }
        }
    }
}

/* Last Modified: November 26th 2017 */
