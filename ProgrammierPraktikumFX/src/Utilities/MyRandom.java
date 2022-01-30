package Utilities;

import java.util.Random;

public class MyRandom {

    /**
     * get a Random Number between a Range
     * @param min
     * @param max
     * @return random int
     */
    public static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * gets a random Boolean
     * @return
     */
    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }
}
