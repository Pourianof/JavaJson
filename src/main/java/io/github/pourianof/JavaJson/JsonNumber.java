package io.github.pourianof.JavaJson;

/**
 * Represent a JSON number value
 */
public class JsonNumber extends JsonObject<Number>{
    Number num;
    public JsonNumber(Number num){
        this.num = num;
    }
    private JsonNumber(){}
    public static JsonExtractPair extract (String str, int startIndex){
       return new JsonNumber().extractJson(str, startIndex);
    }

    @Override
    protected JsonExtractPair extractJson(String str, int startIndex) {
        String number = Utils.extractFirstNumber(str, startIndex);
        
        JsonNumber jo;
        if(number.contains(".")){
            jo = new JsonNumber(
              Double.parseDouble(number)  
            );
        }else{
            jo = new JsonNumber(
                Integer.parseInt(number)
            );
        }

        return new JsonExtractPair(jo, startIndex + number.length());
    }

    @Override
    public Number getValue() {
        return this.num;
    }

    @Override
    public String toJson() {
        return this.num + "" ;
    }
}
