package tetris.board;

public class PlayableBoardWithGraphics extends PlayableBoard {
    public PlayableBoardWithGraphics(int width, int height) {
        super(new BoardWithGraphics(width, height), new BoardWithGraphics(width, height));
    }

    /**
     * Copies the graphics of the board to the given GraphicsInterface.
     * @param gi The GraphicsInterface to copy to.
     */
    public synchronized void copyGraphicsInterface(GraphicsInterface gi) {
        ((BoardWithGraphics)currentBoard).graphicsInterface.copyTo(gi);
    }
}
