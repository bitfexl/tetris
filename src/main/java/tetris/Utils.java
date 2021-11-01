package tetris;

public final class Utils {
    private Utils() { }

    /**
     * Returns a random int.
     * @param min Minimum (included).
     * @param max Maximum (excluded).
     * @return The generated number.
     */
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max-min) + min);
    }

    /**
     * Shuffles an array.
     * @param arr The array to shuffle.
     * @param shuffles How many times it should switch elements (something like length*2).
     * @param <T> Type of the array.
     */
    public static <T> void shuffleArray(T[] arr, int shuffles) {
        T buffer;
        for(int i=0; i<shuffles; i++) {
            int elemA = randomInt(0, arr.length);
            int elemB = randomInt(0, arr.length);

            // switch
            buffer = arr[elemB];
            arr[elemB] = arr[elemA];
            arr[elemA] = buffer;
        }
    }
}
