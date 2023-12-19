package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DayFive {
    private static final Logger log = Logger.getAnonymousLogger();
    private static final String ESCAPE = "<ESCAPE>";
    private static final Map<String, String> FROM_TO_ORDER = Map.of(
            "seed-to-soil map:", "soil-to-fertilizer map:",
            "soil-to-fertilizer map:", "fertilizer-to-water map:",
            "fertilizer-to-water map:", "water-to-light map:",
            "water-to-light map:", "light-to-temperature map:",
            "light-to-temperature map:", "temperature-to-humidity map:",
            "temperature-to-humidity map:", "humidity-to-location map:",
            "humidity-to-location map:", ESCAPE);

    public Long ifYouGiveASeedAFertilizer(List<String> input) {
        List<Long> seeds = Stream.of(input.get(0).split(": ")[1].split(" "))
                .parallel()
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
        List<String> sublist = input.subList(2, input.size());
        Finder finder = new Finder(sublist);
        List<Long> locations = seeds.parallelStream()
                .map(seed -> finder.findDestination("seed", seed))
                .toList();
        return Collections.min(locations);
    }

    public Long ifYouGiveASeedAFertilizerPartTwo(List<String> input) {
        long[] seedRanges = Stream.of(input.get(0).split(": ")[1].split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        List<String> sublist = input.subList(2, input.size());
        Finder finder = new Finder(sublist);
        List<StartRange> seeds = new ArrayList<>();
        for (int i = 0; i < seedRanges.length; i += 2) {
            seeds.add(new StartRange(seedRanges[i], seedRanges[i + 1]));
        }
        long lowest = Long.MAX_VALUE;
        for (StartRange seed : seeds) {
            long remaining = seed.range();
            long start = seed.start();
            while (remaining > 0L) {
                StartRange result = finder.findDestination("seed", start, remaining);
                remaining -= result.range();
                start += result.range();
                if (result.start() < lowest) {
                    lowest = result.start();
                }
            }
        }
        return lowest;
    }

    private static final class Finder {
        List<Ranges> ranges;

        private Finder(List<String> input) {
            ranges = new ArrayList<>();
            String from = "";
            String to = "";
            List<SourceToDestinationMap> maps = new ArrayList<>();
            for (String line : input) {
                if (line.isEmpty()) {
                    ranges.add(new Ranges(from, to, createNegativeRanges(maps)));
                    maps = new ArrayList<>();
                    continue;
                }
                if (FROM_TO_ORDER.containsKey(line)) {
                    String[] splitted = line.split(" ")[0].split("-");
                    from = splitted[0];
                    to = splitted[2];
                    continue;
                }
                maps.add(createRange(line));
            }
            ranges.add(new Ranges(from, to, createNegativeRanges(maps)));
            ranges.add(new Ranges("location", ESCAPE, Collections.emptyList()));
        }

        private List<SourceToDestinationMap> createNegativeRanges(List<SourceToDestinationMap> maps) {
            maps.sort(Comparator.comparingLong(SourceToDestinationMap::sourceStart));
            List<SourceToDestinationMap> result = new ArrayList<>();
            Long start = 0L;
            for (int i = 0; i < maps.size(); i++) {
                SourceToDestinationMap map = maps.get(i);
                result.add(map);
                if (map.sourceStart() > start) {
                    SourceToDestinationMap negativeRange = new SourceToDestinationMap(start, start, map.sourceStart() - start);
                    log.info(() -> "adding negative: " + negativeRange);
                    result.add(i, negativeRange);
                }
                start = map.sourceStart() + map.range();
            }
            return result;
        }

        StartRange findDestination(String from, Long source, Long range) {
            Ranges rangeMap = ranges.parallelStream().filter(r -> r.from().equals(from)).findFirst().orElseThrow(() -> new IllegalArgumentException("from not found: " + from));
            if (rangeMap.to().equals(ESCAPE)) {
                return new StartRange(source, range);
            }
            Optional<SourceToDestinationMap> optionalMap = rangeMap.maps().parallelStream()
                    .filter(map -> map.sourceStart() <= source && source < map.sourceStart() + map.range())
                    .findFirst();
            if (optionalMap.isPresent()) {
                SourceToDestinationMap map = optionalMap.get();
                Long diff = source - map.sourceStart();
                Long destination = map.destinationStart() + diff;
                return findDestination(rangeMap.to(), destination, Math.min(range, map.range() - diff));
            }
            return findDestination(rangeMap.to(), source, 1L);
        }

        Long findDestination(String from, Long source) {
            Ranges range = ranges.parallelStream().filter(r -> r.from().equals(from)).findFirst().orElseThrow(() -> new IllegalArgumentException("from not found: " + from));
            if (range.to().equals(ESCAPE)) {
                return source;
            }
            Optional<SourceToDestinationMap> optionalMap = range.maps().parallelStream()
                    .filter(map -> map.sourceStart() <= source && source < map.sourceStart() + map.range())
                    .findFirst();
            if (optionalMap.isPresent()) {
                SourceToDestinationMap map = optionalMap.get();
                Long diff = source - map.sourceStart();
                Long destination = map.destinationStart() + diff;
                return findDestination(range.to(), destination);
            }
            return findDestination(range.to(), source);
        }

        private SourceToDestinationMap createRange(String line) {
            long[] maps = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            return new SourceToDestinationMap(maps[0], maps[1], maps[2]);
        }
    }

    private record SourceToDestinationMap(Long destinationStart, Long sourceStart, Long range) {
    }

    private record Ranges(String from, String to, List<SourceToDestinationMap> maps) {
    }

    private record StartRange(long start, long range) {
    }
}
