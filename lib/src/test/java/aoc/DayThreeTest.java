package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayThreeTest {
    @Test
    void testDayThree() {
        int actualResult = DayThree.gearRatios(readInputFile("/daythree.txt"));

        assertThat(actualResult).isEqualTo(527446);
    }

    @Test
    void testDayThreePartTwo() {
        int actualResult = DayThree.gearRatiosPartTwo(readInputFile("/daythree.txt"));

        assertThat(actualResult).isEqualTo(73201705);
    }
}