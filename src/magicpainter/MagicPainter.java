package magicpainter;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * A cute application that lets you paint pretty pictures. The only brush type
 * is a star, because it's very practical that way. You can draw stars by
 * dragging the mouse across the canvas or entering information and clicking
 * "place!" You can click the palette swatches or size buttons on the sides to
 * change the brush size and color. You can also press 1-6 on the keyboard to
 * pick a color and + or - to increase/decrease the brush size. The clear button
 * will clear the canvas without further warning, so tread carefully around it.
 *
 * @author sylverk @ github
 */
public class MagicPainter extends Application {
    
    /**
     * Image containing the logo of the application.
     */
    Image logo = new Image(this.getClass().getResourceAsStream("/magicpainter/images/logo.png"));

    /**
     * Image containing the instructions for the application.
     */
    Image infoScreen = new Image(this.getClass().getResourceAsStream("/magicpainter/images/infoscreen.png"));

    /**
     * boolean indicating if the instructions have been dismissed.
     */
    boolean infoRead = false;
    /**
     * GraphicsContext for the first canvas object which is the main drawing
     * area.
     */
    GraphicsContext gc;

    /**
     * GraphicsContext for the second canvas object which holds the logo.
     */
    GraphicsContext gc2;

    /**
     * Label providing feedback on the following actions in the application:
     * changing brush size, changing brush color, errors in input.
     */
    Label whatsUp = new Label("");

    /**
     * Textfield for user input defining the size of the star to be placed.
     */
    TextField sizeField;

    /**
     * Textfield for user input defining the x position of the star to be
     * placed.
     */
    TextField positionFieldX;

    /**
     * Textfield for user input defining the y position of the star to be
     * placed.
     */
    TextField positionFieldY;

    /**
     * double describing the size of the star to be placed.
     */
    double thisStarSize;

    /**
     * double describing the X position of the star to be placed.
     */
    double thisStarX;

    /**
     * double describing the Y position of the star to be placed.
     */
    double thisStarY;

    /**
     * double defining the current size of the brush tool.
     */
    double drawStarSize = 10;

    // EVENT HANDLERS AND HELPERS //--------------------------------------------
    /**
     * Handler that clears the canvas by storing the current brush color,
     * drawing a new blank rectangle over the canvas and then resetting the
     * brush color to the one selected by the user. Also sets a message letting
     * the user know the canvas has successfully been cleared.
     *
     * @param e
     */
    private void clearCanvas(ActionEvent e) {
        Paint oldColor = gc.getFill();
        gc.setFill(Color.rgb(255, 250, 220));
        gc.fillRect(0, 0, 600, 600);
        whatsUp.setTextFill(Color.WHITE);
        whatsUp.setText("Canvas has been cleared!");
        gc.setFill(oldColor);

    }

    /**
     * Method that sets the brush color to the color of the button that was
     * clicked. It retrieves the color using the getColor method of the
     * ColorButton class.
     *
     * @param e
     */
    private void colorHandler(ActionEvent e) {
        Color color = ((ColorButton) (e.getTarget())).getColor();
        gc.setFill(color);
        whatsUp.setText("Set new Color!");
        whatsUp.setTextFill(color);

    }

    /**
     * Method that sets the brush size to the size defined by the button that
     * was clicked. It retrieves the size using the getSize method of the
     * ImageButton.
     *
     * @param e
     */
    private void sizeHandler(ActionEvent e) {
        drawStarSize = ((ImageButton) (e.getTarget())).getSize();
        whatsUp.setTextFill(Color.web("#ffed52"));
        whatsUp.setText("Changed to size " + drawStarSize);
    }

    /**
     * Method that places a star at the user designated location. It also
     * handles potential errors from invalid inputs by changing the whatsUp
     * label to a descriptive message, and highlights the offending field in
     * pink. The star is only placed when valid input exists in all 3 fields.
     *
     * @param e
     */
    private void starPlacer(ActionEvent e) {

        if (infoRead == false) {
            Paint oldColor = gc.getFill();
            gc.setFill(Color.rgb(255, 250, 220));
            gc.fillRect(0, 0, 600, 600);
            gc.setFill(oldColor);
            infoRead = true;
        }
        
        whatsUp.setText("");
        sizeField.setStyle("-fx-background-color: #fffbda");
        positionFieldY.setStyle("-fx-background-color: #fffbda");
        positionFieldX.setStyle("-fx-background-color: #fffbda");
        whatsUp.setTextFill(Color.PINK);
        try {
            thisStarSize = Double.parseDouble(sizeField.getText());
        } catch (NumberFormatException n) {
            whatsUp.setText(whatsUp.getText() + "Size must be a number. ");
            sizeField.setStyle("-fx-background-color: #ff9cba");

        }

        try {
            thisStarX = Double.parseDouble(positionFieldX.getText());
        } catch (NumberFormatException n) {
            whatsUp.setText(whatsUp.getText() + "X-position must be a number. ");
            positionFieldX.setStyle("-fx-background-color: #ff9cba");
        }

        try {
            thisStarY = Double.parseDouble(positionFieldY.getText());
        } catch (NumberFormatException n) {
            whatsUp.setText(whatsUp.getText() + "Y-position must be a number.");
            positionFieldY.setStyle("-fx-background-color: #ff9cba");

        }

        if (whatsUp.getText().equals("")) {
            Star star = new Star(thisStarSize, thisStarX, thisStarY);
            star.draw(gc);
            whatsUp.setTextFill(Color.web("fffbda"));
            whatsUp.setText("Star Placed!!!");
        }

    }

    /**
     * Method that draws stars in the current size and color when the mouse is
     * dragged within the drawable canvas area.
     *
     * @param me
     */
    private void dragHandler(MouseEvent me) {
        if (infoRead == true) {
            Star star = new Star(drawStarSize, me.getX(), me.getY());
            star.draw(gc);
            whatsUp.requestFocus();
        }
        if (infoRead == false) {
            Paint oldColor = gc.getFill();
            gc.setFill(Color.rgb(255, 250, 220));
            gc.fillRect(0, 0, 600, 600);
            gc.setFill(oldColor);
            infoRead = true;
        }

    }

    /**
     * The main method.
     *
     * @param stage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 650);
        stage.setTitle("Magic Star Painter");
        stage.setScene(scene);

        // GUI COMPONENTS // ---------------------------------------------------
        /**
         * Creates the rectangles that make up the background of the
         * application.
         */
        Rectangle background = new Rectangle(600, 625);
        Rectangle bottomStripe = new Rectangle(600, 100);
        Rectangle bottomStripe2 = new Rectangle(600, 25);
        Rectangle canvasBackground = new Rectangle(500, 400);

        /**
         * Creates the 2 canvases used by the application, their
         * graphicsContexts,and sets the initial brush color to white.
         */
        Canvas canvas2 = new Canvas(600, 159);
        Canvas canvas = new Canvas(500, 400);
        gc2 = canvas2.getGraphicsContext2D();
        gc2.drawImage(logo, 0, 0);
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(infoScreen, 0, 0);
        gc.setFill(Color.WHITE);

        /**
         * Create the labels for the application.
         */
        Label sizeLabel = new Label("Size:");
        Label positionLabel = new Label("Position:");

        /**
         * Create the textfields used by the application.
         */
        sizeField = new TextField("#");
        positionFieldX = new TextField("X");
        positionFieldY = new TextField("Y");

        /**
         * Create the ColorButton objects in the appropriate colors.
         */
        ColorButton colorWhite = new ColorButton("ffffff");
        ColorButton colorBlue = new ColorButton("8aeaff");
        ColorButton colorGreen = new ColorButton("91fd7c");
        ColorButton colorRed = new ColorButton("ff9cba");
        ColorButton colorOrange = new ColorButton("ffbb19");
        ColorButton colorPurple = new ColorButton("b79aff");

        /**
         * Create the ImageButton objects in the appropriate sizes.
         */
        ImageButton sizeOne = new ImageButton("1", 10);
        ImageButton sizeTwo = new ImageButton("2", 20);
        ImageButton sizeThree = new ImageButton("3", 40);
        ImageButton sizeFour = new ImageButton("4", 80);
        ImageButton sizeFive = new ImageButton("5", 160);
        ImageButton sizeSix = new ImageButton("6", 320);

        /**
         * create buttons for the application.
         */
        Button placeStar = new Button("Place!");
        Button clearAll = new Button("Clear All!");

        // ADD TO ROOT // ------------------------------------------------------
        root.getChildren().addAll(background, bottomStripe, bottomStripe2, canvasBackground, canvas, canvas2);
        root.getChildren().addAll(sizeLabel, positionLabel, whatsUp);
        root.getChildren().addAll(sizeField, positionFieldX, positionFieldY);
        root.getChildren().addAll(colorWhite, colorBlue, colorGreen, colorRed, colorOrange, colorPurple);
        root.getChildren().addAll(sizeOne, sizeTwo, sizeThree, sizeFour, sizeFive, sizeSix);
        root.getChildren().addAll(placeStar, clearAll);

        // CONFIGURE COMPONENTS // ---------------------------------------------
        background.setFill(Color.rgb(75, 75, 190));
        background.setLayoutX(0);
        background.setLayoutY(0);

        bottomStripe.setFill(Color.rgb(50, 50, 160));
        bottomStripe.setLayoutX(0);
        bottomStripe.setLayoutY(543);

        bottomStripe2.setFill(Color.rgb(25, 25, 125));
        bottomStripe2.setLayoutX(0);
        bottomStripe2.setLayoutY(625);

        canvasBackground.setFill(Color.rgb(255, 250, 220));
        canvasBackground.setLayoutX(50);
        canvasBackground.setLayoutY(50);

        canvas2.setLayoutX(0);
        canvas2.setLayoutY(415);
        canvas.setLayoutX(50);
        canvas.setLayoutY(50);

        whatsUp.setLayoutX(50);
        whatsUp.setLayoutY(630);
        whatsUp.setFont(Font.font("Verdana", 10));
        whatsUp.setPrefWidth(500);
        whatsUp.setAlignment(Pos.CENTER); // Keeps the label centered.

        sizeLabel.setTextFill(Color.rgb(255, 230, 80));
        sizeLabel.setLayoutX(50);
        sizeLabel.setLayoutY(600);

        positionLabel.setTextFill(Color.rgb(255, 230, 80));
        positionLabel.setLayoutX(180);
        positionLabel.setLayoutY(600);

        sizeField.setStyle("-fx-background-color: #fffbda");
        sizeField.setLayoutX(100);
        sizeField.setLayoutY(597);
        sizeField.setMaxSize(50, 20);
        sizeField.setMinSize(50, 20);
        sizeField.setFont(Font.font("Verdana", 9));

        positionFieldX.setStyle("-fx-background-color: #fffbda");
        positionFieldX.setLayoutX(250);
        positionFieldX.setLayoutY(597);
        positionFieldX.setMaxSize(50, 20);
        positionFieldX.setMinSize(50, 20);
        positionFieldX.setFont(Font.font("Verdana", 9));

        positionFieldY.setStyle("-fx-background-color: #fffbda");
        positionFieldY.setLayoutX(310);
        positionFieldY.setLayoutY(597);
        positionFieldY.setMaxSize(50, 20);
        positionFieldY.setMinSize(50, 20);
        positionFieldY.setFont(Font.font("Verdana", 9));

        colorWhite.setLayoutX(15);
        colorWhite.setLayoutY(280);

        colorRed.setLayoutX(15);
        colorRed.setLayoutY(310);

        colorOrange.setLayoutX(15);
        colorOrange.setLayoutY(340);

        colorGreen.setLayoutX(15);
        colorGreen.setLayoutY(370);

        colorBlue.setLayoutX(15);
        colorBlue.setLayoutY(400);

        colorPurple.setLayoutX(15);
        colorPurple.setLayoutY(430);

        sizeOne.setLayoutX(560);
        sizeOne.setLayoutY(50);

        sizeTwo.setLayoutX(560);
        sizeTwo.setLayoutY(90);

        sizeThree.setLayoutX(560);
        sizeThree.setLayoutY(130);

        sizeFour.setLayoutX(560);
        sizeFour.setLayoutY(170);

        sizeFive.setLayoutX(560);
        sizeFive.setLayoutY(210);

        sizeSix.setLayoutX(560);
        sizeSix.setLayoutY(250);

        placeStar.setLayoutX(380);
        placeStar.setLayoutY(597);
        placeStar.setMinSize(60, 20);
        placeStar.setMaxSize(60, 20);
        placeStar.setStyle("-fx-background-color: #ffed52");
        placeStar.setFont(Font.font("Verdana", 11));

        clearAll.setLayoutX(470);
        clearAll.setLayoutY(597);
        clearAll.setMinSize(80, 20);
        clearAll.setMaxSize(80, 20);
        clearAll.setStyle("-fx-background-color: #ffed52");
        clearAll.setFont(Font.font("Verdana", 11));

        // Event handlers //----------------------------------------------------
        colorWhite.setOnAction(this::colorHandler);
        colorRed.setOnAction(this::colorHandler);
        colorOrange.setOnAction(this::colorHandler);
        colorGreen.setOnAction(this::colorHandler);
        colorBlue.setOnAction(this::colorHandler);
        colorPurple.setOnAction(this::colorHandler);

        sizeOne.setOnAction(this::sizeHandler);
        sizeTwo.setOnAction(this::sizeHandler);
        sizeThree.setOnAction(this::sizeHandler);
        sizeFour.setOnAction(this::sizeHandler);
        sizeFive.setOnAction(this::sizeHandler);
        sizeSix.setOnAction(this::sizeHandler);

        placeStar.setOnAction(this::starPlacer);
        clearAll.setOnAction(this::clearCanvas);

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::dragHandler);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT1) {
                gc.setFill(colorWhite.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorWhite.getColor());
            }
            if (e.getCode() == KeyCode.DIGIT2) {
                gc.setFill(colorRed.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorRed.getColor());
            }
            if (e.getCode() == KeyCode.DIGIT3) {
                gc.setFill(colorOrange.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorOrange.getColor());
            }
            if (e.getCode() == KeyCode.DIGIT4) {
                gc.setFill(colorGreen.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorGreen.getColor());
            }
            if (e.getCode() == KeyCode.DIGIT5) {
                gc.setFill(colorBlue.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorBlue.getColor());
            }
            if (e.getCode() == KeyCode.DIGIT6) {
                gc.setFill(colorPurple.getColor());
                whatsUp.setText("Set new Color!");
                whatsUp.setTextFill(colorPurple.getColor());
            }
            if (e.getCode() == KeyCode.EQUALS) {
                if (drawStarSize == sizeSix.getSize()) {
                    drawStarSize = sizeFive.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush reduced to " + drawStarSize);
                } else if (drawStarSize == sizeFive.getSize()) {
                    drawStarSize = sizeFour.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush reduced to " + drawStarSize);
                } else if (drawStarSize == sizeFour.getSize()) {
                    drawStarSize = sizeThree.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush reduced to " + drawStarSize);
                } else if (drawStarSize == sizeThree.getSize()) {
                    drawStarSize = sizeTwo.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush reduced to " + drawStarSize);
                } else if (drawStarSize == sizeTwo.getSize()) {
                    drawStarSize = sizeOne.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush reduced to " + drawStarSize);
                } else {
                    whatsUp.setTextFill(Color.PINK);
                    whatsUp.setText("Brush can't get smaller!");
                }
            }

            if (e.getCode() == KeyCode.MINUS) {
                if (drawStarSize == sizeOne.getSize()) {
                    drawStarSize = sizeTwo.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush increased to " + drawStarSize);
                } else if (drawStarSize == sizeTwo.getSize()) {
                    drawStarSize = sizeThree.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush increased to " + drawStarSize);
                } else if (drawStarSize == sizeThree.getSize()) {
                    drawStarSize = sizeFour.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush increased to " + drawStarSize);
                } else if (drawStarSize == sizeFour.getSize()) {
                    drawStarSize = sizeFive.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush increased to " + drawStarSize);
                } else if (drawStarSize == sizeFive.getSize()) {
                    drawStarSize = sizeSix.getSize();
                    whatsUp.setTextFill(Color.WHITE);
                    whatsUp.setText("Brush increased to " + drawStarSize);
                } else {
                    whatsUp.setTextFill(Color.PINK);
                    whatsUp.setText("Brush can't get bigger!");
                }

            }
        });

        // SHOW STAGE!! WOO//
        stage.show();
    }

    /**
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
