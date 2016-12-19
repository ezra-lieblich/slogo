package FrontEndExternalAPI;

import javafx.scene.shape.Rectangle;

/**
 * This is the interface for the GUIEditor class in the GUIController package.
 * This class controls the inputting of commands by allowing the user to type directly
 * into a text area or open a file.
 * The GUIEditor class contains a small caret "> " at the beginning of each command
 * after the use clicks the "Run" button.
 * This lets the user know how which commands will be parsed the next time "Run" is
 * clicked.
 * Additionally, the "Clear" button lets users clear the text area at any time.
 * The "Open file" button lets users choose a file from their own directory to run
 * as a set of Slogo commands.
 * The "Save file" button lets users to save what currently exists in the text area
 * as a text file in their own directory.
 * The question mark provides useful tips to users about how to use the editor.
 * @author Delia
 */
public interface Editor{

    /**
     * Returns the translucent rectangle upon which every Editor node is drawn
     * @return backdrop
     */
    Rectangle getBackdrop();

    /**
     *Creates the necessary changes in the text area by appending the caret
     * to show the user that a new command has started.
     */
    void startNewCommand();

    /**
     *Returns all text currently located in the textArea, including previously
     * executed commands
     * @return textArea.getText()
     */
    String getCurrentText();

    /**
     * @param str
     *
     * String parameter passed in from History is previously executed command
     * that user wishes to run again. Concatenates it to textArea if it were a
     * new command.
     */
    void redoCommand(String str);
}