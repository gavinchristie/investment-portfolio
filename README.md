Name: Gavin Christie
Email: gchristi@uoguelph.ca
Due Date: November 29th 2017
Course: CIS2430 Object Oriented Programming
Assignment 3

Description:
	Program to simulate an investment portfolio. Users can purchase Stocks or Mutual Funds.  Once users have purchased a stock they can use 
	the different screens to perform a variety of functions, such as: buying more shares or new investments, selling shares of an investment
	that they already own, calculate the total gain of their shares, update the price of individual investments, or search their investments.

Assumptions:
	1) When buying only show the user the purchase order of the last purchase and clear the message area once the screen is changed
	2) When the user is buy more of a stock that they already own, the text in the name field can be anything

User Guide:
    To run the program run the Investment package.


Test Plan:
    1. Test all GUI components to make sure that they appear correctly
        - Visit every page of the program and make sure that the text boxes and everything is correctly laid out
    2. Test file input
        - Give no file name for input
        - Give an empty file as input
        - Give a file with correct input
    3. Test file output
        - Give a file that already exists but is empty
        - Give a file that does not exist to see if new file is created
        - Give a file that already has information in it to see if it gets over written
    4. Test the buy function
        - Enter a symbol that already exists
        - Enter a symbol that does not exist
        - Enter a negative into price or number of shares
        - Don't give input for any field
        - Give proper input for all fields
    5. Test the sell function
        - Enter a symbol that does not exist
        - Enter a symbol that does exist
        - Enter a negative for ptice or number of shares to sell
        - Don't give input for any field
        - Give proper input for all fields
        - Sell first and last investments
    6. Test the update funtion
        - When the list is empty
        - When the list has values
        - Input a negative as the new price
        - Input nothing as the new price
        - Update the first investment in the list
        - Update the last investment in the list
    7. Test the get gain function
        - When the list is empty
        - When the list has multiple values
        - Test for a negative gain
    8. Test the search function
        - Search for the first element of the list
        - Search for the last element of the list
        - Search with no conditions
        - Search with conditions that return no investments
        - Test the input of price range with only one value
        - Test the input of price range with only lower bound
        - Test the input of price range with only upper bound
        - Test with both upper and lower bounds on the price range
        - Search when there is nothing in the list
