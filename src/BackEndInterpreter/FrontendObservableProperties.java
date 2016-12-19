//This entire file is part of my masterpiece and is created just for it
//Ezra Lieblich
package BackEndInterpreter;

/**
 * This is the main part of my masterpiece. Since the TurtleProperties is accessed by both the front end and back end,
 * a lot of the ObservableProperties methods need to be public for front end to edit and change.
 * However, not all the public methods that the backend uses need to be accessed by the front end such as 
 * NewLine Property and image visible property. Therefore, I created an interface that ObservableProperties implements
 * and that the front end will access which you can see in GUIDisplay, that only has the public methods that 
 * the front end only needs. That way, ObservableProperties is much more encapsulated and hidden from the front end.
 * @author ezra
 *
 */
public interface FrontendObservableProperties {
	
	/**
	 * 
	 * @return The x coordinate of the turtle 
	 */
    double getXProperty();
    
    /**
     * 
     * @return The y coordinate of the turtle
     */
    double getYProperty();
    
    /**
     * 
     * @return A boolean that represents whether the turtle's pen is up or down
     */
    boolean getPathVisibleProperty();
    
    /**
     * Sets the turtle's pen up or down. This setter is accessible because the front end is allowed to 
     * alter whether the pen is up or down by clicking on the turtle
     * @param value The value of whether the pen is up or down
     */
    void setPathVisibleProperty(boolean value);
    
    /**
     * 
     * @return The angle in degrees that the turtle is facing
     */
    double getRotateProperty();
    
    /**
     * 
     * @return The id of the current turtle
     */
    double getID();
}
