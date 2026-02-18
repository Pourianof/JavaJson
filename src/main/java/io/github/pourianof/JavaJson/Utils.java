package io.github.pourianof.JavaJson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isSpaceChar(char c){
        return Character.isSpaceChar(c) || c == '\t' || c == '\n';
    }

    /**
     * This helper function find the index of first non-space character from position startIndex.
     * This can help in parsing process.
     * @param str input string
     * @param startIndex index where search should begin
     * @return int
     * @throws Exception When no non-space character found
     */
    public static int indexOfClosestNonSpaceCharacter(String str, int startIndex) throws Exception{
        for(int i = startIndex; i < str.length(); i++){
            if(isSpaceChar(str.charAt(i))){
                continue;
            }else{
                return i;
            }
        }
        throw new Exception("No non-space character exist after index -> " + startIndex + "." );
    }

    /**
     * This function is looking for the index of first occurrence of target character from position startIndex.
     * @param str
     * @param c
     * @param startIndex
     * @return
     */
    public static int indexOfCharAfterSpaces (String str,char c, int startIndex){
        for(int i = startIndex; i < str.length(); i++){
            if(str.charAt(i) == c){
                return i;
            }else if(isSpaceChar(str.charAt(i))){
                continue;
            }else{
                return -1;
            }
        }
        return -1;
    }

    /**
     * This function is looking for first occurrence of regular expression from position from.
     * @param str
     * @param regex
     * @param from
     * @return
     */
    static String extractFirstJsonValue(String str, String regex,int from){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String extracted = "";
        if(matcher.find(from)){
            extracted = matcher.group();
        }
        return  extracted;
    }
    static String extractFirstJsonString(String str, int from){
        String extracted = extractFirstJsonValue(str, "\".*?(?<!\\\\)\"", from);
        return extracted.substring(1, extracted.length() - 1);
    }
    public static String escapeUnicodeControlCharacters(String input) {
        StringBuilder builder = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isISOControl(ch)) {
                builder.append("\\u").append(String.format("%04x", (int) ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
    // TO DO : invoking String.replace method multiple times is not well-performance. It iterates over string
    // multiple times from start to end. we can change it by do iteration ourselves.
    public static String escape(String str){
        return escapeUnicodeControlCharacters(
                str.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\"", "\\\"")
        );
    }
    static String extractFirstNumber(String str, int from){
        // TODO: add supportion of plus sign at beginning of number
        return extractFirstJsonValue(str, "-?\\d+(?:\\.?\\d+|\\d*)", from);
    }
}
