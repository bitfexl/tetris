package tetris;

import tetris.board.BoardWithGraphics;
import tetris.board.GraphicsInterface;
import tetris.board.PlayableBoard;
import tetris.board.PlayableBoardWithGraphics;
import tetris.pieceGenerators.BagGenerator;
import tetris.pieceGenerators.Generator;
import tetris.pieces.Piece;
import window.InputCb;
import window.Input;
import window.Window;

public class Main {
    // tetris game objects
    private static PlayableBoardWithGraphics board;
    private static Generator pieceGen;

    private static GraphicsInterface graphicsInterface;

    // lines cleared
    private static int score;

    // app window
    private static Window window;

    public static void main(String[] args) throws InterruptedException {
        // init tetris game objects
        board = new PlayableBoardWithGraphics(10, 20);
        graphicsInterface = new GraphicsInterface(10, 20);
        pieceGen = new BagGenerator();
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

            // add cleared lines to score
            score += board.tryClear();

            // update graphics
            refreshGraphics();

            // update score
            window.setScore(score);

            Thread.sleep(10);
        }
    }

    /**
     * Copies graphics of currentBoard to graphicsInterface.
     */
    private static void refreshGraphics() {
        // Copy to get rid of flickering while graphicsInterface of currentBoard is updating.
        board.copyGraphicsInterface(graphicsInterface);
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
            board = new PlayableBoardWithGraphics(10, 20);
            pieceGen = new BagGenerator();
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
