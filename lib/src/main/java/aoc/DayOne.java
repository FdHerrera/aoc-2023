package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayOne {
    public static int trebuchect(List<String> input) {
        return input.parallelStream().map(line -> {
            int first = 0;
            int last = 0;
            char[] charArray = line.toCharArray();
            for (char c : charArray) {
                if (Character.isDigit(c)) {
                    first = Character.getNumericValue(c);
                    break;
                }
            }
            for (int i = charArray.length; i > 0; i--) {
                char c = charArray[i - 1];
                if (Character.isDigit(c)) {
                    last = Character.getNumericValue(c);
                    break;
                }
            }
            return Integer.parseInt(String.valueOf(first) + last);
        }).reduce(0, Integer::sum);
    }
}
