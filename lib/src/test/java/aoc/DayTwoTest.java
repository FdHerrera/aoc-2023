package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayTwoTest {
    @Test
    void testDayTwo() {
        int actualResult = DayTwo.cubeConundrum(readInputFile("daytwo.txt"));

        assertThat(actualResult).isEqualTo(2563);
    }

    @Test
    void testDayTwoPartTwo() {
        int actualResult = DayTwo.partTwo(readInputFile("daytwo.txt"));

        assertThat(actualResult).isEqualTo(70768);
    }
}