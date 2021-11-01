package tetris.pieces;

public class Jpiece extends Piece {
    /*
            # * #
                #
     */

    // cords relative to the Anchor (x, y):
    public byte[][][] pieceOrientations = {{
            {-1, 0}, {0, 0}, {1, 0}, {1, 1}
    },{
            {0, -1}, {0, 0}, {0, 1}, {-1, 1}
    },{
            {-1, -1}, {-1, 0}, {0, 0}, {1, 0}
    },{
            {0, -1}, {1, -1}, {0, 0}, {0, 1}
    }};

    @Override
    public byte[][][] getPieceOrientations() {
        return pieceOrientations;
    }
}
