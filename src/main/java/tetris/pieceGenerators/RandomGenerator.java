package tetris.pieceGenerators;

import tetris.Utils;
import tetris.pieces.*;

public class RandomGenerator implements Generator {
    @Override
    public Piece next() {
        return switch (Utils.randomInt(0, 7)) {
            case 1 -> new Jpiece();
            case 2 -> new Lpiece();
            case 3 -> new Opiece();
            case 4 -> new Spiece();
            case 5 -> new Tpiece();
            case 6 -> new Zpiece();
            default -> new Ipiece();
        };
    }
}
