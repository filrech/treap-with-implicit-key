package treap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ImplicitTreapTest {
    private final ImplicitTreap<Integer> testImplicitTreap = new ImplicitTreap<>();

    @BeforeEach
    void setTree() {
        testImplicitTreap.add(1, 15);
        testImplicitTreap.add(2, 32);
        testImplicitTreap.add(3, 11);
        testImplicitTreap.add(4, 17);
        testImplicitTreap.add(5, 515);
        testImplicitTreap.add(6, 67);
    }

    @Test
    void size() {
        assertEquals(6, testImplicitTreap.size());
        testImplicitTreap.add(7, 7);
        assertEquals(7, testImplicitTreap.size());
        testImplicitTreap.remove(5);
        assertEquals(6, testImplicitTreap.size());
    }

    @Test
    void get() {
        assertEquals(15 , testImplicitTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 , testImplicitTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(515 , testImplicitTreap.get(5)); // [15, 32, 11, 17, {515}, 67]
        assertEquals(67 , testImplicitTreap.get(6)); // [15, 32, 11, 17, 515, {67}]
    }

    @Test
    void add() {
        assertEquals(17, testImplicitTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplicitTreap.size());

        testImplicitTreap.add(4, 1111); // [15, 32, 11, 1111, 17, 515, 67]
        assertEquals(1111, testImplicitTreap.get(4)); // [15, 32, 11, {1111}, 17, 515, 67]
        assertEquals(17, testImplicitTreap.get(5)); // [15, 32, 11, 1111, {17}, 515, 67]
        assertEquals(7, testImplicitTreap.size());

        testImplicitTreap.add(4, 88); // [15, 32, 11, 88, 1111, 17, 515, 67]
        assertEquals(11, testImplicitTreap.get(3)); // // [15, 32, {11}, 88, 1111, 17, 515, 67]
        assertEquals(88, testImplicitTreap.get(4)); // [15, 32, 11, {88}, 1111, 17, 515, 67]
        assertEquals(1111, testImplicitTreap.get(5)); // [15, 32, 11, 88, {1111}, 17, 515, 67]
        assertEquals(8, testImplicitTreap.size());
    }

    @Test
    void remove() {
        assertEquals(17, testImplicitTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplicitTreap.size());

        testImplicitTreap.remove(4); // [15, 32, 11, 515, 67]

        assertEquals(515, testImplicitTreap.get(4)); // [15, 32, 11, {515}, 67]
        assertEquals(5, testImplicitTreap.size());

        testImplicitTreap.remove(4); // [15, 32, 11, 67]

        assertEquals(67, testImplicitTreap.get(4)); // [15, 32, 11, {67}]
        assertEquals(4, testImplicitTreap.size());

        testImplicitTreap.remove(1);
        testImplicitTreap.remove(1);
        testImplicitTreap.remove(1);
        testImplicitTreap.remove(1);

        assertTrue(testImplicitTreap.isEmpty());
    }

    @Test
    void modifyValue() {
        assertEquals(17, testImplicitTreap.get(4)); // [15, 32, 11, {17}, 515, 67]
        assertEquals(6, testImplicitTreap.size());

        testImplicitTreap.modifyValue(4, 795);

        assertEquals(795, testImplicitTreap.get(4)); // [15, 32, 11, {795}, 515, 67]
        assertEquals(6, testImplicitTreap.size());

        testImplicitTreap.modifyValue(4, 88);
        assertEquals(88, testImplicitTreap.get(4)); // [15, 32, 11, {88}, 515, 67]
        assertEquals(6, testImplicitTreap.size());
    }

    @Test
    void shiftLeft() {
        assertEquals(15 , testImplicitTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 , testImplicitTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(11 , testImplicitTreap.get(3)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(17 , testImplicitTreap.get(4)); // [15, {32}, 11, 17, 515, 67]

        testImplicitTreap.shiftLeft(4);

        assertEquals(515 , testImplicitTreap.get(1)); // [{515}, 67, 15, 32, 11, 17]
        assertEquals(67 , testImplicitTreap.get(2)); // [515, {67}, 15, 32, 11, 17]
        assertEquals(15 , testImplicitTreap.get(3)); // [515, 67, {15}, 32, 11, 17]
        assertEquals(32 , testImplicitTreap.get(4)); // [515, 67, 15, {32}, 11, 17]
    }

    @Test
    void shiftRight() {
        assertEquals(15 , testImplicitTreap.get(1)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(32 , testImplicitTreap.get(2)); // [15, {32}, 11, 17, 515, 67]
        assertEquals(11 , testImplicitTreap.get(3)); // [{15}, 32, 11, 17, 515, 67]
        assertEquals(17 , testImplicitTreap.get(4)); // [15, {32}, 11, 17, 515, 67]

        testImplicitTreap.shiftRight(4);

        assertEquals(11 , testImplicitTreap.get(1)); // [{11}, 17, 515, 67, 15, 32]
        assertEquals(17 , testImplicitTreap.get(2)); // [11, {17}, 515, 67, 15, 32]
        assertEquals(515 , testImplicitTreap.get(3)); // [11, 17, {515}, 67, 15, 32]
        assertEquals(67 , testImplicitTreap.get(4)); // [11, 17, 515, {67}, 15, 32]
    }

    @Test
    void iteratorTest() {
        Iterator<Integer> iterator = testImplicitTreap.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(15, iterator.next());
        assertEquals(32, iterator.next());
        assertEquals(11, iterator.next());
        assertEquals(17, iterator.next());
        assertEquals(515, iterator.next());
        assertEquals(67, iterator.next());
    }
}