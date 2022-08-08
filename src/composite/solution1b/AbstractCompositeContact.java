/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2021, Heinz Kabutz, All rights reserved.
 */
package composite.solution1b;

import java.util.*;

public abstract class AbstractCompositeContact implements Contact {
    public abstract void add(Contact contact);
    public abstract void remove(Contact contact);

    public final boolean isLeaf() {
        return false;
    }

    public abstract Iterator<Contact> children();
}
