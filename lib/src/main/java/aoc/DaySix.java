package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DaySix {
    public static int waitForIt(List<String> input) {
        int[] times = Arrays.stream(getNumbersAsStrings(input.get(0))).mapToInt(Integer::parseInt).toArray();
        int[] distanceRecords = Arrays.stream(getNumbersAsStrings(input.get(1))).mapToInt(Integer::parseInt).toArray();
        List<Integer> chancesToWin = calcChancesToWin(times, distanceRecords);
        return chancesToWin.stream().reduce(1, (a, b) -> a * b);
    }

    private static String[] getNumbersAsStrings(String line) {
        return Arrays.stream(line.split(":")[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);
    }

    private static List<Integer> calcChancesToWin(int[] times, int[] distanceRecords) {
        List<Integer> chancesToWin = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            int thisTime = times[i];
            int thisDistanceRecord = distanceRecords[i];
            int possibleWins = 0;
            for (int holdingTime = 0; holdingTime < thisTime; holdingTime++) {
                int remainingTime = thisTime - holdingTime;
                int distanceCovered = holdingTime * remainingTime;
                if (distanceCovered > thisDistanceRecord) {
                    possibleWins++;
                }
            }
            chancesToWin.add(possibleWins);
        }
        return chancesToWin;
    }

    public static int waitForItPartTwo(List<String> input) {
        String[] time = getNumbersAsStrings(input.get(0));
        String[] distance = getNumbersAsStrings(input.get(1));
        return calcChancesToWinPartTwo(time, distance);
    }

    private static int calcChancesToWinPartTwo(String[] time, String[] distance) {
        long raceTime = Long.parseLong(StringUtils.join(time, ""));
        long distanceRecord = Long.parseLong(StringUtils.join(distance, ""));
        int possibleWins = 0;
        for (int holdingTime = 0; holdingTime < raceTime; holdingTime++) {
            long remainingTime = raceTime - holdingTime;
            long distanceCovered = holdingTime * remainingTime;
            if (distanceCovered > distanceRecord) {
                possibleWins++;
            }
        }
        return possibleWins;
    }
}
