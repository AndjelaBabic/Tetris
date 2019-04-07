package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
        //moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect();
        primaryStage.setScene(scene);
        primaryStage.setTitle("TETRIS");
        primaryStage.show();

        // Timer
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
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
            }

        };
        timer.schedule(task, 0, 300);
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
}
