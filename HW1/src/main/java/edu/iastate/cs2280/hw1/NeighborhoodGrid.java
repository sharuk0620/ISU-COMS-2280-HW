package edu.iastate.cs2280.hw1;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Random;


/**
 * NeighborhoodGrid refers to the households' layout for each simulation.
 * It is a square grid [size X size].
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class NeighborhoodGrid {
    /**
     * Represents the size of the neighborhood grid.
     */
    private final int size;
    /**
     * A 2D grid representing a neighborhood where each cell is occupied by a household.
     * Each the rows and columns define the position of a household within the neighborhood.
     */
    public Household[][] grid;

    /**
     * Constructs a NeighborhoodGrid by reading household data from an input file.
     * Each square in the grid corresponds to a household defined by a specific letter
     * and (if applicable) a passion level.
     *
     * @param inputFileName the name of the file that contains the household data
     *                      used to populate the grid
     * @throws FileNotFoundException if the specified file does not exist or cannot be opened
     * @throws ParseException        if the data in the file is not in the expected format or
     *                               contains invalid household specifications
     */
    public NeighborhoodGrid(String inputFileName) throws FileNotFoundException, ParseException {
        //Initializes File and Scanner Objects
        File fileReader = new File(inputFileName);
        Scanner myScanner = new Scanner(fileReader);

        //Initializes int variable "count" that stores the number of lines txt file has, initially assigned to 0
        int count = 0;

        //Counts how many lines and increments count variable accordingly
        while(myScanner.hasNext()){
            String curLine = myScanner.nextLine();
            count++;
        }

        //Resets scanner object
        myScanner.reset();

        //Assigns size to count
        this.size = count;

        //Assigns 2D Array grid to a new 2D Array of Household objects of (size)x(size)
        grid = new Household[size][size];

        //Call to private helper method: fillGrid to fill newly initialized 2D Array Grid with tokens from file
        fillGrid(inputFileName);

        //Closes Scanner
        myScanner.close();
    }

    /**
     * Helper Method for NeighborhoodGrid constructor with file parameter:
     * Fills in 2D array of NeighborhoodGrid object by extracting token from file and assigning
     * respective position in array the token's household representation with the help of
     * another helper method: fillCell.
     *
     * @param inputFileName the name of the file that contains the household data
     *                      used to populate the grid
     * @throws FileNotFoundException if the specified file does not exist or cannot be opened
     * @throws ParseException        if the data in the file is not in the expected format or
     *                               contains invalid household specifications
     */
    private void fillGrid(String inputFileName) throws FileNotFoundException, ParseException{
        //Initializes File and Scanner object, "fileReader" and "myScanner" respectively.
        File fileReader = new File(inputFileName);
        Scanner myScanner = new Scanner(fileReader);

        //Iterates through entirety of 2D array of a NeighborhoodGrid object
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                //Initializes String "curToken" and assigns the next token of the file (next preference)
                String curToken = myScanner.next();

                //Initializes char var "fileHouseSport" which holds the first character of the token: the sport
                char fileHouseholdSport = curToken.charAt(0);

                //if token's length is two, it means the household is a sport household (sport + lvl)
                if(curToken.length() == 2){
                    //Initializes int var "fileHouseholdPreference", and assigns it the lvl of household post-conversion
                    int fileHouseholdPreference = Character.getNumericValue(curToken.charAt(1));

                    //Calls to 2nd helper method
                    fillCell(i, j, fileHouseholdSport, fileHouseholdPreference);
                //else, the token length is one and that means it's either a E or N household
                } else {
                    //Calls to 2nd helper method
                    fillCell(i, j, fileHouseholdSport, 0);
                }

            }
        }
        //Closes scanner
        myScanner.close();
    }

    /**
     * Helper Method for NeighborhoodGrid constructor with file parameter and fillGrid:
     * Responsible for filling each individual element position in 2D array using Switch cases
     *
     * @param row row that the element is contained on
     * @param col column that the element is contained on
     * @param sport variable that contains the first character of the token extracted from the
     *              file, representing the preferred sport type of that household.
     * @param interest variable that contains the second character of the token extractred from the
     *                 file, if it contains one, representing the interest level of that sport household
     *
     */
    private void fillCell(int row, int col, char sport, int interest) {

        switch(sport){
            //if sport = 'A'
            case 'A':
                //Creates Baseball object at [row][col] with interest level "interest"
                grid[row][col] = new Baseball(this, row, col, interest);
                break;
            //if sport = 'B'
            case 'B':
                //Creates Basketball object at [row][col] with interest level "interest"
                grid[row][col] = new Basketball(this, row, col, interest);
                break;
            //if sport = 'E'
            case 'E':
                //Creates Everything object at [row][col]
                grid[row][col] = new Everything(this, row, col);
                break;
            //if sport = 'F'
            case 'F':
                //Creates Football object at [row][col] with interest level "interest"
                grid[row][col] = new Football(this, row, col, interest);
                break;
            //if sport = 'N'
            case 'N':
                //Creates Nothing object at [row][col]
                grid[row][col] = new Nothing(this, row, col);
                break;
            //if sport = 'R'
            case 'R':
                //Creates Rugby object at [row][col]
                grid[row][col] = new Rugby(this, row, col, interest);
                break;
            //if sport = 'S'
            case 'S':
                //Creates Baseball object at [row][col]
                grid[row][col] = new Soccer(this, row, col, interest);
                break;
        }

    }

    /**
     * Constructs a NeighborhoodGrid of a specified size.
     * If the provided size is greater than zero, it initializes an empty grid of the
     * specified dimensions. Otherwise, it throws an IllegalArgumentException.
     *
     * @param size the size of the grid (number of rows and columns). Must be greater than 0.
     * @throws IllegalArgumentException if the size is not greater than 0.
     */
    public NeighborhoodGrid(int size) {
        //Assigns size of 2D array to passed size value "size"
        this.size = size;

        //If size is greater than 0, grid of NeighborhoodObject is initialized
        if (size > 0) {
            //Assigns grid of object to be empty 2D array, with dimension size x size
            grid = new Household[size][size];
        //If size is 0, then throws exception
        } else {
            //Throws exception
            throw new IllegalArgumentException("Width must be > 0");
        }
    }

    /**
     * Retrieves the size of the neighborhood grid.
     *
     * @return the size of the grid, representing the number of rows and columns.
     */
    public int getSize() {
        //Returns size (length or width) of grid
        return size;
    }

    /**
     * Randomly initializes the grid of the NeighborhoodGrid instance with various household types.
     * Each cell in the grid is assigned one of the household types (Basketball, Football, Baseball,
     * Soccer, Everything, Nothing, or Rugby) based on a random selection.
     */
    public void randomInit() {
        //Iterates through 2D Array
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                //Creates a new Random object "myRandomPicker"
                Random myRandomPicker = new Random();

                //Initializes randomSportsNum to be a random number between 1-7 (inclusive)
                int randomSportNum = myRandomPicker.nextInt(7) + 1;


                switch (randomSportNum){
                    //If randomSportNum = 1
                    case 1:
                        //Calls fillCell and passes i, j, 'A' is Baseball, and a random number 0-6 exclusive
                        fillCell(i, j, 'A', myRandomPicker.nextInt(6));
                        break;
                    //If randomSportNum = 2
                    case 2:
                        //Calls fillCell and passes i, j, 'B' is Basketball, and a random number 0-6 exclusive
                        fillCell(i, j, 'B', myRandomPicker.nextInt(6));
                        break;
                    //If randomSportNum = 3
                    case 3:
                        //Calls fillCell and passes i, j, 'E' is Everything, and a random number 0-6 exclusive
                        fillCell(i, j, 'E', 0);
                        break;
                    //If randomSportNum = 4
                    case 4:
                        //Calls fillCell and passes i, j, 'F' is Football, and a random number 0-6 exclusive
                        fillCell(i, j, 'F', myRandomPicker.nextInt(6));
                        break;
                    //If randomSportNum = 5
                    case 5:
                        //Calls fillCell and passes i, j, 'N' is Nothing, and a random number 0-6 exclusive
                        fillCell(i, j, 'N', 0);
                        break;
                    //If randomSportNum = 6
                    case 6:
                        //Calls fillCell and passes i, j, 'R' is Rugby, and a random number 0-6 exclusive
                        fillCell(i, j, 'R', myRandomPicker.nextInt(6));
                        break;
                    //If randomSportNum = 1
                    case 7:
                        //Calls fillCell and passes i, j, 'S' is Soccer, and a random number 0-6 exclusive
                        fillCell(i, j, 'S', myRandomPicker.nextInt(6));
                        break;
                }
            }
        }
    }

    /**
     * Output the Neighborhood grid.
     * For each square, output the letter associated with the household occupying the square.
     * If the household is interested in a sport, output the interest level in that sport followed
     * by a blank space. Otherwise, output two blank spaces after the letter. One of the blank space
     * is part of toString() implementation of the households
     *
     * @return String
     */
    public String toString() {

        //Initializes String "endResult" and assigns it an empty String.
        String endResult = "";

        //Iterates through 2D array
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                //Extracts string representation of household at i, j, and concatenates to endResult
                endResult += grid[i][j].toString();

                //If household is either Everything or Nothing, 2 white spaces are added
                if(grid[i][j].toString() == "E " || grid[i][j].toString() == "N "){
                    endResult += "  ";
                //Otherwise, a single whitespace is added
                } else {
                    endResult += " ";
                }
                //If at the last element of a row, move down to the next line, if another row exists
                if(j == (size - 1)){
                    endResult += "\n";
                }
            }
        }

        //Returns endResult, the concatenation of all Household string representations.
        return endResult;
    }

    /**
     * Overridden Method: Determines if two NeighborhoodGrid objects are equal by comparing the class types of
     * their elements;
     *
     * @return boolean
     */
    @Override
    public boolean equals(Object other){
        //If "this" and "other" share the same memory address, automatically they are the equal
        if(this == other){
            return true;
        }

        //If "other" is null, or the classes of "this" and "other" are not the same, they are not equal
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }

        //Downcasts "other" as a NeighborhoodGrid object
        NeighborhoodGrid otherGrid = (NeighborhoodGrid) other;
        //Iterates through the 2D array (grid)
        for(int i = 0; i < this.getSize(); i++){
            for(int j = 0; j < this.getSize(); j++){

                //If a single element's class in "this" doesn't match up with "other", they are not equal
                if((this.grid[i][j].getClass() != otherGrid.grid[i][j].getClass())){
                    return false;
                }
            }
        }
        //Otherwise, if all previous returns were passed, means they are equal
        return true;
    }

    public boolean nullCheck(){
        //Initializes and assigns boolean var "empty" the value of false
        boolean empty = false;

        //Iterates through 2D array of "this"
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                //if element is not null, breaks out of loop and proves grid is not 100% null
                if (this.grid[i][j] != null) {
                    empty = false; //will stay false
                    break;
                    //Else, empty will be assigned true
                } else {
                    empty = true;
                }
            }
        }

        //Returns empty after iterating through this (or if a non-null element is found)
        return empty;
    }

    /**
     * Write the Neighborhood grid to a file.
     *
     * @param outputFileName the file that the grid will be written in
     * @throws FileNotFoundException
     */
    public void write(String outputFileName) throws FileNotFoundException{

        //Try-Catch-Block
        try{
            //Creates FileWritier object "myWriter"
            FileWriter myWriter = new FileWriter(outputFileName);

            //Utilizes write() to write the string representation of the token whom method was called on into file
            myWriter.write(toString());

            //Closes myWriter object
            myWriter.close();

        //If an IOException occurs, throws an error message
        } catch (IOException e) {
            System.out.println("An error occurred attempting to write the file!");
        }
    }
}
