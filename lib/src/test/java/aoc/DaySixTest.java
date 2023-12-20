package aoc;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DaySixTest {
    @Test
    void testDaySix() {
        int actualResult = DaySix.waitForIt(readInputFile("daysix.txt"));

        assertThat(actualResult).isEqualTo(503424);
    }

    @Test
    void testDaySixPartTwo() {
        int actualResult = DaySix.waitForItPartTwo(readInputFile("daysix.txt"));

        assertThat(actualResult).isEqualTo(32607562);
    }
}
