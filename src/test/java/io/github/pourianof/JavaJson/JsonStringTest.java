package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonString Tests")
public class JsonStringTest {

    @Test
    @DisplayName("Create JsonString with simple string")
    void testCreateJsonString() {
        JsonString jsonString = new JsonString("Hello World");
        assertEquals("Hello World", jsonString.getValue());
    }

    @Test
    @DisplayName("Create JsonString with empty string")
    void testCreateJsonStringEmpty() {
        JsonString jsonString = new JsonString("");
        assertEquals("", jsonString.getValue());
    }

    @Test
    @DisplayName("Create JsonString with null value")
    void testCreateJsonStringNull() {
        JsonString jsonString = new JsonString(null);
        assertNull(jsonString.getValue());
    }

    @Test
    @DisplayName("Serialize simple string to JSON")
    void testToJsonSimpleString() {
        JsonString jsonString = new JsonString("Hello");
        assertEquals("\"Hello\"", jsonString.toJson());
    }

    @Test
    @DisplayName("Serialize null to JSON")
    void testToJsonNull() {
        JsonString jsonString = new JsonString(null);
        assertEquals("null", jsonString.toJson());
    }

    @Test
    @DisplayName("Serialize string with special characters")
    void testToJsonWithSpecialCharacters() {
        JsonString jsonString = new JsonString("Hello\nWorld");
        String result = jsonString.toJson();
        assertTrue(result.contains("\\n"));
    }

    @Test
    @DisplayName("Serialize string with quotes")
    void testToJsonWithQuotes() {
        JsonString jsonString = new JsonString("Say \"Hello\"");
        String result = jsonString.toJson();
        assertTrue(result.contains("\\\""));
    }

    @Test
    @DisplayName("Serialize string with backslash")
    void testToJsonWithBackslash() {
        JsonString jsonString = new JsonString("C:\\path\\to\\file");
        String result = jsonString.toJson();
        assertTrue(result.contains("\\\\"));
    }

    @Test
    @DisplayName("Serialize string with tab character")
    void testToJsonWithTab() {
        JsonString jsonString = new JsonString("Hello\tWorld");
        String result = jsonString.toJson();
        assertTrue(result.contains("\\t"));
    }

    @Test
    @DisplayName("Parse simple JSON string")
    void testParseSimpleString() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("\"Hello World\"");
        assertInstanceOf(JsonString.class, result);
        assertEquals("Hello World", ((JsonString) result).getValue());
    }

    @Test
    @DisplayName("Parse JSON string with escaped characters")
    void testParseStringWithEscapedCharacters() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("\"Hello\\nWorld\"");
        assertInstanceOf(JsonString.class, result);
        assertEquals("Hello\nWorld", ((JsonString) result).getValue());
    }

    @Test
    @DisplayName("Parse JSON string with escaped quotes")
    void testParseStringWithEscapedQuotes() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("\"Say \\\"Hello\\\"\"");
        assertInstanceOf(JsonString.class, result);
        assertEquals("Say \"Hello\"", ((JsonString) result).getValue());
    }

    @Test
    @DisplayName("Parse empty JSON string")
    void testParseEmptyString() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("\"\"");
        assertInstanceOf(JsonString.class, result);
        assertEquals("", ((JsonString) result).getValue());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse simple string")
    void testRoundTripSimpleString() throws MalformedJsonValue, MalformedJsonStructure {
        JsonString original = new JsonString("Test String");
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(original.getValue(), ((JsonString) parsed).getValue());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse string with special characters")
    void testRoundTripSpecialCharacters() throws MalformedJsonValue, MalformedJsonStructure {
        JsonString original = new JsonString("Line1\nLine2\tTab");
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(original.getValue(), ((JsonString) parsed).getValue());
    }

    @Test
    @DisplayName("Parse string with spaces around")
    void testParseStringWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("  \"Hello\"  ");
        assertInstanceOf(JsonString.class, result);
        assertEquals("Hello", ((JsonString) result).getValue());
    }
}
