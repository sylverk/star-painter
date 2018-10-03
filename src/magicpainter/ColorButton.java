
package magicpainter;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * A subclass of button which has a Color. Its background is the same as its Color. It is 20x20px.
 * @author sylkazk @ github
 */


public class ColorButton extends Button {
    /**
     * Variable storing the color of the button as a Color.
     */
    private Color color;
    
    /**
     * Constructor for the ColorButton.
     * @param c is a string containing a color in hex code format (ie. FFFFFF)
     */
    public ColorButton(String c){
        this.color = Color.web(c); // stores the string as a Color object
        setMinSize(20,20);
        setMaxSize(20,20);
        setStyle("-fx-background-color: #" + c); // also sets the background color of the object using the string
        
    }
    
    /**
     * Standard getter
     * @return the Color of the ColorButton.
     */
    public Color getColor(){
        return color;
    }
}
