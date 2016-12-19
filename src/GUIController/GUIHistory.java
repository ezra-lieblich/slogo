//This entire file is part of my code masterpiece
//Grayson Wise

/**While this file was created by Delia, all of the functionality was 
 * implemented by me. In addition, I refactored this code heavily, and 
 * therefore I feel it is an acceptable code masterpiece. 
 * 
 * I think this code is well designed for a few reasons. First off, every method is 
 * short, to the point, and does exactly what is expected of it. Also, the code makes
 * good use of lambda expressions, which are crucial to implementing button functionality.
 * 
 * Commits: 
 *      00ef8c9286502823e853e50ad35c5fba5e3e74d8
 *              "History works again, now we aren't doomed to repeat it."
 *      befe502d872a74e85f06ccfbfdc8b5ce024b2116
 *              "Can now load hold commands from history to run again."
 */

package GUIController;
import Base.NodeFactory;
import FrontEndExternalAPI.History;
import GUI.HistoryHelp;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Created by Delia on 10/15/2016.
 */
public class GUIHistory implements History {
    private Pane window;
    private Paint border;
    private Rectangle backdrop;
    private ListView<Button> list;
    private ObservableList<Button> oldCommands;
    private String redoCommand;
    private HistoryHelp helpWindow;
    private NodeFactory myFactory = new NodeFactory();
    /**
     * This is the constructor that creates the section of the overall GUI that is 
     * designated to handle the "history," meaning it contains a list of old commands, 
     * and allows a user to click on them to reload them into the editor to run again. 
     * @param p
     * @param bordercolor
     */
    public GUIHistory(Pane p, Paint bordercolor){
        this.window = p;
        this.border = bordercolor;
        drawHistory();
        addTextLabel();
        addHelpButton();
        addListView();
        addClearButton();
    }
    private void drawHistory(){
        backdrop = myFactory.makeBackdrop(border, 600, 580, 10, 600);
        window.getChildren().add(backdrop);
    }
    private void addTextLabel(){
        Text label = myFactory.makeTitle("History", 20, 620);
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        window.getChildren().add(label);
    }
    private void addHelpButton(){
        ImageView helpButton = myFactory.makeHelpButton(backdrop.getTranslateX() + backdrop.getWidth() - 35,
                backdrop.getTranslateY() + 10);
        helpButton.setOnMouseClicked(e -> helpHandler());
        helpButton.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        window.getChildren().add(helpButton);
    }
    private void helpHandler(){
        Stage s = new Stage();
        helpWindow = new HistoryHelp(s);
        helpWindow.init();
    }
    
    private void addClearButton(){
        Button clearButton = myFactory.makeClearButton(200, 600);
        clearButton.setOnMouseEntered(e -> {
            makeOpaque();
            clearButton.setStyle(myFactory.getButtonFill());
        });
        clearButton.setOnMouseClicked(e -> clear());
        window.getChildren().add(clearButton);
    }
    @Override
    /**
     * This method returns the backdrop of this section of the GUI. This 
     * method is inherited from the overarching superclass history.java. 
     */
    public Rectangle getBackdrop(){
        return backdrop;
    }
    @Override
    /**
     * This method adds the most recently run command to the list contained in 
     * the history. The command string passed in is made into a new button that 
     * is added to the front of the list, and the button is set to have the 
     * correct behavior when clicked. 
     * @param text
     */
    public void addCommand(String text) {
        Button newCommand = new Button(text);
        setCommandButtonStyle(newCommand);
        oldCommands.add(0, newCommand);
        list.setItems(oldCommands);
    }
    
    private void setCommandButtonStyle(Button command){
        command.setStyle(myFactory.getOverButton());
        command.setOnMouseEntered(e -> {
            makeOpaque();
            command.setStyle(myFactory.getButtonFill());
            });
        command.setOnMouseExited(e -> command.setStyle(myFactory.getOverButton()));
        command.setOnMouseClicked(e -> {
            unboldAllButClickedCommand(command);
            callCommand(command.getText());
            });
    }
    
    private void makeOpaque(){
        backdrop.opacityProperty().setValue(0.8);
    }
    private void unboldAllButClickedCommand(Button command){
        for(int i = 0; i < list.getItems().size(); i++){
            list.getItems().get(i).setFont(Font.font("Verdana", 15));
        }
        command.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    }
    private void addListView(){
        list = new ListView<Button>();
        setListPrefs();
        oldCommands = FXCollections.observableArrayList();
        window.getChildren().add(list);
    }
    
    private void setListPrefs(){
        list.setOrientation(Orientation.HORIZONTAL);
        list.setTranslateX(20);
        list.setTranslateY(625);
        list.setPrefSize(580, 155);
        list.opacityProperty().setValue(0.5);
        list.setOnMouseEntered(e -> {
            list.opacityProperty().setValue(0.8);
            makeOpaque();
        });
        list.setOnMouseExited(e -> list.opacityProperty().setValue(0.5));
    }
    @Override
    /**
     * This method is used to bind the location of the history portion of the 
     * window to the same spot in the overall GUI, even when the window is resized.  
     * @param height
     */
    public void bindNodes(ReadOnlyDoubleProperty height){
        list.prefHeightProperty().bind(height.subtract(650));
    }
    @Override
    /**
     * This method is passed in the most recent command from the GUIEditor, and
     * sets the variable "redoCommand" equal to it, so that the history has a 
     * command to use as the most recent unless a different one is clicked. 
     * @param str
     */
    public void callCommand(String str) {
        redoCommand = str;
    }
    /**
     * This returns the saved string "redoCommand," which contains the text of the
     * most recently clicked button in the list, allowing the GUI to know which 
     * command has been clicked and needs to be loaded into the editor.
     * @return
     */
    public String getRedoCommand(){
        return redoCommand;
    }
    
    private void clear(){
        oldCommands.clear();
    }
}