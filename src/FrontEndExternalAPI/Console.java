package FrontEndExternalAPI;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.shape.Rectangle;

/**
 * This is the interface for the GUIConsole class in GUIMController.
 *The console contains useful printouts such as numbers for commands
 * and error messages for incorrect commands.
 * It can be cleared and comes in the form of a listview.
 * @author Delia
 */
public interface Console {

    /**
     *Adds a string passed in from either GUIManager or the back end to the listView
     * @param text
     */
    void addConsole(String text);

    /**
     *Sets up size bindings to always fit a user's resizeable workspace window
     * @param width
     * @param height
     */
    void bindNodes(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height);

    /**
     *Returns the translucent rectangle upon which every Console node is drawn
     * @return
     */
    Rectangle getBackdrop();
}