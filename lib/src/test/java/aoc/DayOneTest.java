package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayOneTest {
    @Test
    void testDayOne() {
        int actualResult = DayOne.trebuchect(readInputFile("dayone.txt"));

        assertThat(actualResult).isEqualTo(54951);
    }
}
