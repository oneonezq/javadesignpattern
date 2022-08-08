/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package state.exercise1;

import org.junit.jupiter.api.*;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static util.TestHelpers.*;

//DON'T CHANGE
public class StateTest {
    @Test
    public void testEmployeeFields() {
        for (Field field : Employee.class.getDeclaredFields()) {
            if (field.getType() == State.class) {
                // success
                System.out.println("Found field " + field);
                return;
            }
        }
        fail("Could not find field in Employer to hold current State");
    }

    @Test
    public void testStateClasses() throws ClassNotFoundException {
        Collection<Class<? extends State>> stateClasses = getClassesExtending(State.class);
        assertTrue(stateClasses.size() >= 4, "We need at least 4 state classes: Programmer, Manager, Retiree, End");
    }

    @Test
    public void testDefaultStateMethods() {
        // The default behaviour in our state machine should just ignore
        // unexpected events.
        class DefaultState extends State {
        }

        State state = new DefaultState();
        state.pay(null);
        state.advance(null);
        state.fire(null);
    }

    @Test
    public void testRetireeCannotBeFiredButCanAdvance() {
        Employee heinz = new Employee();
        int totalPaid = 0;
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        heinz.advance();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        heinz.advance();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(76000, totalPaid);
        heinz.fire();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(86000, totalPaid);
        heinz.advance();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(86000, totalPaid);
    }

    @Test
    public void testEmployeePromotedToManagerThenFired() {
        Employee heinz = new Employee();
        int totalPaid = 0;
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        heinz.advance();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        heinz.fire();
        assertEquals(102000, totalPaid);
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(102000, totalPaid);
    }

    @Test
    public void testEmployeeEarlyRetirement() {
        Employee heinz = new Employee();
        int totalPaid = 0;
        heinz.advance();
        heinz.advance();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(15000, totalPaid);
    }

    @Test
    public void testFastTrackManager() {
        Employee heinz = new Employee();
        int totalPaid = 0;
        heinz.advance();
        assertEquals(30000, heinz.pay());
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(90000, totalPaid);
        heinz.fire();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        totalPaid += heinz.pay();
        assertEquals(90000, totalPaid);
    }

    @Test
    public void testStandardTrackManager() {
        Employee heinz = new Employee();
        int totalPaid = 0;
        for (int i = 0; i < 24; i++) {
            totalPaid += heinz.pay();
        }

        heinz.advance();

        for (int i = 0; i < 24; i++) {
            totalPaid += heinz.pay();
        }

        heinz.advance();

        for (int i = 0; i < 12; i++) {
            totalPaid += heinz.pay();
        }
        assertEquals(852000, totalPaid);
    }
}
