package aoc;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static aoc.ResourceReaderUtil.readInputFile;
import static org.assertj.core.api.Assertions.assertThat;

class DaySevenTest {
    @Test
    void testDaySeven() {
        long actualResult = DaySeven.camelCards(readInputFile("dayseven.txt"));

        assertThat(actualResult).isEqualTo(251927063L);
    }

    @Test
    void testDaySevenPartTwo() {
        long actualResult = DaySeven.camelCardsPartTwo(readInputFile("dayseven.txt"));

        assertThat(actualResult).isEqualTo(255632664L);
    }

    @Nested
    class HandTest {
        @Nested
        class NormalHandTest {
            @Test
            void testFiveOfAKind() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'A', 'A', 'A'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FIVE_KIND);
            }

            @Test
            void testFourOfAKind() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'A', 'A', 'B'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FOUR_KIND);
            }

            @Test
            void testFullHouse() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'A', 'B', 'B'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FULL_HOUSE);
            }

            @Test
            void testThreeOfAKind() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'A', 'B', 'C'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.THREE_KIND);
            }

            @Test
            void testTwoPair() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'B', 'B', 'C'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.TWO_PAIR);
            }

            @Test
            void testOnePair() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'A', 'B', 'C', 'D'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.ONE_PAIR);
            }

            @Test
            void testHighCard() {
                DaySeven.Hand hand = new DaySeven.Hand(List.of('A', 'B', 'C', 'D', 'E'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.HIGH_CARD);
            }
        }

        @Nested
        class HandWithJokersTest {
            @Test
            void testFiveOfAKind() {
                DaySeven.HandWithJoker hand = new DaySeven.HandWithJoker(List.of('A', 'A', 'A', 'A', 'J'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FIVE_KIND);
            }

            @Test
            void testFourOfAKind() {
                DaySeven.HandWithJoker hand = new DaySeven.HandWithJoker(List.of('A', 'A', 'A', 'J', 'B'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FOUR_KIND);
            }

            @Test
            void testFullHouse() {
                DaySeven.HandWithJoker hand = new DaySeven.HandWithJoker(List.of('A', 'A', 'J', 'B', 'B'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.FULL_HOUSE);
            }

            @Test
            void testThreeOfAKind() {
                DaySeven.HandWithJoker hand = new DaySeven.HandWithJoker(List.of('A', 'A', 'J', 'B', 'C'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.THREE_KIND);
            }

            @Test
            void testOnePair() {
                DaySeven.HandWithJoker hand = new DaySeven.HandWithJoker(List.of('A', 'J', 'B', 'C', 'D'));
                assertThat(hand.getType()).isEqualTo(DaySeven.HandType.ONE_PAIR);
            }
        }
    }
}