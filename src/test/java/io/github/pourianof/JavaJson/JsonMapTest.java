package io.github.pourianof.JavaJson;

import io.github.pourianof.ListMap.ListMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonMap Tests")
public class JsonMapTest {

    @Test
    @DisplayName("Create empty JsonMap")
    void testCreateEmptyJsonMap() {
        JsonMap jsonMap = new JsonMap();
        assertEquals(0, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Create JsonMap with entries")
    void testCreateJsonMapWithEntries() {
        Map.Entry<String, JsonObject>[] entries = new Map.Entry[] {
            Map.entry("name", new JsonString("John")),
            Map.entry("age", new JsonNumber(30))
        };
        JsonMap jsonMap = new JsonMap(entries);
        assertEquals(2, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Add pair to JsonMap")
    void testAddPairToJsonMap() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.addPair("name", new JsonString("John"));
        jsonMap.addPair("age", new JsonNumber(30));
        assertEquals(2, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Get value from JsonMap by key")
    void testGetValueFromJsonMap() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.addPair("name", new JsonString("John"));
        JsonObject value = (JsonObject) jsonMap.getValue().get("name");
        assertInstanceOf(JsonString.class, value);
        assertEquals("John", ((JsonString) value).getValue());
    }

    @Test
    @DisplayName("Serialize empty map to JSON")
    void testToJsonEmptyMap() {
        JsonMap jsonMap = new JsonMap();
        assertEquals("{}", jsonMap.toJson());
    }

    @Test
    @DisplayName("Serialize map with one pair to JSON")
    void testToJsonOneEntryMap() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.addPair("name", new JsonString("John"));
        String result = jsonMap.toJson();
        assertTrue(result.contains("name"));
        assertTrue(result.contains("John"));
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    @DisplayName("Serialize map with multiple pairs to JSON")
    void testToJsonMultipleEntriesMap() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.addPair("name", new JsonString("John"));
        jsonMap.addPair("age", new JsonNumber(30));
        jsonMap.addPair("active", new JsonBoolean(true));
        String result = jsonMap.toJson();
        assertTrue(result.contains("name"));
        assertTrue(result.contains("age"));
        assertTrue(result.contains("active"));
    }

    @Test
    @DisplayName("Parse empty object")
    void testParseEmptyObject() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{}");
        assertInstanceOf(JsonMap.class, result);
        assertEquals(0, ((JsonMap) result).getValue().size());
    }

    @Test
    @DisplayName("Parse empty object with spaces")
    void testParseEmptyObjectWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{  }");
        assertInstanceOf(JsonMap.class, result);
        assertEquals(0, ((JsonMap) result).getValue().size());
    }

    @Test
    @DisplayName("Parse object with single pair")
    void testParseObjectWithSinglePair() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"name\": \"John\"}");
        assertInstanceOf(JsonMap.class, result);
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(1, jsonMap.getValue().size());
        assertEquals("John", ((JsonString) jsonMap.getValue().get("name")).getValue());
    }

    @Test
    @DisplayName("Parse object with multiple pairs")
    void testParseObjectWithMultiplePairs() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"name\": \"John\", \"age\": 30, \"active\": true}");
        assertInstanceOf(JsonMap.class, result);
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(3, jsonMap.getValue().size());
        assertEquals("John", ((JsonString) jsonMap.getValue().get("name")).getValue());
        assertEquals(30.0, ((JsonNumber) jsonMap.getValue().get("age")).getValue());
        assertTrue(((JsonBoolean) jsonMap.getValue().get("active")).getValue());
    }

    @Test
    @DisplayName("Parse object with string value")
    void testParseObjectWithStringValue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"message\": \"Hello World\"}");
        JsonMap jsonMap = (JsonMap) result;
        assertEquals("Hello World", ((JsonString) jsonMap.getValue().get("message")).getValue());
    }

    @Test
    @DisplayName("Parse object with number value")
    void testParseObjectWithNumberValue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"count\": 42}");
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(42.0, ((JsonNumber) jsonMap.getValue().get("count")).getValue());
    }

    @Test
    @DisplayName("Parse object with boolean value")
    void testParseObjectWithBooleanValue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"active\": true}");
        JsonMap jsonMap = (JsonMap) result;
        assertTrue(((JsonBoolean) jsonMap.getValue().get("active")).getValue());
    }

    @Test
    @DisplayName("Parse object with nested object")
    void testParseObjectWithNestedObject() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"person\": {\"name\": \"John\", \"age\": 30}}");
        JsonMap jsonMap = (JsonMap) result;
        JsonObject nestedObj = (JsonObject) jsonMap.getValue().get("person");
        assertInstanceOf(JsonMap.class, nestedObj);
        assertEquals("John", ((JsonString) ((JsonMap) nestedObj).getValue().get("name")).getValue());
    }

    @Test
    @DisplayName("Parse object with array value")
    void testParseObjectWithArrayValue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"numbers\": [1, 2, 3]}");
        JsonMap jsonMap = (JsonMap) result;
        JsonObject arrayObj = (JsonObject) jsonMap.getValue().get("numbers");
        assertInstanceOf(JsonArray.class, arrayObj);
        assertEquals(3, ((JsonArray) arrayObj).getValue().size());
    }

    @Test
    @DisplayName("Parse object with spaces around colons and commas")
    void testParseObjectWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{ \"name\" : \"John\" , \"age\" : 30 }");
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(2, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Parse object with newlines and indentation")
    void testParseObjectWithNewlines() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{\n  \"name\": \"John\",\n  \"age\": 30,\n  \"active\": true\n}";
        JsonObject result = JsonObject.parse(json);
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(3, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Parse object with special characters in key")
    void testParseObjectWithSpecialCharactersInKey() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"user-name\": \"John\"}");
        JsonMap jsonMap = (JsonMap) result;
        assertEquals("John", ((JsonString) jsonMap.getValue().get("user-name")).getValue());
    }

    @Test
    @DisplayName("Parse object with special characters in value")
    void testParseObjectWithSpecialCharactersInValue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("{\"address\": \"123 Main St\\nCity, State\"}");
        JsonMap jsonMap = (JsonMap) result;
        String address = ((JsonString) jsonMap.getValue().get("address")).getValue();
        assertTrue(address.contains("\n"));
    }

    @Test
    @DisplayName("Round-trip: serialize and parse object")
    void testRoundTripObject() throws MalformedJsonValue, MalformedJsonStructure {
        JsonMap original = new JsonMap();
        original.addPair("name", new JsonString("John"));
        original.addPair("age", new JsonNumber(30));
        original.addPair("active", new JsonBoolean(true));
        
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        
        assertInstanceOf(JsonMap.class, parsed);
        JsonMap jsonMap = (JsonMap) parsed;
        assertEquals(3, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Complex nested structure round-trip")
    void testRoundTripComplexStructure() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{" +
            "\"person\": {" +
                "\"name\": \"John\"," +
                "\"age\": 30" +
            "}," +
            "\"numbers\": [1, 2, 3]," +
            "\"active\": true" +
            "}";
        
        JsonObject result = JsonObject.parse(json);
        assertInstanceOf(JsonMap.class, result);
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(3, jsonMap.getValue().size());
    }
}
