package aoc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayTwo {

    public static int cubeConundrum(List<String> input) {
        List<Game> games = input.parallelStream()
                .map(DayTwo::mapGame)
                .toList();
        return games.stream().filter(DayTwo::assertGame)
                .map(Game::getId)
                .reduce(0, Integer::sum);
    }

    private static boolean assertGame(Game game) {
        return game.rounds.stream()
                .allMatch(round -> round.redCubes <= 12 && round.greenCubes <= 13 && round.blueCubes <= 14);
    }

    private static Game mapGame(String gameLine) {
        Game game = new Game();
        String[] gameAndRounds = gameLine.split(":");
        game.id = Integer.parseInt(gameAndRounds[0].split(" ")[1]);
        String roundsDescription = gameAndRounds[1];
        game.rounds = Arrays.stream(roundsDescription.split(";")).map(String::trim)
                .map(DayTwo::mapRound).toList();
        return game;
    }

    private static Round mapRound(String roundDescription) {
        Round round = new Round();
        for (String cubesFoundInRound : Arrays.stream(roundDescription.split(",")).map(String::trim).toList()) {
            if (cubesFoundInRound.contains("blue")) {
                round.blueCubes = Integer.parseInt(cubesFoundInRound.split(" ")[0]);
            }

            if (cubesFoundInRound.contains("green")) {
                round.greenCubes = Integer.parseInt(cubesFoundInRound.split(" ")[0]);
            }

            if (cubesFoundInRound.contains("red")) {
                round.redCubes = Integer.parseInt(cubesFoundInRound.split(" ")[0]);
            }
        }
        return round;
    }

    @ToString
    private static class Game {
        @Getter
        int id;
        List<Round> rounds;
    }

    private static class Round {
        int blueCubes;
        int redCubes;
        int greenCubes;
    }
}
