
/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package composite.solution1;

import java.util.*;

public interface Contact {
    void sendMail(String msg);

    default void add(Contact contact) {}
    default void remove(Contact contact) {}

    default boolean isLeaf() {
        return true;
    }

    default Iterator<Contact> children() {
        return Collections.emptyIterator();
    }
}
