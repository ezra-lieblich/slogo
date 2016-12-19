package BackEndInterpreter;

import BackEndInterface.RGB;
import GUIController.GUIDisplay;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Properties class that contains properties for the GUI Display to update with. Calls methods in GUIDisplay
 * and is accessed by the DisplayCommands
 * @author ezra
 */
public class DisplayProperties {
	private DoubleProperty penSize;
	private DoubleProperty penColor;
	private DoubleProperty imageIndex;
	private DoubleProperty backgroundImage;
	private ObjectProperty<RGB> paletteIndex;
	private DoubleProperty stamp;
	private DoubleProperty clearStamp;
	
	private double stampCount;
	
	public DisplayProperties(GUIDisplay display) {
		imageIndex = new SimpleDoubleProperty(0);
		imageIndex.addListener((observable, oldValue, newValue) -> display.changeImage((Double) newValue));
		penColor = new SimpleDoubleProperty(0);
		penColor.addListener((observable, oldValue, newValue) -> display.changePenColor((Double) newValue));
		penSize = new SimpleDoubleProperty(0);
		penSize.addListener((observable, oldValue, newValue) -> display.setPenSize((Double) newValue));
		backgroundImage = new SimpleDoubleProperty(0);
		backgroundImage.addListener((observable, oldValue, newValue) -> display.changePalette((Double)newValue));
		paletteIndex = new SimpleObjectProperty<RGB>();
		paletteIndex.addListener((observable, oldValue, newValue) -> display.changePaletteRGB(newValue));
		stamp = new SimpleDoubleProperty(0);
		stamp.addListener((observable, oldValue, newValue) -> display.stamp(imageIndex.get()));
		clearStamp = new SimpleDoubleProperty(0);
		clearStamp.addListener((observable, oldValue, newValue) -> display.clearStamps());
		stampCount = 0;
		
	}
	
	public double getPenColor() {
		return penColor.get();
	}
	
	/**
	 * 
	 * @param value pen color index to set in the front end 
	 */
	public void setPenColor(double value) {
		penColor.set(value);
	}
	
	/**
	 * 
	 * @param value The new pen width size
	 */
	public void setPenSize(double value) {
		penSize.set(value);
	}
	
	/**
	 * 
	 * @return The image shape index
	 */
	public double getImageIndex() {
		return imageIndex.get();
	}
	
	/**
	 * 
	 * @param value The new shape index
	 */
	public void setImageIndex(double value) {
		imageIndex.set(value);
	}
	
	/**
	 * 
	 * @param value The new background color index
	 */
	public void setBackgroundImage(double value) {
		backgroundImage.set(value);
	}
	
	/**
	 * Creates a new palette for the background color to be set to
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPallete(double r, double g, double b) {
		RGB palette = new RGB(r, g, b);
		paletteIndex = new SimpleObjectProperty<>(palette);
	}
	
	public double addStamp() {
		stampCount = 1;
		stamp.set(stamp.get()+1);
		return imageIndex.get();
	}
	
	public double clearStamp() {
		clearStamp.set(clearStamp.get() + 1);
		double answer = stampCount;
		stampCount  = 0;
		return answer;
	}
}