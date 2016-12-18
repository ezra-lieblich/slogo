package BackEndCommands.DisplayCommands;

import BackEndCommands.DisplayCommand;
import BackEndInterpreter.ParseTreeNode;

public class Stamp extends DisplayCommand{
    private static final int ARGS = 0;

	
	 /**
     * Calls the add stamp to alert front end to clear stamps
     */
    @Override
    public double executeCommand(ParseTreeNode node) {
        return properties.addStamp();
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
	

}
