package io.github.pourianof.JavaJson;

/**
 * Represent a JSON boolean value
 */
public class JsonBoolean extends JsonObject<Boolean>{
    final static String TRUE_STRING = "true";
    final static String FALSE_STRING = "false";

    Boolean bool;
    public JsonBoolean(Boolean bool){
        this.bool = bool;
    }
    private JsonBoolean(){}

    public static JsonExtractPair extract(String str, int startIndex){
        return new JsonBoolean().extractJson(str, startIndex);
    }

    @Override
    protected JsonExtractPair extractJson(String str, int startIndex)  {
        char firstChar = Character.toLowerCase(str.charAt(startIndex));
        String normalizedStr = str.toLowerCase();

        boolean bool;
        if( firstChar == 't' &&  normalizedStr.startsWith(TRUE_STRING, startIndex)){
            bool = true;
        }else if( firstChar == 'f' && normalizedStr.startsWith(FALSE_STRING, startIndex)){
            bool = false;
        } else{
            bool = false;
        }
        JsonBoolean jb = new JsonBoolean(bool);
        return new JsonExtractPair(jb, startIndex + (bool ? 4 : 5));
    }

    @Override
    public Boolean getValue() {
        return this.bool;
    }

    @Override
    public String toJson() {
        if(this.bool == null){
            return "null";
        }
        return this.bool ? "true" : "false";
    }
}
