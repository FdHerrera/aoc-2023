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
        List<Long> minPaths = getMinPathsBetweenGalaxies(input, 2L);
        return minPaths.stream().reduce(0L, Long::sum);
    }

    public static long cosmicExpansionParTwo(List<String> input) {
        List<Long> minPaths = getMinPathsBetweenGalaxies(input, 1_000_000L);
        return minPaths.stream().reduce(0L, Long::sum);
    }

    private static List<Long> getMinPathsBetweenGalaxies(List<String> input, long expandBy) {
        Body[][] world = input.stream()
                .map(DayEleven::mapLine)
                .toArray(Body[][]::new);
        Body[][] expandedWorld = markDeepVoids(world);
        LinkedList<int[]> galaxyLocations = findGalaxyLocations(expandedWorld);
        return findMinPathsBetweenGalaxies(galaxyLocations, expandedWorld, expandBy);
    }


    private static List<Long> findMinPathsBetweenGalaxies(LinkedList<int[]> galaxyLocations,
                                                          Body[][] expandedWorld,
                                                          long expandBy) {
        List<Long> distances = new ArrayList<>();
        while (!galaxyLocations.isEmpty()) {
            int[] galaxyLocation = galaxyLocations.poll();
            long steps = 0L;
            for (int[] anotherGalaxyLocation : galaxyLocations) {
                int oX = anotherGalaxyLocation[0];
                int oY = anotherGalaxyLocation[1];
                steps += walkX(oX, galaxyLocation, expandedWorld, expandBy);
                steps += walkY(oY, galaxyLocation, expandedWorld, expandBy);
            }
            distances.add(steps);
        }
        return distances;
    }

    private static long walkX(int oX, int[] galaxyLocation, Body[][] expandedWorld, long expandBy) {
        int thisX = galaxyLocation[0];
        int thisY = galaxyLocation[1];
        long steps = 0;
        while (thisX != oX) {
            if (thisX < oX) {
                thisX++;
            } else {
                thisX--;
            }
            Body steppedInto = expandedWorld[thisX][thisY];
            if (Body.DEEP_VOID.equals(steppedInto)) {
                steps += expandBy;
            } else {
                steps++;
            }
        }
        return steps;
    }

    private static long walkY(int oY, int[] galaxyLocation, Body[][] expandedWorld, long expandBy) {
        long steps = 0;

        int thisX = galaxyLocation[0];
        int thisY = galaxyLocation[1];
        while (thisY != oY) {
            if (thisY < oY) {
                thisY++;
            } else {
                thisY--;
            }
            Body steppedInto = expandedWorld[thisX][thisY];
            if (Body.DEEP_VOID.equals(steppedInto)) {
                steps += expandBy;
            } else {
                steps++;
            }
        }
        return steps;
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

    private static Body[][] markDeepVoids(Body[][] world) {
        List<List<Body>> expandedByRow = new ArrayList<>();
        for (Body[] row : world) {
            if (Arrays.stream(row).allMatch(Body.VOID::equals)) {
                expandedByRow.add(asArrayList(Arrays.stream(row).map(body -> Body.DEEP_VOID).toArray(Body[]::new)));
            } else {
                expandedByRow.add(asArrayList(row));
            }
        }
        for (int col = 0; col < expandedByRow.get(0).size(); col++) {
            int finalCol = col;
            if (expandedByRow.stream().map(eachRow -> eachRow.get(finalCol)).allMatch(body -> Body.DEEP_VOID.equals(body) || Body.VOID.equals(body))) {
                expandedByRow.forEach(row -> row.set(finalCol, Body.DEEP_VOID));
            }
        }
        return expandedByRow.stream()
                .map(bodies -> bodies.toArray(Body[]::new))
                .toArray(Body[][]::new);
    }

    /**
     * {@link Arrays#asList} lists does not support changing the size.
     *
     * @param source to convert to {@link ArrayList}
     * @return an {@link ArrayList} with all the values of the source, as per {@link Collections#addAll}
     */
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
        DEEP_VOID("-"),
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
