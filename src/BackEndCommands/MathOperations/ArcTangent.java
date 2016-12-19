package BackEndCommands.MathOperations;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Ezra Lieblich
 *         <p>
 *         This command instance represents the atan command in Logo.
 */
public class ArcTangent implements Command {

    private static final int ARGS = 1;

    public void setProperties(Object o) {
        return;
    }

    /**
     * Calculates the ATan value of the value given
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double value1 = arg1.executeCommand(arg1);
        return Math.atan(value1);
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}