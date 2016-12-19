package BackEndInterpreter;

/**
 * @author Robert Steilberg
 * @author ezra
 *         <p>
 *         An interface for the Command object, which specifies
 *         the actions and arguments unique to each Logo command.
 */
public interface Command {

    /**
     * Sets an object for a command object. Used primarily for setting observable
     * properties for turtle commands or for setting variable mappings for defining
     * variables.
     *
     * @param o is the object to set
     */
    void setProperties(Object o);


    /**
     * Performs the actions unique to each Command instance
     *
     * @param node is the current ParseTreeNode
     * @return the result of executing the command
     */
    double executeCommand(ParseTreeNode node);

    /**
     * Specifies the number of arguments that a Command takes
     *
     * @return an int that specifies the number of accepted arguments
     */
    int numArguments();
}