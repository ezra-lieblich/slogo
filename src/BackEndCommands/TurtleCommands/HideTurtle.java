package BackEndCommands.TurtleCommands;


import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the HideTurtle command
 *
 * @author ezra
 */
public class HideTurtle extends TurtleCommand {
    private static final int ARGS = 0;

    /**
     * Sets image visible property to false
     * Returns 0
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        properties.setImageVisibleProperty(false);
        return 0;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
