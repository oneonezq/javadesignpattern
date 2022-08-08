/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package memento.exercise2;

public final class Employee {
    public enum Position {TESTER, PROGRAMMER, MANAGER}

    private int salary = 1000;
    private int balance = 2000;
    private Position position = Position.TESTER;

    public void pay() {
        balance += salary;
    }

    public void promote() {
        switch (position) {
            case TESTER -> salary += 400;
            case PROGRAMMER -> salary *= 3;
            case MANAGER -> salary *= 1.5;
        }
        position = switch (position) {
            case TESTER -> Position.PROGRAMMER;
            case PROGRAMMER, MANAGER -> Position.MANAGER;
        };
    }

    public boolean equals(Object o) {
        return o instanceof Employee employee &&
            salary == employee.salary
            && balance == employee.balance
            && position == employee.position;
    }

    public Memento createMemento() {
        return new EmployeeMemento(this);
    }

    public void setMemento(Memento m) {
        EmployeeMemento mi = (EmployeeMemento) m;
        this.salary = mi.salary;
        this.balance = mi.balance;
        this.position = mi.position;
    }

    private static class EmployeeMemento implements Memento {
        private final int salary;
        private final int balance;
        private final Employee.Position position;

        public EmployeeMemento(Employee employee) {
            this.salary = employee.salary;
            this.balance = employee.balance;
            this.position = employee.position;
        }
    }
}
