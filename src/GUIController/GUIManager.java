package GUIController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import BackEndCommands.TurtleCommands.SetXY;
import BackEndInterface.CommandParser;
import BackEndInterpreter.DisplayProperties;
import BackEndInterpreter.ObservableComposite;
import Base.NodeFactory;
import FrontEndExternalAPI.Manager;
import FrontEndInternalAPI.ButtonMenu;
import GUI.HelpMenu;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * Created by Delia on 10/15/2016.
 */
public class GUIManager implements Manager {
    private static final int IDE_WIDTH = 1400;
    private static final int IDE_HEIGHT = 800;
    private ImageView background, turtle;
    private Stage stage;
    private Scene myWindow;
    private Pane window;
    private Line line;
    private Color penColor;
    private GUIConsole myConsole;
    private GUIEditor myEditor;
    private GUIHistory myHistory;
    private GUIVariables myVariables;
    private GUIDisplay myDisplay;
    private GUIButtonMenu myButtonMenu;
    private NodeFactory myFactory = new NodeFactory();
    private CommandParser commandParser;
    private ObservableComposite turtleProperties;
    private DisplayProperties displayProperties;
    private SimpleStringProperty myLanguage;
    private String backgroundStr, turtleStr, language;
    /**
     * @param penColor
     * @param background
     * @param turtle
     * @param language
     */
    public GUIManager(Color penColor, String background, String turtle, String language, Line lineType) {
        this.penColor = penColor;
        this.backgroundStr = background;
        this.turtleStr = turtle;
        Image newImg = new Image(getClass().getClassLoader()
                .getResourceAsStream(background));
        ImageView backgroundImageIDE = new ImageView(newImg);
        backgroundImageIDE.setFitWidth(IDE_WIDTH);
        backgroundImageIDE.setFitHeight(IDE_HEIGHT);
        this.background = backgroundImageIDE;
        newImg = new Image(getClass().getClassLoader()
                .getResourceAsStream(turtle));
        ImageView turtleImageIDE = new ImageView(newImg);
        this.turtle = turtleImageIDE;
        this.language = language;
        myLanguage = new SimpleStringProperty(language);
        this.line = lineType;
    }
    
    @Override
    public void init() {
        stage = new Stage();
        stage.setTitle("Slogo");
        Scene myScene = new Scene(setUpWindow());
        myWindow = myScene;
        stage.setScene(myWindow);
        turtleProperties = setupBindings();
        displayProperties = new DisplayProperties(myDisplay);
        commandParser = new CommandParser(myLanguage, turtleProperties, displayProperties, myVariables);
        myVariables.setVariableSetter(commandParser);
        SetXY fd = new SetXY();
        fd.setProperties(turtleProperties);
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(50.0);
        list.add(-75.0);
        stage.setScene(myScene);
        stage.show();
    }
    private Parent setUpWindow() {
        window = new Pane();
        window.setPrefSize(IDE_WIDTH, IDE_HEIGHT);
        window.getChildren().add(background);
        myConsole = new GUIConsole(window, penColor);
        myEditor = new GUIEditor(window, penColor, stage);
        myHistory = new GUIHistory(window, penColor);
        myVariables = new GUIVariables(window, penColor);
        myButtonMenu = new GUIButtonMenu(window, penColor);
        myDisplay = new GUIDisplay(window, turtle, penColor, line);
        addRunButton();
        addLoadButton();
        addMoreTurtlesField();
        setSizeBindings();
        return window;
    }
    private void setSizeBindings() {
        background.fitWidthProperty().bind(window.widthProperty());
        background.fitHeightProperty().bind(window.heightProperty());
        myDisplay.bindNodes(window.widthProperty());
        myConsole.getBackdrop().widthProperty().bind(window.widthProperty().subtract(630));
        myConsole.getBackdrop().heightProperty().bind(window.heightProperty().subtract(610));
        myConsole.bindNodes(window.widthProperty(), window.heightProperty());
        myButtonMenu.getBackdrop().widthProperty().bind(window.widthProperty().subtract(20));
        myHistory.getBackdrop().heightProperty().bind(window.heightProperty().subtract(610));
        myHistory.bindNodes(window.heightProperty());
    }
    private ObservableComposite setupBindings() {
        ObservableComposite answer = new ObservableComposite(myDisplay);
        return answer;
    }
    private void addRunButton(){
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/play.png"));
        ImageView imgV = new ImageView(newImage);
        Button run = myFactory.makeButton("Run", imgV, 80, 350);
        run.setOnMouseEntered(e -> {
            run.setStyle(myFactory.getButtonFill());
            myEditor.getBackdrop().opacityProperty().setValue(0.8);
        });
        run.setOnMouseClicked(e -> returnAction());
        window.getChildren().add(run);
    }
    private void addLoadButton() {
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/load.png"));
        ImageView imgV = new ImageView(newImage);
        Button hist = myFactory.makeButton("Load", imgV, 100, 600);
        hist.setOnMouseEntered(e -> {
            hist.setStyle(myFactory.getButtonFill());
            myHistory.getBackdrop().opacityProperty().setValue(0.8);
        });
        hist.setOnMouseClicked(e -> getAndLoadHistoryCommand());
        window.getChildren().add(hist);
    }
    private void addMoreTurtlesField() {
        TextField enterID = myFactory.makeTextField(
                "Enter a new turtle's ID", 200, IDE_WIDTH - 210, 43);
        enterID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                turtleProperties.setNewTurtle(Double.parseDouble(enterID.getText()));
            }
        });
        window.getChildren().add(enterID);
    }
    @Override
    public void returnAction() {
        String fullText = myEditor.getCurrentText();
        myEditor.startNewCommand();
        String newCommands = fullText.substring(lookForLatest(fullText));
        String[] splitCommands = newCommands.split("\n");
        for (String command : splitCommands) {
            myHistory.addCommand(command);
        }
        double result = commandParser.executeCommands(splitCommands);
        if (commandParser.getErrors().size() == 0) {
            myConsole.addConsole(Double.toString(result));
        } else {
            commandParser.getErrors().forEach(myConsole::addConsole);
        }
    }
    private void getAndLoadHistoryCommand() {
        String redoCommand = myHistory.getRedoCommand();
        myEditor.redoCommand(redoCommand);
    }
    private int lookForLatest(String fullText) {
        int startIndex = -1;
        for (int i = fullText.length() - 1; i >= 0; i--) {
            if (fullText.charAt(i) == '>') {
                startIndex = i + 2;
                break;
            }
        }
        return startIndex;
    }
    private class GUIButtonMenu implements ButtonMenu {
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
        private NodeFactory myFactory = new NodeFactory();
        /**
         * @param p
         * @param borderColor
         */
        public GUIButtonMenu(Pane p, Paint borderColor) {
            this.window = p;
            this.border = borderColor;
            drawButtonMenu();
            addTextLabel();
            addButtons();
            addComboBoxes();
        }
        private void drawButtonMenu() {
            backdrop = myFactory.makeBackdrop(border, 1580, 90, 10, 10);
            window.getChildren().add(backdrop);
        }
        private void addTextLabel() {
            Text label = myFactory.makeTitle("Options", 20, 30);
            label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
            window.getChildren().add(label);
        }
        @Override
        public void loadFile() throws FileNotFoundException {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Defaults");
            File file = fileChooser.showOpenDialog(stage);
            FileReader fr = new FileReader(file);
            BufferedReader buffRead = new BufferedReader(fr);
            String line = null;
            int count = 0;
            try {
                while ((line = buffRead.readLine()) != null) {
                    if (count == 0) {
                        languageBox.setValue(line);
                    } else if (count == 1) {
                        backgroundBox.setValue(line);
                    } else if (count == 2) {
                        //myDisplay.setPenColor(line);
                    } else if (count == 3) {
                        turtleStr = "Images/" + line;
                    }
                    count++;
                }
                //TODO: Actually update based on what was loaded
                buffRead.close();
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + file + "'");
            }
        }
        public void saveFile() {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Save Defaults");
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
            String str = new String();
            if (myDisplay.getTurtleStr() == null) {
                str = turtleStr;
            } else
                str = myDisplay.getTurtleStr();
            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(languageBox.getValue() + "\n" +
                        backgroundBox.getValue() + "\n" +
                        myDisplay.getPenColor().toString() + "\n" +
                        str.substring(7));
                fileWriter.close();
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
        }
        @Override
        public void addButtons() {
            Image newImage = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Images/help.png"));
            ImageView imgV = new ImageView(newImage);
            Button help = myFactory.makeButton("HELP", imgV, 130, 40);
            help.setOnMouseEntered(e -> {
                help.setStyle(myFactory.getButtonFill());
                backdrop.opacityProperty().setValue(0.8);
            });
            help.setOnMouseClicked(e -> helpHandler());
            newImage = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Images/save.png"));
            imgV = new ImageView(newImage);
            Button save = myFactory.makeButton("Save Defaults", imgV, 555, 40);
            save.setOnMouseEntered(e -> {
                save.setStyle(myFactory.getButtonFill());
                backdrop.opacityProperty().setValue(0.8);
            });
            save.setOnMouseClicked(e -> saveFile());
            newImage = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Images/load.png"));
            imgV = new ImageView(newImage);
            Button load = myFactory.makeButton("Load defaults", imgV, 697, 40);
            load.setOnMouseEntered(e -> {
                load.setStyle(myFactory.getButtonFill());
                backdrop.opacityProperty().setValue(0.8);
            });
            load.setOnMouseClicked(e -> {
                try {
                    loadFile();
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            newImage = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Images/apply.png"));
            imgV = new ImageView(newImage);
            Button play = myFactory.makeButton("APPLY", imgV, 20, 40);
            play.setOnMouseEntered(e -> {
                play.setStyle(myFactory.getButtonFill());
                backdrop.opacityProperty().setValue(0.8);
            });
            window.getChildren().addAll(save, load, help, play);
        }
        private void addComboBoxes() {
            System.setProperty("glass.accessible.force", "false");
            backgroundBox = new ComboBox<String>(backgroundOptions);
            backgroundBox.setValue(defaultBackground);
            backgroundBox.setTranslateX(230);
            backgroundBox.setTranslateY(45);
            backgroundBox.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
            backgroundBox.setOnMouseExited(ee -> backdrop.opacityProperty().setValue(0.5));
            backgroundBox.valueProperty().addListener((ov, oldbackground, newbackground) -> {
                String chosenBackground = "";
                switch (newbackground){
                    case "Circuits":
                        chosenBackground = "Images/background.jpg";
                        break;
                    case "Floating Cubes":
                        chosenBackground = "Images/floatingCubes.jpg";
                        break;
                    case "Nebula":
                        chosenBackground = "Images/nebula.jpg";
                        break;
                    case "Metal Sheets":
                        chosenBackground = "Images/dark-wallpaper-2.jpg";
                        break;
                    case "Spinning Screens":
                        chosenBackground = "Images/spinningScreens.jpg";
                        break;
                }
                setNewBackground(chosenBackground);
            });
            languageBox = new ComboBox<String>(languageOptions);
            languageBox.setValue(defaultLanguage);
            languageBox.setTranslateX(415);
            languageBox.setTranslateY(45);
            window.getChildren().addAll(backgroundBox, languageBox);
            languageBox.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
            languageBox.setOnMouseExited(ee -> backdrop.opacityProperty().setValue(0.5));
            languageBox.valueProperty().addListener((ov, oldLang, newLang) -> myLanguage.set(newLang));
        }
        private void setNewBackground(String newBackground){
            Image newB = new Image(getClass().getClassLoader()
                    .getResourceAsStream(newBackground));
            background.setImage(newB);
        }
        private void helpHandler() {
            myHelpMenu = new HelpMenu(s);
            myHelpMenu.init();
        }
        @Override
        public Rectangle getBackdrop() {
            return backdrop;
        }
    }
}