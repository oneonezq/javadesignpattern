/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package decorator.exercise1;

import java.util.concurrent.locks.*;

// this class should implement Iterable<T>
public class ThreadSafeIterable<T> {
    // synchronize on the lock and copy the source into a new collection
    public ThreadSafeIterable(Iterable<T> source, Object lock) {
        throw new UnsupportedOperationException("todo");
    }

    // lock() the Java 5 lock and copy the source into a new collection
    public ThreadSafeIterable(Iterable<T> source, Lock lock) {
        throw new UnsupportedOperationException("todo");
    }
}
