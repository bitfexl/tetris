package tetris.board;

import tetris.pieces.Ipiece;
import tetris.pieces.*;

public class BoardWithGraphics extends Board {
    public Board iPieces;
    public Board jPieces;
    public Board lPieces;
    public Board oPieces;
    public Board sPieces;
    public Board tPieces;
    public Board zPieces;

    /**
     * Same as Board.
     * @param width Width of new board.
     * @param height Height of new board.
     */
     public BoardWithGraphics(int width, int height) {
         super(width, height);

         iPieces = new Board(width, height);
         jPieces = new Board(width, height);
         lPieces = new Board(width, height);
         oPieces = new Board(width, height);
         sPieces = new Board(width, height);
         tPieces = new Board(width, height);
         zPieces = new Board(width, height);
     }

    /**
     * Copies the board.
     * @param board The board to copy.
     */
     public BoardWithGraphics(BoardWithGraphics board) {
         super(board);

         iPieces = new Board(board.iPieces);
         jPieces = new Board(board.jPieces);
         lPieces = new Board(board.lPieces);
         oPieces = new Board(board.oPieces);
         sPieces = new Board(board.sPieces);
         tPieces = new Board(board.tPieces);
         zPieces = new Board(board.zPieces);
     }

    /**
     * Draws piece as super does but also draws it in the specific color board.
     */
    @Override
    public synchronized boolean drawPiece(int x, int y, Piece piece) {
        boolean success = super.drawPiece(x, y, piece);
        if(success) {
            // draw on specific color board
            if(piece instanceof Ipiece) {
                iPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Jpiece) {
                jPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Lpiece) {
                lPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Opiece) {
                oPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Spiece) {
                sPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Tpiece) {
                tPieces.drawPiece(x, y, piece);
            } else if(piece instanceof Zpiece) {
                zPieces.drawPiece(x, y, piece);
            }
        }
        return success;
    }

    /**
     * Deletes a line.
     */
    @Override
    public void deleteLine(int line) {
        super.deleteLine(line);
        iPieces.deleteLine(line);
        jPieces.deleteLine(line);
        lPieces.deleteLine(line);
        oPieces.deleteLine(line);
        sPieces.deleteLine(line);
        tPieces.deleteLine(line);
        zPieces.deleteLine(line);
    }
}
