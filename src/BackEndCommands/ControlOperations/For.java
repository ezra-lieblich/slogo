package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInterpreter.ParseTreeNode;

/**
 * @author Robert Steilberg, Ezra Lieblich
 *         <p>
 *         This command instance represents a For statement in Logo.
 */
public class For extends ControlCommand {

    private static final int ARGS = 2;

    /**
     * Runs the commands specified in the nodes second child. Runs that based off of the
     * increment, start, and end the user specifies. Also updates a variable specified.
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        ParseTreeNode parameters = node.getChild(0);
        ParseTreeNode body = node.getChild(1);
        String variable = parameters.getChild(0).getRawCommand();
        getVariables().put(variable, 1.0); // start at 1
        double start = parameters.getChild(1).executeCommand(parameters.getChild(1));
        double end = parameters.getChild(2).executeCommand(parameters.getChild(2));
        double increment = parameters.getChild(3).executeCommand(parameters.getChild(3));
        double result = 0;
        for (double i = start; i < end; i += increment) {
            getVariables().put(variable, i);
            result = body.executeCommand(body);
        }
        return result;
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}
