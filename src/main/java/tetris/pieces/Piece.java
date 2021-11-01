package tetris.pieces;

import tetris.board.Board;

public abstract class Piece {

    /**
     * The piece coordinates. [orientation][blocks][cords (x,y)].
     * orientation: 0-3
     * blocks: 1-n blocks to draw;
     * cords: offset to anchor: [0]=x offset, [1]=y offset;
     */
    public abstract byte[][][] getPieceOrientations();

    /**
     * Rotation to use for drawing the piece (clockwise). 0-3;
     */
    public int rotation = 0;

    /**
     * Rotates the piece once clockwise.
     */
    public void rotate() {
        rotation++;
        if(rotation > 3) {
            rotation = 0;
        }
    }
}
