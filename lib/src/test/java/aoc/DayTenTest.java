package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayTenTest {
    @Test
    void testDayTen() {
        long actualResult = DayTen.pipeMaze(readInputFile("dayten.txt"));

        assertThat(actualResult).isEqualTo(6956L);
    }

    @Test
    void testDayTenPartTwo() {
        long actualResult = DayTen.pipeMazePartTwo(readInputFile("dayten.txt"));

        assertThat(actualResult).isEqualTo(455L);
    }
}