package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Tetris extends Application {

    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int MESH[][] = new int[XMAX / SIZE][YMAX / SIZE];

    private static Pane group = new Pane();
    private static Form object;
    private static Scene scene = new Scene(group, XMAX + 150, YMAX);
    public static int score = 0;
    public static int time = 0;
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect();
    private static int linesNo = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (int i = 0; i < MESH.length; i++) {
            int[] a = MESH[i];
            Arrays.fill(a, 0);
        }

        // creating score and level text
        Line line = new Line(XMAX, YMAX, XMAX, 0);
        Text scoreText = new Text("Score: ");
        scoreText.setStyle("-fx-font: 20 arials;");
        scoreText.setX(XMAX + 5);
        scoreText.setY(50);
        Text lines = new Text("Lines: ");
        lines.setStyle("-fx-font: 20 arials;");
        lines.setX(XMAX + 5);
        lines.setY(100);

        group.getChildren().addAll(scoreText, lines, line);

        // Creating first block
        Form a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPressed(a);
        object = a;
        nextObj = Controller.makeRect();
        primaryStage.setScene(scene);
        primaryStage.setTitle("TETRIS");
        primaryStage.show();

        // Timer
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0) {
                time++;
            } else {
                time = 0;
            }
            // for the 2 seconds the block is on the top
            if (time == 2) {
                Text over = new Text("Game over");
                over.setFill(Color.RED);
                over.setStyle("-fx-font: 70 arial;");
                over.setY(250);
                over.setX(10);
                group.getChildren().add(over);
                game = false;
            }
            if (time == 15) {
                System.exit(0);
            }

            if (game) {
                moveDown(object);
                scoreText.setText("Score: " + score);
                lines.setText("Lines: " + linesNo);
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void moveOnKeyPressed(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        Controller.moveRight(form);
                        break;
                    case LEFT:
                        Controller.moveLeft(form);
                        break;
                    case UP:
                        moveTurn(form);
                        break;
                    case DOWN:
                        moveDown(form);
                        score++;
                        break;
                }
            }
        });
    }

    private void moveTurn(Form form) {
        int f = form.form;
        Rectangle a = form.a;
        Rectangle b = form.b;
        Rectangle c = form.c;
        Rectangle d = form.d;
        switch (form.getName()) { // TODO make functions to move with coordinates
            case "j":
                if (f == 1 && checkBoundries(a, 1, 2) && checkBoundries(b, 0, 1) && checkBoundries(c, 0, -1)
                        && checkBoundries(d, -1, 0)) {
                    moveRight(a);
                    moveDown(a);
                    moveDown(a);
                    moveDown(b);
                    moveUp(c);
                    moveLeft(d);
                    form.changeForm();
                } else if (f == 2 && checkBoundries(a, -1, 0) && checkBoundries(b, 0, -1) && checkBoundries(c, 2, -1)
                        && checkBoundries(d, 1, -2)) {
                    moveLeft(a);
                    moveUp(b);
                    moveRight(c);
                    moveRight(c);
                    moveUp(c);
                    moveRight(d);
                    moveUp(d);
                    moveUp(d);
                    form.changeForm();
                } else if (f == 3 && checkBoundries(a, -1, -1) && checkBoundries(c, 0, 2) && checkBoundries(d, 1, 1)) {
                    moveLeft(a);
                    moveUp(a);
                    moveDown(c);
                    moveDown(c);
                    moveRight(d);
                    moveDown(d);
                    form.changeForm();
                } else if (checkBoundries(a, 1, -1) && checkBoundries(d, -1, 1) && checkBoundries(c, -2, 0)) {
                    moveRight(a);
                    moveUp(a);
                    moveLeft(d);
                    moveDown(d);
                    moveLeft(c);
                    moveLeft(c);
                    form.changeForm();
                }
                break;
            case "l":
                if (f == 1 && checkBoundries(a, -1, 0) && checkBoundries(b, 0, 1) && checkBoundries(c, 2, 1)
                        && checkBoundries(d, 1, 2)) {
                    moveLeft(a);
                    moveDown(b);
                    moveRight(c);
                    moveRight(c);
                    moveDown(c);
                    moveRight(d);
                    moveDown(d);
                    moveDown(d);
                    form.changeForm();
                } else if (f == 2 && checkBoundries(a, 1, -2) && checkBoundries(b, 0, -1) && checkBoundries(c, 0, 1)
                        && checkBoundries(d, -1, 0)) {
                    moveRight(a);
                    moveUp(a);
                    moveUp(a);
                    moveUp(b);
                    moveDown(c);
                    moveLeft(d);
                    form.changeForm();
                } else if (f == 3 && checkBoundries(a, 1, 1) && checkBoundries(c, -2, 0) && checkBoundries(d, -1, -1)) {
                    moveRight(a);
                    moveDown(a);
                    moveLeft(c);
                    moveLeft(c);
                    moveLeft(d);
                    moveUp(d);
                    form.changeForm();
                } else if(checkBoundries(a, -1, 1) && checkBoundries(c, 0, -2) &&
                checkBoundries(d, 1, -1)){
                    moveDown(a);
                    moveLeft(a);
                    moveUp(c);
                    moveUp(c);
                    moveRight(d);
                    moveUp(d);
                    form.changeForm();
                }
                break;
            case "o":
                break;
            case "s":
                break;
            case "t":
                break;
            case "z":
                break;
            case "i":
                break;
        }

    }

    private boolean checkBoundries(Rectangle rect, int x, int y) {
        boolean xok = false;
        boolean yok = false;

        if (rect.getX() + MOVE * x + SIZE <= XMAX && rect.getX() + MOVE * x >= 0) {
            xok = true;
        }
        if (rect.getY() + MOVE * y + SIZE <= YMAX || rect.getY() + MOVE * y >= 0) {
            yok = true;
        }
        return xok && yok && MESH[(int) (rect.getX() / SIZE + x)][(int) (rect.getY() / SIZE + y)] == 0;


    }

    private void moveRect(Rectangle rect, int x, int y) {
        rect.setX(rect.getX() + MOVE);
        rect.setY(rect.getY() - MOVE);
    }

    private void moveRight(Rectangle rect) {
        rect.setX(rect.getX() + MOVE);
    }

    private void moveLeft(Rectangle rect) {
        rect.setX(rect.getX() - MOVE);
    }

    private void moveUp(Rectangle rect) {
        rect.setY(rect.getY() - MOVE);
    }

    private void moveDown(Rectangle rect) {
        rect.setY(rect.getY() + MOVE);
    }

    private void moveDown(Form form) {
        // if the block touched other, or if it reached till the end, stop it, set mesh field to 1, remove rows if necessary, get next object
        if (form.a.getY() + MOVE == YMAX || form.b.getY() + MOVE == YMAX || form.c.getY() + MOVE == YMAX || form.d.getY() + MOVE == YMAX
                || touchingOtherBlock(form)) {
            MESH[(int) (form.a.getX() / SIZE)][(int) (form.a.getY() / SIZE)] = 1;
            MESH[(int) (form.b.getX() / SIZE)][(int) (form.b.getY() / SIZE)] = 1;
            MESH[(int) (form.c.getX() / SIZE)][(int) (form.c.getY() / SIZE)] = 1;
            MESH[(int) (form.d.getX() / SIZE)][(int) (form.d.getY() / SIZE)] = 1;

            removeRows(group);
            Form a = nextObj;
            nextObj = Controller.makeRect();
            object = a;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);
            moveOnKeyPressed(a);
        }
        // moving the block down
        if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX && form.d.getY() + MOVE < YMAX
                && !touchingOtherBlock(form)) {
            form.a.setY(form.a.getY() + MOVE);
            form.b.setY(form.b.getY() + MOVE);
            form.c.setY(form.c.getY() + MOVE);
            form.d.setY(form.d.getY() + MOVE);
        }

    }

    private void removeRows(Pane group) {

        List<Integer> rowsToRemoveIndex = new ArrayList<>();
        List<Node> blocks = new ArrayList<>();
        List<Node> newBlocks = new ArrayList<>();

        for (int y = 0; y < YMAX / SIZE; y++) {
            int numberInRow = 0;
            for (int x = 0; x < XMAX / SIZE; x++) {
                if (MESH[x][y] == 1) {
                    numberInRow++;
                }
            }
            if (numberInRow == XMAX / SIZE) {
                rowsToRemoveIndex.add(y);
            }
        }

        score += rowsToRemoveIndex.size() * 50;
        linesNo += rowsToRemoveIndex.size();
        // TODO Try to improve this part
        while (rowsToRemoveIndex.size() > 0) {
            // get all blocks
            for (Node node : group.getChildren()) {
                if (node instanceof Rectangle) {
                    blocks.add(node);
                }
            }
            // for current row to delete remove nodes
            for (Node node : blocks) {
                Rectangle rect = (Rectangle) node;
                if (rect.getY() / SIZE == rowsToRemoveIndex.get(0)) {
                    MESH[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] = 0;
                    group.getChildren().remove(rect);
                } else {
                    newBlocks.add(node);
                }
            }

            for (Node node : newBlocks) {
                Rectangle rect = (Rectangle) node;
                if (rect.getY() / SIZE < rowsToRemoveIndex.get(0)) {
                    MESH[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] = 0;
                    rect.setY(rect.getY() + SIZE);
                }
            }

            blocks.clear();
            newBlocks.clear();
            rowsToRemoveIndex.remove(0);

            for (Node node : group.getChildren()) {
                if (node instanceof Rectangle) blocks.add(node);
            }

            for (Node node : blocks) {
                Rectangle rect = (Rectangle) node;
                try {
                    MESH[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] = 1;
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
            }
            blocks.clear();

        }

        score += rowsToRemoveIndex.size() * 50;
        linesNo += rowsToRemoveIndex.size();
    }


    private boolean touchingOtherBlock(Form form) {
        return MESH[(int) (form.a.getX() / SIZE)][(int) (form.a.getY() / SIZE + 1)] == 1 ||
                MESH[(int) (form.b.getX() / SIZE)][(int) (form.b.getY() / SIZE + 1)] == 1 ||
                MESH[(int) (form.c.getX() / SIZE)][(int) (form.c.getY() / SIZE + 1)] == 1 ||
                MESH[(int) (form.d.getX() / SIZE)][(int) (form.d.getY() / SIZE + 1)] == 1;
    }
}
