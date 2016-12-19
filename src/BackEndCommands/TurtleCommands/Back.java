package BackEndCommands.TurtleCommands;


import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the back command
 *
 * @author ezra
 */
public class Back extends TurtleCommand {
    private static final int ARGS = 1;

    /**
     * Performs the same update as forward command except it subtracts distance instead of adding it
     * Returns the distance moved
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double x = properties.calculateXDistance(arg1, false);
        double y = properties.calculateYDistance(arg1, false);
        properties.setNewLineProperty(true);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
