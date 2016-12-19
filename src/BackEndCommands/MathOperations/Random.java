package BackEndCommands.MathOperations;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;


/**
 * Executes the Random Command
 *
 * @author ezra
 */
public class Random implements Command {

    private static final int ARGS = 1;

    public void setProperties(Object o) {
        return;
    }

    /**
     * Returns a random double between zero and argument specified
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double value1 = arg1.executeCommand(arg1);
        return Math.random() * value1;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}