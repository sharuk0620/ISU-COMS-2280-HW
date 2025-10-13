package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Rugby Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class RugbyTest {

    /**
     * Test Case #1: Tests that a Rugby object with an interest level of 5 updates to a Nothing object if
     * Condition #1 in the Basketball Class next() method's rules is met:
     * (Rugby Object's interest level == MAX_INTEREST (5))
     *
     * (Makes sure households do not go above the MAX_INTEREST threshold)
     **/
    @Test
    public void maxInterestShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Rugby household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Rugby(testGrid, 1, 1, 5);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Nothing(testGrid, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #2: Tests that a Rugby household updates to a Soccer household with
     * an interest level of 2 if condition #2 in the Rugby Class next() method's rules is met:
     * (football + soccer >= 8)
     *
     * (Assuming condition #1 is False);
     **/
    @Test
    public void FootballSoccerNeighborsCombinedGreaterAtLeastEightShouldReturnFootballObjectWithPrefTwo(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Rugby household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Rugby(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Soccer(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Soccer(testGrid, 1, 0, 1);
        testGrid.grid[1][2] = new Football(testGrid, 1, 2, 2);
        testGrid.grid[2][0] = new Soccer(testGrid, 2, 0, 2);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 4);
        testGrid.grid[2][2] = new Soccer(testGrid, 2, 2, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Soccer(testGrid, 1, 1, 2);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #3: Tests that a Rugby household updates to a Baseball object with an interest level of 4
     * if condition #3 in the Rugby class next() method's rules is met:
     * (baseball > (2 * basketball))
     *
     * (Assuming conditions #1 and condition #2 are false);
     **/
    @Test
    public void baseballNeighborsMoreThanTwiceThanBasketballNeighborsShouldReturnBaseballObjectWithPrefFour(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Rugby household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Rugby(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Baseball(testGrid, 0, 0, 2);
        testGrid.grid[0][1] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 1);
        testGrid.grid[1][0] = new Baseball(testGrid, 1, 0, 4);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 2, 3);
        testGrid.grid[2][0] = new Baseball(testGrid, 2, 0, 5);
        testGrid.grid[2][1] = new Baseball(testGrid, 2, 1, 2);
        testGrid.grid[2][2] = new Baseball(testGrid, 2, 2, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Baseball(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #4: Tests that a Rugby object updates to a football object if condition #4 in the Rugby Class
     * next() method's rules are met:
     * (rugby < 2)
     *
     * (Assuming conditions #1, #2, and #3 are false);
     **/
    @Test
    public void fewerThanTwoRugbyNeighborsShouldReturnFootballObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Rugby household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Rugby(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 2);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 4);
        testGrid.grid[1][0] = new Basketball(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Baseball(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 2);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

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
     * Test Case #5: Tests that a Rugby object increments ONLY it's interest level by 1 as per the condition that
     * all other conditions fail.
     *
     * (Assuming conditions #1, #2, #3, and #4 are false);
     **/

    @Test
    public void allConditionsFalseShouldIncrementByOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Rugby household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Rugby(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Basketball(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Baseball(testGrid, 1, 0, 1);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 2, 1);   // another Rugby → total 2 Rugby
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0); // ignored in conditions
        testGrid.grid[2][1] = new Soccer(testGrid, 2, 1, 1);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);    // ignored in conditions

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Rugby(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

}