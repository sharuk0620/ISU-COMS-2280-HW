package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Neighborhood Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class NeighborhoodTest {
    /**
     * Tests to see that the calling of the beginSimulation calls the updateGrid() static method properly  and updates
     * the grid with the correct next() method conditions, and that updated grid is NOT the same
     *
     **/
    @Test
    public void shouldUpdateGrid() {
        //Initializes NeighborhoodGrid object "initialGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Initializes NeighborhoodGrid object "updatedGrid" of size, and will contain updated households from initialGrid
        NeighborhoodGrid updatedGrid = new NeighborhoodGrid(3);
        updatedGrid.randomInit();

        //Initializes NeighborhoodGrid object "tempGrid" of size, stores the content of grid before update.
        NeighborhoodGrid tempGrid = new NeighborhoodGrid(3);

        //Initializes boolean var "NotEqual", containing whether arrays or equal or not
        boolean notEqual = false;

        //Hello!
        //Prints out testGrid to visually show the initial grid (pre-update)
        System.out.println("Initial Grid");
        System.out.println("================");
        System.out.println(testGrid);


        //Assigns tempGrid's grid to testGrid's 2D array
        for(int i = 0; i < testGrid.getSize(); i++){
            for(int j = 0; j < testGrid.getSize(); j++){
                tempGrid.grid[i][j] = testGrid.grid[i][j];
            }
        }


        System.out.print(tempGrid);

        //Calls the beginSimulation method on testGrid to begin updating households (1 month simulation for example)
        Neighborhood.beginSimulation(testGrid, 1);

        //Prints out testGrid to visually show the updated grid (post-update)
        System.out.println("Updated Grid");
        System.out.println("================");
        System.out.println(testGrid);

        //Iterates through 2D array with nested for lop
        for (int i = 0; i < testGrid.getSize(); i++) {
            for (int j = 0; j < testGrid.getSize(); j++) {
                //If even a single element in each element isn't equal, it proves arrays updated
                if (!tempGrid.grid[i][j].equals(testGrid.grid[i][j])) {
                    //Changes notEqual to true
                    notEqual = true;
                    break; //breaks out of iteration if this condition is met a single time
                }
            }
        }
        //Checks to see if notEqual is true, which shows the arrays are not equal.
        assertTrue(notEqual);
    }

    /**
     * Tests to see if firstGrid and secondGrid, both NeighborhoodGrid objects with "empty" grids (all null) equal
     * each other after beginSimulation has been called
     **/
    @Test
    public void shouldEqualEmptyGrids(){
        //Initializes NeighborhoodGrid object "initialGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid firstGrid = new NeighborhoodGrid(3);
        System.out.println(firstGrid.nullCheck());

        //Initializes NeighborhoodGrid object "updatedGrid" of size, and will contain updated households from initialGrid
        NeighborhoodGrid secondGrid = new NeighborhoodGrid(3);

        Neighborhood.beginSimulation(firstGrid, 1);

        assertArrayEquals(firstGrid.grid, secondGrid.grid);
    }

    /**
     * Tests to see that a grid passed in with all Nothing households still equals a neighborhood with all Nothing
     * households after beginSimulation was called
     **/
    @Test
    public void nothingNeighborhoodEqualsNothingNeighborhood(){
        //Creates two Neighborhood Grid objects, both of size 2
        NeighborhoodGrid testOne = new NeighborhoodGrid(2);
        NeighborhoodGrid testTwo = new NeighborhoodGrid(2);

        //Iterates through testOne
        for(int i = 0; i < testOne.getSize(); i++){
            for(int j = 0; j < testOne.getSize(); j++){

                //Assigns every element of testOne to a Nothing household
                testOne.grid[i][j] = new Nothing(testOne, i, j);

                //Assigns corresponding element in testTwo to element from testOne
                testTwo.grid[i][j] = testOne.grid[i][j];
            }
        }

        //Begins the simulation, passing testOne into a 2-month long simulation
        Neighborhood.beginSimulation(testOne, 2);

        //Checks to see if testOne post-simulation is equal to testTwo
        assertArrayEquals(testOne.grid, testTwo.grid);
    }

    /**
     * (File-Specific Test) Tests to see that updating grid from 8x8.txt across a simulation of 5 months
     * gives a certain array, proving simulation works.
     *
     * (Certain array, after 5 months, is obtained from HW1 Instructions under Sample Run)
     **/
    @Test
    public void txtFileEqualsFinalArray() throws FileNotFoundException, ParseException {
        //Initializes NeighborhoodGrid object "initialGrid" with file 8x8.txt
        NeighborhoodGrid testGrid = new NeighborhoodGrid("8x8.txt");

        //Prints out testGrid to visually show the updated grid (pre-update)
        System.out.println("Initial Grid");
        System.out.println("================");
        System.out.println(testGrid);

        //Initializes NeighborhoodGrid object "final Grid" with file 8x8Post5MonthRun.txt (end result)
        NeighborhoodGrid finalGrid = new NeighborhoodGrid("8x8Post5MonthRun.txt");

        //Initializes boolean var to hold if both arrays are equal or not
        boolean isEqual = true;

        //Updates testGrid through a simulation of 5 months
        Neighborhood.beginSimulation(testGrid, 5);

        //Prints out testGrid to visually show the updated grid (post-update)
        System.out.println("Updated Grid");
        System.out.println("================");
        System.out.println(testGrid);

        //Checks to see if isEquals is true, meaning the arrays are the same
        assertArrayEquals(testGrid.grid, finalGrid.grid);

    }
}

