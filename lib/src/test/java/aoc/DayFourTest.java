package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayFourTest {
    @Test
    void testDayFour() {
        int actualResult = DayFour.scratchCards(readInputFile("dayfour.txt"));

        assertThat(actualResult).isEqualTo(22488);
    }

    @Test
    void testDayFourPartTwo() {
        int actualResult = DayFour.scratchCardsPartTwo(readInputFile("dayfour.txt"));

        assertThat(actualResult).isEqualTo(7013204);
    }
}