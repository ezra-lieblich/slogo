package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Sets the background display images color
 * @author ezra
 */
public class SetBackground extends DisplayCommand {
    private static final int ARGS = 1;

    /**
     * Sets the background color of the display
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double value = arg1.executeCommand(arg1);
        properties.setBackgroundImage(value);
        return value;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
