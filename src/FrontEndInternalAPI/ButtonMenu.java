package FrontEndInternalAPI;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Delia
 */
public interface ButtonMenu {
//    public ButtonMenuk();

    public Button newButton(String text, ImageView img, int x, int y);

    public void addButtons();
}