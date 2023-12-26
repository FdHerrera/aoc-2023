package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayEight {
    public static long hauntedWasteland(List<String> input) {
        List<Instruction> instructions = Arrays.stream(input.get(0).split(""))
                .map(Instruction::from)
                .toList();
        Map<String, List<String>> path = mapPath(input);
        long count = 0L;
        String step = "AAA";
        while (true) {
            for (Instruction instruction : instructions) {
                if (step.equals("ZZZ")) {
                    return count;
                }
                List<String> possibleNextSteps = path.get(step);
                step = getNextStep(instruction, possibleNextSteps);
                count++;
            }
        }
    }

    public static long hauntedWastelandPartTwo(List<String> input) {
        List<Instruction> instructions = Arrays.stream(input.get(0).split(""))
                .map(Instruction::from)
                .toList();
        Map<String, List<String>> path = mapPath(input);
        List<String> steps = path.keySet().stream().filter(s -> s.endsWith("A")).toList();
        List<Long> stepsUntilEnd = steps.parallelStream()
                .map(init -> {
                    long count = 0L;
                    String nextStep = init;
                    do {
                        for (Instruction instruction : instructions) {
                            List<String> possibleNextSteps = path.get(nextStep);
                            nextStep = getNextStep(instruction, possibleNextSteps);
                            count++;
                        }
                    } while (!nextStep.endsWith("Z"));
                    return count;
                })
                .toList();
        return stepsUntilEnd.stream().reduce(1L, ArithmeticUtils::lcm);
    }

    private static String getNextStep(Instruction instruction, List<String> possibleNextSteps) {
        if (instruction.equals(Instruction.LEFT)) {
            return possibleNextSteps.get(0);
        } else {
            return possibleNextSteps.get(1);
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
                    )
                    .map(String::trim)
                    .toList();
            pathMap.put(mapping.get(0), nextSteps);

        }
        return pathMap;
    }

    private enum Instruction {
        RIGHT,
        LEFT;

        static Instruction from(String el) {
            if (el.equals("R")) {
                return RIGHT;
            } else if (el.equals("L")) {
                return LEFT;
            }
            throw new IllegalArgumentException("Unknown instruction");
        }
    }
}
