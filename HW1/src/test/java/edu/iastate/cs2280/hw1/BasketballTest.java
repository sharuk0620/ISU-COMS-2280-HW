package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Basketball Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class BasketballTest {

    /**
     * Test Case #1: Tests that a Basketball object with a preference level of 5 updates to a Nothing object as per
     * Condition #1 in the Basketball Class next() method's rules.
     *
     * (Makes sure households do not go above the MAX_INTEREST threshold)
     **/
    @Test
    public void maxInterestShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Basketball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 5);

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
     * Test Case #2: Tests that a Basketball household (Not at MAX_INTEREST) updates to a Football household with
     * an interest level of 2 if the number of neighboring households that prefer Football is greater than 5 as per
     * condition #2 in the Basketball Class next() method's rules.
     *
     * (Presumes Condition #1 is False);
     **/
    @Test
    public void greaterThanFiveFootballNeighborsShouldReturnFootballObjectWithPrefTwo(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Basketbal household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 4);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Football(testGrid, 1, 0, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 2);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #3: Tests that a Basketball household (Not at MAX_INTEREST and doesn't have more than 5
     * Football-preferred neighbors) updates to a Soccer household with a preference level of 0 if the number of
     * neighboring households that prefer Soccer is at least 2 as per condition #3 in the Basketball class next()
     * method's rules.
     *
     * (Presumes Condition#1 and Condition#2 are false);
     **/
    @Test
    public void atLeastTwoSoccerNeighborsShouldReturnSoccerObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Basketball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Soccer(testGrid, 1, 0, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Soccer(testGrid, 1, 1, 0);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #4: Tests that a Basketball object (Not at MAX_INTEREST, doesn't have more than 5 Football-preferred
     * neighbors, and doesn't have at least 2 Soccer neighbors) updates to an Everything household if there are fewer
     * than 2 neighboring Basketball households as per condition #4 in the Basketball Class next()
     * method's rules.
     *
     * (Presumes Condition#1, #2, and #3 are false);
     **/
    @Test
    public void fewerThanTwoBasketballNeighborsShouldReturnEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Basketball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Baseball(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Baseball(testGrid, 2, 0, 5);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Baseball(testGrid, 1, 0, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Everything(testGrid, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


    /**
     * Test Case #5: Tests that a Basketball object (Not at MAX_INTEREST, doesn't have more than 5 Football-preferred
     * neighbors, doesn't have at least 2 soccer neighbors, and doesn't have fewer than 2 basketball neighbors)
     * updates to a Everything household if the combined number of neighboring Baseball, Basketball, and Football
     * households is greater than 6 per condition #5 in the Baseball Class next() method's rules.
     *
     * (Presumes Condition#1, #2, #3, and #4 are false);
     **/

    @Test
    public void baseballBasketballFootballNeighborsCombinedGreaterThanSixShouldReturnEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Basketball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Basketball(testGrid, 1, 0, 5);
        testGrid.grid[1][2] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Football(testGrid, 1, 0, 2);
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

    /**
     * Test Case #6: Tests that a Basketball object increments ONLY it's interest level by 1 as per the condition that
     * all other conditions fail.
     *
     * (Presumes Condition#1, #2, #3, #4, and #5 are false);
     **/

    @Test
    public void allConditionsFalseShouldReturnSameObjectIncrementedByOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Basketball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Everything(testGrid, 0, 1);
        testGrid.grid[0][2] = new Basketball(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 5);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Basketball(testGrid, 2, 0, 3);
        testGrid.grid[2][1] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Nothing(testGrid, 1, 0);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Basketball(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


}
