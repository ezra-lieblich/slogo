package GUI;

import FrontEndInternalAPI.ButtonMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIButtonMenu implements ButtonMenu{
    private Pane window;
    private Paint border;
    private Rectangle backdrop;
    private Stage s = new Stage();
    private String defaultBackground = "Nebula";
    private String defaultLanguage = "English";
    private ComboBox<String> backgroundBox, languageBox;
    private ObservableList<String> backgroundOptions =
            FXCollections.observableArrayList(
                    "Circuits",
                    "Floating Cubes",
                    "Nebula",
                    "Metal Sheets",
                    "Spinning Screens"
            );
  
    private ObservableList<String> languageOptions =
            FXCollections.observableArrayList(
                    "Chinese",
                    "English",
                    "French",
                    "German",
                    "Italian",
                    "Portuguese",
                    "Russian",
                    "Spanish",
                    "Syntax"

            );
    private HelpMenu myHelpMenu;
    private String overButton = "-fx-background-color: linear-gradient(#0079b3, #00110e);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String buttonFill = "-fx-background-color: linear-gradient(#00110e, #0079b3);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";

    /**
     *
     * @param p
     * @param borderColor
     */
    public GUIButtonMenu(Pane p, Paint borderColor){
        this.window = p;
        this.border = borderColor;
        drawButtonMenu();
        addTextLabel();
        addButtons();
    }

    private void drawButtonMenu(){
        backdrop = new Rectangle(1580, 90, Color.WHITE);
        backdrop.setStroke(border);
        backdrop.setStrokeWidth(5);
        backdrop.setTranslateY(10);
        backdrop.setTranslateX(10);
        backdrop.opacityProperty().setValue(0.5);
//        backdrop.setOnMouseMoved(e -> handle(e));
        backdrop.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        backdrop.setOnMouseExited(e -> backdrop.opacityProperty().setValue(0.5));
        window.getChildren().add(backdrop);
    }

    private void addTextLabel(){
        Text label = new Text("Options");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        label.setTranslateX(20);
        label.setTranslateY(30);
        window.getChildren().add(label);
    }
    
    

    /**
     *
     */
    public void addButtons(){
        Image newImage = new Image(getClass().getClassLoader()
                                   .getResourceAsStream("images/play.png"));
        ImageView imgV = new ImageView(newImage);
        Button play = newButton("PLAY", imgV, 30, 40);
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/pause.png"));
        imgV = new ImageView(newImage);
        Button pause = newButton("PAUSE", imgV, 130, 40);
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/stop.png"));
        imgV = new ImageView(newImage);
        Button stop = newButton("STOP", imgV, 240, 40);
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/options.png"));
        imgV = new ImageView(newImage);
        Button options = newButton("OPTIONS", imgV, 340, 40);
        options.setOnMouseClicked(e -> optionsHandler());
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/help.png"));
        imgV = new ImageView(newImage);
        Button help = newButton("HELP", imgV, 465, 40);
        help.setOnMouseClicked(e -> helpHandler());
        window.getChildren().add(play);
        window.getChildren().add(pause);
        window.getChildren().add(stop);
        window.getChildren().add(help);
        
    }

    public Button newButton(String text, ImageView imgV, int x, int y){
        imgV.setFitWidth(40);
        imgV.setFitHeight(40);
        Button run = new Button(text, imgV);
        run.setStyle(overButton);
        run.setOnMouseEntered(e -> {
            run.setStyle(buttonFill);
            backdrop.opacityProperty().setValue(0.8);
        });
        run.setOnMouseExited(e -> run.setStyle(overButton));
        run.setTranslateX(x);
        run.setTranslateY(y);
        return run;
    }

    private void helpHandler(){
        myHelpMenu = new HelpMenu(s);
        myHelpMenu.init();
    }
 
    /**
     *
     * @return
     */
    public Rectangle getBackdrop(){
        return backdrop;
    }
}