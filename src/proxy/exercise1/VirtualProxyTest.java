/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package proxy.exercise1;

import org.junit.jupiter.api.*;
import util.*;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//DON'T CHANGE
public class VirtualProxyTest {
    public static final Class<?> LUTEFISK_CLASS;

    static {
        try {
            char[] lutefiskName = {'L', 'u', 't', 'e', 'f', 'i', 's', 'k'};
            LUTEFISK_CLASS = ClassHelper.getClass(new String(lutefiskName));
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testLutefiskIsInterface() throws IllegalAccessException, InstantiationException {
        assertTrue(LUTEFISK_CLASS.isInterface(), "We need to convert Lutefisk into an interface, it is our 'Subject'");
    }

    @Test
    public void testScandinaviansHaveReferenceToVirtualLutefisk() throws IllegalAccessException, InstantiationException {
        Class<?> virtualLutefiskClass = findVirtualLutefiskClass();
        for (Field field : Swede.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(LUTEFISK_CLASS)) {
                field.setAccessible(true);
                assertTrue(virtualLutefiskClass.isInstance(field.get(new Swede())), "lutefisk field in Swede should be assigned to a VirtualLutefisk");
            }
        }
        for (Field field : Norwegian.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(LUTEFISK_CLASS)) {
                field.setAccessible(true);
                assertTrue(virtualLutefiskClass.isInstance(field.get(new Norwegian())), "lutefisk field in Norwegian should be assigned to a VirtualLutefisk");
            }
        }
    }

    @Test
    public void testScandinaviansHaveNonNullReferenceToVirtualLutefisk() throws IllegalAccessException, InstantiationException {
        Class<?> virtualLutefiskClass = findVirtualLutefiskClass();
        for (Field field : Swede.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(LUTEFISK_CLASS)) {
                field.setAccessible(true);
                assertTrue(virtualLutefiskClass.isInstance(field.get(new Swede())), "lutefisk field in Swede should be assigned to a VirtualLutefisk");
            }
        }
        for (Field field : Norwegian.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(LUTEFISK_CLASS)) {
                field.setAccessible(true);
                assertTrue(virtualLutefiskClass.isInstance(field.get(new Norwegian())), "lutefisk field in Norwegian should be assigned to a VirtualLutefisk");
            }
        }
    }

    @Test
    public void testRealLutefiskClassExists() throws Exception {
        try {
            Class<?> realLutefiskClass =
                ClassHelper.getClass("RealLutefisk").asSubclass(LUTEFISK_CLASS);
            Object realLutefisk = realLutefiskClass.getConstructor().newInstance();
            eat(realLutefisk);
        } catch (ClassCastException e) {
            fail("The RealLutefisk should be derived from Lutefisk");
        } catch (ClassNotFoundException e) {
            fail("We need a RealLutefisk class that contains the original Lutefisk code");
        }
    }

    private void eat(Object realLutefisk) throws ReflectiveOperationException {
        LUTEFISK_CLASS.getMethod("eat").invoke(realLutefisk);
    }

    @Test
    public void testVirtualLutefiskClassExists() throws Exception {
        try {
            Class<?> virtualLutefiskClass = findVirtualLutefiskClass();
            Object virtualLutefisk = virtualLutefiskClass.getConstructor().newInstance();
            assertEquals(1, virtualLutefiskClass.getDeclaredFields().length, "Virtual Lutefisk should only have one field, the delegate");
            Field delegate = virtualLutefiskClass.getDeclaredFields()[0];
            assertTrue(delegate.getType().isAssignableFrom(LUTEFISK_CLASS), "Delegate should be of type Lutefisk");
            delegate.setAccessible(true);
            assertNull(delegate.get(virtualLutefisk), "Initially the delegate field should be null");
            eat(virtualLutefisk);
            assertNotNull(delegate.get(virtualLutefisk), "After calling eat(), the delegate field should NOT be null");
        } catch (ClassCastException e) {
            fail("The VirtualLutefisk should be derived from Lutefisk");
        }
    }

    private Class<?> findVirtualLutefiskClass() {
        try {
            return ClassHelper.getClass("VirtualLutefisk").asSubclass(LUTEFISK_CLASS);
        } catch (ClassNotFoundException e) {
            fail("We need a VirtualLutefisk that constructs the real lutefisk on demand");
            return null; // will never be called.
        }
    }

    @Test
    public void testForObjectMethods() throws Throwable {
        Collection<String> missingMethods = new ArrayList<>();

        Class<?> virtualLutefiskClass = findVirtualLutefiskClass();
        try {
            var hashCode = virtualLutefiskClass.getDeclaredMethod("hashCode");
            var lutefisk = virtualLutefiskClass.getConstructor().newInstance();
            try {
                hashCode.invoke(lutefisk);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        } catch (NoSuchMethodException e) {
            missingMethods.add("hashCode");
        }
        try {
            var toString = virtualLutefiskClass.getDeclaredMethod("toString");
            var lutefisk = virtualLutefiskClass.getConstructor().newInstance();
            try {
                toString.invoke(lutefisk);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        } catch (NoSuchMethodException e) {
            missingMethods.add("toString");
        }
        try {
            var equals = virtualLutefiskClass.getDeclaredMethod("equals", Object.class);
            var lutefisk = virtualLutefiskClass.getConstructor().newInstance();
            try {
                equals.invoke(lutefisk, lutefisk);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        } catch (NoSuchMethodException e) {
            missingMethods.add("equals");
        }
        if (!missingMethods.isEmpty())
            fail("Did you forget to implement the Object methods in the virtual proxy: " +
                missingMethods + "?");
    }
}
