package BackEndCommands.TurtleQueries;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the YCor command
 *
 * @author ezra
 */
public class YCor extends TurtleCommand {
    private static final int ARGS = 0;

    /**
     * Gets the y property value of the current active turtle and returns it
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.getYProperty();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}