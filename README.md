
# JavaJson

A lightweight library for working with **JSON** strings in Java.

## API Reference
This JSON engine can either parse a JSON string and
also stringify the structure which generate by apis
classes.

Library define a map of JSON value to a java class.
```java
new JsonBoolean();	 // JSON boolean value
new JsonString();        // JSON string value
new JsonNumber();    // JSON number value
new JsonArray();         // JSON array value
new JsonMap();          // JSON map value
```
>  All of these classes are subclasses of **JsonObject**. Then you can use polymorphism to generalize code.

## Stringifying
### Define JSON value to stringify
At follow we introduce the classes which can use to struct a JSON value.

#### JsonBoolean
This class accept a boolean value as paramater and present a json boolean value.

####  JsonString
This class accept a string value and represent a string value in JSON.

#### JsonNumber
This class accept a number(int, float, double) as parameter and represent a JSON number value.

#### JsonArray
This class accept array of JsonObject. For example:
```java
JsonObject[] jsonArrayItems = {
	new JsonString("Hello World"),
	new JsonNumber(12.5),
};

new JsonArray(
	jsonArrayItems
);
```


#### JsonMap
This class is a representation of a map value in JSON. The store map can be initialized by passing array of Map.Entry to JsonMap class.

And then you can add items at runtime by addPair method from created JsonMap object.
```java
JsonArray jsonArr= new JsonArray();

Map.Entry[] jsonObj = {
    Map.entry("key", new JsonString(null)),
    Map.entry("key2", jsonArr),
    Map.entry("key3", new JsonBoolean(false))
};

JsonMap jsonMap = new JsonMap(jsonObj);
jsonMap.addPair("key3", new JsonMap() /* another map */);
```

You can set null value in map or array by passing null to concrete constructors of JsonObject.
Like what we did in example above by passing null to JsonString.

### How to stringify
After you construct your json value or structure with proper classes,
then you can use `yourJsonObject.toJson()` method and it then return a
JSON string back.

It is important to notice that if you constructed a compound(map or array) value then you should call the `toJson()` method from that compound object, not from the inner json objects. Actually this method will try to stringify from the json object it get called and then go down to reach to primitives values.

## Parsing
As we said this library support parsing JSON string either.
For doing that you can use `JsonObject.parse()` static method and pass
the json string to it, it will back the JsonObject which match the root structure(For compound json values like map or array). For example:

```java
JsonMap map = (JsonMap) JsonObject.parse(
        "{\"key\": [false, 12]}"
);
```
## Contributing

Contributions are always welcome!
