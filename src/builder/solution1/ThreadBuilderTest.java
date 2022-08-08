/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package builder.solution1;

import org.junit.jupiter.api.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

//DON'T CHANGE
public class ThreadBuilderTest {
    @Test
    public void testPlainThread() throws InterruptedException {
        AtomicBoolean ran = new AtomicBoolean(false);
        Thread test = new ThreadBuilder(
            () -> ran.set(true), "testPlainThread()")
            .start();
        test.join();
        assertTrue(ran.get());
        assertEquals("testPlainThread()", test.getName());
        assertFalse(test.isDaemon());
        assertEquals(Thread.NORM_PRIORITY, test.getPriority());
    }

    @Test
    public void testThreadGroup() throws InterruptedException {
        ThreadGroup group = new ThreadGroup("testGroup");
        AtomicBoolean ran = new AtomicBoolean(false);
        Phaser phaser = new Phaser(2);
        Thread test = new ThreadBuilder(
            () -> {
                ran.set(true);
                phaser.arriveAndAwaitAdvance();
            }, "testThreadGroup()")
            .threadGroup(group)
            .stackSize(1000)
            .inheritThreadLocals(true)
            .start();
        assertEquals(group, test.getThreadGroup());
        phaser.arriveAndDeregister();
        test.join();
        assertTrue(ran.get());
        assertEquals("testThreadGroup()", test.getName());
        assertFalse(test.isDaemon());
        assertEquals(Thread.NORM_PRIORITY, test.getPriority());
    }

    @Test
    public void testDaemonTrue() throws InterruptedException {
        AtomicBoolean ran = new AtomicBoolean(false);
        Thread test = new ThreadBuilder(
            () -> ran.set(true), "testDaemonTrue()")
            .daemon(true)
            .start();
        assertTrue(test.isDaemon());
        test.join();
        assertTrue(ran.get());
        assertEquals("testDaemonTrue()", test.getName());
    }

    @Test
    public void testDaemonParentUnsetChild() throws InterruptedException {
        // parent daemon
        // child not set -> daemon
        testDaemonChild(true, null, true);
    }

    @Test
    public void testNotDaemonParentUnsetChild() throws InterruptedException {
        // parent not daemon
        // child not set -> not daemon
        testDaemonChild(false, null, false);
    }

    @Test
    public void testDaemonParentDaemonChild() throws InterruptedException {
        // parent daemon
        // child set to daemon -> daemon
        testDaemonChild(true, true, true);
    }

    @Test
    public void testDaemonParentNotDaemonChild() throws InterruptedException {
        // parent daemon
        // child set to not daemon -> not daemon
        testDaemonChild(true, false, false);
    }

    @Test
    public void testNotDaemonParentDaemonChild() throws InterruptedException {
        // parent not daemon
        // child set to daemon -> daemon
        testDaemonChild(false, true, true);
    }

    @Test
    public void testNotDaemonParentNotDaemonChild() throws InterruptedException {
        // parent not daemon
        // child set to not daemon -> not daemon
        testDaemonChild(false, false, false);
    }

    private void testDaemonChild(boolean parentDaemon,
                                 Boolean childDaemonOverride,
                                 boolean childDaemonExpected) throws InterruptedException {
        AtomicBoolean childIsDaemon = new AtomicBoolean();
        Thread parent = new ThreadBuilder(
            () -> {
                ThreadBuilder childBuilder = new ThreadBuilder(
                    () -> childIsDaemon.set(Thread.currentThread().isDaemon()),
                    "child");
                if (childDaemonOverride != null)
                    childBuilder.daemon(childDaemonOverride);
                Thread child = childBuilder.build();
                child.start();
                try {
                    child.join();
                } catch (InterruptedException e) {
                    throw new CancellationException("interrupted");
                }
            },
            "parent")
            .daemon(parentDaemon).start();
        parent.join();
        assertEquals(
            childDaemonExpected, childIsDaemon.get(), "ThreadBuilderTest.testDaemonChild " +
                "parentDaemon = " + parentDaemon + ", " +
                "childDaemonOverride = " + childDaemonOverride + ", " +
                "childDaemonExpected = " + childDaemonExpected);
    }

    @Test
    public void testPrioritySet() throws InterruptedException {
        for (int parentPriority = Thread.MIN_PRIORITY; parentPriority <= Thread.MAX_PRIORITY; parentPriority++) {
            for (int childPriority = Thread.MIN_PRIORITY; childPriority <= Thread.MAX_PRIORITY; childPriority++) {
                testPriorityChild(parentPriority, childPriority, childPriority);
            }
        }
    }

    @Test
    public void testPriorityUnset() throws InterruptedException {
        for (int parentPriority = Thread.MIN_PRIORITY; parentPriority <= Thread.MAX_PRIORITY; parentPriority++) {
            testPriorityChild(parentPriority, null, parentPriority);
        }
    }

    private void testPriorityChild(int parentPriority,
                                   Integer childPriorityOverride,
                                   int childPriorityExpected) throws InterruptedException {
        AtomicInteger childPriority = new AtomicInteger();
        Thread parent = new ThreadBuilder(
            () -> {
                ThreadBuilder childBuilder = new ThreadBuilder(
                    () -> childPriority.set(Thread.currentThread().getPriority()),
                    "child");
                if (childPriorityOverride != null)
                    childBuilder.priority(childPriorityOverride);
                Thread child = childBuilder.build();
                child.start();
                try {
                    child.join();
                } catch (InterruptedException e) {
                    throw new CancellationException("interrupted");
                }
            },
            "parent")
            .priority(parentPriority).start();
        parent.join();
        assertEquals(
            childPriorityExpected, childPriority.get(), "ThreadBuilderTest.testPriorityChild " +
                "parentPriority = " + parentPriority + ", " +
                "childPriorityOverride = " + childPriorityOverride + ", " +
                "childPriorityExpected = " + childPriorityExpected);
    }

    @Test
    public void testUncaughtExceptionHandler() throws InterruptedException {
        var gingerbreadMan = new IllegalStateException("Gingerbread Man");
        AtomicBoolean ran = new AtomicBoolean();
        AtomicBoolean caught = new AtomicBoolean();
        Thread test = new ThreadBuilder(
            () -> {
                ran.set(true);
                throw gingerbreadMan;
            },
            "testUncaughtExceptionHandler()")
            .uncaughtExceptionHandler((thread, exception) -> {
                System.out.println(exception.getMessage());
                if (exception == gingerbreadMan) caught.set(true);
            })
            .start();
        test.join();
        assertTrue(ran.get());
        assertTrue(caught.get(), "Gingerbread Man not caught");
    }
}
