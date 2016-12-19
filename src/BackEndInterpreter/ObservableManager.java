package BackEndInterpreter;

/**
 * Interface component of the composite design. Contains all the methods related to turtle commands
 * that both the individual turtle and multiple turtle components use. See ObservableComposite and ObservableProperties
 * for details on each of the methods as they serve different purposes
 * @author ezra
 *
 */
public interface ObservableManager {
	
    void setNewLineProperty(boolean value);

    void setClearScreenProperty(boolean value);

    boolean getImageVisibleProperty();

    void setImageVisibleProperty(boolean value);

    double getRotateProperty();

    double setRotateProperty(ParseTreeNode value, boolean isAbsolute, boolean sign);

    double getXProperty();

    void setXProperty(double value);

    double getYProperty();

    void setYProperty(double value);

    boolean getPathVisibleProperty();

    void setPathVisibleProperty(boolean value);

    double calculateDegrees(ParseTreeNode x, ParseTreeNode y);

    /**
     * Calculates the distance between two points. Method is called by the Home, ClearScreen, SetXY commands
     *
     * @param x  
     * @param y 
     * @return The distance between point specified and current turtle position
     */
    double calculateTotalDistance(double x, double y1);

    /**
     * Calculates the X distance the turtle travels when it moves. Called by forward and back commands
     *
     * @param hyp Distance of the hypotenuse
     * @param sign 
     * @return X distance traveled
     */
    double calculateXDistance(ParseTreeNode hyp, boolean sign);

    /**
     * Calculates the Y distance the turtle travels when it moves. Called by the forward and back commands
     *
     * @param hyp the node whose value when executed is the distance needed to travel
     * @return Y distance traveled
     */
    double calculateYDistance(ParseTreeNode hyp, boolean sign);

    /**
     * Performs the SetXY command
     * @param arg1
     * @param arg2
     * @return
     */
    double setXY(ParseTreeNode arg1, ParseTreeNode arg2);
}
