/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package proxy.exercise1;

public class VirtualLutefisk implements Lutefisk {
    private Lutefisk realLutefisk;

    private Lutefisk lutefisk() {
        if (realLutefisk == null) realLutefisk = new RealLutefisk();
        return realLutefisk;
    }

    @Override
    public void eat() {
        lutefisk().eat();
    }

    @Override
    public int hashCode() {
        return lutefisk().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return lutefisk().equals(obj);
    }

    @Override
    public String toString() {
        return lutefisk().toString();
    }
}
