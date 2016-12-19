package BackEndCommands;

import BackEndInterpreter.Command;
import BackEndInterpreter.ObservableComposite;
import BackEndInterpreter.ParseTreeNode;

/**
 * An abstract class that all Turtle Commands and Turtle Queries extend. This parent class is needed
 * because all these commands need access to ObservableComposite whether it is for getting or setting
 * All children of this class have access to an instance of the ObservableComposite class
 *
 * @author ezra
 */
public abstract class TurtleCommand implements Command {

    protected ObservableComposite properties;

    /**
     * Sets the properties file in the class.
     *
     * @param o
     */
    @Override
    public void setProperties(Object o) {
        if (o.getClass().equals(ObservableComposite.class)) {
            properties = (ObservableComposite) o;
        }
    }

    @Override
    public abstract double executeCommand(ParseTreeNode node);

    @Override
    public abstract int numArguments();
}