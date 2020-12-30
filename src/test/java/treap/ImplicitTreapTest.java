package treap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ImplicitTreapTest {
    private final ImplicitTreap<Integer> testImplictTreap = new ImplicitTreap<>();

    @BeforeEach
    void setTree() {
        testImplictTreap.add(1, 15);
        testImplictTreap.add(2, 32);
        testImplictTreap.add(3, 11);
        testImplictTreap.add(4, 17);
        testImplictTreap.add(5, 515);
        testImplictTreap.add(6, 67);
    }

    @Test
    void size() {
        assertEquals(6, testImplictTreap.size());
        testImplictTreap.add(7, 7);
        assertEquals(7, testImplictTreap.size());
        testImplictTreap.remove(5);
        assertEquals(6, testImplictTreap.size());
    }

    @Test
    void get() {
        assertEquals(15 ,testImplictTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 ,testImplictTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(515 ,testImplictTreap.get(5)); // [15, 32, 11, 17, {515}, 67]
        assertEquals(67 ,testImplictTreap.get(6)); // [15, 32, 11, 17, 515, {67}]
    }

    @Test
    void add() {
        assertEquals(17, testImplictTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplictTreap.size());

        testImplictTreap.add(4, 1111); // [15, 32, 11, 1111, 17, 515, 67]
        assertEquals(1111, testImplictTreap.get(4)); // [15, 32, 11, {1111}, 17, 515, 67]
        assertEquals(17, testImplictTreap.get(5)); // [15, 32, 11, 1111, {17}, 515, 67]
        assertEquals(7, testImplictTreap.size());

        testImplictTreap.add(4, 88); // [15, 32, 11, 88, 1111, 17, 515, 67]
        assertEquals(11, testImplictTreap.get(3)); // // [15, 32, {11}, 88, 1111, 17, 515, 67]
        assertEquals(88, testImplictTreap.get(4)); // [15, 32, 11, {88}, 1111, 17, 515, 67]
        assertEquals(1111, testImplictTreap.get(5)); // [15, 32, 11, 88, {1111}, 17, 515, 67]
        assertEquals(8, testImplictTreap.size());
    }

    @Test
    void remove() {
        assertEquals(17, testImplictTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplictTreap.size());

        testImplictTreap.remove(4); // [15, 32, 11, 515, 67]

        assertEquals(515, testImplictTreap.get(4)); // [15, 32, 11, {515}, 67]
        assertEquals(5, testImplictTreap.size());

        testImplictTreap.remove(4); // [15, 32, 11, 67]

        assertEquals(67, testImplictTreap.get(4)); // [15, 32, 11, {67}]
        assertEquals(4, testImplictTreap.size());

        testImplictTreap.remove(1);
        testImplictTreap.remove(1);
        testImplictTreap.remove(1);
        testImplictTreap.remove(1);

        assertTrue(testImplictTreap.isEmpty());
    }

    @Test
    void modifyValue() {
        assertEquals(17, testImplictTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplictTreap.size());

        testImplictTreap.modifyValue(4, 795);

        assertEquals(795, testImplictTreap.get(4)); // [15, 32, 11, {795}, 515, 67]
        assertEquals(6, testImplictTreap.size());

        testImplictTreap.modifyValue(4, 88);
        assertEquals(88, testImplictTreap.get(4)); // [15, 32, 11, {88}, 515, 67]
        assertEquals(6, testImplictTreap.size());
    }

    @Test
    void shiftLeft() {
        assertEquals(15 ,testImplictTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 ,testImplictTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(11 ,testImplictTreap.get(3)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(17 ,testImplictTreap.get(4)); // [15, {32}, 11, 17, 515, 67]

        testImplictTreap.shiftLeft(4);

        assertEquals(515 ,testImplictTreap.get(1)); // [{515}, 67, 15, 32, 11, 17]
        assertEquals(67 ,testImplictTreap.get(2)); // [515, {67}, 15, 32, 11, 17]
        assertEquals(15 ,testImplictTreap.get(3)); // [515, 67, {15}, 32, 11, 17]
        assertEquals(32 ,testImplictTreap.get(4)); // [515, 67, 15, {32}, 11, 17]
    }

    @Test
    void shiftRight() {
        assertEquals(15 ,testImplictTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 ,testImplictTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(11 ,testImplictTreap.get(3)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(17 ,testImplictTreap.get(4)); // [15, {32}, 11, 17, 515, 67]

        testImplictTreap.shiftRight(4);

        assertEquals(11 ,testImplictTreap.get(1)); // [{11}, 17, 515, 67, 15, 32]
        assertEquals(17 ,testImplictTreap.get(2)); // [11, {17}, 515, 67, 15, 32]
        assertEquals(515 ,testImplictTreap.get(3)); // [11, 17, {515}, 67, 15, 32]
        assertEquals(67 ,testImplictTreap.get(4)); // [11, 17, 515, {67}, 15, 32]
    }

    @Test
    void iteratorTest() {
        Iterator<Integer> iterator = testImplictTreap.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(15, iterator.next());
        assertEquals(32, iterator.next());
        assertEquals(11, iterator.next());
        assertEquals(17, iterator.next());
        assertEquals(515, iterator.next());
        assertEquals(67, iterator.next());
    }
}