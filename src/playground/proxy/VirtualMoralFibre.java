/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package playground.proxy;

import java.util.function.Supplier;

public class VirtualMoralFibre implements MoralFibre {
    private MoralFibre fibre;
    private final Supplier<String> unitNameSupplier;

    public VirtualMoralFibre(Supplier<String> unitNameSupplier) {
        this.unitNameSupplier = unitNameSupplier;
    }

    private MoralFibre fibre() {
        if (fibre == null) fibre = new RealMoralFibre(
            unitNameSupplier.get()
        );
        return fibre;
    }

    @Override
    public void empowerEmployees() {
        fibre().empowerEmployees();
    }

    @Override
    public int hashCode() {
        return fibre().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return fibre().equals(obj);
    }

    @Override
    public String toString() {
        return fibre().toString();
    }
}
