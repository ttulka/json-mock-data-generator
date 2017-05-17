package cz.net21.ttulka.json.mock.generator.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.Guesser;

/**
 * Created by ttulka
 */
public class GuesserTest {

    private static final Path JSON_SIMPLE = Paths.get("examples/simple.json.out");
    private static final Path JSON_FULL = Paths.get("examples/full.json.out");

    private static final Path OUTPUT = Paths.get("guess.json.test");

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.delete(OUTPUT);
    }

    @Test
    public void runSimpleTest() throws Exception {
        new Guesser(JSON_SIMPLE, OUTPUT).run();

        JSONObject json = JsonUtils.readJson(OUTPUT);
        Map<String, ?> map = json.toMap();
    }

    @Test
    public void runFullTest() throws Exception {
        new Guesser(JSON_FULL, OUTPUT).run();

        JSONObject json = JsonUtils.readJson(OUTPUT);
        Map<String, ?> map = json.toMap();
    }
}
