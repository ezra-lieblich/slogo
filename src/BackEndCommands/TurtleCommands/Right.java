package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the right command
 *
 * @author ezra
 */
public class Right extends TurtleCommand {
    private static final int ARGS = 1;

    /**
     * Updates the rotation of image clockwise and updates the rotation property
     * Returns the degrees rotated
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double answer = properties.setRotateProperty(arg1, false, true);
        return answer;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
