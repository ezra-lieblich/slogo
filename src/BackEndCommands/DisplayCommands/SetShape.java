package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Changes the image of the turtle in the front end
 * @author ezra
 */
public class SetShape extends DisplayCommand {
    private static final int ARGS = 1;

    /**
     * Sets the image of the turtle
     * Returns the image index
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double value = arg1.executeCommand(arg1);
        properties.setImageIndex(value);
        return value;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
