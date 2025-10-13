package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the NeighborhoodGrid Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class NeighborhoodGridTest {

    /**
     * Tests that the initialization of a NeighborhoodGrid object with a given file as a parameter properly
     * translates the contents of the given file into the grid of the object.
     **/
    @Test
    public void shouldInitializeProperGridFromInputtedFileParameter() throws FileNotFoundException, ParseException {
        //Initializes NeighborhoodGrid object "testGrid" and passes in the file "8x8.txt" as a file parameter
        NeighborhoodGrid testGrid = new NeighborhoodGrid("8x8.txt");

        //Initializes FileReader objects to assist with printing out file for comparison to grid
        FileReader myReader = new FileReader("8x8.txt");
        FileReader myReaderTwo = new FileReader("8x8.txt");

        //Initializes Scanner objects to assist with printing out file for comparison to grid
        Scanner myScanner = new Scanner(myReader);
        Scanner myScannerTwo = new Scanner(myReaderTwo);

        //Initializes int variable "matchingCount" to contain the number of same-elements in file and 2d array
        int matchingCount = 0;

        //Prints out the grid from the file using the first myReader, and myScanner objects
        System.out.println("Grid from File: ");
        System.out.println("================");
        while(myScanner.hasNextLine()) {
            System.out.println(myScanner.nextLine());
        }

        System.out.println();

        //Prints out the grid from the 2D array of testGrid using toString() method of the corresponding household
        System.out.println("Grid from 2D Array: ");
        System.out.println("================");
        System.out.println(testGrid);

        //Nested For Loop to iterate through each element of testGrid's 2D array
        for(int i = 0; i < testGrid.getSize(); i++){
            for(int j = 0; j < testGrid.getSize(); j++){
                //Initializes string object myToken and assigns the first token (household) shown in file.
                String myToken = myScannerTwo.next();

                //Initializes string object changedString
                String changedString;
                //If token from file is of length = 2, then it represents a sport household (Pref + interest)
                if (myToken.length() == 2){
                    //Assigns changedString to contain string representation of household WITHOUT whitespaces from grid
                    changedString = "" + testGrid.grid[i][j].toString().charAt(0) + testGrid.grid[i][j].toString().charAt(1);

                    //If token is equal to changedString, increment matching count by 1;
                    if(myToken.equals(changedString)){
                        matchingCount++;
                    }
                //If length of token is not 2, then it represents either a E or N household
                } else {
                    //Assigns changedString to contain string representation of household WITHOUT whitespaces
                    changedString = "" + testGrid.grid[i][j].toString().charAt(0);

                    //If token is equal to changedString, increment matching count by 1;
                    if(myToken.equals(changedString)){
                        matchingCount++;
                    }
                }

            }
        }
        //Initializes int variable expectedCount and assigns the value of all possible households in grid & file
        int expectedCount = testGrid.getSize() * testGrid.getSize();

        //Checks to see if matchingCount (incremented variable if tokens = string rep of household) equals expected
        assertEquals(expectedCount, matchingCount);
    }


    /**
     * Tests that the getSize() accessor method properly returns the size (length) of a neighborhood grid.
     * Also tests that a NeighborhoodGrid object initialization with a given size parameter creates an object
     * with a grid of (length) size.
     **/
    @Test
    public void shouldReturnProperSize(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Initializes and declares int variable "actualSize" to the getSize() method call on testGrid
        int actualSize = testGrid.getSize();

        //Initializes and declares int variable "expectedSize" to 3: the size of testGrid
        int expectedSize = 3;

        //Checks to see if expectedSize and actualSize are equal
        assertEquals(expectedSize, actualSize);
    }

    /**
     * Tests that the randomInit() method actually fills a given NeighborhoodGrid object's grid with households
     * RANDOMLY instead of using patterns
     **/
    @Test
    public void shouldCreateTwoUniqueGrids(){
        //Initializes NeighborhoodGrid object "firstGrid" of size 3x3
        NeighborhoodGrid firstGrid = new NeighborhoodGrid(3);

        //Initializes NeighborhoodGrid object "secondGrid" of size 3x3
        NeighborhoodGrid secondGrid = new NeighborhoodGrid(3);

        //Initializes boolean variable "doGridsDiffer" that contains if the 2 grids differ (initially set to false)
        boolean doGridsDiffer = false;

        //Cals randomInit() on both NeighborhoodGrid objects to fill their grids with households.
        firstGrid.randomInit();
        secondGrid.randomInit();

        //Prints out both grids using (using toString()) to show visualization of both grids and their differences
        System.out.println("First Grid: ");
        System.out.println("=============");
        System.out.println(firstGrid);
        System.out.println();
        System.out.println("Second Grid: ");
        System.out.println("=============");
        System.out.println(secondGrid);

        //First Check: Checks if both grids share the same memory address, checking for false if they don't
        assertNotEquals(firstGrid, secondGrid);

        //Iterates through both grids: checks if even a single element is different. Assigns doGridsDiffer to true if so
        for(int i = 0; i < firstGrid.getSize(); i++){
            for(int j = 0; j < firstGrid.getSize(); j++){
                if(!firstGrid.grid[i][j].equals(secondGrid.grid[i][j])){
                    doGridsDiffer = true;
                    break; //breaks out of iteration if this condition is met a single time
                }
            }
        }

        //Checks if doGridsDiffer is true, in which doGridsDiffer being true proves the grids are different
        assertTrue(doGridsDiffer);
    }

    /**
     * Tests that the randomInit() method doesn't fill the grid with invalid inputs, such as being null
     * or having a preference other than one of Household's subclass type, and that each household that has a SPORT
     * preference doesn't have an interest level below 0 or above 5 upon initialization
     **/
    @Test
    public void randomInitMethodShouldNotCreateInvalidInputs(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(5);

        //Initializes boolean variable "isValidPreference" which contains whether grid contains all valid elements
        boolean allValidPreference = false;

        //Initializes boolean variable "isValidPreference" which contains whether all sport households have valid levels
        boolean allValidInterest = false;

        //Fills in testGrid's grid with randomly-generated households
        testGrid.randomInit();

        //Prints out testGrid for visualization purposes
        System.out.println(testGrid);

        //Iterates through 2D array using nested for loop
        for(int i = 0; i < testGrid.getSize(); i++){
            for(int j = 0; j < testGrid.getSize(); j++){

                //Checks if object is NOT null or is an instance of one of the Household class' subclasses
                if(testGrid.grid[i][j] != null || testGrid.grid[i][j] instanceof Household)  {
                    //If the condition above true, allValidPreference is set to true
                    allValidPreference = true;
                } else {
                    //If condition is false, meaning element is invalid, allValidPrefence is false and breaks out.
                    allValidPreference = false;
                    break; //breaks out of loop as a single invalid element causes case to fail
                }

                //If household is valid, and if household is a sport household, checks to see if it has a valid level
                if(testGrid.grid[i][j] instanceof SportsHouseholds){
                    if(((SportsHouseholds) testGrid.grid[i][j]).getInterest() <= 5 &&
                       ((SportsHouseholds) testGrid.grid[i][j]).getInterest() >= 0){
                        //If condition is true (in between 0 and 5 (inclusive)), allValidInterest is true
                        allValidInterest = true;
                    } else {
                        //If condition is false (below 0 or greater than 5), allValidInterest is false
                        allValidInterest = false;
                        break; //breaks out of loop as a single sport household with invalid level causes case to fail
                    }
                }
            }
        }

        //Checks to see if allValidPreference is true, meaning if all households are valid
        assertTrue(allValidPreference);

        //Checks to see if allValidInterest is true, meaning if all sport household have valid interest levels
        assertTrue(allValidInterest);

    }



}