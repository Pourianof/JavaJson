package io.github.pourianof.ListMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ListMap Tests")
public class ListMapTest {

    @Test
    @DisplayName("Create empty ListMap")
    void testCreateEmptyListMap() {
        ListMap<String, Integer> map = new ListMap<>();
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("Put single entry")
    void testPutSingleEntry() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        assertEquals(1, map.size());
        assertEquals(1, map.get("one"));
    }

    @Test
    @DisplayName("Put multiple entries")
    void testPutMultipleEntries() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("Put overwrites existing key")
    void testPutOverwritesExistingKey() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("key", 1);
        map.put("key", 2);
        assertEquals(1, map.size());
        assertEquals(2, map.get("key"));
    }

    @Test
    @DisplayName("Get value by index - at() method")
    void testAtMethod() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        assertEquals(1, map.at(0));
        assertEquals(2, map.at(1));
        assertEquals(3, map.at(2));
    }

    @Test
    @DisplayName("at() preserves insertion order")
    void testAtMethodPreservesInsertionOrder() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("z", 26);
        map.put("a", 1);
        map.put("m", 13);
        
        assertEquals(26, map.at(0));
        assertEquals(1, map.at(1));
        assertEquals(13, map.at(2));
    }

    @Test
    @DisplayName("getKeyByIndex returns key at index")
    void testGetKeyByIndex() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        assertEquals("one", map.getKeyByIndex(0));
        assertEquals("two", map.getKeyByIndex(1));
        assertEquals("three", map.getKeyByIndex(2));
    }

    @Test
    @DisplayName("getOrderedList returns values in insertion order")
    void testGetOrderedList() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        ArrayList<Integer> ordered = map.getOrderedList();
        assertEquals(3, ordered.size());
        assertEquals(1, ordered.get(0));
        assertEquals(2, ordered.get(1));
        assertEquals(3, ordered.get(2));
    }

    @Test
    @DisplayName("getOrderedList returns empty list for empty map")
    void testGetOrderedListEmpty() {
        ListMap<String, Integer> map = new ListMap<>();
        ArrayList<Integer> ordered = map.getOrderedList();
        assertEquals(0, ordered.size());
    }

    @Test
    @DisplayName("getSubOrderedList with start and end index")
    void testGetSubOrderedList() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        
        ArrayList<Integer> subset = map.getSubOrderedList(1, 3);
        assertEquals(2, subset.size());
        assertEquals(2, subset.get(0));
        assertEquals(3, subset.get(1));
    }

    @Test
    @DisplayName("getSubOrderedList with start index only")
    void testGetSubOrderedListFromStart() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        ArrayList<Integer> subset = map.getSubOrderedList(1);
        assertEquals(2, subset.size());
        assertEquals(2, subset.get(0));
        assertEquals(3, subset.get(1));
    }

    @Test
    @DisplayName("getSubOrderedList with index 0 to size")
    void testGetSubOrderedListFullRange() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        
        ArrayList<Integer> subset = map.getSubOrderedList(0, 2);
        assertEquals(2, subset.size());
        assertEquals(1, subset.get(0));
        assertEquals(2, subset.get(1));
    }

    @Test
    @DisplayName("clear removes all entries")
    void testClear() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("clear makes map empty")
    void testClearMakesEmpty() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("key", 1);
        map.clear();
        
        assertNull(map.get("key"));
        assertEquals(0, map.getOrderedList().size());
    }

    @Test
    @DisplayName("putAll adds all entries from another ListMap")
    void testPutAll() {
        ListMap<String, Integer> map1 = new ListMap<>();
        map1.put("one", 1);
        map1.put("two", 2);
        
        ListMap<String, Integer> map2 = new ListMap<>();
        map2.put("three", 3);
        map2.put("four", 4);
        
        map1.putAll(map2);
        assertEquals(4, map1.size());
        assertEquals(1, map1.get("one"));
        assertEquals(2, map1.get("two"));
        assertEquals(3, map1.get("three"));
        assertEquals(4, map1.get("four"));
    }

    @Test
    @DisplayName("putAll preserves order from both maps")
    void testPutAllPreservesOrder() {
        ListMap<String, Integer> map1 = new ListMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        
        ListMap<String, Integer> map2 = new ListMap<>();
        map2.put("c", 3);
        map2.put("d", 4);
        
        map1.putAll(map2);
        ArrayList<Integer> ordered = map1.getOrderedList();
        assertEquals(1, ordered.get(0));
        assertEquals(2, ordered.get(1));
        assertEquals(3, ordered.get(2));
        assertEquals(4, ordered.get(3));
    }

    @Test
    @DisplayName("Contains functionality inherited from HashMap")
    void testContainsKey() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("key", 1);
        
        assertTrue(map.containsKey("key"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    @DisplayName("ContainsValue functionality inherited from HashMap")
    void testContainsValue() {
        ListMap<String, Integer> map = new ListMap<>();
        map.put("key", 1);
        
        assertTrue(map.containsValue(1));
        assertFalse(map.containsValue(2));
    }

    @Test
    @DisplayName("ListMap with String values")
    void testListMapWithStringValues() {
        ListMap<String, String> map = new ListMap<>();
        map.put("name", "John");
        map.put("city", "New York");
        
        assertEquals("John", map.get("name"));
        assertEquals("New York", map.at(1));
    }

    @Test
    @DisplayName("ListMap with different key-value types")
    void testListMapWithDifferentTypes() {
        ListMap<Integer, String> map = new ListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        assertEquals("one", map.get(1));
        assertEquals("three", map.at(2));
        assertEquals(2, map.getKeyByIndex(1));
    }

    @Test
    @DisplayName("Multiple operations sequence")
    void testMultipleOperations() {
        ListMap<String, Integer> map = new ListMap<>();
        
        // Add entries
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        assertEquals(3, map.size());
        
        // Get by index
        assertEquals(1, map.at(0));
        assertEquals(2, map.at(1));
        
        // Get key by index
        assertEquals("a", map.getKeyByIndex(0));
        
        // Get ordered list
        ArrayList<Integer> ordered = map.getOrderedList();
        assertEquals(3, ordered.size());
        
        // Get subset
        ArrayList<Integer> subset = map.getSubOrderedList(1, 3);
        assertEquals(2, subset.size());
    }
}
