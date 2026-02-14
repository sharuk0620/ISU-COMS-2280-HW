package edu.iastate.cs2280.hw4;

/**
 * Class OrNode is a concrete class that extends from Abstract parent Class BaseNode. It defines the node object for
 * the (|; OR) logic gate, providing a constructor to instantiate an OR gate node and evaluate logic at the node using
 * its children.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class OrNode extends BaseNode{

    /**
     * Constructor. Calls the BaseNode's constructor, passing in a String "|" to assign the node's data to represent it
     * as an OR gate node.
     */
    public OrNode(){
        super("|");
    }

    /**
     * Overrides BaseNode's evaluateLogic() method. Obtains the evaluated logic from it's left and right child (Applicable
     * as OR is a binary gate), and returns the proper output depending on its children's evaluated logic.
     */
    @Override
    public int evaluateLogic(){

        //Creates int variable leftChildVal and assigns it to the call of evaluatedLogic on the node's left child node. (Recursion)
        int leftChildVal = leftChild.evaluateLogic();

        //Creates int variable rightChildVal and assigns it to the call of evaluatedLogic on the node's right child node. (Recursion)
        int rightChildVal = rightChild.evaluateLogic();

        //If either leftChildVal or rightChildval are 1 (or both), evaluates and returns 1. Otherwise, returns 0.
        if(leftChildVal == 1 || rightChildVal == 1){
            return 1;
        } else {
            return 0;
        }
    }
}
