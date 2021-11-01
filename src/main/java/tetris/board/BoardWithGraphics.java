package tetris.board;

import tetris.pieces.Ipiece;
import tetris.pieces.*;

public class BoardWithGraphics extends Board {
    public GraphicsInterface graphicsInterface;

    /**
     * Same as Board.
     *
     * @param width  Width of new board.
     * @param height Height of new board.
     */
    public BoardWithGraphics(int width, int height) {
        super(width, height);
        graphicsInterface = new GraphicsInterface(width, height);
    }

    /**
     * Copies the board.
     *
     * @param board The board to copy.
     */
    public BoardWithGraphics(BoardWithGraphics board) {
        super(board);
        board.copyTo(this);
    }

    /**
     * Clears board.
     */
    @Override
    public void resetBoard() {
        super.resetBoard();

        graphicsInterface.iPieces.resetBoard();
        graphicsInterface.jPieces.resetBoard();
        graphicsInterface.lPieces.resetBoard();
        graphicsInterface.oPieces.resetBoard();
        graphicsInterface.sPieces.resetBoard();
        graphicsInterface.zPieces.resetBoard();
        graphicsInterface.tPieces.resetBoard();
    }

    /**
     * Copies the given board with all graphic boards.
     * @param newBoard The board to copy itself to.
     * @return true: copy successful, false: width and height do not match -> no copy
     */
    public boolean copyTo(BoardWithGraphics newBoard) {
        if (!super.copyTo(newBoard)) {
            return false;
        }

        graphicsInterface.iPieces.copyTo(newBoard.graphicsInterface.iPieces);
        graphicsInterface.jPieces.copyTo(newBoard.graphicsInterface.jPieces);
        graphicsInterface.lPieces.copyTo(newBoard.graphicsInterface.lPieces);
        graphicsInterface.oPieces.copyTo(newBoard.graphicsInterface.oPieces);
        graphicsInterface.sPieces.copyTo(newBoard.graphicsInterface.sPieces);
        graphicsInterface.tPieces.copyTo(newBoard.graphicsInterface.tPieces);
        graphicsInterface.zPieces.copyTo(newBoard.graphicsInterface.zPieces);

        return true;
    }

    @Override
    public boolean copyTo(Board newBoard) {
        try {
            return copyTo((BoardWithGraphics) newBoard);
        } catch (ClassCastException ignore) {
            return super.copyTo(newBoard);
        }
    }

    /**
     * Draws piece as super does but also draws it in the specific color board.
     */
    @Override
    public boolean drawPiece(int x, int y, Piece piece) {
        boolean success = super.drawPiece(x, y, piece);
        if (success) {
            // draw on specific color board
            if (piece instanceof Ipiece) {
                graphicsInterface.iPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Jpiece) {
                graphicsInterface.jPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Lpiece) {
                graphicsInterface.lPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Opiece) {
                graphicsInterface.oPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Spiece) {
                graphicsInterface.sPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Tpiece) {
                graphicsInterface.tPieces.drawPiece(x, y, piece);
            } else if (piece instanceof Zpiece) {
                graphicsInterface.zPieces.drawPiece(x, y, piece);
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
        graphicsInterface.iPieces.deleteLine(line);
        graphicsInterface.jPieces.deleteLine(line);
        graphicsInterface.lPieces.deleteLine(line);
        graphicsInterface.oPieces.deleteLine(line);
        graphicsInterface.sPieces.deleteLine(line);
        graphicsInterface.tPieces.deleteLine(line);
        graphicsInterface.zPieces.deleteLine(line);
    }
}
