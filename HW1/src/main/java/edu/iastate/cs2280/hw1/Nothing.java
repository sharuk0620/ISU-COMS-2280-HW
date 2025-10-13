package edu.iastate.cs2280.hw1;
/**
 * The Nothing Class in one of 7 different Household preferences that some household in a NeighborhoodGrid
 * could have. The Nothing class contains constructors to initialize Nothing objects, primarily in a
 * NeighborhoodGrid object's 2D array "grid", and methods to retrieve data about the Household as well as
 * update it for simulation purposes depending on its neighbors.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class Nothing extends Household{

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    public Nothing() {

    }

    /**
     * Parameterized Constructor: Constructs a Nothing object in specified NeighborhoodGrid object "grid"
     * at position "row", "column". (Uses Household's constructor as no newly parameters are needed for a
     * Nothing household)
     *
     * @param grid The Neighborhood object whose grid should contain the newly created Nothing household
     *             (at its specified location).
     * @param row The row at which the household will be instantiated in the grid
     * @param column The column at which the household will be instantiated in the grid
     */
    public Nothing(NeighborhoodGrid grid, int row, int column) {
        super(grid, row, column);
    }

    /**
     * Overridden Method: Returns the Constant "NOTHING" from the Sports Enum to determine preference
     * for comparison/simulation purposes
     */
    @Override
    public Sports getPreference() {
        return Sports.NOTHING;
    }

    /**
     * Overridden Method: Returns the string representation of the Everything Household, which is the Household's
     * Sport Preference ("N" for Everything) and two whitespace.
     */
    @Override
    public String toString() {
        return "N" + "  ";
    }

    /**
     * Overridden Method: Determines the state of the Nothing household in the next simulation step based on its
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

        //If (Soccer > 5), then household becomes a Soccer Household, lvl 5
        if(Household.members[6] > 5){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Soccer(neighborhoodGrid, this.row, this.column, 5);
            return newGrid.grid[row][column];

        //If (Football > 4), then household becomes a Football Household, lvl 0
        } else if(Household.members[3] > 4){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Football(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //If (Basketball > 3), then household becomes a Basketball Household, lvl 0
        } else if(Household.members[1] > 3){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Basketball(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //If (Baseball > 2), then household becomes a Baseball Household, lvl 0
        } else if(Household.members[0] > 2){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Baseball(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //If (Rugby > 1), then household becomes a Rugby Household, lvl 2
        } else if(Household.members[5] > 1){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Rugby(neighborhoodGrid, this.row, this.column, 2);
            return newGrid.grid[row][column];

        //If (Everything >= 1), then household becomes an Everything Household
        } else if(Household.members[2] >= 1){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Everything(neighborhoodGrid, this.row, this.column);
            return newGrid.grid[row][column];

        //Else, household stays a Nothing household.
        } else {
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = this;
            return newGrid.grid[row][column];
        }
    }
}
