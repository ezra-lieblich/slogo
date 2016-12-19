package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Gets the current image index of the turtle
 * @author ezra
 */
public class Shape extends DisplayCommand {
    private static final int ARGS = 0;

    /**
     * Gets the Image index from the display
     * Returns the current image index
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.getImageIndex();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
