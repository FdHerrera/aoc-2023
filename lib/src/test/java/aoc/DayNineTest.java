package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayNineTest {
    @Test
    void testDayNine() {
        long actualResult = DayNine.mirageMaintenance(readInputFile("daynine.txt"));

        assertThat(actualResult).isEqualTo(1743490457L);
    }
}