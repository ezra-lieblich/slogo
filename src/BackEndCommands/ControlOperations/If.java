package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert H. Steilberg II
 * @author ezra
 *         <p>
 *         This command instance represents an If statement in Logo.
 */
public class If extends ControlCommand {

    private static final int ARGS = 2;

    /**
     * Runs the commands specified in the second child if the first child does not equal zero
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
    	ParseTreeNode condition = node.getChild(0);
		ParseTreeNode body = node.getChild(1);
		Double value = condition.executeCommand(condition);
        if (value == 0) {
            return 0;
        } else {
            return body.executeCommand(body);
        }
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}