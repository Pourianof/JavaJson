package io.github.pourianof.JavaJson;

import io.github.pourianof.ListMap.ListMap;

import java.util.Map;
/**
 * Represent a JSON Map value.
 * This class can be used to define a JSON map of {@link String} to {@link JsonObject}
 */
public class JsonMap extends CompoundJsonStructure<ListMap<String,JsonObject>> {
    private static char openCharacter = '{';
    private static char closeCharacter = '}';

    public JsonMap(){}
    public JsonMap(Map.Entry<String, JsonObject> keyValues []){
        this.map.putAll(Map.ofEntries(keyValues));
    }
    private ListMap<String, JsonObject> map = new ListMap<>();

    public void addPair(String key,JsonObject value){
        this.map.put(key, value);
    }
    public static String typeName = "map";
    // start bracket ->{ "key_name" : value , ... }
    //                       colon -^   ^   ^- comma for separating items
    //                                  |_ some other allowed json values
    // Note : in json compound structure like array and map, after the last element there is no comma.
    public static JsonExtractPair extract (String str, int startIndex) throws MalformedJsonValue,MalformedJsonStructure{
        return new JsonMap().extractJson(str, startIndex);
    }
    @Override
    public ListMap getValue() {
        return this.map;
    }


    @Override
    protected JsonExtractPair lookForStructure(String str, int startIndex) throws MalformedJsonStructure, MalformedJsonValue {
        int keyFirstDoubleQuote = Utils.indexOfCharAfterSpaces(str, '\"', startIndex);
        if(keyFirstDoubleQuote <= 0){
            // no key founded
            return new JsonExtractPair(null, startIndex);
        }
        String key = Utils.extractFirstJsonString(str, keyFirstDoubleQuote);
        int colonIndex = Utils.indexOfCharAfterSpaces(str, ':', keyFirstDoubleQuote + key.length() + 2);
        // Illegal structure occurred, couldn't find colon before end of string or new structure
        if (colonIndex < 0) {
            throw new MalformedJsonStructure(typeName, "Couldn't find maps key-value colon(:) before ending string or new structure.", str.substring(colonIndex - 8, colonIndex), colonIndex);
        }
        JsonExtractPair pair = lookForNewStructure(str, colonIndex + 1);
        this.addPair(key, pair.extractedJO());
        return pair;
    }

    @Override
    protected String getTypeName() {
        return typeName;
    }

    @Override
    protected char getStructureCloseChar() {
        return closeCharacter;
    }

    @Override
    protected char getStructureOpenChar() {
        return openCharacter;
    }

    @Override
    protected int getItemsCount() {
        return this.map.size();
    }

    @Override
    protected String provideJsonItem(int index) {
        String key = this.map.getKeyByIndex(index);
        JsonObject value = this.map.at(index);
        String temp = "";
        temp += "\"" + Utils.escape(key) + "\" : ";
        temp+= value.toJson();
        return temp;
    }
}
