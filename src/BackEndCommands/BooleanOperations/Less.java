package BackEndCommands.BooleanOperations;

import java.util.List;

import BackEndInternalAPI.Command;

/**
 * Executes the Less command
 * @author ezra
 *
 */
public class Less implements Command {
	private static final int ARGS = 2;
	
	/**
	 * Returns 1 if the first arg is less then the second arg 0 otherwise
	 */
	@Override
	public double executeCommand(List<Double> args) {
		double answer = 0;
		if (args.get(0) < args.get(1)) {
			answer++;
		}
		return answer;
	}

	@Override
	public int numArguments() {
		return ARGS;
	}

}