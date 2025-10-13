package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Everything Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class EverythingTest {

    /**
     * Test Case #1: Tests that an Everything object updates to a Soccer object if the number of neighboring Soccer
     * households is at least three times as much as the number of Everything neighboring households per
     * Condition #1 in the Everything Class next() method's rules.
     *
     *
     **/
    @Test
    public void soccerNeighborsAtLeastThreeTimesMuchAsEverythingNeighborsShouldReturnSoccerObjectWithPrefThree(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Everything household (Household to be tested)
        testGrid.grid[1][1] = new Everything(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Soccer(testGrid, 1, 0, 4);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Soccer(testGrid, 2, 0, 4);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Soccer(testGrid, 1, 0, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Soccer(testGrid, 1, 1, 3);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #2: Tests that a Everything household updates to a Football household with an interest level of 0 if
     * the number of neighboring households that prefer Football is greater than 3 as per condition #2 in the Everything
     * Class next() method's rules.
     *
     * (Presumes Condition #1 is False);
     **/
    @Test
    public void greaterThanThreeFootballNeighborsShouldReturnFootballObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Everything household (Household to be tested)
        testGrid.grid[1][1] = new Everything(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Nothing(testGrid, 0, 1);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 4);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 0);
        testGrid.grid[2][0] = new Football(testGrid, 2, 0, 4);
        testGrid.grid[2][1] = new Nothing(testGrid, 1, 0);
        testGrid.grid[2][2] = new Nothing(testGrid, 1, 0);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 0);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #3:
     * Tests that an Everything household updates to a Basketball household with an interest level of 2 if condition #3
     * of Everything Class next() method's rules is met:
     * (nothing < (football + soccer))
     *
     * (Assuming conditions #1 and #2 are false);
     **/
    @Test
    public void nothingNeighborsLessThanFootballSoccerNeighborsCombinedShouldReturnBasketballObjectWithPrefTwo(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Everything household (Household to be tested)
        testGrid.grid[1][1] = new Everything(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 5);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Baseball(testGrid, 1, 0, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Basketball(testGrid, 1, 1, 2);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #4: Tests that a Everything object updates to an Rugby object with an interest of 0 if condition #4
     * of Everything Class next() method's rules are met:
     * (rugby > everything)
     *
     * (Assuming condition #1, #2, and #3 are false);
     **/
    @Test
    public void fewerRugbyThanEverythingNeighborsShouldReturnRugbyObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Everything household (Household to be tested)
        testGrid.grid[1][1] = new Everything(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Nothing(testGrid, 0, 0);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 4);
        testGrid.grid[0][2] = new Rugby(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Everything(testGrid, 1, 0);


        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Rugby(testGrid, 1, 1, 0);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


    /**
     * Test Case #5: Tests that an Everything object remains an Everything household if all other conditions fail:
     *
     * (Assuming Condition#1, #2, #3, and #4 are false);
     **/

    @Test
    public void everythingObjectAllConditionsFalseShouldRemainEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Everything household (Household to be tested)
        testGrid.grid[1][1] = new Everything(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Everything(testGrid, 0, 0);
        testGrid.grid[0][1] = new Everything(testGrid, 0, 1);
        testGrid.grid[0][2] = new Nothing(testGrid, 0, 1);
        testGrid.grid[1][0] = new Everything(testGrid, 1, 0);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 0);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Everything(testGrid, 1, 0);
        testGrid.grid[2][2] = new Baseball(testGrid, 1, 0, 5);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Everything(testGrid, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }
}