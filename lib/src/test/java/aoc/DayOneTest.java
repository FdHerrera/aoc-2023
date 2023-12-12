package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayOneTest {
    @Test
    void testDayOne() {
        int actualResult = DayOne.trebuchet(readInputFile("dayone.txt"));

        assertThat(actualResult).isEqualTo(55218);
    }
}
