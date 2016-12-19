package FrontEndInternalAPI;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by Delia on 11/5/2016.
 */
public interface Factory {

    /**
     * This makes a button with the string "text," the image "img," and places
     * it at the location marked by x and y. 
     * @param text
     * @param img
     * @param x
     * @param y
     * @return
     */
    Button makeButton(String text, ImageView img, double x, double y);

    /**
     * This is a method to make a specific type of button that will need to be used 
     * in multiple different classes, with the functionality to clear whichever 
     * section of the GUI it is assigned to. 
     * @param x
     * @param y
     * @return
     */
    Button makeClearButton(double x, double y);

    /**
     * 
     * @param text
     * @param x
     * @param y
     * @return
     */
    Button makeBigButton(String text, int x, int y);

    /**
     * This is a method to make a specific type of button that will need to be used 
     * in multiple different classes, with the functionality to load the help screen
     * of whichever section of the GUI it is assigned to. 
     * @param x
     * @param y
     * @return
     */
    ImageView makeHelpButton(double x, double y);

    /**
     * This makes a title for the different sections of the GUI, and puts the given
     * string at the given location.
     * @param text
     * @param x
     * @param y
     * @return
     */
    Text makeTitle(String text, int x, int y);

    /**
     * This makes text specifically for pop up windows, and sets the 
     * text to the location and size given.
     * @param text
     * @param x
     * @param y
     * @param fontsize
     * @return
     */
    Text makePopupText(String text, int x, int y, int fontsize);

    /**
     * This method makes a textField. 
     * @param promptText
     * @param prefWidth
     * @param x
     * @param y
     * @return
     */
    TextField makeTextField(String promptText, double prefWidth, int x, int y);

    /**
     * This method makes a backdrop. 
     * @param border
     * @param width
     * @param height
     * @param x
     * @param y
     * @return
     */
    Rectangle makeBackdrop(Paint border, int width, int height, int x, int y);

    /**
     *This method makes a blue backdrop. 
     * @param width
     * @param height
     * @param x
     * @param y
     * @return
     */
    Rectangle makeBlueBackdrop(int width, int height, int x, int y);

    /**
     * This method allows the colors to be adjusted based on the user's actions. 
     * @param color
     * @return
     */
    ColorAdjust makeEffect(Color color);

    /**
     * This gets the standard look for a button, so they are all the same.
     * @return
     */
    String getButtonFill();

    /**
     *This gets the standard look for a button when the mouse
     * is hovering over it, so they are all the same look.
     * @return
     */
    String getOverButton();
}
