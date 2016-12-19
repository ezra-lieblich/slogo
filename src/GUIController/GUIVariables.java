package GUIController;
import BackEndInterface.CommandParser;
import Base.NodeFactory;
import FrontEndExternalAPI.Variables;
import GUI.VariablesHelp;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.stream.Collectors;

/**
 * Created by Delia on 10/15/2016.
 */
public class GUIVariables implements Variables {
    private static final int BACKDROP_X = 10;
    private static final int BACKDROP_Y = 110;
    private static final int BACKDROP_WIDTH = 600;
    private static final int BACKDROP_HEIGHT = 230;
    private static final double DEFAULT_OPACITY = 0.5;
    private static final double MOUSE_OVER_OPACITY = 0.8;
    private static final double COLUMN_WIDTH = 250;
    private Pane window;
    private Paint border;
    private Rectangle backdrop;
    private TableView table = new TableView();
    private TextField addVariableName, addVariableValue;
    private TableColumn variableNameCol, valueCol;
    private ObservableList<Variable> data = FXCollections.observableArrayList();
    private VariablesHelp helpWindow;
    private NodeFactory myFactory = new NodeFactory();
    private CommandParser myVariableSetter;

    /** This is the constructor. It sets items that must be passed in from GUIManager
     * and creates all of the nodes present within the Variables box.
     * @param p
     * @param borderColor
     */
    public GUIVariables(Pane p, Paint borderColor) {
        this.window = p;
        this.border = borderColor;
        drawVariables();
        addTextLabel();
        addHelpButton();
        createTableView();
        addVariableManually();
        addClearButton();
    }

    @Override
    public void setVariableSetter(CommandParser variableSetter) {
        myVariableSetter = variableSetter;
    }

    private void drawVariables() {
        backdrop = myFactory.makeBackdrop(border, BACKDROP_WIDTH, BACKDROP_HEIGHT, BACKDROP_X, BACKDROP_Y);
        window.getChildren().add(backdrop);
    }

    private void addTextLabel() {
        Text label = myFactory.makeTitle("Declared Variables", BACKDROP_X + 10, BACKDROP_Y + 20);
        label.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(MOUSE_OVER_OPACITY));
        window.getChildren().add(label);
    }

    private void addHelpButton() {
        ImageView helpButton = myFactory.makeHelpButton(
                backdrop.getTranslateX() + backdrop.getWidth() - 35,
                backdrop.getTranslateY() + 10);
        helpButton.setOnMouseClicked(e -> helpHandler());
        helpButton.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(MOUSE_OVER_OPACITY));
        window.getChildren().add(helpButton);
    }

    private void helpHandler() {
        Stage s = new Stage();
        helpWindow = new VariablesHelp(s);
        helpWindow.init();
    }

    private void createTableView() {
        createTableColumns();
        table.setTranslateX(BACKDROP_X + 10);
        table.setTranslateY(BACKDROP_Y + 30);
        table.setPrefSize(BACKDROP_WIDTH - 30, BACKDROP_HEIGHT - 40);
        table.opacityProperty().setValue(DEFAULT_OPACITY);
        table.setItems(data);
        table.setOnMouseEntered(e -> {
            table.opacityProperty().setValue(MOUSE_OVER_OPACITY);
            backdrop.opacityProperty().setValue(MOUSE_OVER_OPACITY);
        });
        table.setOnMouseExited(e -> table.opacityProperty().setValue(DEFAULT_OPACITY));
        window.getChildren().add(table);
    }

    private void createTableColumns(){
        variableNameCol = generateColumn("Variable Name");
        variableNameCol.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableName"));
        variableNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Variable, String>>() {
                    @Override
                    public void handle(CellEditEvent<Variable, String> t) {
                        ((Variable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVariableName(t.getNewValue());
                    }
                });
        valueCol = generateColumn("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<Variable, Double>("variableValue"));
        valueCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Variable, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Variable, Double> t) {
                        ((Variable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVariableValue(t.getNewValue());
                    }
                });
        table.getColumns().addAll(variableNameCol, valueCol);
    }

    private TableColumn generateColumn(String title){
        TableColumn newCol = new TableColumn(title);
        newCol.setPrefWidth(COLUMN_WIDTH);
        newCol.setEditable(true);
        return newCol;
    }

    @Override
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
            data.add(new Variable(name, value));
        }
        table.setItems(data);
        table.setEditable(true);
    }

    private void addVariableManually() {
        addVariableName = myFactory.makeTextField(
                "Enter variable name", variableNameCol.getPrefWidth() - 30,
                BACKDROP_X + 40, BACKDROP_Y + 200);
        addVariableValue = myFactory.makeTextField(
                "Enter variable value", valueCol.getPrefWidth() - 30,
                BACKDROP_X + 290, BACKDROP_Y + 200);
        Image newImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Images/add.png"));
        ImageView addImg = new ImageView(newImage);
        Button addButton = myFactory.makeButton("Add", addImg, BACKDROP_X + 510, BACKDROP_Y + 200);
        addButton.setOnMouseEntered(e -> {
            addButton.setStyle(myFactory.getButtonFill());
            backdrop.opacityProperty().setValue(MOUSE_OVER_OPACITY);
        });
        addButton.setOnAction(e -> addButtonHandler());
        table.setEditable(true);
        window.getChildren().addAll(addVariableName, addVariableValue, addButton);
    }

    private void addButtonHandler(){
        addVariable(addVariableName.getText(), Double.parseDouble(addVariableValue.getText()));
        String command = "make :" + addVariableName.getText() + " " + addVariableValue.getText();
        String[] commands = new String[1];
        commands[0] = command;
        myVariableSetter.executeCommands(commands);
        addVariableName.clear();
        addVariableValue.clear();
    }

    private void addClearButton() {
        Button clear = myFactory.makeClearButton(backdrop.getTranslateX() + 200, backdrop.getTranslateY());
        clear.setOnMouseEntered(e -> {
            clear.setStyle(myFactory.getButtonFill());
            backdrop.opacityProperty().setValue(MOUSE_OVER_OPACITY);
        });
        clear.setOnMouseClicked(e -> {
            data.removeAll(data);
            table.setItems(data);
        });
        window.getChildren().add(clear);
    }

    @Override
    public void setMap(ObservableMap<? extends String, ? extends Double> map) {
        data.clear();
        data.addAll(map.keySet().stream().map(variable ->
                new Variable(variable, map.get(variable)))
                .collect(Collectors.toList()));
        table.setItems(data);
    }

    /** Nested class Variable.java helps GUIVariables.java organize the data
     * contained within the TableView.
     */
    public static class Variable {
        private final SimpleStringProperty variableName;
        private final SimpleDoubleProperty variableValue;

        /**Constructor sets initial properties
         * @param variableName
         * @param variableValue
         */
        private Variable(String variableName, double variableValue) {
            this.variableName = new SimpleStringProperty(variableName);
            this.variableValue = new SimpleDoubleProperty(variableValue);
        }

        /** Returns the name of the variable, like x for x = 10.
         * @return
         */
        public String getVariableName() {
            return variableName.get();
        }

        /** Sets name of the variable
         * @param fName
         */
        public void setVariableName(String fName) {
            variableName.set(fName);
        }

        /**Returns value of the variable, like 10 for x = 10.
         * @return
         */
        public double getVariableValue() {
            return variableValue.get();
        }

        /**Sets value of the variable.
         * @param fName
         */
        public void setVariableValue(double fName) {
            variableValue.set(fName);
        }
    }
}