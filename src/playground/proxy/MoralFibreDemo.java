/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package playground.proxy;

import eu.javaspecialists.books.dynamicproxies.Proxies;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoralFibreDemo {
    public static void main(String... args) {
        var fibre1 = new RealMoralFibre("coolUnit");
        var fibre2 = new RealMoralFibre("coolUnit");
        assertEquals(fibre1, fibre2);
        var fibre4 = new VirtualMoralFibre(() -> "coolUnit");
        assertEquals(fibre4, fibre1);
        assertEquals(fibre4, fibre4);
        assertEquals(fibre1, fibre4);
        MoralFibre fibre5 = Proxies.virtualProxy(MoralFibre.class,
            () -> new RealMoralFibre("coolUnit"));
        assertEquals(fibre1, fibre5);
        assertEquals(fibre4, fibre5);
        assertEquals(fibre5, fibre5);
    }
}
