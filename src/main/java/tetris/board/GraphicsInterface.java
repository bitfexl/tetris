package tetris.board;

public class GraphicsInterface {
    /**
     * Draw all the Opieces here.
     */
    public Board oPieces;

    /**
     * Draw all the Tpieces here.
     */
    public Board tPieces;

    public Board iPieces;
    public Board jPieces;
    public Board lPieces;
    public Board sPieces;
    public Board zPieces;

    /**
     * The interface between the gui and the rest.
     * @param width Width of the board.
     * @param height Height of the board.
     */
    public GraphicsInterface(int width, int height) {
        iPieces = new Board(width, height);
        jPieces = new Board(width, height);
        lPieces = new Board(width, height);
        oPieces = new Board(width, height);
        sPieces = new Board(width, height);
        tPieces = new Board(width, height);
        zPieces = new Board(width, height);
    }
}
