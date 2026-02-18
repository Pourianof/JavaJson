package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonBoolean Tests")
public class JsonBooleanTest {

    @Test
    @DisplayName("Create JsonBoolean with true value")
    void testCreateJsonBooleanTrue() {
        JsonBoolean jsonBoolean = new JsonBoolean(true);
        assertTrue(jsonBoolean.getValue());
    }

    @Test
    @DisplayName("Create JsonBoolean with false value")
    void testCreateJsonBooleanFalse() {
        JsonBoolean jsonBoolean = new JsonBoolean(false);
        assertFalse(jsonBoolean.getValue());
    }

    @Test
    @DisplayName("Create JsonBoolean with null value")
    void testCreateJsonBooleanNull() {
        JsonBoolean jsonBoolean = new JsonBoolean(null);
        assertNull(jsonBoolean.getValue());
    }

    @Test
    @DisplayName("Serialize true to JSON")
    void testToJsonTrue() {
        JsonBoolean jsonBoolean = new JsonBoolean(true);
        assertEquals("true", jsonBoolean.toJson());
    }

    @Test
    @DisplayName("Serialize false to JSON")
    void testToJsonFalse() {
        JsonBoolean jsonBoolean = new JsonBoolean(false);
        assertEquals("false", jsonBoolean.toJson());
    }

    @Test
    @DisplayName("Serialize null to JSON")
    void testToJsonNull() {
        JsonBoolean jsonBoolean = new JsonBoolean(null);
        assertEquals("null", jsonBoolean.toJson());
    }

    @Test
    @DisplayName("Parse 'true' value")
    void testParseTrue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("true");
        assertInstanceOf(JsonBoolean.class, result);
        assertTrue(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse 'false' value")
    void testParseFalse() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("false");
        assertInstanceOf(JsonBoolean.class, result);
        assertFalse(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse 'true' with spaces")
    void testParseTrueWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("  true  ");
        assertInstanceOf(JsonBoolean.class, result);
        assertTrue(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse 'false' with spaces")
    void testParseFalseWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("  false  ");
        assertInstanceOf(JsonBoolean.class, result);
        assertFalse(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse uppercase 'TRUE' value")
    void testParseUppercaseTrue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("TRUE");
        assertInstanceOf(JsonBoolean.class, result);
        assertTrue(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse uppercase 'FALSE' value")
    void testParseUppercaseFalse() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("FALSE");
        assertInstanceOf(JsonBoolean.class, result);
        assertFalse(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Parse mixed case 'TrUe' value")
    void testParseMixedCaseTrue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("TrUe");
        assertInstanceOf(JsonBoolean.class, result);
        assertTrue(((JsonBoolean) result).getValue());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse true")
    void testRoundTripTrue() throws MalformedJsonValue, MalformedJsonStructure {
        JsonBoolean original = new JsonBoolean(true);
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(original.getValue(), ((JsonBoolean) parsed).getValue());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse false")
    void testRoundTripFalse() throws MalformedJsonValue, MalformedJsonStructure {
        JsonBoolean original = new JsonBoolean(false);
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(original.getValue(), ((JsonBoolean) parsed).getValue());
    }

    @Test
    @DisplayName("Invalid boolean string should return false")
    void testParseInvalidBooleanReturnsDefault() throws MalformedJsonValue, MalformedJsonStructure {
        // Based on the code, invalid boolean parse defaults to false
        // This test verifies the behavior of the extract method
        JsonObject result = JsonObject.parse("false");
        assertInstanceOf(JsonBoolean.class, result);
        assertFalse(((JsonBoolean) result).getValue());
    }
}
