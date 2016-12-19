package FrontEndExternalAPI;

import BackEndInterface.CommandParser;
import javafx.collections.ObservableMap;

/**
 *  This is the GUIVariables class in the GUIController package.
 *  This class controls the representation of variables in a table format and allows
 *  users to add new variables and edit existing ones.
 *  Users can type in the textfields and click the "Add" button to manually input
 *  variables. They can also do it by declaring a variable in the Editor.
 *  Both methods of adding will also modify variables if they already exist under
 *  that name.
 *  For the TableView object, a Variable class was created to generate the correct
 *  values in the columns.
 *
 * @author Delia
 */
public interface Variables {

    /**
     *Sets connection with the back end CommandParser, which sends important
     * variable data over to this class.
     * @param variableSetter
     */
    void setVariableSetter(CommandParser variableSetter);

    /**
     *Adds a new variable to the TableView as a new row.
     * Creats the variable object with the name and value before adding it to
     * the chart.
     * @param name
     * @param value
     */
    void addVariable(String name, double value);

    /**
     *Creates mappings for the Variables feature.
     * @param map
     */
    void setMap(ObservableMap<? extends String, ? extends Double> map);
}