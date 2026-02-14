package edu.iastate.cs2280.hw4;

/**
 * Class NotNode is a concrete class that extends from Abstract parent Class BaseNode. It defines the node object for
 * the (!; NOT) logic gate, providing a constructor to instantiate an NOT gate node and evaluate logic at the node using
 * its single child (Unary Gate)
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public class NotNode extends BaseNode{

    /**
     * Constructor. Calls the BaseNode's constructor, passing in a String "!" to assign the node's data to represent it
     * as an NOT gate node.
     */
    public NotNode(){
        super("!");
    }

    /**
     * Overrides BaseNode's evaluateLogic() method. Obtains the evaluated logic from it's left child (Applicable
     * as NOT is a unary gate), and returns the proper output depending on its child's evaluated logic.
     */
    @Override
    public int evaluateLogic(){

        //Creates int variable leftChildVal and assigns it to the call of evaluatedLogic on the node's left child node. (Recursion)
        int leftChildVal = leftChild.evaluateLogic();

        //If leftChildVal is 1, evaluates and returns 0. Otherwise, returns 1.
        if(leftChildVal == 1){
            return 0;
        } else {
            return 1;
        }
    }
}
