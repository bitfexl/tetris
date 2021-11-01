package tetris.pieceGenerators;

import tetris.Utils;
import tetris.pieces.*;

public class BagGenerator implements Generator {
    private Piece[] pieces;
    int bagIndex = 0;

    public BagGenerator() {
        genBag();
    }

    @Override
    public Piece next() {
        if(bagIndex >= pieces.length) {
            // entire bag used -> new bag
            genBag();
            bagIndex = 0;
        }
        return pieces[bagIndex++];
    }

    private void genBag() {
        pieces = new Piece[] {new Ipiece(), new Jpiece(), new Lpiece(), new Opiece(), new Spiece(), new Tpiece(), new Zpiece()};
        Utils.shuffleArray(pieces, pieces.length*3);
    }
}
