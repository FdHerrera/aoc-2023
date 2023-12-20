package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaySix {
    public static int waitForIt(List<String> input) {
        int[] times = Arrays.stream(input.get(0).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] distanceRecords = Arrays.stream(input.get(1).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
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
        return chancesToWin.stream().reduce(1, (a, b) -> a * b);
    }

    public static int waitForItPartTwo(List<String> input) {
        int[] time = Arrays.stream(input.get(0).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] distance = Arrays.stream(input.get(1).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
        Long raceTime = 0L;
        Long distanceRecord = 0L;
        for (int i = 0; i < time.length; i++) {
            raceTime = Long.parseLong(String.valueOf(raceTime) + String.valueOf(time[i]));
            distanceRecord = Long.parseLong(String.valueOf(distanceRecord) + String.valueOf(distance[i]));
        }
        int possibleWins = 0;
        for (int holdingTime = 0; holdingTime < raceTime; holdingTime++) {
            Long remainingTime = raceTime - holdingTime;
            Long distanceCovered = holdingTime * remainingTime;
            if (distanceCovered > distanceRecord) {
                possibleWins++;
            }
        }
        return possibleWins;
    }
}
