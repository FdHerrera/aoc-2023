package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayTen {
    private static final int[] NORTH = {-1, 0};
    private static final int[] EAST = {0, 1};
    private static final int[] WEST = {0, -1};
    private static final int[] SOUTH = {1, 0};

    public static long pipeMaze(List<String> input) {
        Glyph[][] map = input.stream().map(DayTen::mapLine).toArray(Glyph[][]::new);
        int[] step = findStart(map);
        int[] nextStep = findAPipePointingToStart(map, step);
        long count = 1L;
        Glyph nextGlyph = map[nextStep[0]][nextStep[1]];
        while (!nextGlyph.equals(Glyph.START)) {
            int[] comingFrom = new int[]{step[0] - nextStep[0], step[1] - nextStep[1]};
            int[] nextMove = nextGlyph.nextMove(comingFrom);
            int nextPositionRow = nextStep[0] + nextMove[0];
            int nextPositionCol = nextStep[1] + nextMove[1];
            nextGlyph = map[nextPositionRow][nextPositionCol];
            step = nextStep;
            nextStep = new int[]{nextPositionRow, nextPositionCol};
            count++;
        }
        return count / 2;
    }

    private static int[] findAPipePointingToStart(Glyph[][] map, int[] startLocation) {
        for (int row = -1; row < 2; row++) {
            for (int col = -1; col < 2; col++) {
                try {
                    Glyph glyph = map[startLocation[0] + row][startLocation[1] + col];
                    for (int[] pointer : glyph.pointsTo) {
                        if (pointer[0] + row + startLocation[0] == startLocation[0] && pointer[1] + col + startLocation[1] == startLocation[1]) {
                            return new int[]{row + startLocation[0], col + startLocation[1]};
                        }
                    }
                } catch (Exception e) {
                    // Never mind, just an edge or a glyph with no pointers
                }
            }
        }
        throw new IllegalArgumentException("Didn't find a pipe pointing to start???");
    }

    private static int[] findStart(Glyph[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col].equals(Glyph.START)) {
                    return new int[]{row, col};
                }
            }
        }
        throw new IllegalArgumentException("No start???");
    }

    private static Glyph[] mapLine(String line) {
        return line.chars()
                .mapToObj(c -> (char) c)
                .map(DayTen::getGlyphFromChar)
                .toArray(Glyph[]::new);
    }

    private static Glyph getGlyphFromChar(Character c) {
        return Arrays.stream(Glyph.values())
                .filter(glyph -> c.equals(glyph.character))
                .findFirst()
                .orElseThrow();
    }

    enum Glyph {
        START('S', null),
        GROUND('.', null),
        VERTICAL('|', new int[][]{SOUTH, NORTH}),
        HORIZONTAL('-', new int[][]{WEST, EAST}),
        NORTH_EAST('L', new int[][]{NORTH, EAST}),
        NORTH_WEST('J', new int[][]{NORTH, WEST}),
        SOUTH_EAST('F', new int[][]{SOUTH, EAST}),
        SOUTH_WEST('7', new int[][]{SOUTH, WEST});

        private final char character;
        private final int[][] pointsTo;

        Glyph(char character, int[][] pointsTo) {
            this.character = character;
            this.pointsTo = pointsTo;
        }

        private int[] nextMove(int[] comingFrom) {
            if (Arrays.equals(comingFrom, pointsTo[0])) {
                return pointsTo[1];
            } else {
                return pointsTo[0];
            }
        }
    }
}