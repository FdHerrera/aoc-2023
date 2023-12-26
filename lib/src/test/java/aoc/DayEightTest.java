package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayEightTest {

    @Test
    void testDayEight() {
        long actualResult = DayEight.hauntedWasteland(readInputFile("dayeight.txt"));

        assertThat(actualResult).isEqualTo(15989L);
    }

    @Test
    void testDayEightPartTwo() {
        long actualResult = DayEight.hauntedWastelandPartTwo(readInputFile("dayeight.txt"));

        assertThat(actualResult).isEqualTo(13830919117339L);
    }

}