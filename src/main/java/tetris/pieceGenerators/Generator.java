package tetris.pieceGenerators;

import tetris.pieces.Piece;

public interface Generator {
    /**
     * Generate the next piece.
     * @return The generated piece.
     */
    Piece next();
}
