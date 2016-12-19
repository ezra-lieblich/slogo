package FrontEndInternalAPI;

import BackEndInterface.RGB;
import BackEndInterpreter.ObservableProperties;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Paint;

/**
 * Created by Delia on 11/5/2016.
 */
public interface Display {
    /**
     * This method adds a turtle to the display with the unique ID that is 
     * passed in, making it able to distinguish when it needs to listen to 
     * a command typed by the user.
     * @param newID
     * @return
     */
    ObservableProperties addTurtle(double newID);

    /**
     * This method is used to bind one variable, the overall size of the 
     * display portion of the screen, to another width, which in our case is 
     * the total width of the window. Therefore, resizing the window also 
     * resizes the display. 
     * @param width
     */
    void bindNodes(ReadOnlyDoubleProperty width);

    /**
     * This method is where the specific turtle's location is updated
     *  to reflect the command input into the editor. 
     * @param x
     * @param y
     * @param id
     */
    void moveTurtle(double x, double y, double id);

    /**
     * This method is meant to clear the display screen of all lines. 
     * @param id
     */
    void clearScreen(double id);

    /**
     * This method resets the IDE to the beginning settings, with one 
     *  turtle in the middle of the display. 
     */
    void resetIDE();

    /**
     * This sets the pen color to be equal to the new value given.
     * @param newValue
     */
    void changePenColor(Double newValue);

    /**
     * This sets the pen size to be equal to the new value given.
     * @param newValue
     */
    void setPenSize(Double newValue);

    /**
     * This sets the turtle's image to be equal to the new value given, 
     * based on the mapping from number to image.
     * @param newValue
     */
    void changeImage(Double newValue);

    /**
     * This sets the palate to be equal to the new value given.
     * @param newValue
     */
    void changePalette(Double newValue);

    /**
     * This changes the color of the palate to be the new value given.
     * @param newValue
     */
    void changePaletteRGB(RGB newValue);

    /**
     * This sets the pen visibility for the given turtle. 
     * @param isVisible
     * @param newID
     */
    void setVisibility(boolean isVisible, double newID);

    /**
     * This gets the name of the image being used by a specific turtle.
     * @return
     */
    String getTurtleStr();

    /**
     * This gets the pen color being used by a specific turtle. 
     * @return
     */
    Paint getPenColor();

    /**
     * This updates the display background, pen color, pen size, and 
     * other aspects once a user has chosen what to change. 
     */
    void updateDisplayOptions();

}
