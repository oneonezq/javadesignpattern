/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2021, Heinz Kabutz, All rights reserved.
 */
package visitor.solution1b;

public class EmailSendingVisitor implements Visitor {
    private final String msg;

    public EmailSendingVisitor(String msg) {
        this.msg = msg;
    }

    public void visit(Person p) {
        System.out.println("To: " + p.email());
        System.out.println("Msg: " + msg);
        System.out.println();
    }

    public void visit(DistributionList dl) {
        // do nothing
    }
}
