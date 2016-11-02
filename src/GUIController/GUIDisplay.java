package GUIController;

import BackEndInternalAPI.ObservableProperties;
import Base.OptionsMenu;
import FrontEndInternalAPI.RenderSprite;
import GUI.DisplayHelp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
//
//import java.awt.*;
//import java.awt.Button;
import javax.swing.*;

import BackEndExternalAPI.RGB;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIDisplay implements RenderSprite {
    private static final int TURTLE_FIT_SIZE = 40;
    private static final int X_POS = 620;
    private static final int Y_POS = 110;
    private static boolean visibility = true;
    private int numSteps = 0;
    private int strokeWidth = 5;
    private Pane window;
    private Line myPath;
    private ImageView helpButton;
    private Button optionsButton;
    private ImageView myTurtle, displayGraph;
    private DisplayHelp helpWindow;
    private Paint pathColor;
    private DisplayMenu myOptions;
    private ArrayList<Line> turtleMotion = new ArrayList<>();
    //    private ArrayList<Turtle> myTurtles = new ArrayList<>();
    private HashMap<Double, Turtle> myTurtles;
    private String currentTurtle;

    private String overButton = "-fx-background-color: linear-gradient(#0079b3, #00110e);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String buttonFill = "-fx-background-color: linear-gradient(#00110e, #0079b3);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";

    /**
     * @param p
     * @param turtle
     * @param pathColor
     */
    public GUIDisplay(Pane p, ImageView turtle, Paint pathColor, Line lineType) {
        this.window = p;
        this.myTurtle = turtle;
        this.pathColor = pathColor;
        this.myPath = lineType;
        drawDisplay();
        myTurtles = new HashMap<Double, Turtle>();
        addDisplayControlButtons();
        addTextLabel();
//        addMoreTurtlesButton();
//        addTurtle();
        addHelpButton();

    }

    public void setInitialTurtle(String initialTurtle) {
        currentTurtle = initialTurtle;
    }

    private void drawDisplay() {
        Image newImg = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/graphPaper.gif"));
        displayGraph = new ImageView(newImg);
        displayGraph.setFitWidth(770);
        displayGraph.setFitHeight(480);
        displayGraph.setTranslateY(Y_POS);
        displayGraph.setTranslateX(X_POS);
//        displayGraph.setstr
        displayGraph.opacityProperty().setValue(0.9);
        window.getChildren().add(displayGraph);
    }

    public ObservableProperties addTurtle(double newID) {
        ImageView myNewTurtle = new ImageView();
        myNewTurtle.setImage(myTurtle.getImage());
        myNewTurtle.setTranslateX(displayGraph.getTranslateX() + (displayGraph.getFitWidth() / 2));
        myNewTurtle.setTranslateY(displayGraph.getTranslateY() + (displayGraph.getFitHeight() / 2));
        myNewTurtle.setFitHeight(TURTLE_FIT_SIZE);
        myNewTurtle.setFitWidth(TURTLE_FIT_SIZE);
        Turtle newTurtle = new Turtle();
        newTurtle.setImage(myNewTurtle);
        newTurtle.setID(newID);
        myTurtles.put(newID, newTurtle);
        makeTooltip(newID);
        window.getChildren().add(newTurtle.getImage());
        myTurtles.get(newID).getImage().setOnMouseClicked(e -> togglePen());
        return new ObservableProperties(myNewTurtle, this, newID);

    }


    //PEN TOGGLES HERE
    //TODO Need to call observable properties to update penup and pendown
    private void togglePen() {
        visibility = !visibility;
    }

    private void makeTooltip(double newID) {
        double yLoc = 0 - getTurtleLocation(newID).getY();
        Tooltip t = new Tooltip("X: " + getTurtleLocation(newID).getX() + "\n" + "Y: " + yLoc + "\n"
                + "Rotation: " + myTurtles.get(newID).getImage().getRotate() + "\n" +
                "Turtle ID: " + newID);
        Tooltip.install(myTurtles.get(newID).getImage(), t);
    }

    private void addTextLabel() {
        Text label = new Text("Display");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setTranslateX(630);
        label.setTranslateY(130);
        window.getChildren().add(label);
    }

    private void addOptionsButton() {
        Stage s = new Stage();
        optionsButton = new Button("Display Options");
        optionsButton.setStyle(overButton);
        optionsButton.setOnMouseEntered(e -> optionsButton.setStyle(buttonFill));
        optionsButton.setOnMouseExited(e -> optionsButton.setStyle(overButton));
        optionsButton.setOnMouseClicked(e -> updateDisplayOptions());
        optionsButton.setTranslateX(760);
        optionsButton.setTranslateY(120);
        window.getChildren().add(optionsButton);
    }

    private void addHelpButton() {
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/help.png"));
        helpButton = new ImageView(newImage);
        helpButton.setOnMouseClicked(e -> helpHandler());
        helpButton.setTranslateX(displayGraph.getTranslateX() + displayGraph.getFitWidth() - 35);
        helpButton.setTranslateY(displayGraph.getTranslateY() + 10);
        helpButton.setFitWidth(30);
        helpButton.setFitHeight(30);
        window.getChildren().add(helpButton);
    }

//    private void addMoreTurtlesButton(){
//        Button addTurtles = new Button("Add Turtles");
//        addTurtles.setTranslateX(1110);
//        addTurtles.setTranslateY(125);
//        addTurtles.setStyle(overButton);
//        addTurtles.setOnMouseEntered(e -> addTurtles.setStyle(buttonFill));
//        addTurtles.setOnMouseExited(e -> addTurtles.setStyle(overButton));
////        addTurtles.setOnMouseClicked(e -> addTurtle());
//        window.getChildren().add(addTurtles);
//    }

    public String getTurtleStr() {
        if (myOptions != null)
            return myOptions.getTurtleString();
        else
            return null;
    }

    public DisplayMenu getMyOptions() {
        return myOptions;
    }

    private void helpHandler() {
        Stage s = new Stage();
        helpWindow = new DisplayHelp(s);
        helpWindow.init();
    }

    /**
     * @param width
     */
    public void bindNodes(ReadOnlyDoubleProperty width) {
        helpButton.translateXProperty().bind(width.subtract(50));
    }

    //    /**
//     *
//     * @param x
//     * @param y
//     */
    public void moveTurtle(double x, double y, double id) {

        numSteps++;
        drawNewLine(x, y, id);
        myTurtles.get(id).getImage().setX(x);
        myTurtles.get(id).getImage().setY(-y);
        //System.out
        makeTooltip(id);
    }


    private void drawNewLine(double x, double y, double id) {
//        Line newLine = new Line(origin.getX() + 20, origin.getY() + 20,
//                X_POS + destination.getX() + 20, Y_POS + destination.getY() + 20);
        double xFrom = myTurtles.get(id).getImage().getTranslateX() + myTurtles.get(id).getImage().getX() + 20;
        double yFrom = myTurtles.get(id).getImage().getTranslateY() + myTurtles.get(id).getImage().getY() + 20;
        Line newLine = new Line(xFrom, yFrom, myTurtles.get(id).getImage().getTranslateX() + x + 20, myTurtles.get(id).getImage().getTranslateY() - y + 20);
        newLine.setFill(pathColor);
        newLine.setStroke(pathColor);
        newLine.setStrokeWidth(5);
        newLine.setId("Step" + numSteps);
        newLine.setVisible(visibility);
        //turtleMotion.add(newLine);
        myTurtles.get(1.0).getLines().add(newLine);
        window.getChildren().add(window.getChildren().size() - 1, newLine);

    }

    /**
     *
     */
    public void clearScreen(double id) {
        window.getChildren().removeAll(myTurtles.get(id).getLines());
        myTurtles.get(id).getLines();
        turtleMotion.clear();
        //clearScreenProperty.set(false);
    }

    private void addDisplayControlButtons() {
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/clear.png"));
        ImageView clearImg = new ImageView(newImage);
        Button clear = newButton("Clear", clearImg, (int) displayGraph.getTranslateX() + 300,
                (int) displayGraph.getTranslateY() + 10);
        clear.setOnMouseClicked(e -> {
            window.getChildren().removeAll(turtleMotion);
            turtleMotion.clear();
        });
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/options.png"));
        ImageView optionsImg = new ImageView(newImage);
        optionsButton = newButton("Display Options", optionsImg, 760, 120);
        optionsButton.setOnMouseClicked(e -> {
            Stage s = new Stage();
            myOptions = new DisplayMenu(s);
            updateDisplayOptions();
        });
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/reset.png"));
        ImageView resetImg = new ImageView(newImage);
        Button reset = newButton("Reset", resetImg, 1010, 120);
        reset.setOnMouseClicked(e -> resetIDE());
//        optionsButton.setTranslateX(760);
//        optionsButton.setTranslateY(120);
        window.getChildren().addAll(clear, optionsButton, reset);
//        window.getChildren().add(clear);
    }

    private Button newButton(String text, ImageView imgV, int x, int y) {
        imgV.setFitWidth(25);
        imgV.setFitHeight(25);
        Button run = new Button(text, imgV);
        run.setStyle(overButton);
        run.setOnMouseEntered(e -> {
            run.setStyle(buttonFill);
        });
        run.setOnMouseExited(e -> run.setStyle(overButton));
        run.setTranslateX(x);
        run.setTranslateY(y);
        return run;
    }


    /**
     * @param isVisible
     */
    public void setVisibility(boolean isVisible) {
        visibility = isVisible;
    }

    /**
     * @return
     */
    public ImageView getGraph() {
        return displayGraph;
    }

    /**
     * @param newID
     * @return
     */
    public Point getTurtleLocation(double newID) {
        return new Point((int) myTurtles.get(newID).getImage().getX(), (int) myTurtles.get(newID).getImage().getY());
    }

    @Override
    public void updateNodes() {

    }

    public Paint getPenColor() {
        return pathColor;
    }

    @Override
    public void updateDisplayOptions() {
//        myOptions.setDefaults(pathColor, currentTurtle);
        myOptions.initPopup();

    }

    public void applyDisplayChanges() {
        pathColor = myOptions.getPenColor().getValue();
//        applyDisplayChanges(myOptions.getTurtleBox().getValue());
        myOptions.setTurtleString();
        System.out.println(myOptions.getTurtleString());
        System.out.println(myTurtles.get(0));
        
        for(int i = 0; i < myTurtles.size(); i++){
        System.out.println(myTurtles.get(i).getImage());
        myTurtles.get(i).getImage().setImage(myOptions.generateTurtleImage());
        }
        myPath = myOptions.getLineBox().getValue();
        createDisplayShading();
        strokeWidth = myOptions.getStrokeWidth();
        setVisibility(!myOptions.isPenUp());
        //makeTooltip();
    }

    private void createDisplayShading() {
        double hue = myOptions.map((myOptions.getDisplayColor().getHue() + 180) % 360, 0, 360, -1, 1);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(myOptions.getDisplayColor().getSaturation());
        colorAdjust.setBrightness(myOptions.getDisplayColor().getBrightness());
        displayGraph.setEffect(colorAdjust);
    }

    @Override
    public void resetIDE() {
        window.getChildren().remove(myTurtle);
        window.getChildren().removeAll(turtleMotion);
        turtleMotion.clear();
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/graphPaper.gif"));
//        drawDisplay();
        displayGraph.setEffect(null);
        displayGraph.setImage(newImage);
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/turtle.png"));
//        ImageView defaultTurtle = new ImageView(newImage);
//        myTurtle = defaultTurtle;
        myTurtle.setImage(newImage);
        myTurtle.setTranslateX(displayGraph.getTranslateX() + (displayGraph.getFitWidth() / 2));
        myTurtle.setTranslateY(displayGraph.getTranslateY() + (displayGraph.getFitHeight() / 2));
//        addTurtle();
        Paint defaultColor = Color.MIDNIGHTBLUE;
        pathColor = defaultColor;
        myPath.getStrokeDashArray().clear();
//        newImage = new Image(getClass().getClassLoader()
//                .getResourceAsStream("images/graphPaper.png"));
//        ImageView defaultDisplay = new ImageView(newImage);
//        displayGraph = defaultDisplay;
        strokeWidth = 5;
//        addTextLabel();
//        addHelpButton();
//        addDisplayControlButtons();
    }

    class DisplayMenu extends OptionsMenu {

        private static final int DROP_DOWN_X_VALUE = 400;
        private static final int PEN_MIN = 0;
        private static final int PEN_MAX = 10;
        private static final int PEN_INIT = 5;
        private ColorPicker displayColor;
        private Slider slider;
        private CheckBox penUpBox;

        /**
         * @param s
         */
        public DisplayMenu(Stage s) {
            super(s);
        }

        public void addNodes() {
            addTitle();
            addRectangle();
            changePenColor();
            changeSpriteImage();
            addLaunchButton();
            addLineStylePicker();
            changeDisplayColor();
            addPenSizeSlider();
            addCheckBox();
        }

        @Override
        public void addTitle() {
            Text title = new Text("Select your preferences for the display.");
            title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            title.setFill(Color.WHITE);
            title.setTranslateX(30);
            title.setTranslateY(130);
            getStartWindow().getChildren().add(title);
        }

        @Override
        public void addRectangle() {
            Rectangle backdrop = new Rectangle(500, 340, Color.MIDNIGHTBLUE);
            backdrop.setTranslateY(180);
            backdrop.setTranslateX(100);
            backdrop.opacityProperty().setValue(0.5);
            getStartWindow().getChildren().add(backdrop);
        }

        @Override
        public void addLaunchButton() {
            Button newButton = new javafx.scene.control.Button("Apply");
            newButton.setStyle(getOverButton());
            newButton.setOnMouseEntered(e -> newButton.setStyle(getButtonFill()));
            newButton.setOnMouseExited(e -> newButton.setStyle(getOverButton()));
            newButton.setOnMouseClicked(e -> {
                applyDisplayChanges();
                getStage().close();
            });
            newButton.setTranslateX(300);
            newButton.setTranslateY(500);
//        newButton.setOnMouseClicked(e -> setParameters());
            getStartWindow().getChildren().add(newButton);
        }

        private void addPenSizeSlider() {
//            JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
//                    PEN_MIN, PEN_MAX, PEN_INIT);
//            framesPerSecond.addChangeListener(this);

            slider = new Slider(0, 10, 5);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(2);
            slider.setMinorTickCount(1);
//            slider.setBlockIncrement(1);
            slider.setSnapToTicks(true);
//            slider.setPrefWidth(200);
//            slider.tick
            slider.setTranslateX(DROP_DOWN_X_VALUE);
            slider.setTranslateY(200);

            Label sliderLabel = generateLabel("Choose pen size \t\t" + (int) slider.getValue(), 125, 200);

            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                    sliderLabel.setText("Choose pen size \t\t" + String.format("%.0f", new_val));
                }
            });

            getStartWindow().getChildren().addAll(slider, sliderLabel);
        }

        @Override
        public void initIDE(String background, String turtle) {
//nope
        }

        public Image generateTurtleImage() {
            Image newImg = new Image(getClass().getClassLoader()
                    .getResourceAsStream(getTurtleString()));
            return newImg;
        }

        public void changeDisplayColor() {
            displayColor = generateColorPicker(Color.MIDNIGHTBLUE, 400, 400);
            Label penLabel = generateLabel("Select display color", 125, 400);
            getStartWindow().getChildren().add(displayColor);
            getStartWindow().getChildren().add(penLabel);
        }

        private void addCheckBox() {
            penUpBox = new CheckBox("penup");
            penUpBox.setTranslateX(DROP_DOWN_X_VALUE);
            penUpBox.setTranslateY(450);
            Label penUpLabel = generateLabel("Check to make pen invisible", 125, 450);
            getStartWindow().getChildren().addAll(penUpLabel, penUpBox);
        }

        /**
         *
         */
        public void initPopup() {
            getStage().setTitle("Options");
            getStage().setScene(new Scene(setUpWindow()));
            getStage().show();
        }

        public double map(double value, double start, double stop, double targetStart, double targetStop) {
            return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
        }


        public Color getDisplayColor() {
            return displayColor.getValue();
        }

        public int getStrokeWidth() {
            return (int) slider.getValue();
        }

        public boolean isPenUp() {
            return penUpBox.isSelected();
        }
    }

    public void changePenColor(Double newValue) {
        // TODO Auto-generated method stub
    }

    public Object setPenSize(Double newValue) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object changeImage(Double newValue) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object setBackgroundImage(Double newValue) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object changePalette(RGB newValue) {
        // TODO Auto-generated method stub
        return null;
    }

}