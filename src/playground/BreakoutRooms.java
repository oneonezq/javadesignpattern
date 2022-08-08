/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2021, Heinz Kabutz, All rights reserved.
 */

package playground;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/*
  URL:
  Username:
  Password:

  What is your name?
    Heinz Kabutz
  What are you working on?
    Research into Java Programming Language nuts and bolts
  How long have you used Java?
    Since 1997, Java 1.1.
  Expectations from the course?
    Learn one new thing per day
  Something Personal?
    Grew up in Cape Town and was once left behind at a zoo school outing
 */
public class BreakoutRooms {
    public static void main(String... args) {
        List<String> students = Arrays.asList(
            "Student 1",
            "Student 2",
            "Student 3",
            "Student 4",
            "Student 5",
            "Student 6",
            "Student 7"
        );

        int numberOfRooms = 1;
        Collections.shuffle(students);
        List<List<String>> rooms = IntStream.range(0, numberOfRooms)
            .mapToObj(__ -> new ArrayList<String>())
            .collect(Collectors.toList());
        students.forEach(student -> allocateRandomly(rooms, student));

        System.out.println("* Screen sharing");
        int roomNumber = 1;
        for (List<String> room : rooms) {
            System.out.println("Room " + roomNumber++);
            System.out.println(room.stream()
                .collect(Collectors.joining(
                    "\n\t",
                    "\t* ",
                    "\n"
                )));
        }
    }

    private static void allocateRandomly(List<List<String>> rooms, String student) {
        rooms.stream().mapToInt(List::size).max().ifPresentOrElse(max -> {
            List<List<String>> eligible = rooms.stream()
                .filter(room -> room.size() < max)
                .collect(Collectors.toList());
            if (eligible.isEmpty()) eligible = rooms;
            int room = ThreadLocalRandom.current().nextInt(eligible.size());
            eligible.get(room).add(student);
        }, () -> {
            throw new IllegalArgumentException("Rooms is empty");
        });
    }
}
