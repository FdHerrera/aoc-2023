package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayNine {
    public static long mirageMaintenance(List<String> input) {
        return input.parallelStream()
                .map(DayNine::mapLineSequence)
                .map(DayNine::getSequenceDifferences)
                .map(DayNine::extrapolateLastDifference)
                .reduce(0L, Long::sum);
    }

    private static LinkedList<LinkedList<Long>> getSequenceDifferences(List<Long> lineSequence) {
        LinkedList<LinkedList<Long>> differences = new LinkedList<>();
        LinkedList<Long> difference = new LinkedList<>(lineSequence);
        do {
            LinkedList<Long> temp = new LinkedList<>();
            for (int i = 0; i < difference.size() - 1; i++) {
                long thisI = difference.get(i);
                long nextI = difference.get(i + 1);
                temp.add(nextI - thisI);
            }
            differences.add(temp);
            difference = temp;
        } while (!difference.stream().allMatch(i -> i.equals(0L)));
        differences.addFirst(new LinkedList<>(lineSequence));
        return differences;
    }

    private static long extrapolateLastDifference(LinkedList<LinkedList<Long>> differences) {
        differences.getLast().addLast(0L);
        while (differences.size() > 1) {
            LinkedList<Long> last = differences.pollLast();
            Long lastFromLast = last.pollLast();
            LinkedList<Long> penultimate = differences.peekLast();
            Long lastFromPenultimate = penultimate.peekLast();
            penultimate.addLast(lastFromLast + lastFromPenultimate);

        }
        return differences.pollLast().pollLast();
    }

    public static long mirageMaintenancePartTwo(List<String> input) {
        return input.stream()
                .map(DayNine::mapLineSequence)
                .map(DayNine::getSequenceDifferences)
                .map(DayNine::extrapolateFirstDifference)
                .reduce(0L, Long::sum);
    }

    private static long extrapolateFirstDifference(LinkedList<LinkedList<Long>> differences) {
        differences.getLast().addFirst(0L);
        while (differences.size() > 1) {
            LinkedList<Long> last = differences.pollLast();
            Long firstFromLast = last.pollFirst();
            LinkedList<Long> penultimate = differences.peekLast();
            Long firstFromPenultimate = penultimate.peekFirst();
            penultimate.addFirst( firstFromPenultimate - firstFromLast);

        }
        return differences.pollFirst().pollFirst();
    }

    private static List<Long> mapLineSequence(String line) {
        return Arrays.stream(line.split(" "))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }
}
