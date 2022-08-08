/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2021, Heinz Kabutz, All rights reserved.
 */
package composite.solution1b;

import java.util.*;

public class ContactIterator implements Iterator<Contact> {
    private final Deque<Iterator<Contact>> unfinishedIterators =
        new ArrayDeque<>();
    private Contact nextLeafContact = null;
    private Iterator<Contact> lastIteratorUsed = null;
    private boolean readyToRemove = false;

    public ContactIterator(Contact contact) {
        if (contact.isLeaf()) nextLeafContact = contact;
        else push(contact);
    }

    private void push(Contact contact) {
        unfinishedIterators.addLast(
            ((AbstractCompositeContact) contact).children());
    }

    public boolean hasNext() {
        if (nextLeafContact == null) {
            nextLeafContact = findNextLeafContact();
        }
        return nextLeafContact != null;
    }

    private Contact findNextLeafContact() {
        while (!unfinishedIterators.isEmpty()) {
            lastIteratorUsed = unfinishedIterators.peekLast();
            if (lastIteratorUsed.hasNext()) {
                Contact c = lastIteratorUsed.next();
                if (c.isLeaf()) {
                    return c;
                }
                push(c);
            } else {
                unfinishedIterators.removeLast();
            }
        }
        return null;
    }

    public Contact next() {
        if (!hasNext()) throw new NoSuchElementException();
        Contact result = nextLeafContact;
        nextLeafContact = null;
        readyToRemove = true;
        return result;
    }

    public void remove() {
        if (!readyToRemove) {
            throw new IllegalStateException("Cannot call remove before calling next");
        }
        if (lastIteratorUsed == null) {
            throw new IllegalStateException("Root object was a leaf - cannot remove");
        }
        lastIteratorUsed.remove();
        // We reset 'readyToRemove' after (not before) success, similar to ArrayList$Itr
        readyToRemove = false;
    }
}
