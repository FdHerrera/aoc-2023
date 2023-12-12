package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayOne {
    private static final Map<String, Integer> TEXT_TO_NUMBER = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    public static int trebuchet(List<String> input) {
        List<Integer> sums = input.parallelStream().map(line -> {
            int first = findFirst(line);
            int last = findLast(line);
            return Integer.parseInt(String.valueOf(first) + last);
        }).toList();
        return sums.stream().reduce(0, Integer::sum);
    }

    private static int findFirst(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                return Character.getNumericValue(line.charAt(i));
            }
            for (Map.Entry<String, Integer> entry : TEXT_TO_NUMBER.entrySet()) {
                if (i + entry.getKey().length() < line.length()) {
                    String sub = line.substring(i, i + entry.getKey().length());
                    if (TEXT_TO_NUMBER.containsKey(sub)) {
                        return TEXT_TO_NUMBER.get(sub);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Didn't find nums");
    }

    private static int findLast(String line) {
        for (int i = line.length(); i >= 0; i--) {
            if (Character.isDigit(line.charAt(i - 1))) {
                return Character.getNumericValue(line.charAt(i - 1));
            }
            for (Map.Entry<String, Integer> entry : TEXT_TO_NUMBER.entrySet()) {
                if (i - entry.getKey().length() > 0) {
                    String sub = line.substring(i - entry.getKey().length(), i);
                    if (TEXT_TO_NUMBER.containsKey(sub)) {
                        return TEXT_TO_NUMBER.get(sub);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Didn't find nums");
    }
}
