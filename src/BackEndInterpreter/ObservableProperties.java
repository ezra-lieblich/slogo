//Line 17 is part of my masterpiece as it also implements FrontendObservableProperties
//Ezra Lieblich
package BackEndInterpreter;

import GUIController.GUIDisplay;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/**
 * A class that contains all the observable properties from the front end and is passed
 * to commands. Note that an instance of this class will be passed to the backend and for the commands to alter
 *
 * @author ezra
 */
public class ObservableProperties implements ObservableManager, FrontendObservableProperties {
    private double myId;
    private BooleanProperty imageVisibleProperty; 
    private DoubleProperty rotateProperty;
    private double xProperty;
    private double yProperty;
    private BooleanProperty pathVisibleProperty;
    private BooleanProperty newLineProperty;
    private BooleanProperty clearScreenProperty;


    /**
     * Creates all the ObservableProperties and also sets up the change Listeners. Also uses binding for the visibleProperty and rotateProperty
     * @param turtle ImageView to bind properties. NOTE, this is only used as constructor and not instantiated 
     * @param myDisplay display so listeners can call methods in the display. NOTE, Display is not instance variable and is only used to setupListeners
     * @param id Id of the turtle that is created
     */
    public ObservableProperties(ImageView turtle, GUIDisplay myDisplay, double id) {
        myId = id;
        imageVisibleProperty = new SimpleBooleanProperty(true);
        rotateProperty = new SimpleDoubleProperty(0);
        xProperty = 0;
        yProperty = 0;
        pathVisibleProperty = new SimpleBooleanProperty(true);
        newLineProperty = new SimpleBooleanProperty(false);
        clearScreenProperty = new SimpleBooleanProperty(false);
        turtle.rotateProperty().bind(rotateProperty);
        turtle.visibleProperty().bind(imageVisibleProperty);
        setupListeners(myDisplay);
    }

    /**
     * Creates listeners and calls corresponding FrontEnd methods
     * @param myDisplay Display that contains methods that listeners need to call
     */
    private void setupListeners(GUIDisplay myDisplay) {
        newLineProperty.addListener((observable, oldValue, newValue) -> {
            //If new value is true we need to draw a new line
            if (newValue) {
                myDisplay.moveTurtle(getXProperty(), getYProperty(), myId);
            }
            newLineProperty.set(false);
        });
        pathVisibleProperty.addListener((observable, oldValue, newValue) -> myDisplay.setVisibility(newValue, myId));
        clearScreenProperty.addListener((observable, oldValue, newValue) -> {
            //If new value is true we need to draw a new line
            if (newValue) {
                myDisplay.clearScreen(myId);
            }
            clearScreenProperty.set(false);
        });
    }

    /**
     * Gets boolean whether turtle is visible or not
     */
    public boolean getImageVisibleProperty() {
        return imageVisibleProperty.get();
    }

    /**
     * Gets the double of the turtle's rotate property
     */
    public double getRotateProperty() {
        return rotateProperty.get();
    }

    /**
     * Gets the XProperty of the turtle
     */
    public double getXProperty() {
        return xProperty;
    }

    /**
     * Sets the XProperty of the turtle
     */
    public void setXProperty(double value) {
        xProperty = value;
    }

    /**
     * Gets the yProperty of the turtle
     */
    public double getYProperty() {
        return yProperty;
    }

    /**
     * Sets the yProperty of the turtle
     */
    public void setYProperty(double value) {
        yProperty = value;
    }

    /**
     * Gets the boolean whether turtle's path is visible or not
     */
    public boolean getPathVisibleProperty() {
        return pathVisibleProperty.get();
    }

    /**
     * Calculates the distance x, y point given and current position. Method is called by the Home, ClearScreen, SetXY commands
     * @param x1
     * @param y1
     * @return The distance between two points
     */
    public double calculateTotalDistance(double x1, double y1) {
        return Math.sqrt(Math.pow(getXProperty() - x1, 2) + Math.pow(getYProperty() - y1, 2));
    }

    /**
     * Calculates the X distance the turtle travels when it moves. Called by forward and back commands
     * Also negates the value depending on whether it is a forward or backward command
     * @param hyp Distance of the hypotenuse
     * @return X distance traveled
     */
    public double calculateXDistance(ParseTreeNode hyp, boolean sign) {
        double value = hyp.executeCommand(hyp);
        if (!sign) {
            value = -value;
        }
        double angle = getRotateProperty();
        double answer = Math.sin(Math.toRadians(angle)) * value;
        return answer;
    }

    /**
     * Calculates the Y distance the turtle travels when it moves. Called by the forward and back commands
     * Also negates the value depending on whether it is a forward or backward command
     * @param hyp Distance of the hypotenuse
     * @return Y distance traveled
     */
    public double calculateYDistance(ParseTreeNode hyp, boolean sign) {
        double value = hyp.executeCommand(hyp);
        if (!sign) {
            value = -value;
        }
        double angle = getRotateProperty();
        double answer = Math.cos(Math.toRadians(angle)) * value;
        return answer;
    }

    /**
     * Sets the NewLineProperty of the turtle to the value specified
     */
    @Override
    public void setNewLineProperty(boolean value) {
        newLineProperty.set(value);
    }

    /**
     * Sets the clearScreenProperty of the turtle to the value specified
     */
    @Override
    public void setClearScreenProperty(boolean value) {
        clearScreenProperty.set(value);
        rotateProperty.set(0);
    }

    /**
     * Sets the imageVisibleProperty of the turtle to the value specified
     */
    @Override
    public void setImageVisibleProperty(boolean value) {
        imageVisibleProperty.set(value);
    }

    /**
     * Sets the rotateProperty to value specified by the node.
     * @param node Value to rotate
     * @param isAbsolute Determines whether we rotate just by value specified or add based off current pos
     * @param sign Determines whether we rotate clockwise or counterclockwise from current heading
     */
    @Override
    public double setRotateProperty(ParseTreeNode node, boolean isAbsolute, boolean sign) {
        double value = node.executeCommand(node);
        if (isAbsolute) {
            value = value % 360;
            double oldValue = rotateProperty.get();
            rotateProperty.set(value);
            return Math.abs(value - oldValue);
        }
        if (!sign) {
            value = -value;
        }
        rotateProperty.set(getRotateProperty() % 360 + value);
        return Math.abs(value);
    }

    /**
     * Sets the PathVisibleProperty of the turtle to value given
     */
    @Override
    public void setPathVisibleProperty(boolean value) {
        pathVisibleProperty.set(value);
    }

    /**
     * Called by the Towards the command
     * Calculates the degrees from the origin of the two nodes specified and sets the rotate property to that 
     * @param node1 the X value
     * @param node2 The Y value
     * @return the Degrees moved
     */
    @Override
    public double calculateDegrees(ParseTreeNode node1, ParseTreeNode node2) {
        double x = node1.executeCommand(node1);
        double y = node2.executeCommand(node2);
        double angle = Math.atan(x / y);
        double answer = adjustAngleQuadrant(x, y, Math.toDegrees(angle));
        double oldDegrees = rotateProperty.get();
        rotateProperty.set(answer);
        return Math.abs(answer - oldDegrees);
    }

    /**
     * Adjusts the angle depending on the quadrant the coordinate is
     * @return The actual angle to set the turtle
     */
    private double adjustAngleQuadrant(double x, double y, double angle) {
        double answer;
    	 // Second quadrant
        if (x > 0 && y < 0) {
            answer = 90 - angle;
        }
        // Third quadrant
        else if (x < 0 && y < 0) {
            answer = 180 + angle;
        }
        // Fourth quadrant
        else if (x < 0 && y > 0) {
            answer = 270 - angle;
        }
        // Default is first quadrant
        else {
            answer = angle;
        }
        return answer;
    }
    /**
     * @return the id of the turtle
     */
    public double getID() {
        return myId;
    }

    /**
     * Sets the x and y Property to the coordinates specified
     * @param arg1 The value is xCord
     * @param arg2 The value is yCord
     * @return the Distance traveled 
     */
    @Override
    public double setXY(ParseTreeNode arg1, ParseTreeNode arg2) {
        double value1 = arg1.executeCommand(arg1);
        double value2 = arg2.executeCommand(arg2);
        double distance = calculateTotalDistance(value1, value2);
        setXProperty(value1);
        setYProperty(value2);
        setNewLineProperty(true);
        return distance;
    }
}