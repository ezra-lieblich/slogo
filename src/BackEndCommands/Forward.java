package BackEndCommands;

import java.util.List;

import BackEndInternalAPI.Command;

public class Forward implements Command {
	private static final int ARGS = 1;

	@Override
	public double executeCommand(List<Double> args) {
		//Need to update line and image position
		return args.get(0);
	}

	@Override
	public int numArguments() {
		return ARGS;
	}

}
