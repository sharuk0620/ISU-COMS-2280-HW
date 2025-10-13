package edu.iastate.cs2280.hw1;
/**
 * The Everything Class in one of 7 different Household preferences that some household in a NeighborhoodGrid
 * could have. The Everything class contains constructors to initialize Everything objects, primarily in a 
 * NeighborhoodGrid object's 2D array "grid", and methods to retrieve data about the Household as well as
 * update it for simulation purposes depending on its neighbors.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class Everything extends Household{

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    public Everything() {

    }

    /**
     * Parameterized Constructor: Constructs a Everything object in specified NeighborhoodGrid object "grid"
     * at position "row", "column". (Uses Household's constructor as no newly parameters are needed for a
     * Everything household)
     *
     * @param grid The Neighborhood object whose grid should contain the newly created Everything household
     *             (at its specified location).
     * @param row The row at which the household will be instantiated in the grid
     * @param column The column at which the household will be instantiated in the grid
     */
    public Everything(NeighborhoodGrid grid, int row, int column) {
        super(grid, row, column);
    }

    /**
     * Overridden Method: Returns the Constant "EVERYTHING" from the Sports Enum to determine preference
     * for comparison/simulation purposes
     */
    @Override
    public Sports getPreference() {
        return Sports.EVERYTHING;
    }

    /**
     * Overridden Method: Returns the string representation of the Everything Household, which is the Household's
     * Sport Preference ("E" for Everything) and one whitespace.
     */
    @Override
    public String toString() {
        return "E" + "  ";
    }

    /**
     * Overridden Method: Determines the state of the Everything household in the next simulation step based on its
     * current context and external influences. This method computes the next household to occupy
     * the corresponding grid cell in the updated grid.
     *
     * @param newGrid the updated neighborhood grid for the upcoming simulation step
     * @param month   the current month of the simulation, potentially influencing the household's state
     * @return the household that will occupy this position in the new grid
     */
    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {

        //Surveys the neighbors' preferences
        this.survey(Household.members);

        //If (Soccer >= 3), then household becomes a Soccer household, lvl 3
        if(Household.members[6] >= (3 * (Household.members[2]))){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Soccer(neighborhoodGrid, this.row, this.column, 3);
            return newGrid.grid[row][column];

        //If (Football > 3), then household becomes a Football household, lvl 0
        } else if(Household.members[3] > 3){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Football(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //If (Nothing < (Football + Soccer)), then household becomes a Basketball household, lvl 2
        } else if(Household.members[4] < (Household.members[3] + Household.members[6])){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Basketball(neighborhoodGrid, this.row, this.column, 2);
            return newGrid.grid[row][column];

        //If (Rugby > Everything), then household becomes a Rugby household, lvl 0
        } else if(Household.members[5] > Household.members[2]){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Rugby(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //Else, household stays an Everything household
        } else {
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = this;
            return newGrid.grid[row][column];
        }
    }
}
