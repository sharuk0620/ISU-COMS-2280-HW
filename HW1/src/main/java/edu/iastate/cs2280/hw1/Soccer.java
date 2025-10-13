package edu.iastate.cs2280.hw1;
/**
 * The Soccer Class in one of 7 different Household preferences that some household in a NeighborhoodGrid
 * could have. The Soccer class contains constructors to initialize Soccer objects, primarily in a
 * NeighborhoodGrid object's 2D array "grid", and methods to retrieve data about the Household as well as
 * update it for simulation purposes depending on its neighbors.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class Soccer extends SportsHouseholds{

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    public Soccer() {

    }

    /**
     * Parameterized Constructor: Constructs a Soccer object in specified NeighborhoodGrid object "grid"
     * at position "row", "column", with an interest level of "interestLevel".
     *
     * @param grid The Neighborhood object whose grid should contain the newly created Soccer household
     *             (at its specified location).
     * @param row The row at which the household will be instantiated in the grid
     * @param column The column at which the household will be instantiated in the grid
     * @param interestLevel The level of interest the household has for Soccer
     */
    public Soccer(NeighborhoodGrid grid, int row, int column, int interestLevel) {
        super(grid, row, column, interestLevel);
    }

    /**
     * Overridden Method: Returns the Constant "SOCCER" from the Sports Enum to determine preference
     * for comparison/simulation purposes
     */
    @Override
    public Sports getPreference() {
        return Sports.SOCCER;
    }

    /**
     * Overridden Method: Returns the string representation of the Soccer Household, which is the Household's
     * Sport Preference ("S" for Soccer) followed by its interest level and one whitespace.
     */
    @Override
    public String toString() {
        return "S" + interestLevel + " ";
    }

    /**
     * Overridden Method: Determines the state of the Soccer household in the next simulation step based on its
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

        //If interest is max, then household becomes a Nothing household
        if(this.getInterest() == MAX_INTEREST){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Nothing(neighborhoodGrid, this.row, this.column);
            return newGrid.grid[row][column];

        //If (Football + Basketball >= Soccer), then household becomes a Everything household
        } else if(Household.members[3] + Household.members[1] >= Household.members[6]){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Everything(neighborhoodGrid, this.row, this.column);
            return newGrid.grid[row][column];

        //If (Soccer > Baseball + Basketball + Football), then household becomes a Soccer household, lvl 5
        } else if(Household.members[6] > Household.members[0] + Household.members[1] + Household.members[3]
                + Household.members[5]){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Soccer(neighborhoodGrid, this.row, this.column, 5);
            return newGrid.grid[row][column];

        //If (Rugby > 4), then household becomes a Rugby household, lvl 3
        } else if(Household.members[5] > 4){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Rugby(neighborhoodGrid, this.row, this.column, 3);
            return newGrid.grid[row][column];

        //If (Everything = 0), then household becomes a Soccer household, lvl 5
        } else if (Household.members[2] == 0){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Nothing(neighborhoodGrid, this.row, this.column);
            return newGrid.grid[row][column];

        //Else, household becomes a Soccer household with lvl incremented by 1
        } else {
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Soccer(neighborhoodGrid, this.row, this.column,
            this.interestLevel + 1);
            return newGrid.grid[row][column];
        }
    }
}
