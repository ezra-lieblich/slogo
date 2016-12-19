package FrontEndExternalAPI;

/**
 * The GUIManager class is crucial to the front end. It is a controller that facilitates
 * communication between the various other front end classes, the front end internal classes,
 * and the back end.
 *
 * Created by Delia on 11/5/2016.
 */
public interface Manager {

    /**
     * Launches the GUIManager and is called from startMenu
     */
    void init();

    /**
     * Splits commands obtained from the Editor class by line and then sends them to the
     * back end to be parsed. Only looks at commands entered since the last time "Run"
     * was pressed in the Editor.
     */
    void returnAction();
}
