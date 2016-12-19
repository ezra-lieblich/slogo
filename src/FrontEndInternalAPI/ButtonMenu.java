package FrontEndInternalAPI;
import javafx.scene.shape.Rectangle;
import java.io.FileNotFoundException;

/**
 * @author Delia
 */
public interface ButtonMenu {

    /**
     * This can be implemented to load a file from the users computer into the 
     * environment in which they are running SLogo.
     */
    void loadFile() throws FileNotFoundException;

    /**
     * This method is meant to be used to save the current workspace information 
     * to a file on the user's computer that can then be loaded back in. 
     */
    void saveFile();

    /**
     * This is a method that is crucial to any class that wants to inherit from the 
     * ButtonMenu interface, as it contains the actual code required to add any button, 
     * so that other classes can set up buttons with little to no hassle.
     */
    void addButtons();

    /**
     * This is a simple getter that returns the backdrop of any specific
     *  section of the screen that calls this method. 
     * @return
     */
    Rectangle getBackdrop();
}