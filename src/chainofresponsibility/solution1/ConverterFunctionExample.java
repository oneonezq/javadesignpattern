/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package chainofresponsibility.solution1;

import java.util.function.*;

public class ConverterFunctionExample {
    public static void main(String... args) {
        // the following code should run and produce the output:
        // HEINZ
        // 5.0
        // 4.0
        // INTERESTING
        // S\u00dcSS
//        /*

        Function<Object, Object> doubleTrimmerConverter = o -> o instanceof Double ? (double) Math.round((Double) o) : o;
        Function<Object, Object> stringTrimmerConverter = o -> o instanceof String ? ((String) o).trim() : o;
        Function<Object, Object> stringUpperCaseConverter = o -> o instanceof String ? ((String) o).toUpperCase() : o;

        Function<Object, Object> chain = doubleTrimmerConverter
            .andThen(stringUpperCaseConverter).andThen(stringTrimmerConverter);
        System.out.println(chain.apply("    heinz   "));
        System.out.println(chain.apply(4.5d));
        System.out.println(chain.apply(4.49999999999d));
        System.out.println(chain.apply("interesting"));
        System.out.println(chain.apply("s\u00fc\u00df"));
//        */
    }
}
