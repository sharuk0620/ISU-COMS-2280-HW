package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Nothing Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class NothingTest {

    /**
     * Test Case #1: Tests that a Nothing object updates to a Soccer object with an interest level of 5 if
     * condition #1 in the Nothing Class next() method's rules is met:
     * (Soccer > 5)
     *
     **/
    @Test
    public void greaterThanFiveSoccerNeighborsShouldReturnSoccerObjectWithPrefFive(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Soccer(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Soccer(testGrid, 1, 0, 1);
        testGrid.grid[1][2] = new Soccer(testGrid, 1, 2, 2);
        testGrid.grid[2][0] = new Soccer(testGrid, 2, 0, 1);

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
     * Test Case #2: Tests that a Nothing household updates to a Football household with an interest level of 0 if
     * condition #2 in the Nothing Class next() method's rules is met:
     * (football > 4)
     *
     * (Assuming condition #1 is false);
     **/
    @Test
    public void greaterThanFourFootballNeighborsShouldReturnFootballObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Football(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
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
     * Test Case #3: Tests that a Nothing household updates to a Basketball household with an interest level of 0
     * if condition #3 in the Nothing class next() method's rules is met:
     * (basketball > 3)
     *
     * (Assuming conditions #1 and #2 are false);
     **/
    @Test
    public void greaterThanThreeBasketballNeighborsShouldReturnBasketballObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Basketball(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Basketball(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Basketball(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Basketball(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 2);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
        testGrid.grid[2][2] = new Everything(testGrid, 2, 2);


        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Basketball(testGrid, 1, 1, 0);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #4: Tests that a Soccer household updates to a Baseball household with an interest level of 0 if
     * condition #4 in the Nothing Class next() method's rules are met:
     * (baseball > 2)
     *
     * (Assuming conditions #1, #2, and #3 are false);
     **/
    @Test
    public void greaterThanTwoBaseballNeighborsShouldReturnBaseballObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Basketball(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Basketball(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Baseball(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Baseball(testGrid, 1, 2, 3);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
        testGrid.grid[2][2] = new Everything(testGrid, 2, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Baseball(testGrid, 1, 1, 0);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #5: Tests that a Nothing household updates to a Rugby household with an interest level of 2 if
     * condition #5 in the Nothing Class next() method's rules are met:
     * (Rugby > 1)
     *
     * (Assuming conditions #1, #2, #3, and #4 are false);
     **/
    @Test
    public void greaterThanOneRugbyNeighborShouldReturnRugbyObjectWithLvlTwo(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Rugby(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Nothing(testGrid, 0, 1);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Rugby(testGrid, 2, 0, 1);
        testGrid.grid[2][1] = new Rugby(testGrid, 2, 1, 5);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Rugby(testGrid, 1, 1, 2);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


    /**
     * Test Case #6: Tests that a Nothing household updates to an Everything household if condition #6 in the Nothing
     * Class next() method's rules is met:
     * (Everything >= 1)
     *
     * (Assuming conditions #1, #2, #3, #4, and #5 are false);
     **/

    @Test
    public void atLeastOneEverythingNeighborShouldReturnEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Everything(testGrid, 0, 2);
        testGrid.grid[1][0] = new Everything(testGrid, 1, 0);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Everything(testGrid, 2, 1);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

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
     * Test Case #7: Tests that a Nothing household remains a Nothing household if all previous conditions are false
     *
     * (Assuming conditions #1, #2, #3, #4, #5, and #6 are false);
     **/

    @Test
    public void allConditionsFalseShouldRemainNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Nothing household (Household to be tested)
        testGrid.grid[1][1] = new Nothing(testGrid, 1, 1);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 1);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 2);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
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
}