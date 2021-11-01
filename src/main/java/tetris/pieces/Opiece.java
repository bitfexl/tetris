package tetris.pieces;

import tetris.board.Board;

public class Opiece extends Piece {
    /*
            Anchor is the top left:
            * #
            # #
     */

    // cords relative to the Anchor (x, y):
    public byte[][][] pieceOrientations = {{
            {0, 0}, {1, 0},
            {0, 1}, {1, 1}
    },{
            {0, 0}, {1, 0},
            {0, 1}, {1, 1}
    },{
            {0, 0}, {1, 0},
            {0, 1}, {1, 1}
    },{
            {0, 0}, {1, 0},
            {0, 1}, {1, 1}
    }};

    @Override
    public byte[][][] getPieceOrientations() {
        return pieceOrientations;
    }
}
