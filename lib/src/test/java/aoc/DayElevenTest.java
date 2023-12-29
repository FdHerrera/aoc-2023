package aoc;

import org.junit.jupiter.api.Test;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DayElevenTest {
    @Test
    void testDayEleven() {
        long actualResult = DayEleven.cosmicExpansion(readInputFile("dayeleven.txt"));

        assertThat(actualResult).isEqualTo(9693756L);
    }
}
