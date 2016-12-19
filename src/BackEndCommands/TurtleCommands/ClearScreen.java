package BackEndCommands.TurtleCommands;


import BackEndCommands.TurtleCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * Executes the ClearScreen command
 *
 * @author ezra
 */
public class ClearScreen extends TurtleCommand {
    private static final int ARGS = 0;

    /**
     * Updates x, y property to the origin. Also sets clear property to true to tell front end to erase
     * all lines created.
     * Returns the distance traveled to the origin
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        double answer = properties.calculateTotalDistance(0, 0);
        properties.setXProperty(0);
        properties.setYProperty(0);
        properties.setNewLineProperty(true);
        properties.setClearScreenProperty(true);
        return answer;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
