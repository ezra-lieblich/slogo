package BackEndCommands.BooleanOperations;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;


/**
 * @author Ezra Lieblich
 *         <p>
 *         This command instance represents the less? command in Logo.
 */
public class LessThan implements Command {

    private static final int ARGS = 2;

    public void setProperties(Object o) {
        return;
    }

    /**
     * Executes its two children and returns 1 if the first value is
     * less, 0 otherwise
     * @param node The current node
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        ParseTreeNode arg2 = node.getChild(1);
        double value1 = arg1.executeCommand(arg1);
        double value2 = arg2.executeCommand(arg2);
        if (value1 < value2) {
            return 1;
        }
        return 0;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}