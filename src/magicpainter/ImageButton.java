package magicpainter;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * Subclass of Button which has a double describing the size of the star brush.
 * It also sets the background of the button to an image describing the scale of
 * the star brush. It is 30x30.
 *
 * @author sylverk @ github
 */
public class ImageButton extends Button {

    /**
     * Double describing the size of the star brush (the size of the star from
     * left tip to right tip)
     */
    private double size;

    /**
     * Constructor for the ImageButton.
     *
     * @param img is a String that indicates which button it is and lets it load
     * the appropriate image, it can be "1" to "6" inclusive for this program.
     * @param size is a double that indicates the size of the brush in pixels.
     */
    public ImageButton(String img, double size) {

        this.size = size;
        Image resource = new Image(this.getClass().getResourceAsStream("/magicpainter/images/starsize" +img + ".png"));
        setStyle("-fx-background-color: #3232a0; -fx-background-image: url('/magicpainter/images/starsize" + img + ".png')");
        setMinSize(30, 30);
        setMaxSize(30, 30);

    }

    /**
     * standard getter.
     * @return double representing the size
     */
    public double getSize() {
        return size;
    }

}
