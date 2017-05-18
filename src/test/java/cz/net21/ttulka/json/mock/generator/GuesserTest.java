package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ttulka
 */
public class GuesserTest {

    private static final Path JSON_SIMPLE = Paths.get("examples/simple.json.out");
    private static final Path JSON_FULL = Paths.get("examples/full.json.out");

    private static final Path OUTPUT = Paths.get("guess.json.test");

    @AfterClass
    public static void cleanUp() throws IOException {
        //Files.delete(OUTPUT);
    }

    @Test
    public void runSimpleTest() throws Exception {
        new Guesser(JSON_SIMPLE, OUTPUT).run();

        testBasicConfigStructure();
    }

    @Test
    public void runFullTest() throws Exception {
        new Guesser(JSON_FULL, OUTPUT).run();

        testBasicConfigStructure();
    }

    private void testBasicConfigStructure() throws Exception {
        JSONObject json = JsonUtils.readJson(OUTPUT);
        Map<String, ?> map = json.toMap();

        assertNotNull(map);
        assertTrue(map.containsKey("items"));
        assertTrue(map.get("items") instanceof List);

        List<?> items = (List<?>) map.get("items");
        assertEquals(2, items.size());
        assertTrue(items.get(0) instanceof Map);
        assertTrue(items.get(1) instanceof Map);

        Map<String, ?> item = (Map<String, ?>) items.get(0);
        assertTrue(item.containsKey("name"));
        assertTrue(item.containsKey("items"));
    }
}
