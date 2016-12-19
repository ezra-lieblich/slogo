package BackEndCommands;

import BackEndInterpreter.Command;
import BackEndInterpreter.ParseTreeNode;

import java.util.List;

/**
 * @author Robert H. Steilberg II
 * @author ezra
 *         <p>
 *         This command instance represents the beginning of a list in Logo.
 */
public class ListStart implements Command {

	//Note that a list has an unknown amount of children so this is merely a placeholder
    private static final int ARGS = 1;

    public void setProperties(Object o) {
        return;
    }

    /**
     * Loops through all its children and executes the respective childrens commands, returning the last
     * childs double value
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        List<ParseTreeNode> args = node.getChildren();
        double answer = 0;
        for (ParseTreeNode child : args) {
            answer = child.executeCommand(child);
        }
        return answer;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
