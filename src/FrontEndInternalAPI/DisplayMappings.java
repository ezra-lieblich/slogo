package FrontEndInternalAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Contains the mappings for Display Commands that backend specifies and the front end accesses to map it properly
 * @author ezra
 *
 */
public class DisplayMappings {
	private List<Color> penColorMappings;
	private List<Image> turtleImageMappings;
	private List<Color> backgroundImageMappings;
	
	private static ResourceBundle resources;

	
	/**
	 * Populates the maps and uses the ImageMappings.properties to get corresponding values
	 */
	public DisplayMappings() {
		resources =  ResourceBundle.getBundle("resources/internal/ImageMappings");
		penColorMappings = setupColors();
		backgroundImageMappings = setupColors();
		setupTurtleImage();
		setupBackgroundImage();
	}

	private void setupBackgroundImage() {
		
	}

	private void setupTurtleImage() {
		turtleImageMappings = new ArrayList<Image>();		
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image0"))));
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image1"))));
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image2"))));
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image3"))));
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image4"))));
		turtleImageMappings.add(new Image(getClass().getClassLoader()
											.getResourceAsStream(resources.getString("image5"))));
	}

	private List<Color> setupColors() {
		List<Color> list = new ArrayList<Color>();
		list.add(Color.BLACK);
		list.add(Color.RED);
		list.add(Color.BLUE);
		list.add(Color.GREEN);
		list.add(Color.PINK);
		return list;
	}
	
	/**
	 * This gets the pen color in use in the GUI. 
	 * @param i
	 * @return
	 */
	public Color getPenColor(int i) {
		if(i >= penColorMappings.size()) {
			return penColorMappings.get(0);
		}
		else {
			return penColorMappings.get(i);
		}
	}
	
	/**
	 * This gets the turtle image in use in the GUI. 
	 * @param i
	 * @return
	 */
	public Image getTurtleImage(int i) {
		if(i >= turtleImageMappings.size()) {
			return turtleImageMappings.get(0);
		}
		else {
			return turtleImageMappings.get(i);	
		}
	}
	
	/**
	 * This gets the background color in use in the GUI. 
	 * @param i
	 * @return
	 */
	public Color getBackgroundColor(int i) {
		if(i >= backgroundImageMappings.size()) {
			return backgroundImageMappings.get(0);
		}
		else {
			return backgroundImageMappings.get(i);
		}
	}
}
