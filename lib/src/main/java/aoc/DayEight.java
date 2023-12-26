package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayEight {
    public static long hauntedWasteland(List<String> input) {
        List<Instructions> instructions = Arrays.stream(input.get(0).split(""))
                .map(Instructions::from)
                .toList();
        Map<String, List<String>> path = mapPath(input);
        long count = 0L;
        String step = "AAA";
        while (true) {
            for (Instructions instruction : instructions) {
                if (step.equals("ZZZ")) {
                    return count;
                }
                List<String> nextSteps = path.get(step);
                if (instruction.equals(Instructions.LEFT)) {
                    step = nextSteps.get(0);
                } else {
                    step = nextSteps.get(1);
                }
                count++;
            }
        }
    }

    private static Map<String, List<String>> mapPath(List<String> input) {
        Map<String, List<String>> pathMap = new LinkedHashMap<>();
        List<String> path = input.subList(2, input.size());
        for (String l : path) {
            List<String> mapping = Arrays.stream(l.split("="))
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .toList();
            List<String> nextSteps = Arrays.stream(
                    mapping.get(1)
                            .replaceAll("[^\\w\\s]", "") // clear special characters
                            .split(" ") // split by space to get the actual steps
            ).map(String::trim)
                    .toList();
            pathMap.put(mapping.get(0), nextSteps);

        }
        return pathMap;
    }

    private enum Instructions {
        RIGHT,
        LEFT;

        static Instructions from(String el) {
            if (el.equals("R")) {
                return RIGHT;
            } else if (el.equals("L")) {
                return LEFT;
            }
            throw new IllegalArgumentException("Unknown instruction");
        }
    }
}
