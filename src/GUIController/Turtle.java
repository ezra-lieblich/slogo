package GUIController;
import BackEndInterpreter.FrontendObservableProperties;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import java.util.List;

/**
 * Created by Grayson on 11/01/2016.
 */
public class Turtle {
    private ImageView turtleView;
    private double id;
    private boolean visibility = true;
    private List<Line> turtleMotion = new ArrayList<>();
    private ObservableProperties properties;

    public Turtle(ObservableProperties newProperty, ImageView myImage) {
        turtleView = myImage;
        properties = newProperty;
    }

    /**
     * This method sets the image of the turtle to whatever Image is passed in. 
     * @param img
     */
    public void setImage(Image img){
        turtleView.setImage(img);
    }
    
    /**
     * This method returns the current image of the turtle as an ImageView.
     * @return
     */
    public ImageView getImage(){
        return turtleView;
    }
    
    /**
     * This method gets the ID of a specific turtle, which is crucial to knowing which 
     * turtle is being updated, moved, or which turtles we are directing commands to.
     * @return
     */
    public double getID(){
        return id;
    }
    
    /**
     * This is used when a new turtle is created to set the turtle's ID to a unique 
     * value so that we can always tell it apart from other turtles.
     * @param i
     */
    public void setID(double i){
        id = i;
    }

    /**
     * This sets the status of the visibility of a turtles pen, i.e. whether
     * the pen is down or up. 
     * @param isVisible
     */
    public void setVisibility(boolean isVisible){
        visibility = isVisible;
    }

    /**
     * This returns the status of the visibility of a turtles pen, i.e. whether
     * the pen is down or up. 
     * @return
     */
    public boolean isVisible(){
        return visibility;
    }

    /**
     * This returns all of the lines associated with this turtle. 
     * @return
     */
    public List<Line> getLines() {
    	return turtleMotion;
    }

    /**
     * This returns the observable properties of the turtle. 
     * @return
     */
    public ObservableProperties getProperties(){
        return properties;
    }

}
