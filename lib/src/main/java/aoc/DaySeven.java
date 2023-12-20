package aoc;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DaySeven {
    private static final List<Character> NORMAL_HIERARCHY = List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final List<Character> HIERARCHY_WITH_JOKERS = List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J');

    public static long camelCards(List<String> input) {
        List<Game> games = input.parallelStream()
                .map(DaySeven::mapGame)
                .sorted()
                .toList();
        long result = 0L;
        for (int i = 0; i < games.size(); i++) {
            result += (long) (i + 1) * games.get(i).bid();
        }
        return result;
    }

    public static long camelCardsPartTwo(List<String> input) {
        List<Game> games = input.parallelStream()
                .map(DaySeven::mapGameWithJokers)
                .sorted()
                .toList();
        long result = 0L;
        for (int i = 0; i < games.size(); i++) {
            result += (long) (i + 1) * games.get(i).bid();
        }
        return result;
    }

    static Game mapGame(String line) {
        String[] split = line.split(" ");
        List<Character> hand = split[0].chars()
                .mapToObj(c -> (char) c)
                .toList();
        Integer bid = Integer.parseInt(split[1]);
        return new Game(new Hand(hand), bid);
    }

    static Game mapGameWithJokers(String line) {
        String[] split = line.split(" ");
        List<Character> hand = split[0].chars()
                .mapToObj(c -> (char) c)
                .toList();
        Integer bid = Integer.parseInt(split[1]);
        return new Game(new HandWithJoker(hand), bid);
    }

    record Game(Hand hand, Integer bid) implements Comparable<Game> {
        @Override
        public int compareTo(Game o) {
            return this.hand.compareTo(o.hand());
        }
    }


    @EqualsAndHashCode
    static class Hand implements Comparable<Hand>, Hierarchical {
        List<Character> cards;

        public Hand(List<Character> a) {
            this.cards = a;
        }

        @Override
        public int compareTo(@NonNull Hand o) {
            if (this.equals(o)) return 0;
            HandType thisType = this.getType();
            HandType oType = o.getType();
            if (thisType.ordinal() < oType.ordinal()) return 1;
            if (thisType.ordinal() > oType.ordinal()) return -1;
            if (thisType.equals(oType)) {
                return compareOrder(o) ? 1 : -1;
            }
            return 0;
        }

        private boolean compareOrder(Hand o) {
            for (int i = 0; i < cards.size(); i++) {
                Character thisCard = cards.get(i);
                Character oCard = o.cards.get(i);
                int idxThisCard = getHierarchy().indexOf(thisCard);
                int idxOCard = getHierarchy().indexOf(oCard);
                if (idxThisCard < idxOCard) {
                    return true;
                } else if (idxThisCard > idxOCard) {
                    return false;
                }
            }
            //should not happen
            return false;
        }

        HandType getType() {
            if (new HashSet<>(cards).size() == 1) return HandType.FIVE_KIND;
            Map<Character, Long> occurrences = cards.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            if (occurrences.containsValue(4L)) return HandType.FOUR_KIND;
            if (occurrences.containsValue(3L) && occurrences.containsValue(2L)) return HandType.FULL_HOUSE;
            if (occurrences.containsValue(3L) && occurrences.size() == 3) return HandType.THREE_KIND;
            if (occurrences.containsValue(2L) && occurrences.size() == 3) return HandType.TWO_PAIR;
            if (occurrences.containsValue(2L) && occurrences.size() == 4) return HandType.ONE_PAIR;
            if (occurrences.size() == 5) return HandType.HIGH_CARD;
            throw new IllegalArgumentException("didn't found the type for: " + cards);
        }

        @Override
        public List<Character> getHierarchy() {
            return NORMAL_HIERARCHY;
        }
    }

    static class HandWithJoker extends Hand implements Comparable<Hand> {
        HandWithJoker(List<Character> a) {
            super(a);
        }

        @Override
        HandType getType() {
            Map<Character, Long> occurrences = cards.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            if (occurrences.containsKey('J')) {
                char keyOfGreatest = '*';
                Long greatestValue = 0L;
                for (Map.Entry<Character, Long> occurrenceEntry : occurrences.entrySet()) {
                    if (occurrenceEntry.getValue() > greatestValue && !occurrenceEntry.getKey().equals('J')) {
                        keyOfGreatest = occurrenceEntry.getKey();
                        greatestValue = occurrenceEntry.getValue();
                    }
                }
                occurrences.merge(keyOfGreatest, occurrences.get('J'), Long::sum);
                occurrences.remove('J');
            }
            if (occurrences.size() == 1) return HandType.FIVE_KIND;
            if (occurrences.containsValue(4L)) return HandType.FOUR_KIND;
            if (occurrences.containsValue(3L) && occurrences.containsValue(2L)) return HandType.FULL_HOUSE;
            if (occurrences.containsValue(3L) && occurrences.size() == 3) return HandType.THREE_KIND;
            if (occurrences.containsValue(2L) && occurrences.size() == 3) return HandType.TWO_PAIR;
            if (occurrences.containsValue(2L) && occurrences.size() == 4) return HandType.ONE_PAIR;
            if (occurrences.size() == 5) return HandType.HIGH_CARD;
            throw new IllegalArgumentException("didn't found the type for: " + cards);
        }

        @Override
        public List<Character> getHierarchy() {
            return HIERARCHY_WITH_JOKERS;
        }
    }

    private interface Hierarchical {
        List<Character> getHierarchy();
    }


    enum HandType {
        FIVE_KIND,
        FOUR_KIND,
        FULL_HOUSE,
        THREE_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }
}

