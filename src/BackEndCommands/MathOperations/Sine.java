package BackEndCommands.MathOperations;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Ezra Lieblich
 *         <p>
 *         This command instance represents the sin command in Logo.
 */
public class Sine implements Command {

    private static final int ARGS = 1;

    public void setProperties(Object o) {
        return;
    }

    /**
     * Returns the sin with angle being the arg given
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double value1 = arg1.executeCommand(arg1);
        return Math.sin(Math.toRadians(value1));
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}