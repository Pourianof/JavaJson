package io.github.pourianof.JavaJson;

/**
 * This record is a helper class which is used in parsing process.
 * It holds two data:
 * 1- extractedJo -> the structure that got extracted from json and
 * 2- endIndexOfExtractedInMainJson -> the position of the structure end in main json string.
 * Note that the endIndexOfExtractedInMainJson actually points to one character after the last character of extractedJo in main json string.
 * It means that for a structure like -> [ "hi man" ], <- after extracting array, this parameter points to comma character
 * @param extractedJO
 * @param endIndexOfExtractedInMainJson
 */
record JsonExtractPair(JsonObject extractedJO, int endIndexOfExtractedInMainJson) { }

/**
 * Abstract class which represent a JSON value
 * The available concrete options:
 * {@link JsonString} {@link JsonNumber} {@link JsonBoolean} {@link JsonArray} {@link JsonMap}
 * @param <T> The actual value type it holds
 */
public abstract class JsonObject<T> {
    /**
     * Serialize or stringify sub structure json.
     * @return String - json string
     */
    public abstract String toJson();

    /**
     * Parse a json string to its matched json objects.
     * As json object is an abstract and general class, then you can use instanceof operator or getTypeName method to determine jsonObject actual type.
     * @param json - Actual json string to parse
     * @return org.pooriava.JavaJson.JsonObject - Mapped or parsed json.
     * @throws MalformedJsonValue
     * @throws MalformedJsonStructure
     */
    public static JsonObject parse(String json) throws MalformedJsonValue, MalformedJsonStructure{
        return JsonObject.lookForNewStructure(json, 0).extractedJO();
    }

    /**
     * Get the value which this json structure hold.
     * @return T - depends on concrete classes
     */
    public abstract T getValue();

    /**
     * This method is responsible know its json structure and can parse that from a json string.
     * @param str - whole string
     * @param startIndex - last index which parsed
     * @return org.pooriava.JavaJson.JsonExtractPair
     * @throws MalformedJsonValue
     * @throws MalformedJsonStructure
     */
    abstract protected JsonExtractPair extractJson(String str, int startIndex)throws MalformedJsonValue, MalformedJsonStructure;

    /**
     * This static helper method is responsible for what json structure is going to begin and delegate the actual extraction to it class.
     * @param str The input json string
     * @param startIndex Search start index
     * @return The parsed json structure and the place it got end in actual json string
     * @throws MalformedJsonValue
     * @throws MalformedJsonStructure
     */
    protected static JsonExtractPair lookForNewStructure(String str, int startIndex) throws MalformedJsonValue, MalformedJsonStructure {
        int indexOfChar;
        try {
            indexOfChar = Utils.indexOfClosestNonSpaceCharacter(str, startIndex);
        }catch (Exception e){
            throw new MalformedJsonStructure("Json",
                    "Json string is a blank string, which" +
                            " means consist of any json structure like array, map, string or other.",
                    "",
                    0);
        }
        char c = str.charAt(indexOfChar);
        switch (c) {
            case '\"': { // string
                return JsonString.extract(str, indexOfChar);
            }
            case '{': { // map
                return JsonMap.extract(str,indexOfChar);
            }
            case '[': { // array
                return JsonArray.extract(str,indexOfChar);
            }
            default: {
                if (Character.isDigit(c)) { // number
                    return JsonNumber.extract(str, indexOfChar);
                } else if (Character.toLowerCase(c) == 't' || Character.toLowerCase(c) == 'f') { // boolean
                    return JsonBoolean.extract(str, indexOfChar);
                }else { // invalid
                    throw new MalformedJsonValue(str, indexOfChar);
                }
            }
        }

    }
}
