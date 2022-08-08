/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package command.exercise1;

import org.junit.jupiter.api.*;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static util.TestHelpers.*;

//DON'T CHANGE
public class CommandTest {
    @Test
    public void testThatCommandLeftUnchanged() {
        assertTrue(Command.class.isInterface(), "Command must remain an interface");
        assertEquals(1, Command.class.getDeclaredMethods().length, "Command should only have one method");
        try {
            Command.class.getDeclaredMethod("execute");
        } catch (NoSuchMethodException e) {
            fail("Command needs to keep the execute() method");
        }
        assertEquals(0, Command.class.getInterfaces().length, "Command should not extend any other interfaces");
    }

    @Test
    public void testThatFanLeftUnchanged() {
        assertFalse(Fan.class.isInterface(), "Fan must remain a class");
        assertEquals(Object.class, Fan.class.getSuperclass(), "Fan must keep on extending Object");
        assertEquals(0, Fan.class.getInterfaces().length, "Fan should not implement any interfaces");
        for (Method method : Fan.class.getDeclaredMethods()) {
            assertEquals(0, method.getParameterTypes().length, "Fan methods do not take parameters");
            assertTrue(method.getName().equals("startRotate") || method.getName().equals("stopRotate"), "Only two fan methods allowed - startRotate() and stopRotate()");
            assertTrue(Modifier.isPublic(method.getModifiers()), "Fan methods should be public");
        }
        assertEquals(0, Fan.class.getDeclaredFields().length, "Fan should have no fields");
    }

    @Test
    public void testThatLightLeftUnchanged() {
        assertFalse(Light.class.isInterface(), "Light must remain a class");
        assertEquals(Object.class, Light.class.getSuperclass(), "Light must keep on extending Object");
        assertEquals(0, Light.class.getInterfaces().length, "Light should not implement any interfaces");
        for (Method method : Light.class.getDeclaredMethods()) {
            assertEquals(0, method.getParameterTypes().length, "Light methods do not take parameters");
            assertTrue(method.getName().equals("turnOn") || method.getName().equals("turnOff"), "Only two light methods allowed - turnOn() and turnOff()");
            assertTrue(Modifier.isPublic(method.getModifiers()), "Light methods should be public");
        }
        assertEquals(0, Light.class.getDeclaredFields().length, "Light should have no fields");
    }

    @Test
    public void testThatSwitchIsCorrect() {
        assertFalse(Switch.class.isInterface(), "Switch should remain a class");
        assertTrue(Modifier.isFinal(Switch.class.getModifiers()), "Switch should remain final");
        assertEquals(Object.class, Switch.class.getSuperclass(), "Switch must keep on extending Object");
        assertEquals(0, Switch.class.getInterfaces().length, "Switch should not implement any interfaces");
        for (Method method : Switch.class.getDeclaredMethods()) {
            assertEquals(0, method.getParameterTypes().length, "Switch methods do not take parameters");
            assertTrue(method.getName().equals("flipUp") || method.getName().equals("flipDown"), "Only two switch methods allowed - flipUp() and flipDown()");
            assertTrue(Modifier.isPublic(method.getModifiers()), "Switch methods should be public");
        }
        assertTrue(Switch.class.getDeclaredFields().length > 0, "Switch should have fields");

        for (Field field : Switch.class.getDeclaredFields()) {
            assertNotSame(field.getType(), Fan.class, "Switch should not know about Fan");
            assertNotSame(field.getType(), Light.class, "Switch should not know about Light");
        }

        for (Constructor<?> constr : Switch.class.getDeclaredConstructors()) {
            for (Class<?> parameterType : constr.getParameterTypes()) {
                assertNotSame(parameterType, Fan.class, "Switch should not know about Fan");
                assertNotSame(parameterType, Light.class, "Switch should not know about Light");
            }
        }
    }

    @Test
    public void countNumberOfSubclasses() throws ClassNotFoundException {
        Collection<Class<? extends Command>> commands = getCommandClasses();
        assertEquals(1 << 2, commands.size());
    }

    private Collection<Class<? extends Command>> getCommandClasses() throws ClassNotFoundException {
        return getClassesExtending(Command.class);
    }

    @Test
    public void testFanSwitchCreation() {
        class TestFan extends Fan {
            private String lastMethod = null;

            public void startRotate() {
                lastMethod = "startRotate";
            }

            public void stopRotate() {
                lastMethod = "stopRotate";
            }
        }
        TestFan fan = new TestFan();
        assertNull(fan.lastMethod);

        Switch sw = SwitchFactory.make(fan);
        assertNull(fan.lastMethod);

        sw.flipUp();
        assertEquals("startRotate", fan.lastMethod);
        sw.flipUp();
        assertEquals("startRotate", fan.lastMethod);
        sw.flipDown();
        assertEquals("stopRotate", fan.lastMethod);
        sw.flipUp();
        assertEquals("startRotate", fan.lastMethod);
    }

    @Test
    public void testLightSwitchCreation() {
        class TestLight extends Light {
            private String lastMethod = null;

            public void turnOn() {
                lastMethod = "turnOn";
            }

            public void turnOff() {
                lastMethod = "turnOff";
            }
        }
        TestLight light = new TestLight();
        assertNull(light.lastMethod);

        Switch sw = SwitchFactory.make(light);
        assertNull(light.lastMethod);

        sw.flipUp();
        assertEquals("turnOn", light.lastMethod);
        sw.flipUp();
        assertEquals("turnOn", light.lastMethod);
        sw.flipDown();
        assertEquals("turnOff", light.lastMethod);
        sw.flipUp();
        assertEquals("turnOn", light.lastMethod);
    }
}
