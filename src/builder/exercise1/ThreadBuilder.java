/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package builder.exercise1;

public class ThreadBuilder {
    private final Runnable target;
    private final String name;
    private ThreadGroup threadGroup;
    private long stackSize;
    private boolean inheritThreadLocals;
    private Boolean daemon;
    private Integer priority;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ThreadBuilder(Runnable target, String name) {
        this.target = target;
        this.name = name;
    }

    public Thread build() {
        Thread thread = new Thread(threadGroup, target, name, stackSize,
            inheritThreadLocals);
        if (daemon != null) thread.setDaemon(daemon);
        if (priority != null) thread.setPriority(priority);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return thread;
    }

    public Thread start() {
        Thread thread = build();
        thread.start();
        return thread;
    }

    public ThreadBuilder threadGroup(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
        return this;
    }

    public ThreadBuilder stackSize(long stackSize) {
        this.stackSize = stackSize;
        return this;
    }

    public ThreadBuilder inheritThreadLocals(boolean inheritThreadLocals) {
        this.inheritThreadLocals = inheritThreadLocals;
        return this;
    }

    public ThreadBuilder daemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    public ThreadBuilder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }
}
