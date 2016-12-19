package BackEndCommands.TurtleCommands;


import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the forward command
 *
 * @author ezra
 */
public class Forward extends TurtleCommand {
    private static final int ARGS = 1;

    /**
     * Calculates the distance moved and changes x and y property. Also lets front end now needs a new line
     * Returns the distance moved
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double x = properties.calculateXDistance(arg1, true);
        double y = properties.calculateYDistance(arg1, true);
        properties.setNewLineProperty(true);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
