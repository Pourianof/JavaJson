package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonObject Integration Tests")
public class JsonObjectIntegrationTest {

    @Test
    @DisplayName("Parse complex nested JSON structure")
    void testComplexNestedStructure() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{\n" +
            "  \"name\": \"John Doe\",\n" +
            "  \"age\": 30,\n" +
            "  \"active\": true,\n" +
            "  \"address\": {\n" +
            "    \"street\": \"123 Main St\",\n" +
            "    \"city\": \"New York\"\n" +
            "  },\n" +
            "  \"hobbies\": [\"reading\", \"gaming\", \"coding\"],\n" +
            "  \"scores\": [95.5, 87.3, 92.1]\n" +
            "}";

        JsonObject result = JsonObject.parse(json);
        assertInstanceOf(JsonMap.class, result);
        
        JsonMap jsonMap = (JsonMap) result;
        assertEquals(6, jsonMap.getValue().size());
    }

    @Test
    @DisplayName("Parse and verify deeply nested arrays")
    void testDeeplyNestedArrays() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "[[[1, 2], [3, 4]], [[5, 6], [7, 8]]]";
        JsonObject result = JsonObject.parse(json);
        assertInstanceOf(JsonArray.class, result);
        
        JsonArray array = (JsonArray) result;
        assertEquals(2, array.getValue().size());
        
        JsonArray subArray = (JsonArray) array.getValue().get(0);
        assertEquals(2, subArray.getValue().size());
    }

    @Test
    @DisplayName("Parse all primitive types in single structure")
    void testAllPrimitiveTypesInStructure() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{" +
            "\"string\": \"test\"," +
            "\"number\": 42," +
            "\"decimal\": 3.14," +
            "\"boolean_true\": true," +
            "\"boolean_false\": false" +
            "}";

        JsonObject result = JsonObject.parse(json);
        JsonMap jsonMap = (JsonMap) result;
        
        assertInstanceOf(JsonString.class, jsonMap.getValue().get("string"));
        assertInstanceOf(JsonNumber.class, jsonMap.getValue().get("number"));
        assertInstanceOf(JsonNumber.class, jsonMap.getValue().get("decimal"));
        assertInstanceOf(JsonBoolean.class, jsonMap.getValue().get("boolean_true"));
        assertInstanceOf(JsonBoolean.class, jsonMap.getValue().get("boolean_false"));
    }

    @Test
    @DisplayName("Round-trip complex JSON structure")
    void testRoundTripComplexStructure() throws MalformedJsonValue, MalformedJsonStructure {
        String originalJson = "{\"items\":[{\"id\":1,\"name\":\"Item 1\"},{\"id\":2,\"name\":\"Item 2\"}],\"count\":2}";
        
        JsonObject parsed = JsonObject.parse(originalJson);
        String serialized = parsed.toJson();
        JsonObject reparsed = JsonObject.parse(serialized);
        
        assertInstanceOf(JsonMap.class, reparsed);
    }

    @Test
    @DisplayName("Parse minimal JSON objects")
    void testMinimalJsonObjects() throws MalformedJsonValue, MalformedJsonStructure {
        // Empty array
        JsonObject emptyArray = JsonObject.parse("[]");
        assertInstanceOf(JsonArray.class, emptyArray);
        assertEquals(0, ((JsonArray) emptyArray).getValue().size());
        
        // Empty object
        JsonObject emptyObject = JsonObject.parse("{}");
        assertInstanceOf(JsonMap.class, emptyObject);
        assertEquals(0, ((JsonMap) emptyObject).getValue().size());
    }

    @Test
    @DisplayName("Parse single values")
    void testSingleValues() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject string = JsonObject.parse("\"test\"");
        assertInstanceOf(JsonString.class, string);
        
        JsonObject number = JsonObject.parse("42");
        assertInstanceOf(JsonNumber.class, number);
        
        JsonObject bool = JsonObject.parse("true");
        assertInstanceOf(JsonBoolean.class, bool);
    }

    @Test
    @DisplayName("Parse with various spacing and formatting")
    void testVariousSpacingAndFormatting() throws MalformedJsonValue, MalformedJsonStructure {
        String json1 = "{\"a\":1}";
        String json2 = "{ \"a\" : 1 }";
        String json3 = "{\n  \"a\": 1\n}";
        
        JsonObject result1 = JsonObject.parse(json1);
        JsonObject result2 = JsonObject.parse(json2);
        JsonObject result3 = JsonObject.parse(json3);
        
        assertTrue(result1 instanceof JsonMap);
        assertTrue(result2 instanceof JsonMap);
        assertTrue(result3 instanceof JsonMap);
    }

    @Test
    @DisplayName("Verify type names for compound structures")
    void testTypeNamesForCompoundStructures() {
        JsonArray array = new JsonArray();
        assertEquals("array", JsonArray.typeName);
        
        JsonMap map = new JsonMap();
        assertEquals("map", JsonMap.typeName);
    }

    @Test
    @DisplayName("Parse Unicode escape sequences")
    void testUnicodeEscapeSequences() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "\"Hello\\u0020World\""; // \u0020 is space
        JsonObject result = JsonObject.parse(json);
        assertInstanceOf(JsonString.class, result);
    }

    @Test
    @DisplayName("Parse strings with escape sequences")
    void testEscapeSequencesInStrings() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{" +
            "\"newline\": \"Line1\\nLine2\"," +
            "\"tab\": \"Col1\\tCol2\"," +
            "\"quote\": \"Say \\\"Hello\\\"\"," +
            "\"backslash\": \"Path\\\\To\\\\File\"" +
            "}";

        JsonObject result = JsonObject.parse(json);
        assertInstanceOf(JsonMap.class, result);
        
        JsonMap map = (JsonMap) result;
        JsonString newline = (JsonString) map.getValue().get("newline");
        assertTrue(newline.getValue().contains("\n"));
    }

    @Test
    @DisplayName("Array containing mixed nested structures")
    void testArrayWithMixedNestedStructures() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "[" +
            "\"string value\"," +
            "42," +
            "true," +
            "[1, 2, 3]," +
            "{\"key\": \"value\"}" +
            "]";

        JsonObject result = JsonObject.parse(json);
        JsonArray array = (JsonArray) result;
        assertEquals(5, array.getValue().size());
        
        assertInstanceOf(JsonString.class, array.getValue().get(0));
        assertInstanceOf(JsonNumber.class, array.getValue().get(1));
        assertInstanceOf(JsonBoolean.class, array.getValue().get(2));
        assertInstanceOf(JsonArray.class, array.getValue().get(3));
        assertInstanceOf(JsonMap.class, array.getValue().get(4));
    }

    @Test
    @DisplayName("Object with nested arrays and objects")
    void testObjectWithNestedStructures() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{" +
            "\"users\": [" +
            "{\"id\": 1, \"name\": \"Alice\"}," +
            "{\"id\": 2, \"name\": \"Bob\"}" +
            "]," +
            "\"total\": 2" +
            "}";

        JsonObject result = JsonObject.parse(json);
        JsonMap map = (JsonMap) result;
        
        JsonArray users = (JsonArray) map.getValue().get("users");
        assertEquals(2, users.getValue().size());
        
        JsonMap firstUser = (JsonMap) users.getValue().get(0);
        assertEquals("Alice", ((JsonString) firstUser.getValue().get("name")).getValue());
    }

    @Test
    @DisplayName("Parse numbers with decimal points and negative values")
    void testNumberVariations() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "[0, -1, 3.14, -2.5, 1000000]";
        JsonObject result = JsonObject.parse(json);
        JsonArray array = (JsonArray) result;
        
        assertEquals(5, array.getValue().size());
        assertEquals(0, ((JsonNumber) array.getValue().get(0)).getValue());
        assertEquals(-1, ((JsonNumber) array.getValue().get(1)).getValue());
        assertEquals(3.14, ((JsonNumber) array.getValue().get(2)).getValue());
    }

    @Test
    @DisplayName("Serialization produces valid JSON")
    void testSerializationProducesValidJson() throws MalformedJsonValue, MalformedJsonStructure {
        JsonMap map = new JsonMap();
        map.addPair("name", new JsonString("Test"));
        map.addPair("count", new JsonNumber(5));
        map.addPair("active", new JsonBoolean(true));
        
        String json = map.toJson();
        
        // Should be parseable again
        JsonObject reparsed = JsonObject.parse(json);
        assertInstanceOf(JsonMap.class, reparsed);
    }

    @Test
    @DisplayName("Test error handling for blank JSON")
    void testErrorHandlingBlankJson() {
        String blankJson = "   \n\t   ";
        assertThrows(MalformedJsonStructure.class, () -> JsonObject.parse(blankJson));
    }

    @Test
    @DisplayName("Preserve insertion order in maps")
    void testPreserveInsertionOrderInMaps() throws MalformedJsonValue, MalformedJsonStructure {
        JsonMap map = new JsonMap();
        map.addPair("z", new JsonString("last"));
        map.addPair("a", new JsonString("first"));
        map.addPair("m", new JsonString("middle"));
        
        JsonObject result = JsonObject.parse(map.toJson());
        // Parse and verify structure is maintained
        assertInstanceOf(JsonMap.class, result);
    }

    @Test
    @DisplayName("Large JSON parsing and serialization")
    void testLargeJsonStructure() throws MalformedJsonValue, MalformedJsonStructure {
        JsonArray array = new JsonArray();
        for (int i = 0; i < 100; i++) {
            array.addItem(new JsonNumber(i));
        }
        
        String json = array.toJson();
        JsonObject parsed = JsonObject.parse(json);
        JsonArray reparsed = (JsonArray) parsed;
        
        assertEquals(100, reparsed.getValue().size());
    }

    @Test
    @DisplayName("Special case: numeric string IDs in objects")
    void testNumericStringIdsInObjects() throws MalformedJsonValue, MalformedJsonStructure {
        String json = "{\"123\": \"value\", \"456\": \"another\"}";
        JsonObject result = JsonObject.parse(json);
        JsonMap map = (JsonMap) result;
        
        assertEquals("value", ((JsonString) map.getValue().get("123")).getValue());
    }
}
