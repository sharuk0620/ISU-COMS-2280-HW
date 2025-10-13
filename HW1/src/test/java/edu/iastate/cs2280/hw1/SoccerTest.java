package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Soccer Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class SoccerTest {


    /**
     * Test Case #1: Tests that a Soccer object with an interest level of 5 updates to a Nothing object if
     * Condition #1 in the Soccer Class next() method's rules is met:
     * (Soccer Object's interest level == MAX_INTEREST (5))
     *
     * (Makes sure households do not go above the MAX_INTEREST threshold)
     **/
    @Test
    public void maxInterestShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 5);

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
     * Test Case #2: Tests that a Soccer household updates to an Everything household if condition #2 in the Soccer
     * Class next() method's rules is met:
     * ((football + basketball) >= soccer)
     *
     * (Assuming condition #1 is false);
     **/
    @Test
    public void footballBasketballNeighborsCombinedGreaterThanSoccerNeighborsShouldReturnEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 2);
        testGrid.grid[0][1] = new Basketball(testGrid, 0, 1, 1);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 2);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Basketball(testGrid, 2, 0, 2);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 2);
        testGrid.grid[2][2] = new Rugby(testGrid, 2, 2, 1);

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
     * Test Case #3: Tests that a Soccer household updates to a Soccer household with an interest level of 5
     * if condition #3 in the Soccer class next() method's rules is met:
     * (baseball + basketball + football + rugby < (soccer))
     *
     * (Assuming conditions #1 and #2 are false);
     **/
    @Test
    public void baseballBasketballFootballRugbyNeighborsLessThanSoccerNeighborsShouldReturnSoccerObjectWithPrefFive(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Soccer(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 2, 1);
        testGrid.grid[1][0] = new Soccer(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Soccer(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Everything(testGrid, 2, 1);
        testGrid.grid[2][2] = new Soccer(testGrid, 2, 2, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Soccer(testGrid, 1, 1, 5);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #4: Tests that a Soccer household updates to a Rugby household with an interest level of 3 if condition
     * #4 in the Soccer Class next() method's rules are met:
     * (rugby > 4)
     *
     * (Assuming conditions #1, #2, and #3 are false);
     **/
    @Test
    public void greaterThanFourRugbyNeighborsShouldReturnRugbyObjectWithPrefThree(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Rugby(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Rugby(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Rugby(testGrid, 2, 0, 1);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
        testGrid.grid[2][2] = new Everything(testGrid, 2, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Rugby(testGrid, 1, 1, 3);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #5: Tests that a Soccer household updates to a Nothing household if condition #5 in the Soccer Class
     * next() method's rules are met:
     * (Everything = 0)
     *
     * (Assuming conditions #1, #2, and #3 are false);
     **/
    @Test
    public void zeroEverythingNeighborsShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Rugby(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Nothing(testGrid, 0, 1);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Rugby(testGrid, 2, 0, 1);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

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
     * Test Case #6: Tests that a Soccer object increments ONLY it's interest level by 1 as per the condition that
     * all other conditions fail.
     *
     * (Assuming conditions #1, #2, #3, #4, and #5 are false);
     **/

    @Test
    public void allConditionsFalseShouldReturnSameObjectIncrementedByOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Soccer household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Soccer(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 1);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);  // prevents Rule #5
        testGrid.grid[2][1] = new Everything(testGrid, 2, 1);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Soccer(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


}