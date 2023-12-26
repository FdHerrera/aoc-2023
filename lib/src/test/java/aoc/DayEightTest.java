package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayEightTest {

    @Test
    void testDayEight() {
        long actualResult = DayEight.hauntedWasteland(readInputFile("dayeight.txt"));

        assertThat(actualResult).isEqualTo(6L);
    }

}