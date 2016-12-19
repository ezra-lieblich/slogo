package FrontEndInternalAPI;

import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author Delia
 */
public interface Options {
    /**
     * This sets up a new window that the user can interact with. 
     * @return
     */
    Parent setUpWindow();

    /**
     * This adds the necessary pieces to the window, such as text, 
     * buttons, a textfield, a table, etc...
     */
    void addNodes();

    /**
     * This sets the parameters of the window.
     */
    void setParameters();

    /**
     * This sets the background of the window. 
     */
    void setBackgroundString();

    /**
     * This sets the turtle contained in the window.
     */
    void setTurtleString();

    /**
     *This gets the name of the image of the turtle contained in the window.
     * @return
     */
    String getTurtleString();

    /**
     * This adds a slider that allows the user to pick the style of the line
     * that the turtle contained in the window uses.
     */
    void addLineStylePicker();

    /**
     * This allows the user to pick the color of the pen
     * that the turtle contained in the window uses.
     */
    void changePenColor();

    /**
     * This adds a slider that allows the user to pick the color of the pen
     * that the turtle contained in the window uses.
     * @param defaultColor
     * @param x
     * @param y
     * @return
     */
    ColorPicker generateColorPicker(Color defaultColor, int x, int y);

    /**
     * This method changes the background image of the GUI. 
     */
    void changeBackground();

    /**
     * This method changes the sprite image in the GUI. 
     */
    void changeSpriteImage();

    /**
     * This method creates labels for each section of the GUI and places 
     * the given text at the given location.
     * @param text
     * @param x
     * @param y
     * @return
     */
    Label generateLabel(String text, int x, int y);

    /**
     *This method changes the language used in the IDE. 
     */
    void changeLanguage();

    /**
     * This method returns the pen color currently in use.
     * @return
     */
    ColorPicker getPenColor();

    /**
     * This method returns the pane of the start window. 
     * @return
     */
    Pane getStartWindow();

    /**
     * This method gets the selection of the background selection combo box. 
     * @return
     */
    ComboBox<String> getBackgroundBox();

    /**
     * This method gets the selection of the turtle selection combo box. 
     * @return
     */
    ComboBox<String> getTurtleBox();

    /**
     *This method gets the selection of the line selection combo box. 
     */
    ComboBox<Line> getLineBox();

    /**
     * This method gets the selection of the language selection combo box. 
     * @return
     */
    ComboBox<String> getLanguageBox();

    /**
     * This method gets the stage of the window. 
     * @return
     */
    Stage getStage();

    /**
     * This method adds a title to the window. 
     * @return
     */
    void addTitle();

    /**
     * This method adds a title to the window. 
     * @return
     */
    void addRectangle();

    /**
     * This method adds a button to the window that launches the IDE. 
     * @return
     */
    void addLaunchButton();
}