package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInternalAPI.ParseTreeNode;

/**
 * @author Robert H. Steilberg II
 *         <p>
 *         This command instance represents an if statement in Logo.
 */
public class If extends ControlCommand {

    private static final int ARGS = 2;

    @Override
    public double executeCommand(ParseTreeNode node) {
    	ParseTreeNode condition = node.getChild(0);
		ParseTreeNode commandBody = node.getChild(1);
		Double value = condition.executeCommand(condition);
        if (value == 0) {
            return 0;
        } else {
            return commandBody.executeCommand(commandBody);
        }
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
