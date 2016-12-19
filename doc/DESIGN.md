#SLogo DESIGN.md

##Project team members:
* Robert Steilberg | rhs16
* Delia Li | dl202
* Ezria Lieblich | eml36
* Grayson Wise | gkw

##High-level design goals
* Our project is divided into 11 packages. The BackEndCommands package contains all of the Command objects that represent Logo commands.
The BackEndInterface contains classes that the front end will interact with when using the back end. The BackEndInterpreter package contains
all the classes used to build and execute a parse tree made of inputted Logo commands. The Base package is a front end package used for
creating JavaFX nodes and other JavaFX elements. The FrontEndExternalAPI, FrontEndInternalAPI, GUI, and GUIController packages all contain
classes for creating the front end that interfaces with the back end. Specifically, the GUI package contains classes dealing with help menus.
The GUIController package is the overall package that handles the GUI and its initialization. The front end and back end interface work together
through the BackEndInterface package that has the CommandParser class. The CommandParser class is initialized once for each workspace and
will subsequently accept Logo commands to interpret and reflect back to the front end. The only data exchanged between the front and back end
is done through the executeCommands() method of CommandParser within the BackEndInterface package, and through observable properties that are
initialized with CommandParser and used to reflect turtle and display changes between the front and back end. Finally, the GUI front end
classes depend on the images within the Images package, and the BackEndInterpreter package depends on the properties files in the resources
package that define regular expression definitions, class locations, or other settings.
* One of our high-level design goals for the back end was to make it simple such that there would be a single method called by the front end
to execute a series of commands. This design choice is reflected in the buildAndExecuteTree() method.
* For the front end, a high-level design goal was to standardize the styling of every node and make the creation of new features very easy.
Because of this, a number of superclasses and helper classes were built to facilitate the proliferation of features in a way that would be
flexible, aesthetically pleasing, less of a hindrance to the developer, and barely noticeable in terms of quality change to the user.

##Adding new features to the project
* To add a new command to the project, a new class for the command must be created that implements the overall Command class (or one of the
sub-classes like ControlCommand or TurtleCommand, depending on the type of the new command). Then, it is only necessary to store the path to
that class in the ClassLocations.properties properties file so that reflection will know exactly where the new command class is located when
it is to be called. More complex commands, like those that interrupt the control flow of the program, may necessitate further manipulation of
the ParseTreeBuilder class, but the actions associated with the new command need only be coded in the executeCommand() method of the new
command class for the tree to correctly build and execute a parse tree using those commands. The number of arguments that the command expects
should also be hard-coded in the new command class.
* As aforementioned, adding a new front end feature to the project is very easy through the standardization of styles and nodes found in
places such as the NodeFactory.java class and various other superclasses like the OptionsMenu.

##Design choices
1. Commands in separate classes executed through a parse tree
    * Originally, we had a separate design where the tree was traversed node by node and executed recursively. However, we decided it would
    be better design to simply store each subtree within nodes so that the nodes could be executed by calling executeCommand() within each
    Command object and passing in the node with its subtrees as arguments. The benefit of the design choice we made is that the tree no
    longer requires a separate class for execution. Execution is simple; it is only necessary to execute the head of the tree, and the rest
    of the tree will be executed as a result. Adding new commands is also very simple since only a new Command object need be created for them.
2. Separate observable classes
    * We decided to create separate observable classes to encapsulate all of the observable properties used to communicate with the front end.
    We did this so that we could keep all front end - back end communication in one place so that it would be easy to extend or modify any
    of the existing observable properties. We considered not using observable properties and simply finding some other way to pass changes
    back up to the front end, possibly through return statements, but decided a separate class would be a much easier way of implementing
    communication. However, this meant creating a possibly bloated class with lots of properties, some of which were completely unrelated to
    each other. Nevertheless, we decided that this was an acceptable tradeoff, since we knew we would be extending the turtle commands in the
    future. Thus, I prefer the current way we decided to implement this functionality.
3. Nested classes in the front end
    * A number of crucial front end classes have nested classes of their own, especially if those classes require back communication from the
    nested class. In GUIVariables.java, creating the TableView required a nested Variable class. In GUIManager.java, a lot of important
    decision-making buttons for user preference were located in the ButtonMenu class, a nested class that could send information back to
    GUIManager the moment something was clicked. In GUIDisplay.java, the OptionsMenu class is nested for the same reason.
4. Helper classes in the front end
    * The Turtle.java and NodeFactory.java classes contribute a lot of flexibility and organization to the classes in the front end. By
    using NodeFactory to standardize most repeatedly created nodes across the many classes that share them, nearly a hundred lines of
    duplicated code were removed. The Turtle.java class made creating multiple turtles and changing user preferences in the display very easy.

##Assumptions or decisions made

* To resolve issues with commands issued over multiple lines (i.e. a list that spans multiple lines), we decided to sanitize inputted commands after being issued from the GUI such that they are all placed on one line and executed sequentially. To achieve this, we put all of the commands in one big list, thus assuming that the user will be satisfied from always getting a single return value after pressing the "run" button.

