package edu.iastate.cs2280.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test cases for methods and logic in the Football Class
 *
 * @author: Sai Sharuk Lakshmi Narayanan
 **/
class FootballTest {

    /**
     * (SPECIAL CASE)
     * Test Case #1a: Tests that a Football household increments it's interest level by 1 if
     * it is the 4th month of the simulation, there is at least one neighboring Everything or Nothing household,
     * and interest level is not maxed out (lvl 5).
     *
     **/
    @Test
    public void monthFourNotMaxedInterestLevelNoEverythingNothingHouseholdShouldIncrementInterestLvlOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 4);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * (SPECIAL CASE)
     * Test Case #1b: Tests that a Football household increments it's interest level by 1 if
     * it is the 5th month of the simulation, there is at least one neighboring Everything or Nothing household,
     * and interest level is not maxed out (lvl 5).
     *
     **/
    @Test
    public void monthFiveNotMaxedInterestLevelNoEverythingNothingHouseholdShouldIncrementInterestLvlOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 5);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * (SPECIAL CASE)
     * Test Case #1c: Tests that a Football household increments it's interest level by 1 if
     * it is the 6th month of the simulation AND interest level is not maxed out (lvl 5).
     *
     **/
    @Test
    public void monthSixNotMaxedInterestLevelNoEverythingNothingHouseholdShouldIncrementInterestLvlOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 3);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 6);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 4);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * (SPECIAL CASE)
     * Test Case #1d: Tests that a Football household doesn't increment its interest level by 1 if it
     * is the 7th (or greater) month of the simulation.
     *
     **/
    @Test
    public void monthSevenNotMaxedInterestLevelNoEverythingNothingHouseholdsShouldNOTIncrementInterestLvlOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 3 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 3);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Football(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Football(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Football(testGrid, 2, 0, 4);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 5);
        testGrid.grid[2][2] = new Football(testGrid, 2, 2, 0);


        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 7);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Everything(testGrid, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * (SPECIAL CASE)
     * Test Case #1e: Tests that a Football household does not exceed the MAX_INTEREST limit if it
     * is either the 4th, 5th, 6th month of the simulation, there is at least one neighboring Everything or
     * Nothing household, and it's interest level is at max (lvl 5).
     *
     **/
    @Test
    public void monthFourFiveOrSixMaxedInterestLevelAtLeastOneEverythingNothingHouseholdShouldNOTIncrementInterestLvlOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 5);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Football(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Everything(testGrid, 1, 0);
        testGrid.grid[1][2] = new Football(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Football(testGrid, 2, 0, 4);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 5);
        testGrid.grid[2][2] = new Football(testGrid, 2, 2, 0);


        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 4);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 5);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }

    /**
     * (FOLLOWING TEST CASES BELOW OCCUR ASSUMING SIMULATION MONTH IS NOT: 4th, 5th, or 6th MONTH)
     * (I.E SPECIAL CASE FAILS/IS SKIPPED)
     */

    /**
     * Test Case #2: Tests that a Football object with an interest level of 5 updates to a Nothing object if
     * condition #1 in the Football Class next() method's rules is met:
     * (Football Object's interest level == MAX_INTEREST (5))
     *
     * (Assuming Special Case is false; Makes sure households do not go above the MAX_INTEREST threshold)
     **/
    @Test
    public void maxInterestShouldReturnNothingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3 and randomly assigns Households
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);
        testGrid.randomInit();

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 5 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 5);

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
     * Test Case #3: Tests that a Football household updates to an Everything household if condition #2 in the Football
     * class next() method's rules is met:
     * ((baseball + basketball + football) > 7)
     *
     * (Assuming special case and condition #1 are false);
     **/
    @Test
    public void greaterThanSevenBaseballBasketballFootballNeighborsCombinedShouldReturnEverythingObject(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 4 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 4);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Basketball(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Basketball(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Basketball(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Basketball(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Baseball(testGrid, 1, 2, 0);
        testGrid.grid[2][0] = new Baseball(testGrid, 2, 0, 4);
        testGrid.grid[2][1] = new Football(testGrid, 2, 1, 5);
        testGrid.grid[2][2] = new Everything(testGrid, 2, 2);

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
     * Test Case #4: Tests that a Football household updates to a Basketball household with an interest level of 0 if
     * condition #3 in the Football Class next() method's rules are met:
     * (basketball > 3)
     *
     * (Assuming special case and conditions #1 and #2 are false);
     **/
    @Test
    public void greaterThanThreeBasketballNeighborsShouldReturnBasketballObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 2 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 2);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Basketball(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Basketball(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Basketball(testGrid, 0, 2, 0);
        testGrid.grid[1][0] = new Baseball(testGrid, 1, 0, 3);
        testGrid.grid[1][2] = new Baseball(testGrid, 1, 2, 3);
        testGrid.grid[2][0] = new Basketball(testGrid, 2, 0, 2);
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
     * Test Case #5: Tests that a Football household updates to a Baseball household with an interest level of 0 if
     * condition #4 in the Football Class next() method's rules are met:
     * (Football < 2)
     *
     * (Assuming special case and conditions #1, #2, #3, and #4 are false);
     **/
    @Test
    public void fewerThanTwoFootballNeighborsShouldReturnBaseballObjectWithLvlZero(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 0 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 0);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Rugby(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Nothing(testGrid, 0, 1);
        testGrid.grid[0][2] = new Baseball(testGrid, 0, 2, 3);
        testGrid.grid[1][0] = new Everything(testGrid, 1, 0);
        testGrid.grid[1][2] = new Rugby(testGrid, 1, 2, 1);
        testGrid.grid[2][0] = new Rugby(testGrid, 2, 0, 1);
        testGrid.grid[2][1] = new Rugby(testGrid, 2, 1, 5);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

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
     * Test Case #6: Tests that a Football household increments its interest level by 1 as per the condition that
     * all other conditions are false.
     *
     * (Assuming special case and conditions #1, #2, #3, #4, and #5 are false);
     **/

    @Test
    public void allConditionsFalseShouldIncrementByOne(){
        //Initializes NeighborhoodGrid object "testGrid" of size 3x3
        NeighborhoodGrid testGrid = new NeighborhoodGrid(3);

        //Changes middle element of 2D array of "testGrid" to Football household, lvl 0 (Household to be tested)
        testGrid.grid[1][1] = new Football(testGrid, 1, 1, 0);

        //Assigns other elements specific households to hold condition true
        testGrid.grid[0][0] = new Soccer(testGrid, 0, 0, 1);
        testGrid.grid[0][1] = new Football(testGrid, 0, 1, 2);
        testGrid.grid[0][2] = new Football(testGrid, 0, 2, 1);
        testGrid.grid[1][0] = new Nothing(testGrid, 1, 0);
        testGrid.grid[1][2] = new Nothing(testGrid, 1, 2);
        testGrid.grid[2][0] = new Basketball(testGrid, 2, 0, 1);
        testGrid.grid[2][1] = new Nothing(testGrid, 2, 1);
        testGrid.grid[2][2] = new Nothing(testGrid, 2, 2);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        testGrid.grid[1][1] = testGrid.grid[1][1].next(testGrid, 1);

        //Calls next() method on middle element, assigns to Household object "actualPreference"
        Household actualPreference = testGrid.grid[1][1];

        //Assigns Household object "expectedPreference" with expected Household to be returned
        Household expectedPreference = new Football(testGrid, 1, 1, 1);

        //Determines if expected and actual returned households are equal
        assertEquals(expectedPreference, actualPreference);
    }
}