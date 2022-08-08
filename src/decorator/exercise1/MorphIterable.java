/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package decorator.exercise1;

/**
 * This class should implement Iterable<B>.  The remove() method should remove
 * the item from the input Iterable.
 */
public class MorphIterable<A, B> {
    public MorphIterable(Iterable<A> input, Morpher<A, B> morpher) {
        throw new UnsupportedOperationException("todo");
    }

    public interface Morpher<A, B> {
        B morph(A a);
    }
}
