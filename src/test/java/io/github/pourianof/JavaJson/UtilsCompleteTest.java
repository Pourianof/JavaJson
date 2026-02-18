package io.github.pourianof.JavaJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Utils Tests")
public class UtilsCompleteTest {

    @Test
    @DisplayName("isSpaceChar detects regular space")
    void testIsSpaceCharRegularSpace() {
        assertTrue(Utils.isSpaceChar(' '));
    }

    @Test
    @DisplayName("isSpaceChar detects tab character")
    void testIsSpaceCharTab() {
        assertTrue(Utils.isSpaceChar('\t'));
    }

    @Test
    @DisplayName("isSpaceChar detects non-breaking space")
    void testIsSpaceCharNonBreakingSpace() {
        assertTrue(Utils.isSpaceChar('\u00A0')); // Non-breaking space
    }

    @Test
    @DisplayName("isSpaceChar returns false for letter")
    void testIsSpaceCharLetter() {
        assertFalse(Utils.isSpaceChar('A'));
    }

    @Test
    @DisplayName("isSpaceChar returns false for digit")
    void testIsSpaceCharDigit() {
        assertFalse(Utils.isSpaceChar('5'));
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter finds first character at start")
    void testIndexOfClosestNonSpaceCharacterAtStart() throws Exception {
        String str = "Hello World";
        int index = Utils.indexOfClosestNonSpaceCharacter(str, 0);
        assertEquals(0, index);
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter skips leading spaces")
    void testIndexOfClosestNonSpaceCharacterSkipsSpaces() throws Exception {
        String str = "   Hello";
        int index = Utils.indexOfClosestNonSpaceCharacter(str, 0);
        assertEquals(3, index);
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter skips tabs and spaces")
    void testIndexOfClosestNonSpaceCharacterSkipsTabsAndSpaces() throws Exception {
        String str = "\t\t  \n\n  Z";
        int index = Utils.indexOfClosestNonSpaceCharacter(str, 0);
        assertEquals(str.indexOf('Z'), index);
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter from middle of string")
    void testIndexOfClosestNonSpaceCharacterFromMiddle() throws Exception {
        String str = "Hello   World";
        int index = Utils.indexOfClosestNonSpaceCharacter(str, 5);
        assertEquals(8, index);
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter throws exception for blank string")
    void testIndexOfClosestNonSpaceCharacterBlankString() {
        String str = "     ";
        assertThrows(Exception.class, () -> Utils.indexOfClosestNonSpaceCharacter(str, 0));
    }

    @Test
    @DisplayName("indexOfClosestNonSpaceCharacter throws exception when no non-space found")
    void testIndexOfClosestNonSpaceCharacterNoNonSpaceFound() {
        String str = "Hello   ";
        assertThrows(Exception.class, () -> Utils.indexOfClosestNonSpaceCharacter(str, 5));
    }

    @Test
    @DisplayName("indexOfCharAfterSpaces finds character immediately")
    void testIndexOfCharAfterSpacesImmediate() {
        String str = "Hello,World";
        int index = Utils.indexOfCharAfterSpaces(str, ',', 0);
        assertEquals(5, index);
    }

    @Test
    @DisplayName("indexOfCharAfterSpaces skips spaces before character")
    void testIndexOfCharAfterSpacesSkipsSpaces() {
        String str = "Hello  ,  World";
        int index = Utils.indexOfCharAfterSpaces(str, ',', 0);
        assertEquals(7, index);
    }

    @Test
    @DisplayName("indexOfCharAfterSpaces returns -1 when something else found first")
    void testIndexOfCharAfterSpacesOtherCharFound() {
        String str = "Hello World";
        int index = Utils.indexOfCharAfterSpaces(str, ',', 0);
        assertEquals(-1, index);
    }

    @Test
    @DisplayName("indexOfCharAfterSpaces from specific start index")
    void testIndexOfCharAfterSpacesFromStartIndex() {
        String str = "One,Two,Three";
        int index = Utils.indexOfCharAfterSpaces(str, ',', 4);
        assertEquals(7, index);
    }

    @Test
    @DisplayName("escape handles backslashes")
    void testEscapeBackslash() {
        String result = Utils.escape("C:\\path\\to\\file");
        assertTrue(result.contains("\\\\"));
    }

    @Test
    @DisplayName("escape handles tabs")
    void testEscapeTab() {
        String result = Utils.escape("Hello\tWorld");
        assertTrue(result.contains("\\t"));
    }

    @Test
    @DisplayName("escape handles newlines")
    void testEscapeNewline() {
        String result = Utils.escape("Line1\nLine2");
        assertTrue(result.contains("\\n"));
    }

    @Test
    @DisplayName("escape handles carriage returns")
    void testEscapeCarriageReturn() {
        String result = Utils.escape("Hello\rWorld");
        assertTrue(result.contains("\\r"));
    }

    @Test
    @DisplayName("escape handles backspace")
    void testEscapeBackspace() {
        String result = Utils.escape("Hello\bWorld");
        assertTrue(result.contains("\\b"));
    }

    @Test
    @DisplayName("escape handles form feed")
    void testEscapeFormFeed() {
        String result = Utils.escape("Hello\fWorld");
        assertTrue(result.contains("\\f"));
    }

    @Test
    @DisplayName("escape handles double quotes")
    void testEscapeDoubleQuotes() {
        String result = Utils.escape("Say \"Hello\"");
        assertTrue(result.contains("\\\""));
    }

    @Test
    @DisplayName("escape handles multiple special characters")
    void testEscapeMultipleSpecialCharacters() {
        String result = Utils.escape("Line1\nTab\tQuote\"Backslash\\");
        assertTrue(result.contains("\\n"));
        assertTrue(result.contains("\\t"));
        assertTrue(result.contains("\\\""));
        assertTrue(result.contains("\\\\"));
    }

    @Test
    @DisplayName("escape returns same string for normal text")
    void testEscapeNormalText() {
        String original = "Hello World 123";
        String result = Utils.escape(original);
        assertEquals(original, result);
    }

    @Test
    @DisplayName("escapeUnicodeControlCharacters escapes control characters")
    void testEscapeUnicodeControlCharacters() {
        String input = "Hello\u0000World"; // Null character (control character)
        String result = Utils.escapeUnicodeControlCharacters(input);
        assertTrue(result.contains("\\u"));
    }

    @Test
    @DisplayName("escapeUnicodeControlCharacters preserves normal characters")
    void testEscapeUnicodeControlCharactersPreservesNormal() {
        String input = "Hello World";
        String result = Utils.escapeUnicodeControlCharacters(input);
        assertEquals(input, result);
    }

    @Test
    @DisplayName("Round-trip escape and unescape")
    void testRoundTripEscape() {
        String original = "Hello\nWorld\t\"Test\"\\Backslash";
        String escaped = Utils.escape(original);
        String unescaped = escaped.translateEscapes();
        assertEquals(original, unescaped);
    }
}
