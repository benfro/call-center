package net.benfro.callcenter.util;

import java.util.Date;
import java.util.Random;

public class RandomGenerator {

    private static final Random rand = new Random(new Date().getTime());

    public static int intInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
