/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package decorator.exercise1;

public class RegexIterable<T> {
    // at construction, we build up a new list and add all those
    // objects whose toString() method matches the regular expression
    // Our iterator then simply walks over that list.  remove() should not be
    // allowed
    public RegexIterable(Iterable<T> it, String regex) {
        throw new UnsupportedOperationException("todo");
    }
}
