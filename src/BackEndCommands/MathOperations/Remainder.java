package BackEndCommands.MathOperations;

import java.util.List;

import BackEndInternalAPI.Command;
import BackEndInternalAPI.ParseTreeNode;
import org.apache.velocity.runtime.directive.Parse;


/**
 * Executes the Remainder command
 * @author ezra
 *
 */
public class Remainder implements Command {

	private static final int ARGS = 2;
	
	/**
	 * Returns the remainder of the first arg divided by the second arg
	 */
	@Override
	public double executeCommand(ParseTreeNode node) {
        ParseTreeNode arg1 = node.getChild(0);
        ParseTreeNode arg2 = node.getChild(1);
        double value1 = arg1.executeCommand(arg1);
        double value2 = arg2.executeCommand(arg2);
		return value1 % value2;
	}

	@Override
	public int numArguments() {
		return ARGS;
	}
}