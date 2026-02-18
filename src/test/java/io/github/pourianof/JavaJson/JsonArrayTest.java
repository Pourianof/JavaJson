package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonArray Tests")
public class JsonArrayTest {

    @Test
    @DisplayName("Create empty JsonArray")
    void testCreateEmptyJsonArray() {
        JsonArray jsonArray = new JsonArray();
        assertEquals(0, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Create JsonArray with items")
    void testCreateJsonArrayWithItems() {
        JsonObject[] items = {
            new JsonString("Hello"),
            new JsonNumber(42),
            new JsonBoolean(true)
        };
        JsonArray jsonArray = new JsonArray(items);
        assertEquals(3, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Add item to JsonArray")
    void testAddItemToJsonArray() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.addItem(new JsonString("Hello"));
        jsonArray.addItem(new JsonNumber(42));
        assertEquals(2, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Get items from JsonArray")
    void testGetItemsFromJsonArray() {
        JsonArray jsonArray = new JsonArray();
        JsonString item = new JsonString("Test");
        jsonArray.addItem(item);
        assertEquals("Test", ((JsonString) jsonArray.getValue().get(0)).getValue());
    }

    @Test
    @DisplayName("Serialize empty array to JSON")
    void testToJsonEmptyArray() {
        JsonArray jsonArray = new JsonArray();
        assertEquals("[]", jsonArray.toJson());
    }

    @Test
    @DisplayName("Serialize array with one item to JSON")
    void testToJsonOneItemArray() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.addItem(new JsonString("Hello"));
        String result = jsonArray.toJson();
        assertTrue(result.contains("Hello"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
    }

    @Test
    @DisplayName("Serialize array with multiple items to JSON")
    void testToJsonMultipleItemsArray() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.addItem(new JsonString("Hello"));
        jsonArray.addItem(new JsonNumber(42));
        jsonArray.addItem(new JsonBoolean(true));
        String result = jsonArray.toJson();
        assertTrue(result.contains("Hello"));
        assertTrue(result.contains("42"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("Parse empty array")
    void testParseEmptyArray() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[]");
        assertInstanceOf(JsonArray.class, result);
        assertEquals(0, ((JsonArray) result).getValue().size());
    }

    @Test
    @DisplayName("Parse empty array with spaces")
    void testParseEmptyArrayWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[  ]");
        assertInstanceOf(JsonArray.class, result);
        assertEquals(0, ((JsonArray) result).getValue().size());
    }

    @Test
    @DisplayName("Parse array with single string")
    void testParseArrayWithSingleString() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[\"Hello\"]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(1, jsonArray.getValue().size());
        assertEquals("Hello", ((JsonString) jsonArray.getValue().get(0)).getValue());
    }

    @Test
    @DisplayName("Parse array with multiple strings")
    void testParseArrayWithMultipleStrings() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[\"Hello\", \"World\", \"Test\"]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(3, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Parse array with different types")
    void testParseArrayWithDifferentTypes() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[\"Hello\", 42, true, 3.14]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(4, jsonArray.getValue().size());
        assertInstanceOf(JsonString.class, jsonArray.getValue().get(0));
        assertInstanceOf(JsonNumber.class, jsonArray.getValue().get(1));
        assertInstanceOf(JsonBoolean.class, jsonArray.getValue().get(2));
        assertInstanceOf(JsonNumber.class, jsonArray.getValue().get(3));
    }

    @Test
    @DisplayName("Parse array with nested array")
    void testParseArrayWithNestedArray() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[[1, 2], [3, 4]]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(2, jsonArray.getValue().size());
        assertInstanceOf(JsonArray.class, jsonArray.getValue().get(0));
    }

    @Test
    @DisplayName("Parse array with spaces between items")
    void testParseArrayWithSpacesBetweenItems() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[ \"Hello\" , 42 , true ]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(3, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Parse array with newlines and tabs")
    void testParseArrayWithNewlinesAndTabs() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[\n  \"Hello\",\n  42,\n  true\n]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(3, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse array")
    void testRoundTripArray() throws MalformedJsonValue, MalformedJsonStructure {
        JsonArray original = new JsonArray();
        original.addItem(new JsonString("Test"));
        original.addItem(new JsonNumber(42));
        original.addItem(new JsonBoolean(true));
        
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        
        assertInstanceOf(JsonArray.class, parsed);
        JsonArray jsonArray = (JsonArray) parsed;
        assertEquals(3, jsonArray.getValue().size());
    }

    @Test
    @DisplayName("Parse array with strings containing special characters")
    void testParseArrayWithSpecialCharacters() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("[\"Hello\\nWorld\", \"Tab\\tCharacter\"]");
        assertInstanceOf(JsonArray.class, result);
        JsonArray jsonArray = (JsonArray) result;
        assertEquals(2, jsonArray.getValue().size());
        assertEquals("Hello\nWorld", ((JsonString) jsonArray.getValue().get(0)).getValue());
    }
}
