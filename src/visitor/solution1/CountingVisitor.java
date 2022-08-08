/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package visitor.solution1;

import maths.*;

import java.util.concurrent.atomic.*;

/**
 * This must count how many leaves there are in structure, how many distribution
 * lists and what the average number of contacts in a distribution list.
 * <p/>
 * Use the Statistics class to work out the mean and variance for the list
 * lengths.
 */
public class CountingVisitor implements Visitor {
    private final LongAdder leafCount = new LongAdder();
    private final Statistics compositeStatistics = new Statistics();

    public void visit(Person p) {
        leafCount.increment();
    }

    public void visit(DistributionList dl) {
        compositeStatistics.add(dl.getNumberOfChildren());
    }

    public int getNumberOfLeaves() {
        return leafCount.intValue();
    }

    public int getNumberOfComposites() {
        return compositeStatistics.size();
    }

    public double getAverageNumberOfChildrenPerComposite() {
        return compositeStatistics.getMean();
    }

    public double getVarianceNumberOfChildrenPerComposite() {
        return compositeStatistics.getVariance();
    }
}
