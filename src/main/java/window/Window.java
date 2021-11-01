package window;

import tetris.board.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Window {
    // colors
    private static final Color COLOR_WIN_BG = new Color(236, 240, 241);

    // width, height; TODO: fix width and height to be inner width and height
    private final int WIDTH = 310, HEIGHT = 580;
    // +1 for last grid line
    private final int BOARD_WIDTH = 251, BOARD_HEIGHT = 501;

    // the main window
    private final JFrame window;

    // draw area
    private Board board;

    // line counter
    private JLabel score;

    public Window(GraphicsInterface gi, InputCb inputCallback) {
        this("Window", gi, inputCallback);
    }

    public Window(String name, GraphicsInterface gi, InputCb inputCallback) {
        // create window
        window = initWindow(name);

        // add board
        board = initBoard(gi);
        window.add(board);

        // add line counter
        score = new JLabel("test");
        score.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        window.add(score);
        setScore(0);

        // set up refresh of canvas
        setUpRefresh();

        // setup key event listener
        window.addKeyListener(new InputListener(inputCallback));
    }

    /**
     * Sets the displayed line count.
     * @param count The new score (line count).
     */
    public void setScore(int count) {
        score.setText("Lines cleared: " + count);
    }

    /**
     * Calls board.draw() periodically
     */
    private void setUpRefresh() {
        Thread refresh = new Thread(() -> {
            while(true) {
                board.draw();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {

                }
            }
        });
        refresh.start();
    }

    private JFrame initWindow(String name) {
        // create window
        JFrame window = new JFrame(name);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set size (todo: fix for inner size)
        window.setSize(WIDTH, HEIGHT);

        // layout stuff
        window.setLayout(new FlowLayout());
        window.setResizable(false);

        window.setBackground(COLOR_WIN_BG);

        // open
        window.setVisible(true);

        return window;
    }

    private Board initBoard(GraphicsInterface gi) {
        Board newBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, gi);
        // if focused inputs cannot be read
        newBoard.setFocusable(false);
        return newBoard;
    }
}