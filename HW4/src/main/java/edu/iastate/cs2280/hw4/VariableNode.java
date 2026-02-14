package edu.iastate.cs2280.hw4;

/**
 * Class VariableNode is a concrete class that extends from Abstract parent Class BaseNode. It defines the node object for
 * input variable for the tree, providing a constructor to instantiate the variable node. It also contains a method to
 * set the node's value when evaluating the tree.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class VariableNode extends BaseNode{

    /**
     * Reference variable to store the value of node defined by the circuit definition (0 or 1).
     */
    protected int value;

    /**
     * Constructor. Calls the BaseNode's constructor, passing in a String label. In addition, it sets the default value
     * of the node to 0.
     *
     * @param label A string to assign the node's data to the variable's name in the circuit definition.
     */
    public VariableNode(String label){
        super(label);
        value = 0;
    }

    /**
     * Overrides BaseNode's evaluateLogic() method. Returns the value of the node as a Variable node does not represent
     * a gate nor has children to utilize their evaluated logic. Serves as the base case for recursive evaluatedLogic()
     * calls for other node classes, as they will end once they reach the leaf nodes of the tree, A.K.A, the variable
     * nodes.
     */
    @Override
    public int evaluateLogic(){
        return value;
    }

    /**
     * Sets the value of the node to newVal. Used when assigning input values from circuit definitions to the proper
     * variable nodes.
     */
    public void setValue(int newVal){
        value = newVal;
    }

    /**
     * Overrides BaseNode's isVar() method. As VariableNode represents a variable in the circuit, it returns true.
     */
    @Override
    public boolean isVar(){
        return true;
    }
}
