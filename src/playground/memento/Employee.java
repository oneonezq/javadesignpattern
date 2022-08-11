/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package playground.memento;

public class Employee {
    public enum Position {TESTER, PROGRAMMER, MANAGER}

    private MementoImpl state = new MementoImpl(
        1000, 2000, Position.TESTER
    );

    public void pay() {
        state = state.pay();
    }

    public void promote() {
        state = state.promote();
    }

    public Memento createMemento() {
        return state;
    }

    public void setMemento(Memento m) {
        this.state = (MementoImpl) m;
    }

    private record MementoImpl(int salary, int bankBalance,
                               Position position) implements Memento {
        public MementoImpl pay() {
            return new MementoImpl(salary,
                bankBalance + salary, position);
        }

        public MementoImpl promote() {
            var position = this.position;
            var salary = this.salary;
            switch (position) {
                case TESTER -> salary += 400;
                case PROGRAMMER -> salary *= 3;
                case MANAGER -> salary *= 1.5;
            }
            position = switch (position) {
                case TESTER -> Position.PROGRAMMER;
                case PROGRAMMER, MANAGER -> Position.MANAGER;
            };
            return new MementoImpl(salary, bankBalance, position);
        }
    }
}



