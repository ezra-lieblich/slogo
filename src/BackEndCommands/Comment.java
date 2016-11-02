package BackEndCommands;

import BackEndInternalAPI.Command;
import BackEndInternalAPI.ParseTreeNode;

/**
 * @author Robert H. Steilberg II
 *         <p>
 *         This command instance represents a comment in Logo.
 */
public class Comment implements Command {

    private static final int ARGS = 0;

    public void setProperties(Object o) {
        return;
    }

    @Override
    public double executeCommand(ParseTreeNode node) {
        return 0;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
