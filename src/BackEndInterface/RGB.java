package BackEndInterface;

/**
 * Class for Palette Command. Creates a simple class so the frontend can easily access the value when this is
 * passed from the backend to the frontend
 * @author ezra
 */
public class RGB {
	private static double red;
	private static double green;
	private static double blue;
	
	public RGB(double r, double g, double b) {
		red = checkBounds(r);
		green = checkBounds(g);
		blue = checkBounds(b);
	}
	
	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}
	
	/**
	 * Checks if the value specified is in bounds and sets it to default value if it violates limit. Otherwise sets it
	 * @param value
	 * @return 
	 */
	private double checkBounds(double value) {
		double answer = value;
		if (answer < 0 || answer > 256) {
			//default value if it is out of bounds
			answer = 128;
		}
		return answer;
	}
}
