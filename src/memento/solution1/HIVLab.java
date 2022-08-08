/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package memento.solution1;

import java.util.function.*;

public class HIVLab {
    public static void main(String... args) {
        LabRat mickey = new LabRat();
        while (true) {
            experiment(mickey, LabRat::feedDrugs);
            experiment(mickey, LabRat::blastWithRadar);
        }
    }

    // Make sure mickey can get reanimated if he dies
    private static void experiment(LabRat rat,
                                   Consumer<LabRat> experiment) {
        Memento memento = rat.createMemento();
        experiment.accept(rat);
        if (!rat.isAlive()) {
            rat.setMemento(memento);
        }
    }
}
