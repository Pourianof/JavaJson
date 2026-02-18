package io.github.pourianof.JavaJson;

import io.github.pourianof.Utils.StringUtils;

/**
 * Represent a JSON string value
 */
public class JsonString extends JsonObject<String>{
    public static JsonString parse (String str){
        String actualString = str.substring(1, str.length() - 2);
        return new JsonString(actualString);
    }
    private JsonString(){}
    public static JsonExtractPair extract(String str, int startIndex) {
//        int indexOfFirstDColon = org.pooriava.JavaJson.Utils.indexOfCharAfterSpaces(str, '\"', startIndex);
        return new JsonString().extractJson(str, startIndex);
    }
    String str;
    public JsonString(String str){
        this.str = str;
    }

    @Override
    protected JsonExtractPair extractJson(String str, int startIndex){
        String extracted = Utils.extractFirstJsonString(str, startIndex);
        String unescapedString = StringUtils.decodeAllEscapes(extracted);
        JsonString jo = new JsonString(unescapedString);
        return new JsonExtractPair(jo, startIndex + extracted.length() + 2);
    }

    @Override
    public String getValue() {
        return this.str;
    }

    @Override
    public String toJson() {
        if(this.str == null){
            return "null";
        }
        String escapedString = Utils.escape(this.str);
        return "\"" + escapedString + "\"";
    }
}
