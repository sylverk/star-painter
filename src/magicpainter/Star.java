package magicpainter;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author sylverk @ github
 */
public class Star {

    /**
     * double describing the size of the star from the left tip to the right
     * tip.
     */
    double size;
    /**
     * double describing the x-position of the star from the center of the star.
     */
    double posX;
    /**
     * double describing the y-position of the star from slightly above the
     * center of the star.
     */
    double posY;
    /**
     * array of doubles describing the x-positions of the vertices of the star.
     */
    double[] pointsX;
    /**
     * array of doubles describing the y-positions of the vertices of the star.
     */
    double[] pointsY;

    /**
     * Constructor for a star object.
     *
     * @param size is the size of the star from leftmost point to rightmost
     * point.
     * @param posX is the X-position of the star from the center of the star.
     * @param posY is the Y-position of the star from slightly above the center
     * of the star.
     */
    public Star(double size, double posX, double posY) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        // calculates the x positions of the star vertices based on the inputs
        this.pointsX = new double[]{
            posX,
            posX + (size / 5),
            posX + (size / 2),
            posX + (size / 3.33),
            posX + (size / 2.5),
            posX,
            posX - (size / 2.5),
            posX - (size / 3.33),
            posX - (size / 2),
            posX - (size / 5)
        };
        // calculates the y positions of the star vertices based on the inputs
        this.pointsY = new double[]{
            posY - (size / 2.2),
            posY - (size / 10),
            posY - (size / 10),
            posY + (size / 5),
            posY + (size / 2),
            posY + (size / 3.33),
            posY + (size / 2),
            posY + (size / 5),
            posY - (size / 10),
            posY - (size / 10)
        };

    }

    /**
     * Standard getter.
     *
     * @return double representing the x-position of the center of the star.
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Standard getter.
     *
     * @return double representing the y-position of slightly above the center
     * of the star.
     */
    public double getPosY() {
        return posY;
    }

    /**
     * draw method for the star object using the fillPolygon method.
     *
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        gc.fillPolygon(pointsX, pointsY, 10);
    }

}
