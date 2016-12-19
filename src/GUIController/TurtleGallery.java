package GUIController;

import Base.NodeFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Delia on 12/19/2016.
 */
public class TurtleGallery {
    private static final int HELP_MENU_WIDTH = 700;
    private static final int HELP_MENU_HEIGHT = 600;
    private Stage myStage;
    private Pane helpWindow;
    private HashMap<Double, Turtle> myTurtles;
    private NodeFactory myFactory = new NodeFactory();
    private ScrollPane galleryView;
    private String defaultTurtle = "Turtle";
    private ObservableList<String> turtleOptions =
            FXCollections.observableArrayList(
                    "Turtle",
                    "Drake",
                    "Heart",
                    "Young Rob",
                    "Prof. Duvall");

    public TurtleGallery(HashMap<Double, Turtle> turtleMap) {
        this.myTurtles = turtleMap;
    }

    public Parent setUpWindow() {
        helpWindow = new Pane();
        helpWindow.setPrefSize(HELP_MENU_WIDTH, HELP_MENU_HEIGHT);
        Image background = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/background.jpg"));
        ImageView backgroundImageMainScreen = new ImageView(background);
        backgroundImageMainScreen.setFitWidth(HELP_MENU_WIDTH + 50);
        backgroundImageMainScreen.setFitHeight(HELP_MENU_HEIGHT);
        helpWindow.getChildren().add(backgroundImageMainScreen);

        addNodes();
        return helpWindow;
    }

    private void addNodes() {
        addTurtleViews();
    }

    private void addTurtleViews() {
        int x = 50;
        int y = 100;
        for (Turtle turtle : myTurtles.values()) {
            Image t = turtle.getImage().getImage();
            ImageView turtleImg = new ImageView(t);
            turtleImg.setFitHeight(50);
            turtleImg.setFitWidth(50);
            turtleImg.setTranslateX(x + 100);
            turtleImg.setTranslateY(y);
            turtleImg.setOnMouseClicked(e -> printTurtleAttributes(turtle, turtleImg, turtleImg.getTranslateX(), y + 80));
            helpWindow.getChildren().add(turtleImg);
            x += 100;
        }
//        HBox gameFileBox = new HBox();
//        gameFileBox.setSpacing(10);
//
//        for (Turtle turtle : myTurtles.values())
////		for(String gameFile : gallery.getUnmodifiableListOfGameFiles())
//        {
//            System.out.println("hit");
////            GameFileView newGameFileView = createGameFileView(gameFile);
//////            Node gameFileNode = createGameFileView(gameFile).getNode();
////            Node gameFileNode = newGameFileView.getNode();
//            ImageView turtleImg = turtle.getImage();
//            turtleImg.setOnMouseClicked(e -> printTurtleAttributes(turtle));
//            gameFileBox.getChildren().add(turtleImg);
//        }
//
//        setUpTurtleGalleryView(gameFileBox);
//        helpWindow.getChildren().add(galleryView);
    }

    private void setUpTurtleGalleryView(HBox gameFileBox) {
        galleryView = new ScrollPane();
        galleryView.setContent(gameFileBox);
        galleryView.setPrefViewportHeight(HELP_MENU_HEIGHT);
        galleryView.setPrefViewportWidth(HELP_MENU_WIDTH);
        galleryView.setTranslateX(50);
        galleryView.setTranslateY(50);
        galleryView.setOpacity(0.5);
        galleryView.setOnMouseEntered(e -> {
        });
        galleryView.setOnMouseExited(e -> galleryView.setOpacity(0.5));
    }

//    public void loadFile() throws FileNotFoundException {
//        Stage stage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Load Defaults");
//        File file = fileChooser.showOpenDialog(stage);
//        FileReader fr = new FileReader(file);
//        BufferedReader buffRead = new BufferedReader(fr);
//        String line = null;
//        int count = 0;
//        try {
//
//            }
//            //TODO: Actually update based on what was loaded
//            buffRead.close();
//        } catch (IOException ex) {
//            System.out.println(
//                    "Error reading file '"
//                            + file + "'");
//        }
//    }

    private void printTurtleAttributes(Turtle turtle, ImageView exTurtle, double x, double y) {
        Text turtleData = new Text("Select an image and click apply");
        turtleData.setFill(Color.WHITE);
        turtleData.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        turtleData.setTranslateX(x - 50);
        turtleData.setTranslateY(y);
        helpWindow.getChildren().add(turtleData);
        System.setProperty("glass.accessible.force", "false");
        ComboBox<String> turtleBox = new ComboBox(turtleOptions);
        turtleBox.setValue(defaultTurtle);
        turtleBox.setTranslateX(x - 50);
        turtleBox.setTranslateY(y + 30);


        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/apply.png"));
        ImageView imgV = new ImageView(newImage);
        Button play = myFactory.makeButton("Apply Changes", imgV, 20, 40);
        play.setOnMouseEntered(e -> play.setStyle(myFactory.getButtonFill()));
        play.setOnMouseClicked(e -> {
            turtle.setImage(new Image(getClass().getClassLoader()
                    .getResourceAsStream(setTurtleString(turtleBox))));
            exTurtle.setImage(new Image(getClass().getClassLoader()
                    .getResourceAsStream(setTurtleString(turtleBox))));

        });
        helpWindow.getChildren().addAll(turtleBox, play);
//        Label turtleLabel = generateLabel("Select turtle image", 125, 350);
    }

    public String setTurtleString(ComboBox turtleBox) {
        String chosenTurtle = "";
        if (turtleBox.getValue().equals("Turtle")) {
            chosenTurtle = "Images/turtle.png";
        } else if (turtleBox.getValue().equals("Drake")) {
            chosenTurtle = "Images/drake.png";
        } else if (turtleBox.getValue().equals("Heart")) {
            chosenTurtle = "Images/heart.png";
        } else if (turtleBox.getValue().equals("Young Rob")) {
            chosenTurtle = "Images/robby.png";
        } else if (turtleBox.getValue().equals("Prof. Duvall")) {

            chosenTurtle = "Images/duvall.png";
        }

        return chosenTurtle;
    }
}
