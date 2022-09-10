package ru.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        System.out.println(minValue(values)); // return 123

        List<Integer> integers = List.of(1,5,6,7);
        System.out.println(oddOrEven(integers));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .getAsInt();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        int remainder = integers.stream().mapToInt(Integer::intValue).sum()%2;
        return integers.stream().filter(n->n%2!=remainder).collect(Collectors.toList());
    }
}
