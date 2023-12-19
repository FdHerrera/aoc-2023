package aoc;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DayFiveTest {
    @Test
    void testDayFive() {
        Long actualResult = new DayFive().ifYouGiveASeedAFertilizer(readInputFile("/dayfive.txt"));

        assertThat(actualResult).isEqualTo(251346198L);
    }

    @Test
    void testDayFivePartTwo() {
        Long actualResult = new DayFive().ifYouGiveASeedAFertilizerPartTwo(readInputFile("/dayfive.txt"));

        assertThat(actualResult).isEqualTo(72263011L);
    }
}
