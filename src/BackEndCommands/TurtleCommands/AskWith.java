package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Performs the askWith command
 * @author ezra
 *
 */
public class AskWith extends TurtleCommand {
    private static final int ARGS = 2;

    /**
     *Calls the ObservableComposite class to execute the command
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.performAskWith(node);
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
