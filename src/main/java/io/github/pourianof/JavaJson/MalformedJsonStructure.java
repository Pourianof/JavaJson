package io.github.pourianof.JavaJson;

public class MalformedJsonStructure extends Exception{
    private String structureType ;
    private int index;
    public MalformedJsonStructure(String structureName,String reason, String first8Char, int index){
        super("Error occurred on extracting *"+ structureName + "* structure at index " + index +".\n"+reason+"\nFirst 8 character of the position error occurred -> "+ first8Char);

    }

    public String getStructureType() {
        return structureType;
    }

    public int getErrorOccurrenceIndex() {
        return index;
    }
}
