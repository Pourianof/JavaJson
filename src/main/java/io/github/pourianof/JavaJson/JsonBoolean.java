package io.github.pourianof.JavaJson;

/**
 * Represent a JSON boolean value
 */
public class JsonBoolean extends JsonObject<Boolean>{
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
        boolean bool;
        if( firstChar == 't' &&  str.startsWith("true", startIndex)){
            bool = true;
        }else if( firstChar == 'f' && str.startsWith("false", startIndex)){
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
