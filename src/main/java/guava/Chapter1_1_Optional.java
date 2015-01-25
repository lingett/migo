package guava;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

public class Chapter1_1_Optional {
    public static void main(String... args) {
        List<Optional<Integer>> list = Lists.newArrayList(
                Optional.<Integer>absent(),
                Optional.of(1),
                Optional.<Integer>fromNullable(null),
                Optional.fromNullable(5)
        );

        System.out.println("--- Test constructor begin ---");
        for (Optional<Integer> o : list) {
            try {
                System.out.println(o.isPresent());
                System.out.println(o.orNull());
                System.out.println(o.or(10));
                System.out.println(o.get());
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
        System.out.println("--- Test constructor end ---\n");


        System.out.println("--- Test presentInstances begin ---");
        for (Integer i : Optional.presentInstances(list)) {
            System.out.println(i);
        }
        System.out.println("--- Test presentInstances end ---\n");


        System.out.println("--- Test transform begin ---");
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(java.lang.String input) {
                return input.equals("test") ? 0 : 1;
            }
        };

        Optional<String> o1 = Optional.of("test1");
        Optional<String> o2 = Optional.of("test");

        System.out.println(o1.transform(function).get());
        System.out.println(o2.transform(function).get());
        System.out.println("--- Test transform end ---\n");
    }
}
