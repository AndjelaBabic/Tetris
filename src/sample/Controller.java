package sample;

import javafx.scene.shape.Rectangle;

public class Controller {

    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;
    public static int XMAX = Tetris.XMAX;
    public static int YMAX = Tetris.YMAX;
    public static int[][] MESH = Tetris.MESH;

    public static void moveRight(Form form) {
        if (form.a.getX() + SIZE + MOVE <= XMAX && form.b.getX() + SIZE + MOVE <= XMAX &&
                form.c.getX() + SIZE + MOVE <= XMAX && form.d.getX() + SIZE + MOVE <= XMAX) {

            int movea = MESH[(int) (form.a.getX() / SIZE + 1)][(int) form.a.getY() / SIZE];
            int moveb = MESH[(int) (form.b.getX() / SIZE + 1)][(int) form.b.getY() / SIZE];
            int movec = MESH[(int) (form.c.getX() / SIZE + 1)][(int) form.c.getY() / SIZE];
            int moved = MESH[(int) (form.d.getX() / SIZE + 1)][(int) form.d.getY() / SIZE];
            if (movea == 0 && moveb == 0 && movec == 0 && moved == 0) {
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    public static void moveLeft(Form form) {
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 &&
                form.c.getX() - MOVE >= 0 && form.d.getX() - MOVE >= 0) {

            int movea = MESH[(int) (form.a.getX() / SIZE - 1)][(int) form.a.getY() / SIZE];
            int moveb = MESH[(int) (form.b.getX() / SIZE - 1)][(int) form.b.getY() / SIZE];
            int movec = MESH[(int) (form.c.getX() / SIZE - 1)][(int) form.c.getY() / SIZE];
            int moved = MESH[(int) (form.d.getX() / SIZE - 1)][(int) form.d.getY() / SIZE];

            if (movea == 0 && moveb == 0 && movec == 0 && moved == 0) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    public static Form makeRect() {
        // random color
        int randomNumber = (int) (Math.random() * 100);
        String name;

        Rectangle a = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle b = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle c = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle d = new Rectangle(SIZE - 1, SIZE - 1);

        String color;
        if (randomNumber < 100) {
            color = "j";
            a.setX(XMAX / 2);
            b.setX(XMAX / 2);
            b.setY(SIZE);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE * 2);
            d.setX(XMAX / 2);
            d.setY(SIZE * 2);
        } else if (randomNumber < 30) {
            color = "l";
            a.setX(XMAX / 2);
            a.setY(SIZE * 2);
            b.setX(XMAX / 2);
            b.setY(SIZE);
            c.setX(XMAX / 2 - SIZE);
            d.setX(XMAX / 2);
        } else if (randomNumber < 45) {
            // square
            color = "o";
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
        } else if (randomNumber < 60) {
            color = "s";
            a.setX(XMAX / 2);
            b.setX(XMAX / 2 + SIZE);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
        } else if (randomNumber < 75) {
            // raketa
            color = "t";
            a.setX(XMAX / 2 - SIZE);
            a.setY(SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE); // i ovo
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
        } else if (randomNumber < 90) {
            // raketa
            color = "z";
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
        } else {
            color = "i";
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            c.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
        }
        return new Form(a, b, c, d, color);
    }
}
