// This is part of my masterpiece line 92 and 93
//Ezra Lieblich
package GUIController;
import BackEndInterpreter.FrontendObservableProperties;
import BackEndInterpreter.ObservableProperties;
import Base.NodeFactory;
import Base.OptionsMenu;
import FrontEndInternalAPI.DisplayMappings;
import FrontEndInternalAPI.Display;
import GUI.DisplayHelp;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import BackEndInterface.RGB;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by Delia on 10/15/2016.
 */
public class GUIDisplay implements Display {
    private static final int TURTLE_FIT_SIZE = 40;
    private static final int GRAPH_WIDTH = 770;
    private static final int GRAPH_HEIGHT = 480;
    private static final int GRAPH_X_POS = 620;
    private static final int GRAPH_Y_POS = 110;
    private int numSteps = 0;
    private int strokeWidth = 5;
    private Stage s = new Stage();
    private ArrayList<Line> turtleMotion = new ArrayList<>();
    private HashMap<Double, Turtle> myTurtles = new HashMap<>();
    private Pane window;
    private Line myPath;
    private ImageView helpButton, myTurtle, displayGraph;
    private Button optionsButton;
    private Paint pathColor;
    private DisplayHelp helpWindow;
    private DisplayMenu myOptions = new DisplayMenu(s);
    private DisplayMappings displayMappings = new DisplayMappings();
    private NodeFactory myFactory = new NodeFactory();
    /**
     * Creates a new GUIDisplay that contains all the necessary parts of the SLogo IDE.
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
        addDisplayControlButtons();
        addTextLabel();
        addHelpButton();
    }
    
    private void drawDisplay() {
        Image newImg = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/graphPaper.gif"));
        displayGraph = new ImageView(newImg);
        displayGraph.setFitWidth(GRAPH_WIDTH);
        displayGraph.setFitHeight(GRAPH_HEIGHT);
        displayGraph.setTranslateY(GRAPH_Y_POS);
        displayGraph.setTranslateX(GRAPH_X_POS);
        displayGraph.opacityProperty().setValue(0.9);
        ColorAdjust colorAdjust = myFactory.makeEffect(myOptions.getDisplayColor());
        displayGraph.setEffect(colorAdjust);
        window.getChildren().add(displayGraph);
    }
    
    @Override
    public ObservableProperties addTurtle(double newID){
        ImageView newTurtleImg = new ImageView();
        newTurtleImg.setImage(myTurtle.getImage());
        newTurtleImg.setTranslateX(GRAPH_X_POS + (GRAPH_WIDTH / 2));
        newTurtleImg.setTranslateY(GRAPH_Y_POS + (GRAPH_HEIGHT / 2));
        newTurtleImg.setFitHeight(TURTLE_FIT_SIZE);
        newTurtleImg.setFitWidth(TURTLE_FIT_SIZE);
        ObservableProperties turtleProperty = new ObservableProperties(newTurtleImg, this, newID);
        Turtle newTurtle = new Turtle((FrontendObservableProperties)turtleProperty, newTurtleImg);
        newTurtle.setID(newID);
        myTurtles.put(newID, newTurtle);
        makeTooltip(newID);
        window.getChildren().add(newTurtle.getImage());
        myTurtles.get(newID).getImage().setOnMouseClicked(e -> togglePen(newID));
        return turtleProperty;
    }
    
    private void togglePen(double newID) {
        myTurtles.get(newID).setVisibility(!myTurtles.get(newID).isVisible());
        myTurtles.get(newID).getProperties()
                .setPathVisibleProperty(myTurtles.get(newID).isVisible());
    }
    
    private void makeTooltip(double newID) {
        Tooltip t = new Tooltip("X: " + myTurtles.get(newID).getProperties().getXProperty()
                + "\n" + "Y: " + myTurtles.get(newID).getProperties().getYProperty() + "\n"
                + "Rotation: " + myTurtles.get(newID).getImage().getRotate() + "\n"
                + "Turtle ID: " + newID);
        Tooltip.install(myTurtles.get(newID).getImage(), t);
    }
    private void addTextLabel() {
        Text label = myFactory.makeTitle("Display", 630, 130);
        window.getChildren().add(label);
    }
    private void addHelpButton() {
        helpButton = myFactory.makeHelpButton(displayGraph.getTranslateX() + displayGraph.getFitWidth() - 30,
                displayGraph.getTranslateY() + 10);
        helpButton.setOnMouseClicked(e -> helpHandler());
        window.getChildren().add(helpButton);
    }
    private void helpHandler() {
        Stage s = new Stage();
        helpWindow = new DisplayHelp(s);
        helpWindow.init();
    }
    @Override
    public void bindNodes(ReadOnlyDoubleProperty width) {
        helpButton.translateXProperty().bind(width.subtract(50));
    }
    @Override
    public void moveTurtle(double x, double y, double id) {
        numSteps++;
        drawNewLine(x, y, id);
        myTurtles.get(id).getImage().setX(x);
        myTurtles.get(id).getImage().setY(-y);
        boolean out = false;
        if (y != 0 && myTurtles.get(id).getImage().getTranslateY()
                + myTurtles.get(id).getImage().getY() < displayGraph.getTranslateY())
            out = true;
        if (y != 0 && myTurtles.get(id).getImage().getTranslateY()
                + myTurtles.get(id).getImage().getY() > displayGraph.getTranslateY()
                + displayGraph.getFitHeight())
            out = true;
        if (x != 0 && myTurtles.get(id).getImage().getTranslateX()
                + myTurtles.get(id).getImage().getX() < displayGraph.getTranslateX())
            out = true;
        if (x != 0 && myTurtles.get(id).getImage().getTranslateX()
                + myTurtles.get(id).getImage().getX() > displayGraph.getTranslateX()
                + displayGraph.getFitWidth())
            out = true;
        if (out)
            window.getChildren().remove(myTurtles.get(id).getImage());
        else if(!window.getChildren().contains(myTurtles.get(id).getImage()))
            window.getChildren().add(myTurtles.get(id).getImage());
    }
    private void drawNewLine(double x, double y, double id){
        double xDest = myTurtles.get(id).getImage().getTranslateX() + x + 20;
        double yDest = myTurtles.get(id).getImage().getTranslateY() - y + 20;
        double xFrom =  myTurtles.get(id).getImage().getTranslateX() +  myTurtles.get(id).getImage().getX() + 20;
        double yFrom =  myTurtles.get(id).getImage().getTranslateY() +  myTurtles.get(id).getImage().getY() + 20;
        boolean fullyOut = false;
        boolean destOut = (xDest < displayGraph.getTranslateX()
                || xDest > displayGraph.getTranslateX() + displayGraph.getFitWidth())
                || (yDest < displayGraph.getTranslateY()
                || yDest > displayGraph.getTranslateY() + displayGraph.getFitHeight());
        boolean originOut = (xFrom < displayGraph.getTranslateX()
                || xFrom > displayGraph.getTranslateX() + displayGraph.getFitWidth())
                || (yFrom < displayGraph.getTranslateY()
                || yFrom > displayGraph.getTranslateY() + displayGraph.getFitHeight());
        if (destOut && originOut){
            fullyOut = true;
        }
        if (yDest < displayGraph.getTranslateY()){
            yDest = displayGraph.getTranslateY();
        }
        if (yDest > displayGraph.getTranslateY() + displayGraph.getFitHeight()){
            yDest = displayGraph.getTranslateY() + displayGraph.getFitHeight();
        }
        if (xDest < displayGraph.getTranslateX()){
            xDest = displayGraph.getTranslateX();
        }
        if (xDest > displayGraph.getTranslateX() + displayGraph.getFitWidth()){
            xDest = displayGraph.getTranslateX() + displayGraph.getFitWidth();
        }
        if (yFrom < displayGraph.getTranslateY()){
            yFrom = displayGraph.getTranslateY();
        }
        if (yFrom > displayGraph.getTranslateY() + displayGraph.getFitHeight()){
            yFrom = displayGraph.getTranslateY() + displayGraph.getFitHeight();
        }
        if (xFrom < displayGraph.getTranslateX()){
            xFrom = displayGraph.getTranslateX();
        }
        if (xFrom > displayGraph.getTranslateX() + displayGraph.getFitWidth()){
            xFrom = displayGraph.getTranslateX() + displayGraph.getFitWidth();
        }
        Line newLine = new Line(xFrom, yFrom, xDest, yDest);
        makeTooltip(id);
        newLine.setFill(pathColor);
        newLine.setStroke(pathColor);
        newLine.setStrokeWidth(strokeWidth);
        newLine.setId("Step" + numSteps);
        newLine.getStrokeDashArray().addAll(myPath.getStrokeDashArray());
        newLine.setVisible(myTurtles.get(id).isVisible());
        turtleMotion.add(newLine);
        myTurtles.get(1.0).getLines().add(newLine);
        if(!fullyOut)
            window.getChildren().add(window.getChildren().size() - 1, newLine);
    }
    @Override
    public void clearScreen(double id) {
        window.getChildren().removeAll(myTurtles.get(id).getLines());
        myTurtles.get(id).getLines();
        turtleMotion.clear();
    }
    
    private void addDisplayControlButtons() {
        Button clear = myFactory.makeClearButton(displayGraph.getTranslateX() + 380, 40);
        clear.setOnMouseEntered(e -> clear.setStyle(myFactory.getButtonFill()));
        clear.setOnMouseClicked(e -> {
            window.getChildren().removeAll(turtleMotion);
            turtleMotion.clear();
        });
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/options.png"));
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/options.png"));
        ImageView optionsImg = new ImageView(newImage);
        optionsButton = myFactory.makeButton("Display Options", optionsImg, 840, 40);
        optionsButton.setOnMouseEntered(e -> optionsButton.setStyle(myFactory.getButtonFill()));
        optionsButton.setOnMouseClicked(e -> updateDisplayOptions());
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/reset.png"));
        ImageView resetImg = new ImageView(newImage);
        Button reset = myFactory.makeButton("Reset", resetImg, 1090, 40);
        reset.setOnMouseEntered(e -> reset.setStyle(myFactory.getButtonFill()));
        reset.setOnMouseClicked(e -> resetIDE());
        window.getChildren().addAll(clear, optionsButton, reset);
    }
    
    private void applyDisplayChanges() {
        pathColor = myOptions.getPenColor().getValue();
        myOptions.setTurtleString();
        myTurtle.setImage(myOptions.generateTurtleImage());
        for (Turtle turtle : myTurtles.values()) {
            turtle.setImage(myOptions.generateTurtleImage());
        }
        myPath = myOptions.getLineBox().getValue();
        createDisplayShading(myOptions.getDisplayColor());
        strokeWidth = myOptions.getStrokeWidth();
    }
    
    private void createDisplayShading(Color shading) {
        ColorAdjust colorAdjust = myFactory.makeEffect(shading);
        displayGraph.setEffect(colorAdjust);
    }
    
    @Override
    public void resetIDE() {
        window.getChildren().remove(myTurtle);
        window.getChildren().removeAll(turtleMotion);
        turtleMotion.clear();
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/graphPaper.gif"));
        displayGraph.setEffect(null);
        displayGraph.setImage(newImage);
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/turtle.png"));
        myTurtle.setImage(newImage);
        myTurtle.setTranslateX(displayGraph.getTranslateX() + (displayGraph.getFitWidth() / 2));
        myTurtle.setTranslateY(displayGraph.getTranslateY() + (displayGraph.getFitHeight() / 2));
        Paint defaultColor = Color.MIDNIGHTBLUE;
        pathColor = defaultColor;
        myPath.getStrokeDashArray().clear();
        strokeWidth = 5;
    }
    @Override
    public void changePenColor(Double newValue) {
        Color color = displayMappings.getPenColor(newValue.intValue());
        pathColor = color;
    }
    @Override
    public void setPenSize(Double newValue) {
        strokeWidth = newValue.intValue();
    }
    @Override
    public void changeImage(Double newValue) {
        Image newImg = displayMappings.getTurtleImage(newValue.intValue());
        myTurtle.setImage(newImg);
        for (Turtle turtle : myTurtles.values()) {
            turtle.setImage(newImg);
        }
    }
    @Override
    public void changePalette(Double newValue) {
        Color color = displayMappings.getBackgroundColor(newValue.intValue());
        createDisplayShading(color);
    }
    @Override
    public void changePaletteRGB(RGB newValue) {
        Color color = new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(), 1.0);
        createDisplayShading(color);
    }
    @Override
    public void setVisibility(boolean isVisible, double newID) {
        myTurtles.get(newID).setVisibility(isVisible);
    }
    @Override
    public String getTurtleStr() {
        if (myOptions != null)
            return myOptions.getTurtleString();
        else
            return null;
    }
    @Override
    public Paint getPenColor() {
        return pathColor;
    }
    @Override
    public void updateDisplayOptions() {
        myOptions.initPopup();
    }
    
    
    class DisplayMenu extends OptionsMenu {

        private static final int DROP_DOWN_X_VALUE = 400;
        private ColorPicker displayColor = new ColorPicker();
        private Slider slider;
        /**
         * Creates a new DisplayMenu by calling the superclass constructor. 
         * @param s
         */
        public DisplayMenu(Stage s) {
            super(s);
            displayColor.setValue(Color.MIDNIGHTBLUE);
        }
        /**
         * Adds all of the required buttons/sliders/choosers to the display
         * by calling a lot of helper methods.
         */
        public void addNodes() {
            addTitle();
            addRectangle();
            changePenColor();
            changeSpriteImage();
            addLaunchButton();
            addLineStylePicker();
            changeDisplayColor();
            addPenSizeSlider();
        }
        
        @Override
        public void addTitle() {
            Text title = myFactory.makePopupText(
                    "Select your preferences for the display.", 30, 130, 20);
            getStartWindow().getChildren().add(title);
        }
        
        @Override
        public void addRectangle() {
            Rectangle backdrop = myFactory.makeBlueBackdrop(500, 340, 100, 180);
            getStartWindow().getChildren().add(backdrop);
        }
        
        @Override
        public void addLaunchButton() {
            Button newButton = myFactory.makeBigButton("Apply", 300, 500);
            newButton.setOnMouseClicked(e -> {
                applyDisplayChanges();
                getStage().close();
            });
            getStartWindow().getChildren().add(newButton);
        }
        
        private void addPenSizeSlider(){
            slider = new Slider(0, 10, 5);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(2);
            slider.setMinorTickCount(1);
            slider.setSnapToTicks(true);
            slider.setTranslateX(DROP_DOWN_X_VALUE);
            slider.setTranslateY(200);
            Label sliderLabel = generateLabel("Choose pen size \t\t" + (int) slider.getValue(), 125, 200);
            slider.valueProperty().addListener((ov, old_val, new_val) ->
                    sliderLabel.setText("Choose pen size \t\t" + String.format("%.0f", new_val)));
            getStartWindow().getChildren().addAll(slider, sliderLabel);
        }
        
        @Override
        public void initIDE(String background, String turtle) {
//nope
        }
        /**
         * This method loads a new image to represent the Turtle on the screen
         * by getting it's information from a resource file.  
         * @return newImg
         */
        public Image generateTurtleImage() {
            Image newImg = new Image(getClass().getClassLoader()
                    .getResourceAsStream(getTurtleString()));
            return newImg;
        }
        /**
         * This adds a color picker that makes it possible for the user to 
         * change the color of the display window's background. 
         */
        public void changeDisplayColor() {
            displayColor = generateColorPicker(Color.MIDNIGHTBLUE, 400, 400);
            Label penLabel = generateLabel("Select display color", 125, 400);
            getStartWindow().getChildren().add(displayColor);
            getStartWindow().getChildren().add(penLabel);
        }
        /**
         * This method opens the popup options screen.
         */
        public void initPopup() {
            getStage().setTitle("Options");
            getStage().setScene(new Scene(setUpWindow()));
            getStage().show();
        }
        /**
         * This returns the color of the display background
         * @return
         */
        public Color getDisplayColor() {
            return displayColor.getValue();
        }
        /**
         * This returns the selected width of the pen
         * @return
         */
        public int getStrokeWidth() {
            return (int) slider.getValue();
        }
    }

    
	public void stamp(double imageIndex) {
		// TODO Front end will draw a stamp with the imageIndex when this is called
		//Also will add it to the list of imageViews that contain all the stamps
		//They have access to the current position
	}

	public void clearStamps() {
		// TODO Removes all the stamps from the list of imageViews of stamps they have
	}
}