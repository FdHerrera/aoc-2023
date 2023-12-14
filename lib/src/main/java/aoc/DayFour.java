package aoc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayFour {
    public static int scratchCards(List<String> input) {
        List<Card> cards = input.parallelStream().map(DayFour::mapGame).toList();
        List<Integer> gameResults = cards.stream().map(
                card -> {
                    List<Integer> matches = card.myNumbers.stream()
                            .filter(
                                    myNumber -> card.winningNumbers.stream()
                                            .anyMatch(winningNumber -> winningNumber.equals(myNumber))
                            ).toList();
                    return (int) Math.pow(2, (matches.size() - 1));
                }

        ).toList();
        return gameResults.parallelStream().reduce(0, Integer::sum);
    }

    public static int scratchCardsPartTwo(List<String> input) {
        List<Card> cards = input.parallelStream().map(DayFour::mapGame).toList();
        List<Card> deck = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            deck.add(card);
            int numbersWon = (int) card.myNumbers.parallelStream()
                    .filter(myNumber -> card.winningNumbers
                            .stream()
                            .anyMatch(winningNumber -> Objects.equals(myNumber, winningNumber)))
                    .count();
            long countOfOwnedCards = deck.stream().filter(ownedCard -> ownedCard.equals(card)).count();
            int finalI = i;
            IntStream.range(0, (int) countOfOwnedCards)
                    .forEach(ownedCard -> deck.addAll(cards.subList(finalI + 1, finalI + numbersWon + 1)));
        }
        return deck.size();
    }

    private static Card mapGame(String inputLine) {
        List<String> splitByColon = Stream.of(inputLine.split(":")).map(String::trim).toList();
        int cardNumber = Integer.parseInt(splitByColon.get(0).replace("Card ", "").trim());
        List<String> cards = Arrays.stream(splitByColon.get(1).split("\\|")).toList();
        List<Integer> winningNumbers = Arrays.stream(cards.get(0).split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        List<Integer> myHand = Arrays.stream(cards.get(1).split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        return new Card(cardNumber, winningNumbers, myHand);
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static final class Card {
        int cardNumber;
        List<Integer> winningNumbers;
        List<Integer> myNumbers;
    }
}
