package BackEndCommands.TurtleCommands;

import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the Tell Command
 * @author ezra
 *
 */
public class Tell extends TurtleCommand {
    private static final int ARGS = 1;

    /**
     *Calls the ObservableComposite command to perform the tell command
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.performTell(node);
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
