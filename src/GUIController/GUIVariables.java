package GUIController;

import BackEndExternalAPI.CommandParser;
import FrontEndExternalAPI.Variables;
import GUI.VariablesHelp;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIVariables implements Variables {
    private Pane window;
    private Paint border;
    private Rectangle backdrop;
    private VariablesHelp helpWindow;
    private TableView table = new TableView();
    private TableColumn variableNameCol, valueCol;
    private ObservableList<Variable> data = FXCollections.observableArrayList();
    private String overButton = "-fx-background-color: linear-gradient(#0079b3, #00110e);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String buttonFill = "-fx-background-color: linear-gradient(#00110e, #0079b3);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private String textFieldFill = "-fx-background-color: linear-gradient(#00110e, #4d66cc);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
    private CommandParser myVariableSetter;

    /**
     * @param p
     * @param bodercolor
     */
    public GUIVariables(Pane p, Paint bodercolor) {
        this.window = p;
        this.border = bodercolor;
        drawVariables();
        addTextLabel();
        addHelpButton();
        createTableView();
        addVariableManually();
        addClearButton();
    }

    public void setVariableSetter(CommandParser variableSetter) {
        myVariableSetter = variableSetter;
    }

    private void drawVariables() {
        backdrop = new Rectangle(600, 230, Color.WHITE);
        backdrop.setStroke(border);
        backdrop.setStrokeWidth(5);
        backdrop.setTranslateY(110);
        backdrop.setTranslateX(10);
        backdrop.opacityProperty().setValue(0.5);
        backdrop.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        backdrop.setOnMouseExited(e -> backdrop.opacityProperty().setValue(0.5));
        window.getChildren().add(backdrop);
    }

    private void addTextLabel() {
        Text label = new Text("Declared Variables");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        label.setTranslateX(20);
        label.setTranslateY(130);
        window.getChildren().add(label);
    }

    private void addHelpButton() {
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/help.png"));
        ImageView helpButton = new ImageView(newImage);
        helpButton.setOnMouseClicked(e -> helpHandler());
        helpButton.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        helpButton.setTranslateX(backdrop.getTranslateX() + backdrop.getWidth() - 35);
        helpButton.setTranslateY(backdrop.getTranslateY() + 10);
        helpButton.setFitWidth(30);
        helpButton.setFitHeight(30);
        window.getChildren().add(helpButton);
    }

    private void helpHandler() {
        Stage s = new Stage();
        helpWindow = new VariablesHelp(s);
        helpWindow.init();
    }

    private void createTableView() {
        //WHY CANT I GET THIS TO BE FUCKING EDITABLE  TODO remove expletive before submitting
        variableNameCol = new TableColumn("Variable Name");
        variableNameCol.setPrefWidth(250);
        variableNameCol.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableName"));
        variableNameCol.setEditable(true);
        variableNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Variable, String>>() {
                    @Override
                    public void handle(CellEditEvent<Variable, String> t) {
                        ((Variable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVariableName(t.getNewValue());
                    }
                }
        );
        valueCol = new TableColumn("Value");
        valueCol.setPrefWidth(250);
        valueCol.setCellValueFactory(
                new PropertyValueFactory<Variable, Double>("variableValue"));
        valueCol.setEditable(true);
        valueCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Variable, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Variable, Double> t) {
                        ((Variable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVariableValue(t.getNewValue());
                    }
                }
        );
        table.getColumns().addAll(variableNameCol, valueCol);
        table.setTranslateX(20);
        table.setTranslateY(140);
        table.setPrefSize(570, 190);
        table.opacityProperty().setValue(0.5);
        table.setItems(data);
        table.setOnMouseEntered(e -> {
            table.opacityProperty().setValue(0.8);
            backdrop.opacityProperty().setValue(0.8);
        });
        table.setOnMouseExited(e -> table.opacityProperty().setValue(0.5));
        window.getChildren().add(table);
    }

    @Override
    /**
     *
     */
    public void addVariable(String name, double value) {

        boolean contains = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getVariableName().equals(name.substring(1))) {
                contains = true;
                data.get(i).setVariableValue(value);
                break;
            }
        }
        if (!contains) {
            data.add(new Variable(name.substring(1), value));
        }
        table.setItems(data);

        table.setEditable(true);
    }

    private void addVariableManually() {
        final TextField addVariableName = new TextField();
        addVariableName.setPromptText("Enter variable name");
        addVariableName.setMaxWidth(variableNameCol.getPrefWidth() - 30);
        addVariableName.setTranslateX(50);
        addVariableName.setTranslateY(310);
        addVariableName.setStyle(textFieldFill);
        final TextField addVariableVal = new TextField();
        addVariableVal.setMaxWidth(valueCol.getPrefWidth() - 30);
        addVariableVal.setPromptText("Enter variable value");
        addVariableVal.setTranslateX(300);
        addVariableVal.setTranslateY(310);
        addVariableVal.setStyle(textFieldFill);

        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/add.png"));
        ImageView addImg = new ImageView(newImage);
        final Button addButton = newButton("Add", addImg, 520, 310);

        addButton.setOnAction(e -> {
            data.add(new Variable(addVariableName.getText(),
                    Double.parseDouble(addVariableVal.getText())));
            String command = "make " + addVariableName.getText() + " " + addVariableVal.getText();
            String[] commands = new String[1];
            commands[0] = command;
            myVariableSetter.executeCommands(commands);
            addVariableName.clear();
            addVariableVal.clear();
        });

        table.setEditable(true);
        window.getChildren().addAll(addVariableName, addVariableVal, addButton);



    }

    private void addClearButton() {
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/clear.png"));
        ImageView clearImg = new ImageView(newImage);
        Button clear = newButton("Clear", clearImg, (int) backdrop.getTranslateX() + 200, (int) backdrop.getTranslateY());
        clear.setOnMouseClicked(e -> data.clear());
        window.getChildren().add(clear);
    }

    private Button newButton(String text, ImageView imgV, int x, int y) {
        imgV.setFitWidth(25);
        imgV.setFitHeight(25);
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


    @Override
    /**
     *
     */
    public ArrayList<Integer> getAllVariables() {
//        return Arrays.asList(data.toArray());
        return null;
    }

    public void setMap(ObservableMap<? extends String, ? extends Double> map) {
        data.clear();
        data.addAll(map.keySet().stream().map(variable ->
                new Variable(variable, map.get(variable)))
                .collect(Collectors.toList()));
        table.setItems(data);
    }


    /**
     *
     */
    public static class Variable {

        private final SimpleStringProperty variableName;
        private final SimpleDoubleProperty variableValue;
//        private final SimpleStringProperty email;

        /**
         * @param vName
         * @param vValue
         */
        private Variable(String vName, double vValue) {
            this.variableName = new SimpleStringProperty(vName);
            this.variableValue = new SimpleDoubleProperty(vValue);
//            this.email = new SimpleStringProperty(email);
        }

        /**
         * @return
         */
        public String getVariableName() {
            return variableName.get();
        }

        /**
         * @param fName
         */
        public void setVariableName(String fName) {
            variableName.set(fName);
        }

        /**
         * @return
         */
        public double getVariableValue() {
            return variableValue.get();
        }

        /**
         * @param fName
         */
        public void setVariableValue(double fName) {
            variableValue.set(fName);
        }
    }
}
