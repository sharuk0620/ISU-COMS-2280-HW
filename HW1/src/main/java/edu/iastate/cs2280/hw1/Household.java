package edu.iastate.cs2280.hw1;

/**
 * The Household class serves as an abstract representation of a household entity
 * within a neighborhood grid. It encapsulates details about the household's
 * location, as well as its preferences regarding sports interests.
 * The class is intended to be extended by specific household types that define
 * concrete preferences and behaviors.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public abstract class Household{
    /**
     * An array that keeps track of the number of households in the neighborhood grid
     * with a specific sports preference. Each index in the array corresponds to the ordinal
     * value of a sport in the Sports enum.
     */
    public static final int[] members = new int[Sports.values().length];
    /**
     * The maximum allowable passion or interest level for a household in a specific sports category.
     * This constant is used to determine when a household has reached its peak interest level
     * and can no longer increase its passion for a particular sport.
     */
    public static final int MAX_INTEREST = 5;
    /**
     * The grid in which the household resides.
     */
    public NeighborhoodGrid neighborhoodGrid;
    /**
     * The row index of the household's position in the neighborhood grid.
     */
    protected int row;
    /**
     * The column index of the household's position within the neighborhood grid.
     */
    protected int column;
    /**
     * An 2D array of ints containing the directions/coordinates of a Household's neighbors relative to the
     * household at focus. Used for surveying.
     */
    protected int[][] gridDirections = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};


    /**
     * Constructs a new Household object at the specified position in the given neighborhood grid.
     *
     * @param grid   the NeighborhoodGrid instance where the household resides
     * @param row    the row index of the household's position in the grid
     * @param column the column index of the household's position in the grid
     */
    public Household(NeighborhoodGrid grid, int row, int column) {
        //Assigns class variables to passed parameters
        neighborhoodGrid = grid;
        this.row = row;
        this.column = column;
    }

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    public Household() {

    }

    /**
     * Surveys the preferences of neighboring households within the neighborhood grid.
     * Each neighboring household's preference is counted and stored in the 'households' array,
     * where each index corresponds to a preference from the Sports enumeration.
     *
     * @param households An array to store the aggregated counts of sports preferences for neighboring households.
     */
    protected void survey(int households[]) throws NullPointerException{

        //Resets the array containing the neighbors of the previous household
        for(int i = 0; i < households.length; i++){
            households[i] = 0;
        }

        //Iterates through every element in array households
        for(int[] cur : gridDirections){
            //Initializes int var curRow and assigns it the row value of the first neighbor of the household
            int curRow = this.row + cur[0];
            //Initializes int var curColumn and assigns it the column value of the next neighbor of the household
            int curColumn = this.column + cur[1];

            //If row AND column of the neighbor is > 0, and less than the max size of the array: passed into helper method
            if((curRow >= 0 && curRow < neighborhoodGrid.getSize()) && (curColumn >= 0 && curColumn < neighborhoodGrid.getSize())){
                addToHouseholds(households, curRow, curColumn);
            }
        }
    }

    /**
     * Helper Method: If the household at position "houseRow", "houseCol" in the grid is of a certain household,
     * method increments the array index of that sport in "households" by 1
     *
     * @param households An array to store the aggregated counts of sports preferences for neighboring households.
     * @param houseRow the row position of the neighboring household in grid
     * @param houseCol the column position of the neighboring household in grid
     */
    private void addToHouseholds(int households[], int houseRow, int houseCol){
        //If the neighbor at houseRow, housecol preference is of a certain sport, increments it's index accordingly
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.BASEBALL) households[0] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.BASKETBALL) households[1] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.EVERYTHING) households[2] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.FOOTBALL) households[3] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.NOTHING) households[4] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.RUGBY) households[5] += 1;
        if(neighborhoodGrid.grid[houseRow][houseCol].getPreference() == Sports.SOCCER) households[6] += 1;
    }

    /**
     * A method used to clear the Household.members array to allow the next household to properly survey its
     * neighbors without the results of the previous household affecting update conditions
     */
    public static void clearMembers(){
        //Interates through Household.members and replaces all elements with 0
        for(int i = 0; i < Household.members.length; i++){
            Household.members[i] = 0;
        }
    }

    /**
     * Retrieves the sports preference of the household.
     *
     * @return The specific sport preference of the household, represented as a value from the Sports enumeration.
     */
    public abstract Sports getPreference();

    /**
     * Determines the state of the household in the next simulation step based on its current
     * context and external influences. This method computes the next household to occupy
     * the corresponding grid cell in the updated grid.
     *
     * @param newGrid the updated neighborhood grid for the upcoming simulation step
     * @param month   the current month of the simulation, potentially influencing the household's state
     * @return the household that will occupy this position in the new grid
     */
    public abstract Household next(NeighborhoodGrid newGrid, int month);

    /**
     * Overridden Method: Checks to see if two Household objects are equal by checking if they share
     * the same class/household
     *
     * @param other An object that is being passed to see if it's the same object type as the object the method
     *              is being called on.
     */
    @Override
    public boolean equals(Object other) {
        //if true, both objects share the same memory address, so they equal each other content wise as well
        if(this == other){
            return true;
        }
        //If other is either null, or the classes of "this" and "other" different, objects are NOT equal
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        //If no return statement has been met, objects are equal at this point of program
        return true;
    }
}
