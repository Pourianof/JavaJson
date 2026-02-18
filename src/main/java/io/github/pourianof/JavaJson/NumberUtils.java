package io.github.pourianof.JavaJson;

import java.math.BigInteger;

public class NumberUtils {
     public static Number parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e1) {

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e2) {

                return new BigInteger(input);
            }
        }
    }
}
