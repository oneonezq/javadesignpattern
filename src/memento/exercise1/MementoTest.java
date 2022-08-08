/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package memento.exercise1;

import org.junit.jupiter.api.*;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

//DON'T CHANGE
public class MementoTest {
    private void killRatWithRadar(LabRat rat) {
        while (rat.isAlive()) {
            rat.blastWithRadar();
        }
    }

    private void killRatWithDrugs(LabRat rat) {
        while (rat.isAlive()) {
            rat.feedDrugs();
        }
    }

    @Test
    public void testLabRatWithRadar() throws Exception {
        LabRat rat = new LabRat();
        Object memento = createMemento(rat);
        killRatWithRadar(rat);
        assertFalse(rat.isAlive());
        setMemento(rat, memento);
        assertTrue(rat.isAlive());
    }

    @Test
    public void testLabRatWithDrugs() throws Exception {
        LabRat rat = new LabRat();
        Object memento = createMemento(rat);
        killRatWithDrugs(rat);
        assertFalse(rat.isAlive());
        setMemento(rat, memento);
        assertTrue(rat.isAlive());
    }

    private Object createMemento(LabRat rat) throws Exception {
        return findCreateMementoMethod().invoke(rat);
    }

    private Method findCreateMementoMethod() throws Exception {
        for (Method method : LabRat.class.getDeclaredMethods()) {
            if (!method.getReturnType().isPrimitive() && method.getParameterTypes().length == 0) {
                return method;
            }
        }
        fail("Could not find the method for creating a memento.  We need a method that returns an object and without parameters.");
        return null;
    }

    private void setMemento(LabRat rat, Object memento) throws Exception {
        findSetMementoMethod().invoke(rat, memento);
    }

    @Test
    public void testMementoCreateAndSetTypesMatch() throws Exception {
        Method createMemento = findCreateMementoMethod();
        Method setMemento = findSetMementoMethod();
        assertSame(createMemento.getReturnType(), setMemento.getParameterTypes()[0], "The methods to create a memento should return the same type as the method for setting the memento takes as a parameter");
    }

    private Method findSetMementoMethod() throws Exception {
        for (Method method : LabRat.class.getDeclaredMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1 && !parameterTypes[0].isPrimitive()
                && !method.isSynthetic()) {
                return method;
            }
        }
        fail("Could not find the method for setting the memento.  We need a method that takes one object as a parameter.");
        return null;
    }

    @Test
    public void testMementoStructure() throws Exception {
        Class<?> mementoImplClass = findMementoImplClass();

        assertNotNull(mementoImplClass, "We expected to find one inner class, the Memento implementation");
        assertTrue(Modifier.isStatic(mementoImplClass.getModifiers()), "The Memento implementation should probably be static to avoid memory leaks");
        assertTrue(Modifier.isPrivate(mementoImplClass.getModifiers()), "The Memento implementation should probably be private to avoid fake creation");
        assertFalse(Modifier.isStatic(findCreateMementoMethod().getModifiers()), "Method for creating mementos is typically not static");
        assertFalse(Modifier.isStatic(findSetMementoMethod().getModifiers()), "Method for setting mementos is typically not static");
    }

    private Class<?> findMementoImplClass() {
        Class<?>[] classes = LabRat.class.getDeclaredClasses();
        for (Class<?> clazz : classes) {
            if (!clazz.isAnonymousClass()) return clazz;
        }
        return null;
    }
}
