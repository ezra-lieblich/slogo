package GUI;

import FrontEndInternalAPI.ButtonMenu;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

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
    private OptionsPopup myOptions;
    private HelpMenu myHelpMenu;
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
    private String overButton = "-fx-background-color: linear-gradient(#0079b3, #00110e);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String buttonFill = "-fx-background-color: linear-gradient(#00110e, #0079b3);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";

    private SimpleStringProperty myLanguage;

    /**
     *
     * @param p
     * @param borderColor
     */
    public GUIButtonMenu(Pane p, Paint borderColor, SimpleStringProperty language) {
        this.window = p;
        this.border = borderColor;
        myLanguage = language;
//        myOptions = new OptionsPopup();
        drawButtonMenu();
        addTextLabel();
        addButtons();
        addComboBoxes();
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
//        newImage = new Image(getClass().getClassLoader()
//                .getResourceAsStream("images/options.png"));
//        imgV = new ImageView(newImage);
//        Button options = newButton("OPTIONS", imgV, 340, 40);
//        options.setOnMouseClicked(e -> optionsHandler());
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/help.png"));
        imgV = new ImageView(newImage);
        Button help = newButton("HELP", imgV, 337, 40);
        help.setOnMouseClicked(e -> helpHandler());

        Button save = newButton("Save", imgV, 1000, 40);
//        save.setOnMouseClicked(e -> {
//            Stage stage = new Stage();
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Save Image");
//            File file = fileChooser.showSaveDialog(stage);
//            if (file != null) {
//                try {
//                    ImageIO.write(SwingFXUtils.fromFXImage(pic.getImage(),
//                            null), "png", file);
//                } catch (IOException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        });
        window.getChildren().addAll(play, pause, stop, help, save);
//        window.getChildren().add(play);
//        window.getChildren().add(pause);
//        window.getChildren().add(stop);
//        window.getChildren().add(help);
//        window.getChildren().add(options);
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

    private void addComboBoxes(){
        System.setProperty("glass.accessible.force", "false");
        backgroundBox = new ComboBox<String>(backgroundOptions);
        backgroundBox.setValue(defaultBackground);
        backgroundBox.setTranslateX(440);
        backgroundBox.setTranslateY(50);
//        backgroundBox.setStyle(buttonFill);
//        backgroundBox.style
        window.getChildren().add(backgroundBox);
        languageBox = new ComboBox<String>(languageOptions);
        languageBox.setValue(defaultLanguage);
        languageBox.setTranslateX(610);
        languageBox.setTranslateY(50);
        window.getChildren().add(languageBox);
        languageBox.valueProperty().addListener((ov, oldLang, newLang) -> myLanguage.set(newLang));
    }



    /**
     *
     * @param paint
     * @param background
     * @param turtle
     * @param language
     */
    public void setDefaults(Color paint, String background, String turtle, String language){
        myOptions = new OptionsPopup(s, paint, background, turtle, language);
    }
//
//    private void optionsHandler(){
//        myOptions.initPopup();
//    }

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