package edu.iastate.cs2280.hw4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Concrete Class CircuitTree is the main class of the Digital Logic Circuit Simulator. It provides a constructor
 * to build the Circuit in a binary tree format, as well as evaluate the tree using various inputs and print out the tree
 * in the console. CircuitTree also contains the main method for running the simulation.
 *
 * @author Sai Sharuk Lakshmi Narayanan
 * NOTE: In this projected, I attempted Extra Credit Option B: Iterative Constructor for Building the Circuit Tree (15%)
 */
public class CircuitTree {

    /**
     * Reference variable that contains the root node of the binary tree representing the circuit
     */
    private BaseNode<String> root;

    /**
     * Constructor. Builds the circuit by creating a binary expression tree from the passed in prefix expression
     * that defines the circuit. Utilizes an iterative way to create the expression tree, using a stack to keep track
     * of the tree building process.
     *
     * @param circuitDescription a string containing the prefix tree definition of a binary tree representing a circuit
     */
    public CircuitTree(String circuitDescription){

        //Initializes string array tokens and contains the individual elements of the prefix statement (no whitespace)
        String[] tokens = circuitDescription.split("\\s+");

        //Initializes Deque myStack that handles BaseNodes of type String. and assigns it to an ArrayDeque collection.
        Deque<BaseNode<String>> myStack  = new ArrayDeque<BaseNode<String>>();

        //Iterates through tokens from the last element to the first, due to the elements being in prefix
        for(int i = tokens.length - 1; i >= 0; --i){

            //Initializes string variable curToken and assigns it the individual element of the prefix statement at index i
            String curToken = tokens[i];

            //Initializes a BaseNode object of Type String curnode.
            BaseNode<String> curNode = null;

            //if curToken is equal to &, |, or ^, assigns curNode to the respective type of logic Gate Node
            if(curToken.equals("&") || curToken.equals("|") || curToken.equals("^")){
                //Switch case to assign curNode to the proper type of Logic Gate node depending on curToken
                switch(curToken) {
                    case "&":
                        curNode = new AndNode();
                        break;
                    case "|":
                        curNode = new OrNode();
                        break;
                    case "^":
                        curNode = new XorNode();
                        break;
                }

                //Assigns curNode's right child reference to the node popped from the stack
                curNode.leftChild = myStack.pop();

                //Assigns curNode's left child reference to the 2nd node popped from the stack
                curNode.rightChild = myStack.pop();

                //After assignments, pushes curNode back into the stack (now a binary tree)
                myStack.push(curNode);

            //If curToken  is equal to !, (NOT, unary gate)
            } else if(curToken.equals("!")){

                //Assigns curNode to a new NotNode object
                curNode = new NotNode();

                //Assigns  curNode's left child to the node popped from the stack, as it is a unary gate.
                curNode.leftChild = myStack.pop();

                //After assignments, pushes curNode back into the stack (now a binary tree)
                myStack.push(curNode);

            //Otherwise, curToken refers to a variable
            } else {
                //Creates a new VariableNode, passing in the variable into the constructor, and pushes node back into stack
                myStack.push(new VariableNode(tokens[i]));
            }
        }

        //After complete iteration, pops the remaining node out of stack, which represents the root node of the tree
        root = myStack.pop();
    }

    /**
     * Evaluates the circuit with a set of passed in variable input values in a post-order process.
     * These sets are the subsequent lines after the circuit prefix definition in a circuit file. Before evaluation, the
     * tree is traversed to set the variable nodes to the correct values, and then evaluated using each class's
     * evaluateLogic() method.
     *
     * @param inputLine a string containing the variable inputs for a binary tree representing a circuit
     */
    public void evaluate(String inputLine){

        //Initializes a string array assignments and contains the individual variable assignments at each index
        String[] assignments = inputLine.trim().split("\\s+");

        //Iterates through each variable assignment in the assignments string array
        for(int i = 0; i < assignments.length; i++){

            //Initializes string array temp and contains variable assignment at index i split into name and value
            String[] temp = assignments[i].split("=");

            //Initializes and assigns String variable nodeLabel to the variable name located at temp[0]
            String nodeLabel = temp[0];

            //Initializes and assigns int variable nodeValue to the variable input value after converting it to Integer at temp[0]
            int nodeValue = Integer.parseInt(temp[1]);

            //Calls assignmentVarNodeValues helper method to traverse tree, find corresponding variable node, and assign input.
            assignmentVarNodeValues(root, nodeLabel, nodeValue);

        }

        //Initializes and assigns int variable result to output from binary tree using recursive evaluateLogic() method of each node class
        int result = root.evaluateLogic();

        //Print out the set of inputs for each variable and the outputted result from the tree
        System.out.println(inputLine.trim() + " -> " + result);
    }

    /**
     * Recursive Helper method for CircuitTree's evaluate() method. Traverses the tree using post-order traversal and
     * assigns the corresponding variable node with the value given by the set of inputs from circuit definition file.
     *
     * @param rootNode the current rootNode being traversed. Evaluates the left and right child first before rootNode
     * @param nodeName a string containing the name of the variable node to be used for identification
     * @param nodeValue an int variable containing the input value the corresponding variable node should hold.
     */
    private void assignmentVarNodeValues(BaseNode<String> rootNode, String nodeName, int nodeValue){
        //Termination case: If root node is null, meaning rootNode refers to child reference of a variable node (null), returns gracefully
        if(rootNode == null){
            return;
        }

        //Recursively calls assignmentVarNodeValues on left child and right child nodes of rootNode
        assignmentVarNodeValues(rootNode.leftChild, nodeName, nodeValue);
        assignmentVarNodeValues(rootNode.rightChild, nodeName, nodeValue);

        //If current rootNode is a variable, meaning it's a Variable Node
        if(rootNode.isVar()){

            //Initializes curVarNode VariableNode object and assigns to rootNode after being downcasted to VariableNode
            VariableNode curVarNode = (VariableNode) rootNode;

            //If the data of the VariableNode (the name) matches nodeName, the correct VariableNode has been found
            if(curVarNode.nodeData.equals(nodeName)){

                //Sets the value of the VariableNode to the value contained in NodeValue
                curVarNode.setValue(nodeValue);
            }
        }
    }

    /**
     * Prints out the binary tree that represents a certain circuit onto the console with ASCII Art. The tree
     * is displayed in a directory-style format.
     */
    public void printStructure(){

        //Prints out the header above the soon-to-be printed tree
        System.out.println("CIRCUIT STRUCTURE: ");

        //Calls the recursive helper method printTreeDirectoryStyle, passing in root node. Defines isRightChild true to obtain "\-- " start
        printTreeDirectoryStyle(root, "", true);

    }


    /**
     * Recursive Helper Method. Takes in a node, prefix, and a boolean statement to print out the tree in a directory-style
     * ASCII Art in the console
     *
     * @param node the passed in node to be printed out in its proper place in the tree
     * @param prefix a string that contains the ASCII art offset to maintain printing formatting
     * @param isRightChild a boolean to determine if the node is the left or right child of the previously-called node
     */
    private void printTreeDirectoryStyle(BaseNode<String> node, String prefix, boolean isRightChild){
        //Base Case: If the node is null (the child node of a variable node), returns gracefully out of recursion.
        if(node == null){
            return;
        }

        //Initializes String variable childStructure to hold the string offset right before the variable name
        String childStructure;

        //Initializes String variable newPrefix to hold the updated prefix string offset after each line printed.
        String newPrefix;

        //If isRightChild is false, meaning the node is a left child node from the previous called node.
        if(!isRightChild){

            //Assigns childStructure to the String offset "|-- "
            childStructure = "|-- ";

            //Assigns newPrefix to the original prefix with an added offset at the end of the string
            newPrefix = prefix + "|   ";

        //Otherwise, if the node is the right child of the node previously called
        } else {

            //Assigns childStructure to the String offset "\\-- ", with the double \\ indicating a single \ on print.
            childStructure = "\\-- ";

            //Assigns newPrefix to the original prefix with an added offset at the end of the string
            newPrefix = prefix + "    ";

        }

        //Prints out: the prefix offset, followed by childStructure, and the data (variable name) that the node holds
        System.out.println(prefix + childStructure +  node.nodeData);

        //Initializes boolean notGate and assigns it a boolean value if node represents a NotNode (! gate)
        boolean notGate = node instanceof NotNode;

        //if notgate is true, meaning node is an instance of a NotNode (! gate)
        if(notGate){

            //Prints the left child (NotNode only have 1 child, and it's the left), and passes in true for isRightChild to print "\--"
            printTreeDirectoryStyle(node.leftChild, newPrefix, true);

        //Otherwise, node is a binary gate node or variable node
        } else {

            //Prints the left child of node, passing in newPrefix and false for isRightChild
            printTreeDirectoryStyle(node.leftChild, newPrefix, false);

            //Prints the right child of node, passing in newPrefix and true for isRightChild
            printTreeDirectoryStyle(node.rightChild, newPrefix, true);
        }
    }

    /**
     * Main Method for initializing and running program. Circuit Prefix Definition and Variable Input arguments are obtained
     * from Command-Line Arguments.
     *
     * @param args An array containing Command-Line Arguments. For this project, they are various file circuit files
     */
    public static void main(String[] args) {

        //Try-Catch Block
        try{
            //Initializes Scanner object fileReader and assigns it a new Scanner object with a passed-in File Object of args[x]
            Scanner fileReader = new Scanner(new File(args[0]));

            //Initializes String variable curLineCircuitDefinition and assigns it to first line in file (Circuit Prefix Definition)
            String curLineCircuitDefinition = fileReader.nextLine();

            //Passes in cirLineCircuitDefinition into CircuitTree constructor to create new CircuitTree instance LogicGateTree.
            CircuitTree LogicGateTree = new CircuitTree(curLineCircuitDefinition);

            //Calls printStructure() on the CircuitTree instance LogicTreeGate to print the tree to the console
            LogicGateTree.printStructure();

            //Prints out a new line (For formatting)
            System.out.println();

            //Prints out the header of the variable-input evaluation section
            System.out.println("EVALUATION:");

            //While the file has additional lines, meaning the variable input sets in this context
            while(fileReader.hasNextLine()){

                //Assigns curLineCircuitDefinition to the next line in the file, which is a variable-input set.
                curLineCircuitDefinition = fileReader.nextLine();

                //Passes in curLineCircuitDefinition into a call to evaluate on the LogicGateTree CircuitTree instance.
                LogicGateTree.evaluate(curLineCircuitDefinition);
            }

        //Catches a FileNotFoundException if the circuit file passed in as a command line argument was not successfully found,
        } catch(FileNotFoundException e){

            //Prints out an error message to console
            System.out.println("Error: File could not be found!");

            //Catches a NoSuchElement if the circuit file passed in was empty.
        } catch(NoSuchElementException e){

            //Prints out an error message to console
            System.out.println("Error: Circuit file is empty!");

        }
    }
}
