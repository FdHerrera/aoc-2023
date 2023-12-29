package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayEleven {
    public static long cosmicExpansion(List<String> input) {
        Body[][] world = input.stream()
                .map(DayEleven::mapLine)
                .toArray(Body[][]::new);
        Body[][] expandedWorld = expandWorld(world);
        LinkedList<int[]> galaxyLocations = findGalaxyLocations(expandedWorld);
        List<Long> minPaths = findMinPathsBetweenGalaxies(galaxyLocations);
        return minPaths.stream().reduce(0L, Long::sum);
    }

    private static List<Long> findMinPathsBetweenGalaxies(LinkedList<int[]> galaxyLocations) {
        List<Long> distances = new ArrayList<>();
        while (!galaxyLocations.isEmpty()) {
            int[] galaxyLocation = galaxyLocations.pollFirst();
            for (int[] otherGalaxyLocation : galaxyLocations) {
                long d1 = galaxyLocation[0] - (long) otherGalaxyLocation[0];
                long d2 = galaxyLocation[1] - (long) otherGalaxyLocation[1];
                long minDistance = Math.abs(d1) + Math.abs(d2);
                distances.add(minDistance);
            }
        }
        return distances;
    }

    private static LinkedList<int[]> findGalaxyLocations(Body[][] expandedWorld) {
        LinkedList<int[]> galaxyLocations = new LinkedList<>();
        for (int row = 0; row < expandedWorld.length; row++) {
            for (int col = 0; col < expandedWorld[row].length; col++) {
                if (Body.GALAXY.equals(expandedWorld[row][col])) {
                    galaxyLocations.add(new int[]{row, col});
                }
            }
        }
        return galaxyLocations;
    }

    private static Body[][] expandWorld(Body[][] world) {
        List<List<Body>> expandedByRow = new ArrayList<>();
        for (Body[] value : world) {
            if (Arrays.stream(value).allMatch(Body.VOID::equals)) {
                //duplicates
                expandedByRow.add(asArrayList(value));
                expandedByRow.add(asArrayList(value));
            } else {
                expandedByRow.add(asArrayList(value));
            }
        }
        for (int col = 0; col < expandedByRow.get(0).size(); col++) {
            int finalCol = col;
            if (expandedByRow.stream().map(eachRow -> eachRow.get(finalCol)).allMatch(Body.VOID::equals)) {
                //duplicates col
                expandedByRow.forEach(row -> row.add(finalCol, Body.VOID));
                col++;
            }
        }
        return expandedByRow.stream()
                .map(bodies -> bodies.toArray(Body[]::new))
                .toArray(Body[][]::new);
    }

    private static List<Body> asArrayList(Body[] source) {
        List<Body> list = new ArrayList<>(source.length);
        Collections.addAll(list, source);
        return list;
    }

    private static Body[] mapLine(String line) {
        return Arrays.stream(line.split(""))
                .map(Body::from)
                .toArray(Body[]::new);
    }

    enum Body {
        VOID("."),
        GALAXY("#");

        final String representation;

        Body(String representation) {
            this.representation = representation;
        }

        static Body from(String representation) {
            return Arrays.stream(values())
                    .filter(b -> b.representation.equals(representation))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
