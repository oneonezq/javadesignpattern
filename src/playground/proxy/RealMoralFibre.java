/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package playground.proxy;

import java.util.Objects;

public class RealMoralFibre implements MoralFibre {
    private final String unitName;

    public RealMoralFibre(String unitName) {
        this.unitName = Objects.requireNonNull(unitName);
    }

    @Override
    public void empowerEmployees() {
        System.out.println("RealMoralFibre.empowerEmployees");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MoralFibre)) return false;
        if (!(o instanceof RealMoralFibre that)) return o.equals(this);
        return unitName.equals(that.unitName);
    }

    @Override
    public int hashCode() {
        return unitName.hashCode();
    }
}
