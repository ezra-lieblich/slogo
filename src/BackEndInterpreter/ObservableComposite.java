package BackEndInterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUIController.GUIDisplay;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * The composite aspect of the Design Principle. This class deals with multiple turtles and calls
 * on the Observable Properties class to perform individual turtle commands
 * @author ezra
 */
public class ObservableComposite implements ObservableManager{
	private Double currentID;
	private double turtleCount;
	private Map<Double, ObservableProperties> myTurtles;
	private List<Double> activeTurtles;
	private DoubleProperty newTurtle;
	
	/**
	 * Creates the map of individual turtles, list of active turtles
	 * @param display
	 */
	public ObservableComposite(GUIDisplay display) {

		myTurtles = new HashMap<Double, ObservableProperties>();
		activeTurtles = new ArrayList<Double>();
		newTurtle = new SimpleDoubleProperty(0);
		//Change Listener that creates a new turtle properties class if newTurtle is changed and adds to myTurtles. Returns a new ObservableProperties
		newTurtle.addListener((observable, oldValue, newValue) -> {
				ObservableProperties result = display.addTurtle((double) newValue); 
				myTurtles.put((double) newValue, result);
				turtleCount++;
				activeTurtles.add((double) newValue);
		});
		//Creates intial Turtle
		newTurtle.setValue(1.0);
	}

	/**
	 * Gets imageVisibleProperty of current active turtle
	 */
	@Override
	public boolean getImageVisibleProperty() {
		return myTurtles.get(currentID).getImageVisibleProperty();
	}

	/**
	 * Gets the rotate property of current active turtle
	 */
	@Override
	public double getRotateProperty() {
		return myTurtles.get(currentID).getRotateProperty();	
	}

	/**
	 * Gets the xCord of the current active turtle
	 */
	@Override
	public double getXProperty() {
		return myTurtles.get(currentID).getXProperty();	
	}

	/**
	 * Loops through the active turtles and sets their value to the value provided
	 */
	@Override
	public void setXProperty(double value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setXProperty(value);
		}
	}

	/**
	 * Get the yCord of the current active turtle
	 */
	@Override
	public double getYProperty() {
		return myTurtles.get(currentID).getYProperty();	
	
	}

	/**
	 * Loops through the active turtles and sets their value to the value provided
	 */
	@Override
	public void setYProperty(double value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setYProperty(value);
		}
	}

	/**
	 * Gets the PathVisibleProperty of the current active turtle
	 */
	@Override
	public boolean getPathVisibleProperty() {
		return myTurtles.get(currentID).getPathVisibleProperty();
	}
	
	/**
	 * Loops through all active turtles and calculates the total distance traveled from the current pos
	 * and the two values specified
	 * @param x
	 * @param y
	 */
	@Override
	public double calculateTotalDistance(double x, double y) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			answer = myTurtles.get(id).calculateTotalDistance(x, y);
		}
		return answer;
	}
	
	/**
	 * Loops through the all active turtles and determines xDistance traveled based off of hyp.
	 * Then sets the current turtle to its current pos plus the xDistance traveled
	 */
	@Override
	public double calculateXDistance(ParseTreeNode hyp, boolean sign) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			double xDistance = myTurtles.get(id).calculateXDistance(hyp, sign);
			double currentPos = myTurtles.get(id).getXProperty();
			myTurtles.get(id).setXProperty(currentPos + xDistance);
			answer = xDistance;
		}
		return answer;
	}
	
	/**
	 * Loops through the all active turtles and determines yDistance traveled based off of hyp.
	 * Then sets the current turtle to its current pos plus the yDistance traveled
	 */
	@Override
	public double calculateYDistance(ParseTreeNode hyp, boolean sign) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			double yDistance = myTurtles.get(id).calculateYDistance(hyp, sign);
			double currentPos = myTurtles.get(id).getYProperty();
			myTurtles.get(id).setYProperty(currentPos + yDistance);
			answer  = yDistance;
		}
		return answer;
	}
	
	/**
	 * Tells all the active turtles to create a new line
	 */
	@Override
	public void setNewLineProperty(boolean value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setNewLineProperty(value);
		}
	}

	/**
	 * Called when front end wants to create a new Turtle. Only creates if id doesn't already exist
	 * @param value id of the new Turtle
	 */
	public void setNewTurtle(double value){
		if(!myTurtles.containsKey(value)){
			newTurtle.set(value);
		}
	}

	/**
	 * Sets the clearScreenProperty to the value specified for all the active turtles
	 */
	@Override
	public void setClearScreenProperty(boolean value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setClearScreenProperty(value);
		}
	}
	
	/**
	 * Sets the ImageVisibleProperty for all the active turtles
	 */
	@Override
	public void setImageVisibleProperty(boolean value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setImageVisibleProperty(value);
		}
	}
	
	/**
	 * Sets the RotateProperty to all the active turtles
	 */
	@Override
	public double setRotateProperty(ParseTreeNode node, boolean isAbsolute, boolean sign) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			answer = myTurtles.get(id).setRotateProperty(node, isAbsolute, sign);
		}
		return answer;
	}
	
	/**
	 * Sets the PathVisibleProperty to all the active turtles
	 */
	@Override
	public void setPathVisibleProperty(boolean value) {
		for (Double id : activeTurtles) {
			currentID = id;
			myTurtles.get(id).setPathVisibleProperty(value);
		}
	}
	
	/**
	 * Loops through all the activeTurtles and calls the calculateDegrees on the individual turtles 
	 */
	@Override
	public double calculateDegrees(ParseTreeNode x, ParseTreeNode y) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			answer = myTurtles.get(id).calculateDegrees(x, y);
		}
		return answer;
	}

	/**
	 * @return Returns the ID of the current active turtle
	 */
	public double getCurrentID() {
		return currentID;
	}
	
	/**
	 * @return Returns the number of turtles created
	 */
	public double getTurtleCount() {
		return turtleCount;
	}

	/**
	 * Adds the turtles in the list to active turtles. Creates a new turtle if the id specified isnt in the list of turtles
	 * @param node the list of turtles that need to be added to the active turtles
	 * @return
	 */
	public double performTell(ParseTreeNode node) {
		activeTurtles.clear();
		double id = 1.0;
		for (ParseTreeNode turtle : node.getChild(0).getChildren()) {
			id  = turtle.executeCommand(turtle);
			if (!myTurtles.containsKey(id)) {
				newTurtle.set(id);
			}
			if (!activeTurtles.contains(id)) {
				activeTurtles.add(id);
			}
		}
		return id;
	}
	
	/**
	 * Loops through the active turtles and performs the SetXY method on the individual turtles
	 */
	@Override
	public double setXY(ParseTreeNode arg1, ParseTreeNode arg2) {
		double answer = 0;
		for (Double id : activeTurtles) {
			currentID = id;
			answer = myTurtles.get(id).setXY(arg1, arg2);
		}
		return answer;
	}
	
	/**
	 * Sets the activeTurtles to the list specified in the node and performs the commands specified in the node and resets the activeTurtles to the original list
	 * @param node The first child contains the list of children that will perform the commands specified in the nodes second child
	 * @return The value from the commandNode executed
	 */
	public double performAsk(ParseTreeNode node) {
		List<Double> oldActiveTurtles = new ArrayList<Double>(activeTurtles);
		List<ParseTreeNode> tempActiveTurtles = node.getChild(0).getChildren();
		ParseTreeNode commandNode = node.getChild(1);
		activeTurtles.clear();
		for (ParseTreeNode child : tempActiveTurtles) {
			currentID = child.executeCommand(child);
			activeTurtles.add(currentID);
		}
		double answer = commandNode.executeCommand(commandNode);
		activeTurtles = oldActiveTurtles;
		return answer;
	}
	
	/**
	 * Sets the active turtles to the turtles that satisfy the condition specified in node. Executes the commands in the node and resets the activeTurtles
	 * @param node Its first child is the condition that determines if the turtle is moved to the active list. Its second child is the command the turtle will perform
	 * @return The value of the command node
	 */
	public double performAskWith(ParseTreeNode node) {
		List<Double> oldActiveTurtles = new ArrayList<Double>(activeTurtles);
		activeTurtles.clear();
		ParseTreeNode condition = node.getChild(0);
		ParseTreeNode command = node.getChild(1);
		for (Double turtle : myTurtles.keySet()) {
			currentID = turtle;
			Double evaluated = condition.executeCommand(condition);
			if (evaluated.equals(1.0)) {
				activeTurtles.add(turtle);
				currentID = turtle;
			}
		}
		double answer = command.executeCommand(command);
		activeTurtles = oldActiveTurtles;
		return answer;
	}
}
