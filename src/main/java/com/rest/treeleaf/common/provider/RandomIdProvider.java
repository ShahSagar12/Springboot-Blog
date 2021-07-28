package com.rest.treeleaf.common.provider;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public class RandomIdProvider {
	
	 /**
     * different dictionaries used
     */
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static SecureRandom random = new SecureRandom();

    public static String generateRandString(int length) {
        String dictionary = ALPHA_CAPS + ALPHA + NUMERIC;
        StringBuilder result = new StringBuilder();
        IntStream.range(0, length).forEach(value -> {
                    int index = random.nextInt(dictionary.length());
                    result.append(dictionary.charAt(index));
                }
        );
        return result.toString();
    }

}
