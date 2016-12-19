package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the ID command
 * @author ezra
 *
 */
public class ID extends TurtleCommand {
    private static final int ARGS = 0;

    /**
     * Returns the current active turtle
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.getCurrentID();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
