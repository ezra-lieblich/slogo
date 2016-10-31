package GUIController;

import Base.OptionsMenu;
import FrontEndInternalAPI.RenderSprite;
import GUI.DisplayHelp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//
//import java.awt.*;
//import java.awt.Button;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIDisplay implements RenderSprite {
    private static final int TURTLE_FIT_SIZE = 40;
    private static final int X_POS = 620;
    private static final int Y_POS = 110;
    private static boolean visibility = true;
    private int numSteps = 0;
    private Pane window;
    private Line myPath;
    private ImageView helpButton;
    private Button optionsButton;
    private ImageView myTurtle, displayGraph;
    private DisplayHelp helpWindow;
    private Paint pathColor;
    private DisplayMenu myOptions;
    private ArrayList<Line> turtleMotion = new ArrayList<>();
    private String currentTurtle;

    private String overButton = "-fx-background-color: linear-gradient(#0079b3, #00110e);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String buttonFill = "-fx-background-color: linear-gradient(#00110e, #0079b3);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";

    /**
     *
     * @param p
     * @param turtle
     * @param pathColor
     */
    public GUIDisplay(Pane p, ImageView turtle, Paint pathColor, Line lineType){
        this.window = p;
        this.myTurtle = turtle;
        this.pathColor = pathColor;
        this.myPath = lineType;
        drawDisplay();
        addTextLabel();
        addTurtle();
        addHelpButton();
        addOptionsButton();
    }

    public void setInitialTurtle(String initialTurtle){
        currentTurtle = initialTurtle;
    }

    private void drawDisplay(){
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

    private void addTurtle(){
        myTurtle.setTranslateX(displayGraph.getTranslateX() + (displayGraph.getFitWidth() / 2));
        myTurtle.setTranslateY(displayGraph.getTranslateY() + (displayGraph.getFitHeight() / 2));
        myTurtle.setFitHeight(TURTLE_FIT_SIZE);
        myTurtle.setFitWidth(TURTLE_FIT_SIZE);
//        myTurtle.x
        window.getChildren().add(myTurtle);
    }

    private void addTextLabel(){
        Text label = new Text("Display");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setTranslateX(630);
        label.setTranslateY(130);
        window.getChildren().add(label);
    }

    private void addOptionsButton(){
        Stage s = new Stage();
        myOptions = new DisplayMenu(s);
        optionsButton = new Button("Display Options");
        optionsButton.setStyle(overButton);
        optionsButton.setOnMouseEntered(e -> optionsButton.setStyle(buttonFill));
        optionsButton.setOnMouseExited(e -> optionsButton.setStyle(overButton));
        optionsButton.setOnMouseClicked(e -> updateDisplayOptions());
        optionsButton.setTranslateX(760);
        optionsButton.setTranslateY(120);
        window.getChildren().add(optionsButton);
    }

    private void addHelpButton(){
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
    
    private void helpHandler(){
        Stage s = new Stage();
        helpWindow = new DisplayHelp(s);
        helpWindow.init();
    }

    /**
     *
     * @param width
     */
    public void bindNodes(ReadOnlyDoubleProperty width){
        helpButton.translateXProperty().bind(width.subtract(50));
    }

//    /**
//     *
//     * @param x
//     * @param y
//     */
//    public void moveTurtle(int x, int y){
//        numSteps++;
//        drawNewLine(new Point((int)myTurtle.getTranslateX(),
//                (int)myTurtle.getTranslateY()), new Point(x, y));
////        System.out.println("turtle original position:" + (int) myTurtle.getTranslateX());
////        System.out.println("translate x of the editor" + X_POS);
//        myTurtle.setTranslateX(X_POS + x);
//        myTurtle.setTranslateY(Y_POS + y);
//    }

    /**
     *
     * @param bool
     */
    public void drawNewLine(){
    	double centerX =  20;
		double centerY = 20;
		Line newPath;
    	if (turtleMotion.size() < 1) {
    		
    		newPath = new Line(centerX + myTurtle.getTranslateX(),
                    centerY + myTurtle.getTranslateY(),
                    myTurtle.getX() + myTurtle.getTranslateX() + centerX,
                    myTurtle.getY() + myTurtle.getTranslateY() + centerY);
    	}
    	else{
    		newPath = new Line(turtleMotion.get(turtleMotion.size() - 1).getEndX(),
                    turtleMotion.get(turtleMotion.size() - 1).getEndY(),
    				myTurtle.getX() + myTurtle.getTranslateX() + centerX,
                    myTurtle.getY() + myTurtle.getTranslateY() + centerY);
    	}
    	newPath.setFill(pathColor);
        newPath.setStroke(pathColor);
        //newPath.setStrokeWidth(strokeWidth);
//        newPath.setStrokeDashOffset(2);
        newPath.getStrokeDashArray().addAll(myPath.getStrokeDashArray());
        newPath.setId("Step" + numSteps);
        newPath.setVisible(visibility);
        turtleMotion.add(newPath);
        window.getChildren().add(newPath);

        //if(yBoundUpper) myTurtle.setTranslateY(Y_POS);
        //window.getChildren().remove(myTurtle);
        //window.getChildren().add(myTurtle);
    }

    //DONT NEED IT ANYMORE

    /**
     *
     * @param origin
     * @param destination
     */
    public void drawNewLine(Point origin, Point destination){
//        Line newLine = new Line(origin.getX() + 20, origin.getY() + 20,
//                X_POS + destination.getX() + 20, Y_POS + destination.getY() + 20);
        System.out.println("my origin: " + origin.getX() + " " + origin.getY());
        System.out.println("my destination: " + destination.getX() + " " + destination.getY());
        Line newLine = new Line(origin.getX() + 20, origin.getY() + 20,
                destination.getX() + 20, destination.getY() + 20);
        newLine.setFill(pathColor);
        newLine.setStroke(pathColor);
        newLine.setStrokeWidth(5);
        newLine.setId("Step" + numSteps);
        newLine.setVisible(visibility);
        turtleMotion.add(newLine);
        window.getChildren().add(newLine);
    }

    /**
     *
     * @param clearScreenProperty
     */
	public void clearScreen() {
		window.getChildren().removeAll(turtleMotion);
		//clearScreenProperty.set(false);
	}

    /**
     *
     * @param isVisible
     */
    public void setVisibility(boolean isVisible){
        visibility = isVisible;
    }

    /**
     *
     * @return
     */
    public ImageView getGraph(){
        return displayGraph;
    }

    /**
     *
     * @return
     */
    public Point getTurtleLocation(){
        return new Point((int)myTurtle.getTranslateX(), (int)myTurtle.getTranslateY());
    }

    @Override
    public void updateNodes() {

    }

    @Override
    public void updateDisplayOptions() {
//        myOptions.setDefaults(pathColor, currentTurtle);
        myOptions.initPopup();

    }

    private void applyDisplayChanges(){
        pathColor = myOptions.getPenColor().getValue();
//        applyDisplayChanges(myOptions.getTurtleBox().getValue());
        myOptions.setTurtleString();
        myTurtle.setImage(myOptions.generateTurtleImage());
        myPath = myOptions.getLineBox().getValue();
        createDisplayShading();
    }

    private void createDisplayShading(){
        double hue = myOptions.map( (myOptions.getDisplayColor().getHue() + 180) % 360, 0, 360, -1, 1);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(myOptions.getDisplayColor().getSaturation());
        colorAdjust.setBrightness(myOptions.getDisplayColor().getBrightness());
        displayGraph.setEffect(colorAdjust);
    }

    @Override
    public void resetIDE() {

    }

    private class DisplayMenu extends OptionsMenu{

        private ColorPicker displayColor;
        /**
         * @param s
         */
        public DisplayMenu(Stage s) {
            super(s);
        }

        public void addNodes(){
            addTitle();
            addRectangle();
            changePenColor();
            changeSpriteImage();
            addLaunchButton();
            addLineStylePicker();
            changeDisplayColor();
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
            Rectangle backdrop = new Rectangle(500, 290, Color.MIDNIGHTBLUE);
            backdrop.setTranslateY(230);
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

        @Override
        public void initIDE(String background, String turtle) {
//nope
        }

        public Image generateTurtleImage(){
            Image newImg = new Image(getClass().getClassLoader()
                    .getResourceAsStream(getTurtleString()));
            return newImg;
        }

        public void changeDisplayColor() {
            displayColor = generateColorPicker(Color.ALICEBLUE, 400, 400);
            Label penLabel = generateLabel("Select display color", 125, 400);
            getStartWindow().getChildren().add(displayColor);
            getStartWindow().getChildren().add(penLabel);
        }

        /**
         *
         */
        public void initPopup(){
            getStage().setTitle("Options");
            getStage().setScene(new Scene(setUpWindow()));
            getStage().show();
        }

        public double map(double value, double start, double stop, double targetStart, double targetStop) {
            return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
        }

        public Color getDisplayColor(){
            return displayColor.getValue();
        }
    }
}
