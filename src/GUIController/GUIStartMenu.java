package GUIController;
import Base.NodeFactory;
import Base.OptionsMenu;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIStartMenu extends OptionsMenu {
    private NodeFactory myFactory = new NodeFactory();
    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.WHITE),
            new Stop(0.15, Color.HONEYDEW),
            new Stop(0.3, Color.LIGHTBLUE),
            new Stop(0.45, Color.LIGHTSTEELBLUE),
            new Stop(0.6, Color.LIGHTBLUE),
            new Stop(0.75, Color.HONEYDEW),
            new Stop(1, Color.WHITE));
    /**
     * This starts a new GUIStartMenu by calling it's superclass's constructor.
     * @param s
     */
    public GUIStartMenu(Stage s) {
        super(s);
    }
    @Override
    /**
     * This method adds the title to the top of the welcome screen.
     */
    public void addTitle() {
        BigNameText title = new BigNameText("Welcome to \n\tSLogo");
        title.setTranslateX(125);
        title.setTranslateY(75);
        getStartWindow().getChildren().add(title);
    }
    /**
     * This method creates and adds a "Launch" button for someone to start SLogo
     * from the welcome screen. 
     */
    public void addLaunchButton(){
        Button newButton = myFactory.makeBigButton("Launch Slogo", 300, 500);
        newButton.setOnMouseClicked(e -> setParameters());
        getStartWindow().getChildren().add(newButton);
    }
    /**
     * This method adds the backdrop to the welcome menu to make it look nicer. 
     */
    public void addRectangle(){
        Rectangle backdrop = myFactory.makeBlueBackdrop(500, 290, 100, 230);
        getStartWindow().getChildren().add(backdrop);
    }
    /**
     * This method is the result of clicking the Launch button. It creates a new 
     * instance of, and then initializes, the GUIManager class, which is 
     * the over-arching display class that contains all parts of our GUI display.  
     * @param chosenBackground
     * @param chosenTurtle
     */
    public void initIDE(String chosenBackground, String chosenTurtle) {
        GUIManager newGUI = new GUIManager(getPenColor().getValue(),
                chosenBackground, chosenTurtle, getLanguageBox().getValue(),
                getLineBox().getValue());
        newGUI.init();
    }
    private static class BigNameText extends StackPane {
        /**
         * @param Name
         */
        public BigNameText(String Name) {
            Text titleText = new Text(Name);
            titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
            titleText.setFill(textAndBoxGradient);
            getChildren().add(titleText);
        }
    }
}