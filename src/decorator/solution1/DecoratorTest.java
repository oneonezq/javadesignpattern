/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package decorator.solution1;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//DON'T CHANGE
public class DecoratorTest {
    @Test
    public void testRegexIterableStringDecorator() {
        Iterator<String> it = makeRegexIterable("^[AJHZ].*", "John", "Heinz", "Anton", null, "Zach", "Dirk").iterator();
        assertEquals("John", it.next());
        assertEquals("Heinz", it.next());
        assertEquals("Anton", it.next());
        assertEquals("Zach", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testRegexIterableRemove() {
        Iterator<String> it = makeRegexIterable("^[AJHZ].*", "John", "Heinz", "Anton", null, "Zach", "Dirk").iterator();
        assertEquals("John", it.next());
        assertThrows(UnsupportedOperationException.class,
            it::remove,
            "remove() on a RegexIterable should cause an UnsupportedOperationException");
    }

    @Test
    public void testRegexIterableIntegerDecorator() {
        Iterator<Integer> it = makeRegexIterable("^1.*0+$", 1, 4, 10235, 1000, 23, null, 10, 200).iterator();
        assertEquals(Integer.valueOf(1000), it.next());
        assertEquals(Integer.valueOf(10), it.next());
        assertFalse(it.hasNext());
    }

    @SuppressWarnings("unchecked")
    private <T> Iterable<T> makeRegexIterable(String regex, T... values) {
        Collection<T> col = new ArrayList<>();
        Collections.addAll(col, values);
        Object obj = new RegexIterable<>(col, regex);
        Iterable<T> result = (Iterable<T>) obj;
        return result;
    }

    @Test
    public void testMorphIterable() {
        Iterable<Integer> mit = makeMorphIterable();
        int sum = 0;
        for (Integer number : mit) {
            if (number != null) {
                sum += number;
            }
        }
        assertEquals(12365, sum);
    }

    private Iterable<Integer> makeMorphIterable() {
        Collection<String> nums = new ArrayList<>();

        Object mit = new MorphIterable<>(nums, s -> {
            if (s == null) return null;
            return Integer.valueOf(s);
        });
        Collections.addAll(nums, "1", "34", "12321", "9", null);
        @SuppressWarnings("unchecked")
        Iterable<Integer> it = (Iterable<Integer>) mit;
        return it;
    }

    @Test
    public void testMorphIterableRemove() {
        Iterable<Integer> mit = makeMorphIterable();

        for (Iterator<Integer> it = mit.iterator(); it.hasNext(); ) {
            Integer val = it.next();
            if (val == null || val < 30) {
                it.remove();
            }
        }

        int sum = 0;
        for (Integer number : mit) {
            sum += number;
        }
        assertEquals(12355, sum);
    }


    @Test
    public void testThreadSafeIterable() {
        Collection<String> nums = new ArrayList<>();
        Collections.addAll(nums, "34", "12321", "9");
        Object lock = new Object();

        Object tsit = new ThreadSafeIterable<>(nums, lock);

        @SuppressWarnings("unchecked")
        Iterable<String> it = (Iterable<String>) tsit;

        Iterator<String> iterator = it.iterator();
        assertEquals("34", iterator.next());
        assertEquals("12321", iterator.next());
        nums.add("3321");
        assertEquals("9", iterator.next());
        assertFalse(iterator.hasNext());

        // this does not really test thread safety.  To do that would require
        // some more work.  One way is to make the iteration slow, so that you
        // are guaranteed a clash.
    }

    @Test
    public void testThreadSafeIterableRemove() {
        Collection<String> nums = new ArrayList<>();
        Collections.addAll(nums, "34", "12321", "9");
        Object lock = new Object();

        Object tsit = new ThreadSafeIterable<>(nums, lock);

        @SuppressWarnings("unchecked")
        Iterable<String> itb = (Iterable<String>) tsit;
        Iterator<String> it = itb.iterator();
        assertEquals("34", it.next());
        assertThrows(UnsupportedOperationException.class,
            it::remove,
            "remove() on a ThreadSafeIterable should cause an UnsupportedOperationException");
    }
}
