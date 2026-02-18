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
        String substring = str.substring(str.length() - 8);
        while (startIndex < str.length()) {
            JsonExtractPair pair = this.lookForStructure(str, startIndex);
            try {
                int charIndex = Utils.indexOfClosestNonSpaceCharacter(str, pair.endIndexOfExtractedInMainJson());
                if(str.charAt(charIndex) == ','){
                    startIndex = charIndex + 1;
                    continue;

                } else if (str.charAt(charIndex) == getStructureCloseChar()) {
                    int closeBracketIndex = Utils.indexOfCharAfterSpaces(str, getStructureCloseChar(), pair.endIndexOfExtractedInMainJson());
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
                        pair.endIndexOfExtractedInMainJson());
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
