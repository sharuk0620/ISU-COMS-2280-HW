package edu.iastate.cs2280.hw4;

/**
 * Class XorNode is a concrete class that extends from Abstract parent Class BaseNode. It defines the node object for
 * the (^; XOR) logic gate, providing a constructor to instantiate an XOR gate node and evaluate logic at the node using
 * its children.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class XorNode extends BaseNode{

    /**
     * Constructor. Calls the BaseNode's constructor, passing in a String "^" to assign the node's data to represent it
     * as an XOR gate node.
     */
    public XorNode(){
        super("^");
    }

    /**
     * Overrides BaseNode's evaluateLogic() method. Obtains the evaluated logic from it's left and right child (Applicable
     * as XOR is a binary gate), and returns the proper output depending on its children's evaluated logic.
     */
    @Override
    public int evaluateLogic(){

        //Creates int variable leftChildVal and assigns it to the call of evaluatedLogic on the node's left child node. (Recursion)
        int leftChildVal = leftChild.evaluateLogic();

        //Creates int variable rightChildVal and assigns it to the call of evaluatedLogic on the node's right child node. (Recursion)
        int rightChildVal = rightChild.evaluateLogic();

        //If leftChildVal and rightChildVal not equal (i.e 0 and 1), evaluates and returns 1. Otherwise, returns 0 if same value-wise.
        if(leftChildVal != rightChildVal){
            return 1;
        } else {
            return 0;
        }
    }

}
