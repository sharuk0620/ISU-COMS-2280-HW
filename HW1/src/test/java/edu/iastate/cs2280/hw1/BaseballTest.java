package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Baseball Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class BaseballTest {

    /**
     * Test Case #1: Tests that a Baseball household with an interest level of 5 updates to a Nothing household if
     * condition #1 in the Baseball Class next() method's rules is met:
     * (Baseball Object's interest level == MAX_INTEREST (5)) --> returns Nothing household
     *
     * (Makes sure households do not go above the MAX_INTEREST threshold)
     **/
    @Test
    public void maxInterestShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 5);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1].next(testGrid, 1);

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Nothing(testGrid, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * Test Case #2: Tests that a Baseball household updates to a Soccer household with
     * an interest level of 0 if condition #2 in the Baseball Class next() method's rules is met:
     * (soccer > 3) --> returns Soccer household, lvl 0.
     *
     * (Assuming condition #1 is false);
     **/
    @Test
    public void greaterThanThreeSoccerNeighborsShouldReturnSoccerObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Soccer(testGrid, 0, 1, 4);
        testGrid.grid[0][2] = new Soccer(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Soccer(testGrid, 1, 0, 2);

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
     * Test Case #3: Tests that a Baseball household updates to a Rugby household with an interest level of 0 if
     * condition #3 in the Baseball class next() method's rules is met:
     * (baseball < 2) --> returns Rugby household, lvl 0.
     *
     * (Assuming conditions #1 and condition #2 are false);
     **/
    @Test
    public void fewerThanTwoBaseballNeighborsShouldReturnRugbyObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Rugby(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Basketball(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Nothing(testGrid, 2, 0);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Football(testGrid, 1, 0, 2);

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
     * Test Case #4: Tests that a Baseball object updates to a Nothing household if condition #4 in the Baseball Class
     * next() method's rules is met:
     * ((baseball + soccer) > 5) --> returns Nothing household
     *
     * (Assuming conditions #1, #2, and #3 are false);
     **/
    @Test
    public void greaterThanFiveBaseballAndSoccerNeighborsShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Soccer(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Soccer(testGrid, 2, 0, 5);
        testGrid.grid[2][1] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Baseball(testGrid, 1, 0, 2);

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
     * Test Case #5: Tests that a Baseball object updates to a Football household if condition #5 in the Baseball Class
     * next() method's rules is met:
     * (football > (2 * baseball)) --> returns Football household, lvl 0.
     *
     * (Assuming conditions #1, #2, #3, and #4 are false);
     **/
    @Test
    public void NumOfFootballNeighborsMoreThanTwiceTheBaseballNeighborsShouldReturnFootballObjectWithPrefZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[0][2] = new Football(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 5);
        testGrid.grid[1][2] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Football(testGrid, 1, 0, 2);
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
     * Test Case #6: Tests that a Baseball object increments it's interest level by 1 as per the condition that
     * all other conditions fail.
     *
     * (Assuming conditions #1, #2, #3, #4, and #5 are false);
     **/
    @Test
    public void allConditionsFalseShouldReturnSameObjectIncrementedByOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Baseball household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Baseball(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 3);
        testGrid.grid[0][1] = new Everything(testGrid, 0, 1);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 1, 3);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 5);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 0, 2);
        testGrid.grid[2][0] = new Everything(testGrid, 2, 0);
        testGrid.grid[2][1] = new Football(testGrid, 1, 0, 2);
        testGrid.grid[2][2] = new Nothing(testGrid, 1, 0);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Baseball(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }


}