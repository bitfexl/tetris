package tetris.board;

import tetris.pieces.*;

public class PlayableBoard {
    /**
     * The internal board to keep track of occupied squares and line clears.
     */
    private final BoardWithGraphics petrifiedBoard;

    /**
     * The board to test piece drops on. Resembles the current position of the board.
     * Is recreated on each move (advance() or dropPiece()).
     */
    public BoardWithGraphics currentBoard;

    /**
     * The piece that is currently dropping or null if no piece is currently dropping.
     */
    private Piece currentPiece;
    int currentPieceX, currentPieceY;

    /**
     * True. If False: gameover (cannot drop new piece)
     */
    private boolean gameRunning;

    public PlayableBoard(int width, int height) {
        this.petrifiedBoard = new BoardWithGraphics(width, height);
        gameRunning = true;
    }

    /**
     * Checks if the game is running.
     * @return true: game is running, false: gameover;
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Creates a copy of the current board without the falling piece.
     * @return A copy of the current petrified board.
     */
    public BoardWithGraphics getCurrentPetrifiedBoard() {
        return new BoardWithGraphics(petrifiedBoard);
    }

    /**
     * Drops a piece from the top.
     * @param piece The piece to drop.
     * @return true: piece dropped, false: gameover or a piece is not done dropping
     */
    public synchronized boolean dropPiece(Piece piece) {
        if(currentPiece != null) {
            // another piece is currently dropping
            return false;
        }

        currentPiece = piece;
        currentPieceX = 4;
        currentPieceY = 0;
        currentBoard = new BoardWithGraphics(petrifiedBoard);

        if(!currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece)) {
            // piece could not be dropped -> gameover
            gameRunning = false;
        }

        // piece dropped
        return true;
    }

    /**
     * Tries moving the current piece down once.
     * @return true: success, false: the piece has already reached its final position (-> time for next piece).
     */
    public synchronized boolean advance() {
        if(currentPiece == null) {
            // no piece to drop (-> next)
            return false;
        }

        // go to next position
        currentPieceY++;
        currentBoard = new BoardWithGraphics(petrifiedBoard);

        // test drop
        if(currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece)) {
            // dropped one line
            return true;
        }

        // go back
        currentPieceY--;

        // current piece has reached its final position, draw on petrifiedBoard
        petrifiedBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);

        // draw to resemble current position
        currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);

        // piece finished dropping
        currentPiece = null;
        return false;
    }

    /**
     * Move the current piece in x direction.
     * @param amount The amount of squares to move (- = left, + = right).
     * @return true: move successful, false: no active piece or wall in the way;
     */
    public synchronized boolean movePieceX(int amount) {
        if(currentPiece == null) {
            return false;
        }

        if(!petrifiedBoard.testPiece(currentPieceX + amount, currentPieceY, currentPiece)) {
            return false;
        }

        // test successful
        currentPieceX += amount;

        currentBoard = new BoardWithGraphics(petrifiedBoard);

        currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);

        return true;
    }

    /**
     * Rotates current piece.
     * @return If piece rotated or not.
     */
    public synchronized boolean rotatePiece() {
        if(currentPiece == null) {
            return false;
        }

        // save rotation
        int rotation = currentPiece.rotation;

        currentPiece.rotate();

        if(!petrifiedBoard.testPiece(currentPieceX, currentPieceY, currentPiece)) {
            // rotate gone wrong -> rotate back
            currentPiece.rotation = rotation;
            return false;
        }

        // draw
        currentBoard = new BoardWithGraphics(petrifiedBoard);
        currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);

        return true;
    }

    /**
     * Tries to clear lines form petrifiedBoard.
     * @return The number of lines cleared.
     */
    public synchronized int tryClear() {
        int linesCleared = 0;
        for(int y=0; y<petrifiedBoard.height; y++) {
            boolean lineFilled = true;

            for(int x=0; x<petrifiedBoard.width; x++) {
                if(!petrifiedBoard.testSquare(x, y)) {
                    // square is missing
                    lineFilled = false;
                }
            }

            if(lineFilled) {
                // no square missing
                petrifiedBoard.deleteLine(y);
                linesCleared++;
            }
        }

        // copy to resemble position
        currentBoard = new BoardWithGraphics(petrifiedBoard);
        if(currentPiece != null) {
            currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);
        }

        return linesCleared;
    }
}
