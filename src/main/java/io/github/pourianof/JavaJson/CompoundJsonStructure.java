package io.github.pourianof.JavaJson;


/**
 * Represent valid JSON data structures which contains collection of other JSON values.
 * Concrete options are: {@link JsonMap} - {@link JsonArray}
 * @param <T>
 */
public abstract class CompoundJsonStructure<T> extends JsonObject<T> {
    protected abstract JsonExtractPair lookForStructure(String str, int startIndex ) throws MalformedJsonStructure, MalformedJsonValue;
    protected abstract String getTypeName();
    protected abstract char getStructureCloseChar();
    protected abstract char getStructureOpenChar();
    protected abstract int getItemsCount();
    protected abstract String provideJsonItem(int index);
    @Override
    protected JsonExtractPair extractJson(String str, int startIndex) throws MalformedJsonValue, MalformedJsonStructure {
        startIndex = startIndex + 1; // startIndex initially point to beginning (like { or [) character
        String substring = str.substring(0, startIndex);
        while (startIndex < str.length()) {
            JsonExtractPair pair = null;
            try{
                pair = this.lookForStructure(str, startIndex);
            }catch(MalformedJsonValue ex){
                // candidate for end of structure (empty structure)
            }
            
            // if extraction of pair failed, then use the startIndex for seek what is next character
            var endOfExtractedStructure = pair == null ? startIndex : pair.endIndexOfExtractedInMainJson();

            try {
                int charIndex = Utils.indexOfClosestNonSpaceCharacter(str, endOfExtractedStructure);
                if(str.charAt(charIndex) == ','){
                    startIndex = charIndex + 1;
                    continue;

                } else if (str.charAt(charIndex) == getStructureCloseChar()) {
                    int closeBracketIndex = Utils.indexOfCharAfterSpaces(str, getStructureCloseChar(), endOfExtractedStructure);
                    return new JsonExtractPair(this, closeBracketIndex + 1);
                }else{
                    throw new MalformedJsonStructure(this.getTypeName(),
                            " structure couldn't find either close bracket (}) for ending structure nor comma (,) for separating entries. ",
                            str.substring(charIndex, charIndex + 8), charIndex);
                }

            }catch (MalformedJsonStructure e){
                throw e;
            } catch (Exception e){
                throw new MalformedJsonStructure(this.getTypeName(),
                        "Json string got finished without completing current " + this.getTypeName() +
                                " structure.",
                        substring,
                        endOfExtractedStructure);
            }
        }
        throw new MalformedJsonStructure(this.getTypeName(),
                "Json ended but close bracket "
                        + this.getTypeName() +" not found.",
                substring,
                str.length() );
    }


    @Override
    public String toJson() {
        if(this.getValue() == null){
            return "null";
        }
        final StringBuffer tempString = new StringBuffer();
        int itemsCount = this.getItemsCount();
        for(int i = 0; i < itemsCount - 1 ; i++ ){
            tempString.append(this.provideJsonItem(i));
            tempString.append(", ");
        }
        if(itemsCount > 0) {
            tempString.append(this.provideJsonItem(itemsCount -1));
        }
        return "" + this.getStructureOpenChar() + tempString + this.getStructureCloseChar();
    }
}
