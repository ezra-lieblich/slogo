package GUIController;
import Base.NodeFactory;
import FrontEndExternalAPI.Console;
import GUI.ConsoleHelp;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIConsole implements Console{
    private static final int BACKDROP_X = 620;
    private static final int BACKDROP_Y = 600;
    private static final int BACKDROP_WIDTH = 600;
    private static final int BACKDROP_HEIGHT = 240;
    private Pane window;
    private Paint border;
    private Rectangle backdrop;
    private ImageView helpButton;
    private ListView<String> list;
    private ObservableList<String> listOfCommands;
    private ConsoleHelp helpWindow;
    private NodeFactory myFactory = new NodeFactory();

    /**
     * This makes a new GUIConsole which contains the output of any commands that the
     * user inputs into the editor. 
     * @param p
     * @param borderColor
     */
    public GUIConsole(Pane p, Paint borderColor){
        this.window = p;
        this.border = borderColor;
        drawConsole();
        addTextLabel();
        addHelpButton();
        addListView();
        addClearButton();
    }

    @Override
    public void addConsole(String text) {
        listOfCommands.add(text);
        list.setItems(listOfCommands);
    }

    private void drawConsole(){
        backdrop = myFactory.makeBackdrop(border, BACKDROP_WIDTH, BACKDROP_HEIGHT, BACKDROP_X, BACKDROP_Y);
        window.getChildren().add(backdrop);
    }

    private void addListView(){
        list = new ListView<String>();
        list.setOrientation(Orientation.VERTICAL);
        list.setTranslateX(BACKDROP_X + 10);
        list.setTranslateY(BACKDROP_Y + 30);
        list.setPrefSize(BACKDROP_WIDTH - 30, BACKDROP_HEIGHT - 35);
        list.opacityProperty().setValue(0.8);
        listOfCommands = FXCollections.observableArrayList();
        list.setOnMouseEntered(e -> {
            list.opacityProperty().setValue(0.8);
            backdrop.opacityProperty().setValue(0.8);
        });
        list.setOnMouseExited(e -> list.opacityProperty().setValue(0.5));
        window.getChildren().add(list);
    }

    private void addTextLabel(){
        Text label = myFactory.makeTitle("Console", BACKDROP_X + 10, BACKDROP_Y + 20);
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        window.getChildren().add(label);
    }

    private void addHelpButton(){
        helpButton = myFactory.makeHelpButton(backdrop.getTranslateX() + backdrop.getWidth() - 35,
                backdrop.getTranslateY() + 10);
        helpButton.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        helpButton.setOnMouseClicked(e -> helpHandler());
        window.getChildren().add(helpButton);
    }
    
    private void helpHandler(){
        Stage s = new Stage();
        helpWindow = new ConsoleHelp(s);
        helpWindow.init();
    }

    private void addClearButton(){
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/clear.png"));
        ImageView clearImg = new ImageView(newImage);
        Button clear = myFactory.makeButton("Clear", clearImg,
                backdrop.getTranslateX() + 100, backdrop.getTranslateY());
        clear.setOnMouseEntered(e -> {
            backdrop.opacityProperty().setValue(0.8);
            clear.setStyle(myFactory.getButtonFill());
        });
        clear.setOnMouseClicked(e -> listOfCommands.clear());
        window.getChildren().add(clear);
    }

    @Override
    public void bindNodes(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        list.prefWidthProperty().bind(width.subtract(650));
        list.prefHeightProperty().bind(height.subtract(655));
        helpButton.translateXProperty().bind(width.subtract(50));
    }

    @Override
    public Rectangle getBackdrop(){
        return backdrop;
    }
}
