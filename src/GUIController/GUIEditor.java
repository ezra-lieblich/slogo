//This entire file is part of my masterpiece.
//DELIA LI

package GUIController;
import Base.NodeFactory;
import FrontEndExternalAPI.Editor;
import GUI.EditorHelp;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the GUIEditor class in the GUIController package.
 * This class controls the inputting of commands by allowing the user to type directly into a text area or open a file.
 * The GUIEditor class contains a small caret "> " at the beginning of each command after the use clicks the "Run" button.
 * This lets the user know how which commands will be parsed the next time "Run" is clicked. Additionally, the "Clear"
 * button lets users clear the text area at any time. The "Open file" button lets users choose a file from their own
 * directory to run as a set of Slogo commands. The "Save file" button lets users to save what currently exists in the
 * text area as a text file in their own directory. The question mark provides useful tips to users about how to use the
 * editor.
 *
 * I think this class represents good design because it is significantly refactored and contains no magic values that
 * still should be refactored. It is simple and very easy to use, both for the developer and the user. I handle errors
 * in try/catch blocks like we learned in class to do, providing helpful statements should any of those errors happen,
 * in a way that is accessible to the user. It demonstrates that I have learned how to navigate a TextArea object and
 * style it in a way such that commands look like they're appearing on a command line in other editors. 
 *
 * Created by Delia on 10/15/2016.
 */
public class GUIEditor implements Editor {
    private static final int BACKDROP_X = 10;
    private static final int BACKDROP_Y = 350;
    private static final int BACKDROP_WIDTH = 600;
    private static final int BACKDROP_HEIGHT = 240;
    private static final double OPACITY_DEFAULT = 0.5;
    private static final double OPACITY_MOUSE_OVER = 0.8;
    private Pane window;
    private Paint border;
    private Stage myStage;
    private Rectangle backdrop;
    private TextArea textArea;
    private ImageView helpButton;
    private Image newImage;
    private String caret = "> ";
    private String defaultCommand = "Enter command here";
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private NodeFactory myFactory = new NodeFactory();
    private EditorHelp helpWindow;

    /**
     * This is the constructor that sets up the designated section of the 
     * GUI as the editor, where the user can input code and run it. 
     * @param p
     * @param borderColor
     */
    public GUIEditor(Pane p, Paint borderColor, Stage stage) {
        this.window = p;
        this.border = borderColor;
        this.myStage = stage;
        drawEditor();
        addTextLabel();
        addTextArea();
        addHelpButton();
        addClearButton();
        addSaveFileButton();
        addOpenFileButton();
    }

    private void drawEditor() {
        backdrop = myFactory.makeBackdrop(border, BACKDROP_WIDTH, BACKDROP_HEIGHT, BACKDROP_X, BACKDROP_Y);
        window.getChildren().add(backdrop);
    }

    private void addTextLabel() {
        Text label = myFactory.makeTitle("Editor", BACKDROP_X + 10, BACKDROP_Y + 20);
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER));
        window.getChildren().add(label);
    }

    private void addTextArea() {
        textArea = new TextArea();
        textArea.setTranslateX(BACKDROP_X + 10);
        textArea.setTranslateY(BACKDROP_Y + 40);
        textArea.setPrefSize(BACKDROP_WIDTH - 30, BACKDROP_HEIGHT - 50);
        textArea.setWrapText(true);
        textArea.setText(caret + defaultCommand);
        textArea.positionCaret(textArea.getText().length());
        textArea.setOnMouseClicked(e -> {
            if (textArea.getText().equals(caret + defaultCommand))
                textArea.setText(caret);
            textArea.positionCaret(textArea.getText().length());
        });
        textArea.opacityProperty().setValue(OPACITY_DEFAULT);
        textArea.setOnMouseEntered(e -> {
            backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER);
            textArea.opacityProperty().setValue(OPACITY_MOUSE_OVER);
        });
        textArea.setOnMouseExited(e -> textArea.opacityProperty().setValue(OPACITY_DEFAULT));
        window.getChildren().add(textArea);
    }

    private void addHelpButton() {
        helpButton = myFactory.makeHelpButton(backdrop.getTranslateX() + backdrop.getWidth() - 35,
                backdrop.getTranslateY() + 10);
        helpButton.setOnMouseClicked(e -> helpHandler());
        helpButton.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER));
        window.getChildren().add(helpButton);
    }

    private void helpHandler() {
        Stage s = new Stage();
        helpWindow = new EditorHelp(s);
        helpWindow.init();
    }

    private void addClearButton(){
        Button clear = myFactory.makeClearButton(BACKDROP_X + 180, BACKDROP_Y);
        clear.setOnMouseEntered(e -> {
            clear.setStyle(myFactory.getButtonFill());
            backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER);
        });
        clear.setOnMouseClicked(e -> textArea.setText(caret + defaultCommand));
        window.getChildren().add(clear);
    }

    private void addSaveFileButton(){
        newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/save.png"));
        ImageView clearImg = new ImageView(newImage);
        Button saveFileButton = myFactory.makeButton("Save file", clearImg, BACKDROP_X + 450, BACKDROP_Y);
        saveFileButton.setOnMouseEntered(e -> {
            saveFileButton.setStyle(myFactory.getButtonFill());
            backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER);
        });
        saveFileButton.setOnMouseClicked(e -> saveFile());
        window.getChildren().add(saveFileButton);
    }

    /**
     * Saves a file of Logo commands
     *
     * @author Robert Steilberg
     */
    private void saveFile() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("LOGO files (*.logo)", "*.logo");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showSaveDialog(myStage);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(getCurrentText().substring(2));
            fileWriter.close();
        } catch (IOException e) {
            generateAlert("IOException", "You have encountered an IOException.");
        } catch (NullPointerException e){
            generateAlert("NullPointerException", "You have not saved the file.");
        }
    }

    private void addOpenFileButton(){
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/open.png"));
        ImageView clearImg = new ImageView(newImage);
        Button openFileButton = myFactory.makeButton("Open file", clearImg, BACKDROP_X + 330, BACKDROP_Y);
        openFileButton.setOnMouseEntered(e -> {
            openFileButton.setStyle(myFactory.getButtonFill());
            backdrop.opacityProperty().setValue(OPACITY_MOUSE_OVER);
        });
        openFileButton.setOnMouseClicked(e -> openFile());

        window.getChildren().add(openFileButton);
    }

    private void openFile() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(myStage);
        if (textArea.getText().equals(caret + defaultCommand)) {
            textArea.setText(caret);
        }
        try {
            Scanner s = new Scanner(file).useDelimiter("\n");
            while (s.hasNext()) {
                textArea.appendText(s.next() + " \n"); // else read the next token
            }
        } catch (FileNotFoundException ex) {
            generateAlert("FileNotFoundException", "This file may not exist. Try another directory.");
        } catch (NullPointerException e) {
            generateAlert("NullPointerException", "You have not opened a file.");
        }
    }

    private void generateAlert(String title, String content){
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public Rectangle getBackdrop() {
        return backdrop;
    }

    @Override
    public void startNewCommand() {
        textArea.setText(textArea.getText() + "\n" + caret);
    }

    @Override
    public String getCurrentText() {
        return textArea.getText();
    }

    @Override
    public void redoCommand(String str) {
        textArea.setText(textArea.getText() + "\n" + caret + str);
    }
}
