package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonNumber Tests")
public class JsonNumberTest {

    @Test
    @DisplayName("Create JsonNumber with integer")
    void testCreateJsonNumberInteger() {
        JsonNumber jsonNumber = new JsonNumber(42);
        assertEquals(42, jsonNumber.getValue());
    }

    @Test
    @DisplayName("Create JsonNumber with double")
    void testCreateJsonNumberDouble() {
        JsonNumber jsonNumber = new JsonNumber(3.14);
        assertEquals(3.14, jsonNumber.getValue());
    }

    @Test
    @DisplayName("Create JsonNumber with negative integer")
    void testCreateJsonNumberNegativeInteger() {
        JsonNumber jsonNumber = new JsonNumber(-100);
        assertEquals(-100, jsonNumber.getValue());
    }

    @Test
    @DisplayName("Create JsonNumber with negative double")
    void testCreateJsonNumberNegativeDouble() {
        JsonNumber jsonNumber = new JsonNumber(-3.14);
        assertEquals(-3.14, jsonNumber.getValue());
    }

    @Test
    @DisplayName("Create JsonNumber with zero")
    void testCreateJsonNumberZero() {
        JsonNumber jsonNumber = new JsonNumber(0);
        assertEquals(0, jsonNumber.getValue());
    }

    @Test
    @DisplayName("Serialize integer to JSON")
    void testToJsonInteger() {
        JsonNumber jsonNumber = new JsonNumber(42);
        assertEquals("42", jsonNumber.toJson());
    }

    @Test
    @DisplayName("Serialize double to JSON")
    void testToJsonDouble() {
        JsonNumber jsonNumber = new JsonNumber(3.14);
        String result = jsonNumber.toJson();
        assertTrue(result.contains("3.14"));
    }

    @Test
    @DisplayName("Serialize negative number to JSON")
    void testToJsonNegative() {
        JsonNumber jsonNumber = new JsonNumber(-42);
        assertEquals("-42", jsonNumber.toJson());
    }

    @Test
    @DisplayName("Serialize zero to JSON")
    void testToJsonZero() {
        JsonNumber jsonNumber = new JsonNumber(0);
        assertEquals("0", jsonNumber.toJson());
    }

    @Test
    @DisplayName("Parse simple integer")
    void testParseSimpleInteger() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("42");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(42, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse simple double")
    void testParseSimpleDouble() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("3.14");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(3.14, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse negative integer")
    void testParseNegativeInteger() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("-100");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(-100.0, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse negative double")
    void testParseNegativeDouble() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("-3.14");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(-3.14, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse zero")
    void testParseZero() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("0");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(0.0, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse number with spaces around")
    void testParseNumberWithSpaces() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("  42  ");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(42.0, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse large number")
    void testParseLargeNumber() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("999999999999");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals(999999999999.0, ((JsonNumber) result).getValue());
    }

    @Test
    @DisplayName("Parse very small decimal")
    void testParseSmallDecimal() throws MalformedJsonValue, MalformedJsonStructure {
        JsonObject result = JsonObject.parse("0.0001");
        assertInstanceOf(JsonNumber.class, result);
        assertEquals( ((JsonNumber) result).getValue(), 0.00001);
    }

    @Test
    @DisplayName("Round-trip: serialize and parse integer")
    void testRoundTripInteger() throws MalformedJsonValue, MalformedJsonStructure {
        JsonNumber original = new JsonNumber(42);
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(original.getValue(), ((JsonNumber) parsed).getValue());
    }

    @Test
    @DisplayName("Round-trip: serialize and parse double")
    void testRoundTripDouble() throws MalformedJsonValue, MalformedJsonStructure {
        JsonNumber original = new JsonNumber(3.14159);
        String json = original.toJson();
        JsonObject parsed = JsonObject.parse(json);
        assertEquals(((JsonNumber) parsed).getValue().doubleValue(), ((JsonNumber) original).getValue().doubleValue(), 0.00001);
    }
}
