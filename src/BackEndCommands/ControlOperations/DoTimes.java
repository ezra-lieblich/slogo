package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert Steilberg
 * @author ezra
 *         <p>
 *         This command instance represents a DoTimes statement in Logo.
 */
public class DoTimes extends ControlCommand {

    private static final int ARGS = 2;

    /**
     * Runs the commands in the second children by the amount specified in the first child's second child.
     * Also updates the specified variable which is the first child's first child
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode varNode = node.getChild(0).getChild(0);
        ParseTreeNode limitNode = node.getChild(0).getChild(1);
        ParseTreeNode body = node.getChild(1);
        String variable = varNode.getRawCommand();
        double limit = limitNode.executeCommand(limitNode);
		getVariables().put(variable, 1.0); // start at 1
        double result = 0;
        for (double i = getVariables().get(variable); i <= limit; i++) {
            getVariables().put(variable,i); // update variable as body is executed
            result = body.executeCommand(body);
        }
        return result;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}