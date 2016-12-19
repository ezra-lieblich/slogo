package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Performs the Ask commands
 * @author ezra
 *
 */
public class Ask extends TurtleCommand {
    private static final int ARGS = 2;

    /**Calls the ObservableComponent class to execute the Ask command
     *
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.performAsk(node);
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
