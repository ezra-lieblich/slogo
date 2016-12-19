package BackEndCommands;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert H. Steilberg II
 * @author ezra
 *         <p>
 *         This command instance represents the end of a list in Logo.
 */
public class ListEnd implements Command {

    private static final int ARGS = 0;

    public void setProperties(Object o) {
        return;
    }

    @Override
    public double executeCommand(ParseTreeNode node) {
        return 0.0;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
