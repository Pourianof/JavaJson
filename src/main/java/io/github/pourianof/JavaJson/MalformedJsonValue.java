package io.github.pourianof.JavaJson;

public class MalformedJsonValue extends Exception{
    MalformedJsonValue(String value, int index){
        super("Can not recognize the json type of value -> " + value + " <- at index : "+ index);
    }
}
