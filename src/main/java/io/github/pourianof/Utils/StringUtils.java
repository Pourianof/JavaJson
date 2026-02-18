package io.github.pourianof.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String decodeUnicode(String input) {
        Pattern pattern = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher matcher = pattern.matcher(input);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            char ch = (char) Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(String.valueOf(ch)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public static String decodeAllEscapes(String input) {
        String unicodeDecoded = decodeUnicode(input);
        return unicodeDecoded.translateEscapes();
    }
}
