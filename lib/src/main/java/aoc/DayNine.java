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
                .map(DayNine::calcSequence)
                .reduce(0L, Long::sum);
    }

    private static long calcSequence(List<Long> lineSequence) {
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
        return extrapolateDifferences(differences);
    }

    private static long extrapolateDifferences(LinkedList<LinkedList<Long>> differences) {
        differences.getLast().addLast(0L);
        while (differences.size() > 1) {
            LinkedList<Long> last = differences.pollLast();
            Long lastNum = last.pollLast();
            LinkedList<Long> penultimate = differences.peekLast();
            Long lastFromPenultimate = penultimate.peekLast();
            penultimate.addLast(lastNum + lastFromPenultimate);

        }
        return differences.pollLast().pollLast();
    }

    private static List<Long> mapLineSequence(String line) {
        return Arrays.stream(line.split(" "))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }
}
