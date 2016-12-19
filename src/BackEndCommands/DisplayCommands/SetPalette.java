package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Changes the background color display to the color and RGB palette specified
 * @author ezra
 */
public class SetPalette extends DisplayCommand {
    private static final int ARGS = 4;

    /**
     * Sets the color of the background to the first child. Then changes the palette to the RGB values
     * of the second third and fourth child
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        double color = arg1.executeCommand(arg1);
        properties.setBackgroundImage(color);
        double red = node.getChild(1).executeCommand(node.getChild(1));
        double green = node.getChild(2).executeCommand(node.getChild(2));
        double blue = node.getChild(3).executeCommand(node.getChild(3));
        properties.setPallete(red, green, blue);
        return color;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
