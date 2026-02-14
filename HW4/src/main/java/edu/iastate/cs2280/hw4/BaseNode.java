package edu.iastate.cs2280.hw4;

/**
 * Abstract Class BaseNode<E> defines the general structure for a node object in the Logic Binary Tree. It holds data of
 * type E (Generic), as well as references to the left and right child of the node (if applicable). It is the parent
 * to the several explicit types of nodes that define various types of Logic Gates or an input variable. In addition,
 * it defines a few methods for its subclasses (gate and variable nodes) to implement, such as to evaluate inputs
 * based off it's node type, and to check if it is a variable node.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 */
public abstract class BaseNode<E> {

    /**
     * Reference variable to hold the object reference to the right child of "this" BaseNode, (if the child exists)
     */
    protected BaseNode<E> rightChild;

    /**
     * Reference variable to hold the object reference to the left child of "this" BaseNode, (if the child exists)
     */
    protected BaseNode<E> leftChild;

    /**
     * Reference variable to contain the data of type E of "this" BaseNode. In this project's case, the data will be of
     * type String.
     */
    protected E nodeData;

    /**
     * Constructor. Instantiates a BaseNode, but as this is an Abstract class, this constructor is called by of its
     * subclasses with the usage of the super keyword.
     *
     * @param data the data of type E to be held by the node.
     */
    public BaseNode(E data){
        nodeData = data;
    }

    /**
     * Evaluates the logic that the node holds by utilizing the recursed evaluated logic of it's left and/or right
     * children (if applicable). This is an abstract method, and is implemented by all subclasses.
     */
    public abstract int evaluateLogic();

    /**
     * Returns true or false  if this type of node is a variable node or if it is a gate node, respectively.
     */
    public boolean isVar(){
        return false;
    }
}
