/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2022, Heinz Kabutz, All rights reserved.
 */

package playground;

import java.util.*;
import java.util.stream.Stream;

public class BuilderOfStreams {
    public static void main(String... args) throws InterruptedException {
        Stream<Integer> stream1 = List.of(1, 2, 3).stream();
        Stream<Integer> stream2 = Set.of(1, 2, 3).stream();
        Stream<Integer> stream3 = new LinkedList<>(List.of(1, 2, 3)).stream();
        Stream<Integer> stream4 = new TreeSet<>(List.of(1, 2, 3)).stream();
        Stream<Integer> stream5 = new ArrayList<>(List.of(1, 2, 3)).stream();
        Stream<Integer> stream6 = Stream.<Integer>builder().add(1).add(2).build();

        Thread platform = Thread.ofPlatform()
            .priority(Thread.MAX_PRIORITY)
            .daemon()
            .start(() -> System.out.println("I'm a platform thread"));

        Thread virtual = Thread.ofVirtual()
            .start(() -> System.out.println("I'm a virtual thread"));

        platform.join();
        virtual.join();

    }
}
