package GUI;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Delia on 10/23/2016.
 */
public class VariablesHelp extends HelpMenu {
    private String instructions = "Start by entering a command in the editor that " +
            "\n\tdeclares a variable. A command like \n\t\t\t> make :x 10 " +
            "\n\tsets the variable x equal to 10."+
            "\nTo add a variable manually or edit an existing variable, " +
            "\n\ttype it in the text fields and click \"Add\"." +
            "\nPress the Clear button in the Variables box to clear the table.";

    /**
     *
     * @param s
     */
    public VariablesHelp(Stage s) {
        super(s);
    }

    /**
     *
     */
    public void addNodes(){
        getWindow().getChildren().add(addRectangle());
        getWindow().getChildren().add(addTip(instructions, 110, 110));
    }

    /**
     *
     * @param text
     * @param x
     * @param y
     * @return
     */
    public Text addTip(String text, int x, int y){
        Text instructionText = new Text(text);
        instructionText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        instructionText.setFill(Color.WHITE);
        instructionText.setTranslateX(x);
        instructionText.setTranslateY(y);
        return instructionText;
    }
}
