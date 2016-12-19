// This entire file is part of my masterpiece.
// Robert Steilberg
/**
 * This class is the core of the interpreter. It builds the parse tree which is subsequently executed
 * to get the effects of a Logo command specified by CommandParser. It checks for special cases, like
 * lists, groups, and method definitions, and calls the appropriate helper subclasses when necessary
 * (shown by the use of MethodCaller, ListBuilder, and GroupBuilder in the buildParseTree() method).
 * I think it is well designed because buildParseTree() is concise; it's also easy to understand the
 * underlying logic of the method by skimming through it. All instance variables are private and only
 * accessible through getters and setters. Separate functionality is encapsulated in different methods
 * or different classes, specifically MethodCaller, ListBuilder, and GroupBuilder. Methods and variables
 * are given clear names so that it's easy to understand their functionality.
 */

package BackEndInterpreter;

import BackEndCommands.*;
import BackEndCommands.ControlOperations.To;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;

/**
 * @author Robert Steilberg
 * @author ezra
 *         <p>
 *         This class builds a parse tree of a specified Logo commandType. This is done
 *         by reducing the Logo commandType down into its subparts and then recursively
 *         building a tree where each tree node is a irreducible Logo commandType.
 *         <p>
 *         Dependencies: Command classes, CommandTypeDetector, LogoMethod, Mappings,
 *         ObservableProperties, ParseTreeNode, MethodCaller, ListBuilder, GroupBuilder
 */
public class ParseTreeBuilder {

    private static String[] myCommands;
    private static int myCommandIndex;
    private static CommandTypeDetector myDetector;
    private static ObservableComposite myTurtleProperties;
    private static DisplayProperties myDisplayProperties;
    private static Mappings myMappings;
    private static HashSet<String> myErrors; // errors thrown during execution
    private static MethodCaller myMethodCaller;

    /**
     * Sets the language in which commands will be issued
     *
     * @param languageBinding specifies the language to set
     */
    public void setLanguage(SimpleStringProperty languageBinding) {
        myDetector = new CommandTypeDetector(languageBinding.get());
    }

    /**
     * Sets observable properties used for communicating with the GUI
     *
     * @param turtleProperties  contains properties associated turtle manipulation
     * @param displayProperties contains properties associated with GUI display manipulation
     */
    public void setProperties(ObservableComposite turtleProperties, DisplayProperties displayProperties) {
        myTurtleProperties = turtleProperties;
        myDisplayProperties = displayProperties;
    }

    /**
     * Sets variable and method mappings
     *
     * @param maps is the Mappings object containing the mappings
     */
    public void setMappings(Mappings maps) {
        myMappings = maps;
    }

    /**
     * Sets the error set in which to place thrown errors
     *
     * @param errors is the set in which to place thrown errors
     */
    public void setErrorSet(HashSet<String> errors) {
        myErrors = errors;
    }

    /**
     * Gets the set containing error messages
     *
     * @return the set containing error messages
     */
    public HashSet<String> getErrorSet() {
        return myErrors;
    }

    /**
     * Gets the current command array index
     *
     * @return the current command array index
     */
    public int getMyCommandIndex() {
        return myCommandIndex;
    }

    /**
     * Increments the current command index by one
     */
    public void incrMyCommandIndex() {
        myCommandIndex++;
    }

    /**
     * Determines if the command is a method call
     *
     * @param node is the node with the command object to test
     * @return true if the node represents method call, false otherwise
     */
    private boolean methodCall(ParseTreeNode node) {
        return node.getCommandObj().getClass() == MethodCall.class;
    }

    /**
     * Determines if the current command specifies the beginning of a group
     *
     * @param node is the node representing the current command
     * @return true if the node represents the beginning of a group, false otherwise
     */
    private boolean groupStart(ParseTreeNode node) {
        return node.getCommandObj().getClass() == GroupStart.class;
    }

    /**
     * Determines if the current command specifies the beginning of a list
     *
     * @param node is the node representing the current command
     * @return true if the node represents the beginning of a list, false otherwise
     */
    private boolean listStart(ParseTreeNode node) {
        return node.getCommandObj().getClass() == ListStart.class;
    }

    /**
     * Determines if a TO command is being executed
     *
     * @param node is the node representing the current command
     */
    private void checkifDefiningMethod(ParseTreeNode node) {
        if (node.getCommandObj().getClass() == To.class)
            myMethodCaller.setMethodBeingDefined(myCommands[myCommandIndex + 1]);
    }

    /**
     * Create a new ParseTreeNode and initialize its properties
     *
     * @param currCommand is a String representing the node's command
     * @return the newly initialized ParseTreeNode
     */
    protected ParseTreeNode initParseTreeNode(String currCommand) {
        ParseTreeNode newNode = new ParseTreeNode();
        newNode.setRawCommand(currCommand);
        newNode.setCommandObj(myDetector.getCommandObj(currCommand));
        newNode.getCommandObj().setProperties(myTurtleProperties); // observable turtle properties
        newNode.getCommandObj().setProperties(myMappings); // information about variables
        newNode.getCommandObj().setProperties(myDisplayProperties); //observable display properties
        return newNode;
    }

    /**
     * Recursively builds a parse tree representing a Logo command
     *
     * @return a ParseTreeNode corresponding to the root of a parse tree
     */
    protected ParseTreeNode buildParseTree() {
        String currCommand = myCommands[myCommandIndex];
        ParseTreeNode newChild = initParseTreeNode(currCommand);
        checkifDefiningMethod(newChild);
        if (listStart(newChild)) return new ListBuilder(myCommands).buildList(newChild);
        if (groupStart(newChild)) return new GroupBuilder(myCommands).buildGroup(newChild);
        if (methodCall(newChild))
            return myMethodCaller.callMethod(newChild, myDetector, myMappings);
        if (newChild.getNumChildren() == 0) return newChild; // base case, no more commands to add to the tree
        for (int i = 0; i < newChild.getCommandObj().numArguments(); i++) { // create all children
            myCommandIndex++;
            newChild.addChild(buildParseTree());
        }
        myMethodCaller.clearMethodBeingDefined(); // no longer defining method
        return newChild;
    }

    /**
     * Initializes the recursive function buildParseTree
     *
     * @param commands is a sanitized String array containing the commands issued from the GUI
     * @return the root of the newly built parse tree
     */
    public ParseTreeNode buildNewParseTree(String[] commands) {
        myMethodCaller = new MethodCaller(commands); // one method caller to keep track of recursive definitions
        myCommands = commands;
        myCommandIndex = 0; // will be index of current command
        return buildParseTree();
    }
}