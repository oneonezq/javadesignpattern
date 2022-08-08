/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package memento.solution1;

import java.util.*;
import java.util.concurrent.*;

public class LabRat {
    private double cd4Ratio = 0.5;
    private boolean alive = true;

    public void blastWithRadar() {
        if (!alive)
            throw new IllegalStateException("lab rat is dead");
        cd4Ratio /= ThreadLocalRandom.current().nextDouble();
        cd4Ratio -= Math.floor(cd4Ratio);
        checkPulse();
    }

    public void feedDrugs() {
        if (!alive)
            throw new IllegalStateException("lab rat is dead");
        cd4Ratio *= ThreadLocalRandom.current().nextDouble();
        cd4Ratio -= Math.floor(cd4Ratio);
        checkPulse();
    }

    private void checkPulse() {
        if (cd4Ratio < 0.1) {
            alive = false;
        }
        System.out.printf(Locale.US, "Lab rat ha%s CD4 ratio of %.2f%n",
            (alive ? "s" : "d"), cd4Ratio);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setMemento(Memento memento) {
        LabRatMemento m = (LabRatMemento) memento;
        this.cd4Ratio = m.cd4Ratio;
        this.alive = m.alive;
    }

    public Memento createMemento() {
        return new LabRatMemento(cd4Ratio, alive);
    }

    private record LabRatMemento(double cd4Ratio, boolean alive)
        implements Memento {}
}
