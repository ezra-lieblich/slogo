package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

public class ClearStamps extends DisplayCommand{
    private static final int ARGS = 0;

	
	 /**
     * calls DisplayCommand to alert fron end to clear stamps
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.clearStamp();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
	

}
