package FrontEndExternalAPI;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.shape.Rectangle;

/**
 *  This is the interface for the GUIHistory class in the GUIController package.
 *  This class lists the previously executed commands as buttons the user can
 *  click and load back into the Editor.
 *  Users can also click "Clear" to remove all history.
 *
 * @author Delia
 */
public interface History {

    /**
     *Returns the translucent rectangle upon which every History node is drawn
     * @return
     */
    Rectangle getBackdrop();

    /**
     *Sets size bindings of the listView and backdrop rectangle in History to
     * the size of the user's resizeable window
     * @param height
     */
    void bindNodes(ReadOnlyDoubleProperty height);

    /**
     *Adds a new command to history at the front of the list. This is a button
     * that will become bold when clicked by the user.
     * @param text
     */
    void addCommand(String text);

    /**
     *Sets a potentially redoable command as a string
     * @param text
     */
    void callCommand(String text);

    /**
     *Returns selected command to be executed again
     * @return redoCommand
     */
    String getRedoCommand();
}