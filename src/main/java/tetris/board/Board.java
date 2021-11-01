package tetris.board;

import tetris.pieces.*;

import java.util.Arrays;

public class Board {
    // placed pieces false=empty; board[y][x]
    private boolean[][] board;

    // width/height of the board
    public final int width, height;

    /**
     * Initialises a board. Only blocks are stored no colors, shapes, ...
     *
     * @param width  Width (standard tetris: 10).
     * @param height Height (standard tetris: 20).
     */
    public Board(int width, int height) {
        board = new boolean[height][width];
        resetBoard();
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a copy of the given board.
     * @param board Board to copy.
     */
    public Board(Board board) {
        this.width = board.width;
        this.height = board.height;
        this.board = new boolean[height][width];
        board.copyTo(this);
    }

    /**
     * Copies the board to newBoard. Width and height must be the same.
     * @param newBoard The board where it should copy itself to.
     * @return If the board has been copied or not. (width and height were not the same).
     */
    public boolean copyTo(Board newBoard) {
        if(newBoard.width != width || newBoard.height != height) {
            return false;
        }

        newBoard.resetBoard();
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                if(board[y][x]) {
                    newBoard.setSquare(x, y);
                }
            }
        }

        return true;
    }

    /**
     * Removes given line.
     * @param line Line to remove. Must be between 0-height
     */
    public void deleteLine(int line) {
        if(line < 0 || line >= height) {
            return;
        }

        for(int y=line; y>0; y--) {
            board[y] = board[y-1];
        }
        board[0] = new boolean[width];
    }

    /**
     * Clears board.
     */
    public void resetBoard() {
        for (boolean[] row : board) {
            Arrays.fill(row, false);
        }
    }

    /**
     * Sets (places a block) a square on the board.
     *
     * @param x X cord of the block with 0,0 in the top left corner. Must be in bounds of the board.
     * @param y Y cord.
     * @return true: block has been set. false: block has already been set or x,y is greater than width,height.
     */
    public boolean setSquare(int x, int y) {
        // if square is taken
        if (testSquare(x, y)) {
            return false;
        }
        // setSquare
        board[y][x] = true;
        return true;
    }

    /**
     * Tests if square is taken.
     *
     * @param x X cord of the block with 0,0 in the top left corner. Must be in bounds of the board.
     * @param y Y cord.
     * @return true: square taken. false: square empty.
     */
    public boolean testSquare(int x, int y) {
        // if square is taken
        return board[y][x];
    }

    /**
     * Draws the given piece with the current orientation at the given coordinates.
     * WARNING: throws IndexOutOfBoundsException if orientation does not exist.
     *
     * @param x     X cord of the anchor of the piece.
     * @param y     Y cord of the anchor.
     * @param piece The piece to draw.
     * @return true: piece could be drawn; false: not;
     */
    public synchronized boolean drawPiece(int x, int y, Piece piece) {
        byte[][] block = piece.getPieceOrientations()[piece.rotation];

        if(!testPiece(x, y, piece)) {
            return false;
        }

        // test successful -> draw piece
        for (byte[] cord : block) {
            setSquare(x + cord[0], y + cord[1]);
        }

        return true;
    }

    public boolean testPiece(int x, int y, Piece piece) {
        byte[][] block = piece.getPieceOrientations()[piece.rotation];

        try {
            for (byte[] cord : block) {
                if (testSquare(x + cord[0], y + cord[1])) {
                    // square is already taken
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // something was out of the board -> draw not possible
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Board board = new Board(10, 20);

        Piece piece = new Tpiece();

        piece.rotate();
        piece.rotate();
        piece.rotate();

        board.drawPiece(3, 2, piece);

        //board.deleteLine(2);

        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                if (board.testSquare(x, y)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
