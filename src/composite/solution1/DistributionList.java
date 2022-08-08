/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package composite.solution1;

import java.util.*;

public class DistributionList implements Contact {
    private final List<Contact> contacts = new ArrayList<>();

    public void sendMail(String msg) {
        contacts.forEach(contact -> contact.sendMail(msg));
    }

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public void remove(Contact contact) {
        contacts.remove(contact);
    }

    public boolean isLeaf() {
        return false;
    }

    public Iterator<Contact> children() {
        return contacts.iterator();
    }
}
