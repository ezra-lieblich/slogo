package BackEndCommands;

import java.util.List;

import BackEndInternalAPI.Command;

public class Sum implements Command {
	private static final int ARGS = 2;
	
	@Override
	public double executeCommand(List<Double> args) {
		return args.get(0) + args.get(1);
	}
	@Override
	public int numArguments() {
		return ARGS;
	}
}
