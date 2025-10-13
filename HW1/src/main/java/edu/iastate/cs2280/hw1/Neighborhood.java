package edu.iastate.cs2280.hw1;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Performs simulations over the neighborhood grid.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class Neighborhood {
    /**
     * Updates the grid by calculating the next state of each household in the grid.
     * Each household in the old grid determines its new state and updates the
     * corresponding position in the new grid.
     *
     * @param oldGrid      the current state of the neighborhood grid (old grid)
     * @param newGrid      the updated state of the neighborhood grid (new grid)
     * @param currentMonth the current month of the simulation, used for household updates
     */
    public static void updateGrid(NeighborhoodGrid oldGrid, NeighborhoodGrid newGrid, int currentMonth) {

        //If oldGrid's reference, the grid's reference, or any element in Oldgrid is null, safely exits
        if(oldGrid == null || oldGrid.grid == null || oldGrid.nullCheck()){
            return;
        }

        //Initializes an int variable "gridSize", assigns it the size of oldGrid
        int gridSize = oldGrid.getSize();
        
        //Iterates through 2D array elements
        for(int j = 0; j < gridSize; j++){
            for(int k = 0; k < gridSize; k++){
                //newGrid's element at j, k is assigned the corresponding element from oldGrid, but updated
                Household update = oldGrid.grid[j][k].next(newGrid, currentMonth);
                newGrid.grid[j][k] = update;
            }
        }
        
        //Iterates through 2D array, assigning oldGrid to the current grid to prepare for another month run
        for(int k = 0; k < oldGrid.getSize(); k++){
            for(int m = 0; m < oldGrid.getSize(); m++){
                oldGrid.grid[k][m] = newGrid.grid[k][m];
            }
        }
    }

    /**
     * Responsible for running through the simulation depending on how many months the user would
     * like the simulation to run through. Method also prints the final grid of the simulation for 
     * the user.
     *
     * @param beginningGrid  the user's initial grid, created with user's initial conditions
     *                       (random/file, size)
     * @param months    The number of months the user would like the simulation to run for
     *                  (Defines how many months updateGrid will be called)
     */
    static void beginSimulation(NeighborhoodGrid beginningGrid, int months){

        //If the initial grid from the user's conditions is null, safely exits
        if(beginningGrid.nullCheck()){
            return;
        }

        //Prints out the starting, initial grid before updates
        System.out.println();
        System.out.println("Starting Grid:");
        System.out.println("==============");
        System.out.println();
        System.out.println(beginningGrid);
        System.out.println();

        //Iterates for the number of times of months simulation should run for, defined by user
        for(int i = 1; i <= months; i++){

            //Initializes NeighborhoodGrid object "updatedGrid" to act as newGrid for updateGrid
            NeighborhoodGrid updatedGrid = new NeighborhoodGrid(beginningGrid.getSize());

            //Calls updateGrid, passing beginningGrid, updatedGrid, and current month as params.
            Neighborhood.updateGrid(beginningGrid, updatedGrid, i);

            //Prepares beginningGrid for another run of the simulation (if applicable)
            updatedGrid = beginningGrid;

            //Prints out the updated grid after i month has finished
            System.out.println("Current Month: " + (i));
            System.out.println("================");
            System.out.println(updatedGrid);
            System.out.println(" ");
        }

        //Prints out the final grid
        System.out.println();
        System.out.println("Final Grid:");
        System.out.println("==============");
        System.out.println();
        System.out.println(beginningGrid);
    }

    /**
     * The main method serves as the entry point for the simulation of neighborhood sports preferences.
     * It allows users to initialize grids (randomly or from a file), set the duration of the simulation,
     * and view the evolution of the grid over time.
     *
     * @param args command-line arguments passed to the application
     * @throws FileNotFoundException  if the specified file for grid input is not found
     * @throws ParseException         if there is an error in parsing the input file for the grid
     * @throws InputMismatchException if user input does not match the expected input type
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, InputMismatchException {


        //Prints out starting message and creates scanner object to allow for user inputs
        System.out.println("Neighborhood Sports Preferences");
        System.out.println("===============================");
        Scanner scan = new Scanner(System.in);

        //Creates NeighborhoodGrid objects "userGrid"
        NeighborhoodGrid userGrid;

        //Initializes int variable "userMonths" to store how many months user wants to run simulation
        int userMonths;

        //Prints out options to user.
        System.out.println("Choices: 1 (Random Grid.); 2 (File-Inputted Grid.); 3 (Exit.)");
        System.out.println(" ");

        //While loop to allow for JVM to ask for an input until a valid input is given
        while(true){
            //Asks user for their simulation choice (1, 2, or 3)
            System.out.print("Please enter your simulation choice #: ");

            //Stores user chosen choice in userSimChoice
            int userSimChoice = scan.nextInt();

            //If choice == 1, means user wants random grid
            if(userSimChoice == 1){
                //Confirms user choice and asks for grid size
                System.out.println("You have chosen: Random Grid.");
                System.out.print("Please enter grid size (1 side): ");

                //Stores user's grid choice in userGridSize
                int userGridSize = scan.nextInt();

                //Confirms user input, and asks user for number of months to run simulation
                System.out.println("You have chosen: " + userGridSize + "x" + userGridSize + " grid.");
                System.out.print("Please enter the number of months: ");

                //Stores user's choice of months for simulation in userMonths
                userMonths = scan.nextInt();

                //Assigns previously created NeighborhoodGrid objects with size given by user
                userGrid = new NeighborhoodGrid(userGridSize);

                //Randomly initializes userGrid with random values
                userGrid.randomInit();
                break; //Breaks out of the loop; additional userInput is no longer needed

            //If choice == 2, means user wants to input a grid from a text file
            } else if(userSimChoice == 2){
                //Confirms user choice and asks user to type in text file they wanted imported
                System.out.println("You have chosen: File-Inputted Grid.");
                System.out.print("Please enter the file name here (end with .txt): ");

                //calls .nextLine() to clear buffer, and calls .nextLine() once more to store user txt file
                scan.nextLine();
                String userFile = scan.nextLine();

                //Confirms user input, and asks for number of months for simulation
                System.out.println("You have chosen file: " + userFile);
                System.out.print("Please enter the number of months: ");

                //Stores user months of simulation in userMonths
                userMonths = scan.nextInt();

                //Assigns previously created NeighborhoodGrid object with size given by user
                userGrid = new NeighborhoodGrid(userFile);
                break; //breaks out, as additional userInput is no longer needed

            //If choice = 3, then user wants to exit program
            } else if(userSimChoice == 3){
                //Exits out of the program if user decides to exit with exit code 0.
                System.out.println("You have chosen: Exit. Have a good day user!");
                System.out.println(" ");
                System.exit(0);
            //If user provides an invalid input, JVM asks for another until input is valid
            } else {
                //Prints out error message
                System.out.println("Invalid Input. Please Try Again!");
                System.out.println(" ");
            }
        }

        //Closes scanner object
        scan.close();

        //Calls beginSimulation method, passing userGrid and userMonths as parameters
        Neighborhood.beginSimulation(userGrid, userMonths);
    }
}
