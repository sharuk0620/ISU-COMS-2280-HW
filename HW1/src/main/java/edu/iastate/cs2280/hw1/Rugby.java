package edu.iastate.cs2280.hw1;
/**
 * The Rugby Class in one of 7 different Household preferences that some household in a NeighborhoodGrid
 * could have. The Rugby class contains constructors to initialize Rugby objects, primarily in a
 * NeighborhoodGrid object's 2D array "grid", and methods to retrieve data about the Household as well as
 * update it for simulation purposes depending on its neighbors.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class Rugby extends SportsHouseholds{

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    public Rugby() {

    }

    /**
     * Parameterized Constructor: Constructs a Rugby object in specified NeighborhoodGrid object "grid"
     * at position "row", "column", with an interest level of "interestLevel".
     *
     * @param grid The Neighborhood object whose grid should contain the newly created Rugby household
     *             (at its specified location).
     * @param row The row at which the household will be instantiated in the grid
     * @param column The column at which the household will be instantiated in the grid
     * @param interestLevel The level of interest the household has for Rugby
     */
    public Rugby(NeighborhoodGrid grid, int row, int column, int interestLevel) {
        super(grid, row, column, interestLevel);
    }

    /**
     * Overridden Method: Returns the Constant "RUGBY" from the Sports Enum to determine preference
     * for comparison/simulation purposes
     */
    @Override
    public Sports getPreference() {
        return Sports.RUGBY;
    }

    /**
     * Overridden Method: Returns the string representation of the Rugby Household, which is the Household's
     * Sport Preference ("R" for Rugby) followed by its interest level and one whitespace.
     */
    @Override
    public String toString() {
        return "R" + interestLevel + " ";
    }

    /**
     * Overridden Method: Determines the state of the Rugby household in the next simulation step based on its
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

        //If (Football + Soccer >= 8), then household becomes a Soccer household, lvl 2
        } else if(Household.members[3] + Household.members[6] >= 8){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Soccer(neighborhoodGrid, this.row, this.column, 2);
            return newGrid.grid[row][column];

        //If (Baseball > 2 * Basketball), then household becomes a Baseball household, lvl 4
        } else if(Household.members[0] > (2 * (Household.members[1]))){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Baseball(neighborhoodGrid, this.row, this.column, 4);
            return newGrid.grid[row][column];

        //If (Rugby < 2), then household becomes a Football household, lvl 0
        } else if(Household.members[5] < 2){
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Football(neighborhoodGrid, this.row, this.column, 0);
            return newGrid.grid[row][column];

        //Else, household becomes a Rugby household with lvl incremented by 1
        } else {
            //Updates newGrid with proper household, and then returns such household
            newGrid.grid[row][column] = new Rugby(neighborhoodGrid, this.row, this.column,
            this.interestLevel + 1);
            return newGrid.grid[row][column];
        }
    }
}
