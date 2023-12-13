package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayThree {

    public static int gearRatios(List<String> input) {
        int sum = 0;
        for (int lineIndex = 0; lineIndex < input.size(); lineIndex++) {
            String currentLine = input.get(lineIndex);
            for (int lineCursor = 0; lineCursor < currentLine.length(); lineCursor++) {
                if (Character.isDigit(currentLine.charAt(lineCursor))) {
                    String from = currentLine.substring(lineCursor);
                    int numFound = getFirstNumber(from);
                    if (haveToSum(input, lineIndex, lineCursor, Integer.toString(numFound).length())) {
                        sum += numFound;
                    }
                    lineCursor += String.valueOf(numFound).length() - 1;
                }
            }
        }
        return sum;
    }

    private static boolean haveToSum(List<String> input, int lineIndex, int lineCursor, int counter) {
        for (int i = -1; i < 2; i++) {
            for (int j = lineCursor - 1; j < lineCursor + counter + 1; j++) {
                try {
                    if (i + lineIndex < 0) {
                        break;
                    }
                    char charToEval = input.get(i + lineIndex).charAt(j);
                    if (((charToEval != '.') && !Character.isLetterOrDigit(charToEval))) {
                        return true;
                    }
                } catch (Exception e) {
                    //Oops, got out of bounds
                }
            }
        }
        return false;
    }

    private static int getFirstNumber(String from) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < from.length(); i++) {
            char charAt = from.charAt(i);
            if (Character.isDigit(charAt)) {
                builder.append(charAt);
            } else if (!builder.isEmpty()) {
                return Integer.parseInt(builder.toString());
            }
        }
        return Integer.parseInt(builder.toString());
    }

    public static int gearRatiosPartTwo(List<String> input) {
        AtomicInteger sum = new AtomicInteger();
        applyToEach(
                input,
                (grid, line, charAt) -> {
                    if ('*' == grid.get(line).charAt(charAt)) {
                        sum.addAndGet(findDigitsAround(grid, line, charAt));
                    }
                    return 0;
                }
        );
        return sum.get();
    }

    private static int findDigitsAround(List<String> grid, int line, int charAt) {
        List<Integer> numbersFound = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (line > 0 && (Character.isDigit(grid.get(i + line).charAt(j + charAt)))) {
                    numbersFound.add(findSubsequentNumber(grid, i + line, j + charAt));
                }
                if (grid.get(i + line).length() > charAt + j && (Character.isDigit(grid.get(i + line).charAt(j + charAt)))) {
                    numbersFound.add(findSubsequentNumber(grid, i + line, j + charAt));
                }
            }
        }
        List<Integer> setNumbersFound = numbersFound.stream().distinct().toList();
        if (setNumbersFound.size() == 2) {
            return setNumbersFound.stream().reduce(1, Math::multiplyExact);
        }
        return 0;
    }

    private static int findSubsequentNumber(List<String> grid, int i, int j) {
        String line = grid.get(i);
        int cursor = j - 1;
        if (cursor > 0) {
            while (cursor > 0 && Character.isDigit(line.charAt(cursor))) {
                cursor--;
            }
            return getFirstNumber(line.substring(cursor));
        } else if (j < line.length()) {
            return getFirstNumber(line.substring(j));
        } else {
            return 0;
        }
    }

    private static void applyToEach(List<String> grid, Func funcToApply) {
        for (int i = 0; i < grid.size(); i++) {
            String currentLine = grid.get(i);
            for (int j = 0; j < currentLine.length(); j++) {
                funcToApply.applyFunc(grid, i, j);
            }
        }
    }

    @FunctionalInterface
    private interface Func {
        int applyFunc(List<String> grid, int line, int charAt);
    }
}
