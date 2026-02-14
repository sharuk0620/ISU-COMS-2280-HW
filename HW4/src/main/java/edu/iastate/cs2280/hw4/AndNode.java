package edu.iastate.cs2280.hw4;

/**
 * Class AndNode is a concrete class that extends from Abstract parent Class BaseNode. It defines the node object for
 * the (&; AND) logic gate, providing a constructor to instantiate an AND gate node and evaluate logic at the node using
 * its children.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class AndNode extends BaseNode{

    /**
     * Constructor. Calls the BaseNode's constructor, passing in a String "&" to assign the node's data to represent it
     * as an AND gate node.
     */
    public AndNode(){
        super("&");
    }

    /**
     * Overrides BaseNode's evaluateLogic() method. Obtains the evaluated logic from it's left and right child (Applicable
     * as AND is a binary gate), and returns the proper output depending on its children's evaluated logic.
     */
    @Override
    public int evaluateLogic(){
        //Creates int variable leftChildVal and assigns it to the call of evaluatedLogic on the node's left child node. (Recursion)
        int leftChildVal = leftChild.evaluateLogic();

        //Creates int variable rightChildVal and assigns it to the call of evaluatedLogic on the node's right child node. (Recursion)
        int rightChildVal = rightChild.evaluateLogic();

        //If both leftChildVal and rightChildval are 1, returns 1. Otherwise, returns 0.
        if(leftChildVal == 1 && rightChildVal == 1){
            return 1;
        } else {
            return 0;
        }
    }
}
