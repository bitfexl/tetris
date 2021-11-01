package window;

import tetris.board.GraphicsInterface;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Board extends Canvas {
    // tetris game colors
    public static final Color COLOR_BG = new Color(44, 62, 80);
    public static final Color COLOR_LINES = new Color(52, 73, 94);
    public static final Color COLOR_ORANGE = new Color(255, 159, 26);
    public static final Color COLOR_RED = new Color(255, 56, 56);
    public static final Color COLOR_GREEN = new Color(39, 174, 96);
    public static final Color COLOR_BLUE = new Color(41, 128, 185);
    public static final Color COLOR_PURPLE = new Color(155, 89, 182);
    public static final Color COLOR_YELLOW = new Color(254, 202, 87);
    public static final Color COLOR_LIGHT_BLUE = new Color(72, 219, 251);

    // tetris grid size
    // private final int X_SQUARES = 10, Y_SQUARES = 20;
    // now from graphicsInterface

    private final GraphicsInterface tetrisBoard;

    public final int width, height;

    /**
     * A drawable Tetris board Canvas. Width and height should be selected so that: width % 10 == 1; height % 20 == 1; width / 10 == height / 20;
     * @param width Width of the Canvas.
     * @param height Height of the Canvas.
     * @param board The graphicsInterface to use. Updates every time repaint() is called.
     */
    public Board(int width, int height, GraphicsInterface board) {
        tetrisBoard = board;
        this.width = width;
        this.height = height;

        // let canvas fill the whole width and height
        this.setSize(width, height);
        Dimension wh = new Dimension(width, height);
        this.setPreferredSize(wh);
    }

    // TODO: check before updating all (only necessary)

    /**
     * Enable buffering. Call this to update.
     */
    public void draw() {
        // from: https://stackoverflow.com/questions/17770830/java-buffer-strategy-learning#17771010
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        drawBoard(g);
        g.dispose();

        bs.show();
    }

    /**
     * Draw the Tetris board on to the canvas. Called by draw().
     * @param g The graphics object to draw on.
     */
    private void drawBoard(Graphics g) {
        // set background Color
        g.setColor(COLOR_BG);
        g.fillRect(0, 0, width, height);

        // draw pieces

        g.setColor(COLOR_YELLOW);
        drawTetrisBoard(g, tetrisBoard.oPieces);

        g.setColor(COLOR_PURPLE);
        drawTetrisBoard(g, tetrisBoard.tPieces);

        g.setColor(COLOR_LIGHT_BLUE);
        drawTetrisBoard(g, tetrisBoard.iPieces);

        g.setColor(COLOR_BLUE);
        drawTetrisBoard(g, tetrisBoard.jPieces);

        g.setColor(COLOR_ORANGE);
        drawTetrisBoard(g, tetrisBoard.lPieces);

        g.setColor(COLOR_GREEN);
        drawTetrisBoard(g, tetrisBoard.sPieces);

        g.setColor(COLOR_RED);
        drawTetrisBoard(g, tetrisBoard.zPieces);

        // draw lines
        g.setColor(COLOR_LINES);
        // The width and height of all boards in tetrisBoard should be the same.
        drawGrid(g, tetrisBoard.oPieces.width, tetrisBoard.oPieces.height);
    }

    /**
     * Draws a tetris board (specific piece type) from tetrisBoard.
     * @param g The canvas to draw on.
     * @param board The board to draw.
     */
    private void drawTetrisBoard(Graphics g, tetris.board.Board board) {
        for(int y=0; y<board.height; y++) {
            for(int x=0; x<board.width; x++) {
                if(board.testSquare(x, y)) {
                    drawBlock(g, board.width, board.height, x, y);
                }
            }
        }
    }

    /**
     * Draws a block at the given x and y cords.
     * @param g The graphics object to draw on.
     * @param xSquares The amount of squares in x direction.
     * @param ySquares The amount of squares in y direction.
     * @param x The x cord of the block.
     * @param y The y cord of the block.
     */
    public void drawBlock(Graphics g, int xSquares, int ySquares, int x, int y) {
        int xUnit = (width / xSquares);
        int yUnit = (height / ySquares);

        g.fillRect(x*xUnit, y*yUnit, xUnit, yUnit);
    }

    /**
     * Draws a grid on the given graphics object.
     * @param g The graphics object to draw on.
     * @param x The amount of squares in x direction.
     * @param y The amount of squares in y direction.
     */
    public void drawGrid(Graphics g, int x, int y) {
        // calculate square width and height
        // width (and height) must be width % x == 1 or the last line won't be drawn
        int xUnit = (width / x);
        int yUnit = (height / y);

        int i;
        // vertical lines
        for(i=0; i<x+1; i++) {
            g.drawLine(xUnit * i, 0, xUnit * i, height);
        }

        // horizontal lines
        for(i=0; i<y+1; i++) {
            g.drawLine(0, yUnit*i, width, yUnit*i);
        }
    }
}
