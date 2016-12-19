package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert Steilberg
 * @author ezra
 *         <p>
 *         This command instance represents a Repeat statement in Logo.
 */
public class Repeat extends ControlCommand {
    private static final int ARGS = 2;
    private static final String REPCOUNT_VARNAME = ":repcount";

    /**
     * Repeats the command specified in the second child by the first child's value
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode limitNode = node.getChild(0);
        ParseTreeNode body = node.getChild(1);
        double limit = limitNode.executeCommand(limitNode);
        double result = 0;
        for (double i = 1.0; i < limit + 1; i++) {
            getVariables().put(REPCOUNT_VARNAME, i);
            result = body.executeCommand(body);
        }
        getVariables().remove(REPCOUNT_VARNAME);
        return result;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}