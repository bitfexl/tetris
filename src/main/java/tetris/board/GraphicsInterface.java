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

    /**
     * Copies the graphics to the given GraphicsInterface. Creates new Board objects to be fast. Width and height of newGraphicsInterface will be overwritten.
     * @param newGraphicsInterface The GraphicsInterface to copy to.
     */
    public void copyTo(GraphicsInterface newGraphicsInterface) {
        Board iPiecesCopy = new Board(iPieces);
        Board jPiecesCopy = new Board(jPieces);
        Board lPiecesCopy = new Board(lPieces);
        Board oPiecesCopy = new Board(oPieces);
        Board sPiecesCopy = new Board(sPieces);
        Board tPiecesCopy = new Board(tPieces);
        Board zPiecesCopy = new Board(zPieces);

        newGraphicsInterface.iPieces = iPiecesCopy;
        newGraphicsInterface.jPieces = jPiecesCopy;
        newGraphicsInterface.lPieces = lPiecesCopy;
        newGraphicsInterface.oPieces = oPiecesCopy;
        newGraphicsInterface.sPieces = sPiecesCopy;
        newGraphicsInterface.tPieces = tPiecesCopy;
        newGraphicsInterface.zPieces = zPiecesCopy;
    }
}
