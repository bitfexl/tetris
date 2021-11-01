package tetris;

import tetris.board.BoardWithGraphics;
import tetris.board.GraphicsInterface;
import tetris.board.PlayableBoard;
import tetris.pieceGenerators.BagGenerator;
import tetris.pieceGenerators.Generator;
import tetris.pieces.Piece;
import window.InputCb;
import window.Input;
import window.Window;

public class Main {
    // tetris game objects
    private static PlayableBoard  board;
    private static Generator pieceGen;
    private static GraphicsInterface graphicsInterface;

    // lines cleared
    private static int score;

    // app window
    private static Window window;

    public static void main(String[] args) throws InterruptedException {
        // init tetris game objects
        board = new PlayableBoard(10, 20);
        pieceGen = new BagGenerator();
        graphicsInterface = new GraphicsInterface(10, 20);
        score = 0;

        // init gui
        window = new Window("Tetris by Fexl", graphicsInterface, Main::handleInput);

        // auto advance game every 400ms
        setupGameAdcance(400);

        // next piece to drop
        Piece nextPiece = pieceGen.next();

        // game loop
        while(true) {
            if(board.dropPiece(nextPiece)) {
                nextPiece = pieceGen.next();
            }

            // update board
            displayBoard();

            // add cleared lines to score
            score += board.tryClear();

            // update score
            window.setScore(score);

            Thread.sleep(10);
        }
    }

    /**
     * Copies the current board state to graphicsinterface (gui).
     */
    private static void displayBoard() {
        graphicsInterface.oPieces = board.currentBoard.oPieces;
        graphicsInterface.iPieces = board.currentBoard.iPieces;
        graphicsInterface.lPieces = board.currentBoard.lPieces;
        graphicsInterface.jPieces = board.currentBoard.jPieces;
        graphicsInterface.sPieces = board.currentBoard.sPieces;
        graphicsInterface.tPieces = board.currentBoard.tPieces;
        graphicsInterface.zPieces = board.currentBoard.zPieces;
    }

    /**
     * Handles inputs form gui.
     * @param key The pressed key to process.
     */
    private static void handleInput(Input key) {
        if(key == Input.LEFT) {
            board.movePieceX(-1);
        } else if(key == Input.RIGHT) {
            board.movePieceX(1);
        } else if(key == Input.ROTATE) {
            board.rotatePiece();
        } else if(key == Input.DOWN) {
            board.advance();
        } else if(key == Input.HARD_DROP) {
            for(int i=0; i<20; i++) {
                board.advance();
            }
        }

        // gameover -> restart on keypress
        if(!board.isGameRunning()) {
            board = new PlayableBoard(10, 20);
            score = 0;
        }
    }

    /**
     * Automatically advances Game every n milliseconds.
     * @param n Milliseconds between each advance (piece drops one line).
     */
    private static void setupGameAdcance(int n) {
        Thread advThread = new Thread(() -> {
            while(true) {
                board.advance();
                try {
                    Thread.sleep(n);
                } catch (InterruptedException ignored) {

                }
            }
        });
        advThread.start();
    }
}
