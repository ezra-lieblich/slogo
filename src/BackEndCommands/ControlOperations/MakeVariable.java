package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert Steilberg
 * @author ezra
 *         <p>
 *         This command instance represents the MakeVariable command in Logo.
 */
public class MakeVariable extends ControlCommand {

    private static final int ARGS = 2;

    /**
     * Adds to the variable map with the first child being the key and the second child
     * the value. Returns the value
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode variable = node.getChild(0);
        ParseTreeNode value = node.getChild(1);
        String varName = variable.getRawCommand();
        double varVal = value.executeCommand(value);
        getVariables().put(varName,varVal);
        return varVal;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}