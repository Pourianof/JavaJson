package io.github.pourianof.JavaJson;

import java.util.ArrayList;

import java.util.List;

public class JsonArray extends CompoundJsonStructure<ArrayList<JsonObject>> {
    private static final char openCharacter = '[';
    private static final char closeCharacter = ']';
    public final static String typeName = "array";
    public static JsonExtractPair extract (String str, int startIndex) throws MalformedJsonValue, MalformedJsonStructure {
        return new JsonArray().extractJson(str, startIndex);
    }

    @Override
    protected JsonExtractPair lookForStructure(String str, int startIndex) throws MalformedJsonStructure, MalformedJsonValue {
        JsonExtractPair pair = JsonObject.lookForNewStructure(str, startIndex);
        this.addItem(pair.extractedJO());
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
        return this.items.size();
    }

    @Override
    protected String provideJsonItem(int index) {
        return this.items.get(index).toJson();
    }

    public JsonArray() {}
    public JsonArray(JsonObject items[]){
        this.items.addAll(List.of(items));
    }
    ArrayList<JsonObject> items = new ArrayList<>();
    public void addItem(JsonObject item){
        this.items.add(item);
    }

    @Override
    public ArrayList<JsonObject> getValue() {
        return this.items;
    }

}
