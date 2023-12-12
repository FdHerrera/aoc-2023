package aoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.fail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceReaderUtil {
    public static List<String> readInputFile(String inputFile) {
        return readFile("/input/" + inputFile);
    }

    public static List<String> readFile(String file) {
        try (InputStream inputStream = ResourceReaderUtil.class.getResourceAsStream(file)) {
            assert inputStream != null;
            return new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
        } catch (Exception e) {
            fail("Add the file: " + file);
        }
        return emptyList();
    }
}
