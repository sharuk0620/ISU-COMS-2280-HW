package edu.iastate.cs2280.hw1;

/**
 * Represents an abstract household within a neighborhood that has a specific interest
 * in sports. This class is designed to be extended by concrete sports-specific
 * households to define preferences and behavior.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public abstract class SportsHouseholds extends Household implements InterestLevel {
    /**
     * Represents the interest level of the household in a specific sport.
     */
    protected int interestLevel; // Interest level of the household

    /**
     * Constructs a SportsHouseholds object with the specified neighborhood grid,
     * row and column position, and interest level.
     *
     * @param grid          the NeighborhoodGrid instance where the household resides
     * @param row           the row index of the household's position in the grid
     * @param column        the column index of the household's position in the grid
     * @param interestLevel the level of interest of the household in a specific sport
     */
    public SportsHouseholds(NeighborhoodGrid grid, int row, int column, int interestLevel) {
        super(grid, row, column);
        this.interestLevel = interestLevel;
    }

    /**
     * Default Constructor (Unused, but needed for Parameterized Constructor usage)
     */
    protected SportsHouseholds() {

    }

    /**
     * Retrieves the current interest level of the household in its specific sport.
     *
     * @return the interest level of the household as an integer
     */
    @Override
    public int getInterest() {
        return interestLevel;
    }


    /**
     * Overridden Method: Checks to see if two SportHousehold objects are equal by checking if they share
     * the same class/household, as well as the same interest level
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
        //returns if interest level of "this" is equal to "other" as both households are the same preference
        return (this.getInterest() == ((SportsHouseholds) other).getInterest());
    }
}
