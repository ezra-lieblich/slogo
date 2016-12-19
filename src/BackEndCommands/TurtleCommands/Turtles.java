package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the Turtles command
 * @author ezra
 *
 */
public class Turtles extends TurtleCommand {
    private static final int ARGS = 0;

    /**
     *Calls the ObservableComposite class to get the count of the amount of turtles created
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.getTurtleCount();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
