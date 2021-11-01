package tetris.board;

import tetris.pieces.*;

public class PlayableBoard {
    /**
     * The internal board to keep track of occupied squares and line clears.
     */
    protected final Board petrifiedBoard;

    /**
     * The board to test piece drops on. Resembles the current position of the board.
     */
    public Board currentBoard;

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
        this.petrifiedBoard = new Board(width, height);
        this.currentBoard = new Board(width, height);
        gameRunning = true;
    }

    protected PlayableBoard(Board pBoard, Board cBoard) {
        this.petrifiedBoard = pBoard;
        this.currentBoard = cBoard;
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
    public Board getCurrentPetrifiedBoard() {
        return new Board(petrifiedBoard);
    }

    /**
     * Copies newBoard to petrified board. Removes currently dropping piece.
     * @param newBoard Board to copy.
     * @return If board could be set or not. (width and height not matching).
     */
    public boolean setCurrentPetrifiedBoard(Board newBoard) {
        if(newBoard.copyTo(petrifiedBoard)) {
            deleteCurrentDroppingPiece();
            return true;
        }
        return false;
    }

    /**
     * Drops a piece from the top.
     * @param piece The piece to drop.
     * @return true: piece dropped, false: gameover or a piece is not done dropping;
     */
    public synchronized boolean dropPiece(Piece piece) {
        if(currentPiece != null) {
            // another piece is currently dropping
            return false;
        }

        currentPiece = piece;
        currentPieceX = (petrifiedBoard.width-1) / 2;
        currentPieceY = 0;

        if(!drawCurrentPiece()) {
            // piece could not be dropped -> gameover
            gameRunning = false;
            return false;
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

        // test drop
        if(petrifiedBoard.testPiece(currentPieceX, currentPieceY, currentPiece)) {
            drawCurrentPiece();

            // dropped one line
            return true;
        }

        // go back
        currentPieceY--;

        // could not drop -> reached final position (with previous y)
        petrifiedBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);
        clearCurrentPiece();
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
        drawCurrentPiece();
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

        // success
        drawCurrentPiece();
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

        // draw to resemble position
        drawCurrentPiece();
        return linesCleared;
    }

    /**
     * Deletes the currently dropping piece (if there is one) without placing it.
     */
    public void deleteCurrentDroppingPiece() {
        clearCurrentPiece();
        updateCurrentBoard();
    }

    /**
     * Clears currently dropping piece. Does not update current board.
     */
    private void clearCurrentPiece() {
        currentPiece = null;
    }

    /**
     * Copies petrified board to current board.
     */
    private void updateCurrentBoard() {
        petrifiedBoard.copyTo(currentBoard);
    }

    /**
     * Draws current dropping piece on current board. If current piece is null it only redraws the board (return still true).
     * @return If current board could be updated (piece could be drawn).
     */
    private boolean drawCurrentPiece() {
        updateCurrentBoard();
        if(currentPiece != null) {
            return currentBoard.drawPiece(currentPieceX, currentPieceY, currentPiece);
        }
        return true;
    }
}
