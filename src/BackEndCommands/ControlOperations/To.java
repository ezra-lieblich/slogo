package BackEndCommands.ControlOperations;

import BackEndCommands.ControlCommand;
import BackEndInternalAPI.LogoMethod;
import BackEndInternalAPI.ParseTreeNode;

/**
 * @author Robert H. Steilberg II
 *         <p>
 *         This command instance represents a method assignment statement in Logo.
 */
public class To extends ControlCommand {

    private static final int ARGS = 3;

    @Override
    public double executeCommand(ParseTreeNode node) {
    	ParseTreeNode commandName = node.getChild(0);
		ParseTreeNode variables = node.getChild(1);
		ParseTreeNode commandBody = node.getChild(2);
        LogoMethod newMethod = new LogoMethod();
        String methodName = commandName.getRawCommand();
        for (ParseTreeNode p : variables.getChildren()) { // store method arguments
            newMethod.addArgument(p.getRawCommand());
        }
        newMethod.setMethod(commandBody);
        getMethods().put(methodName,newMethod);
        return 1; // method assignment successful
    }

    @Override
    public int numArguments() {
        return ARGS;
    }
}