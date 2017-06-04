package cz.net21.ttulka.json.mock.generator.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JSON Utils test.
 *
 * @author ttulka
 */
public class JsonUtilsTest {

    private static final Path PATH = Paths.get(JsonUtilsTest.class.getName() + ".tmp");
    private static final String JSON =
            "{ " +
            "   items: [ " +
            "       {" +
            "           name: \"itemA\"," +
            "           items: [" +
            "               {name: \"itemAA\", value: \"aa\"}," +
            "               {name: \"itemAB\", value: \"ab\"}" +
            "           ] " +
            "       }," +
            "       {" +
            "           name: \"itemB\"," +
            "           items: [" +
            "               {name: \"itemBA\", value: \"ba\"}," +
            "               {name: \"itemBB\", value: \"bb\"}" +
            "           ]" +
            "       }," +
            "       {" +
            "           name: \"item1\"," +
            "           items: [" +
            "               {name: \"item1A\", value: \"1a\"}," +
            "               {name: \"item1B\", value: \"1b\"}" +
            "           ]" +
            "       }," +
            "       {" +
            "           name: \"item2\"," +
            "           items: [" +
            "               {name: \"item2A\", value: \"2a\"}," +
            "               {name: \"item2B\", value: \"2b\"}" +
            "           ]" +
            "       }" +
            "   ]" +
            "}";

    @BeforeClass
    public static void setUpResources() throws IOException {
        Files.write(PATH, JSON.getBytes());
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.delete(PATH);
    }

    @Test
    public void readJsonTest() throws IOException {
        JSONObject json = JsonUtils.readJson(PATH);

        assertNotNull(json);
        assertNotNull(json.getJSONArray("items"));

        JSONArray items = json.getJSONArray("items");

        assertEquals(4, items.length());
        assertEquals("itemA", items.getJSONObject(0).getString("name"));
        assertEquals("itemB", items.getJSONObject(1).getString("name"));
        assertEquals("item1", items.getJSONObject(2).getString("name"));
        assertEquals("item2", items.getJSONObject(3).getString("name"));

        JSONArray itemsA = items.getJSONObject(0).getJSONArray("items");
        assertEquals(2, itemsA.length());
        assertEquals("itemAA", itemsA.getJSONObject(0).getString("name"));
        assertEquals("aa", itemsA.getJSONObject(0).getString("value"));
        assertEquals("itemAB", itemsA.getJSONObject(1).getString("name"));
        assertEquals("ab", itemsA.getJSONObject(1).getString("value"));

        JSONArray itemsB = items.getJSONObject(1).getJSONArray("items");
        assertEquals(2, itemsB.length());
        assertEquals("itemBA", itemsB.getJSONObject(0).getString("name"));
        assertEquals("ba", itemsB.getJSONObject(0).getString("value"));
        assertEquals("itemBB", itemsB.getJSONObject(1).getString("name"));
        assertEquals("bb", itemsB.getJSONObject(1).getString("value"));

        JSONArray items1 = items.getJSONObject(2).getJSONArray("items");
        assertEquals(2, items1.length());
        assertEquals("item1A", items1.getJSONObject(0).getString("name"));
        assertEquals("1a", items1.getJSONObject(0).getString("value"));
        assertEquals("item1B", items1.getJSONObject(1).getString("name"));
        assertEquals("1b", items1.getJSONObject(1).getString("value"));

        JSONArray items2 = items.getJSONObject(2).getJSONArray("items");
        assertEquals(2, items2.length());
        assertEquals("item1A", items2.getJSONObject(0).getString("name"));
        assertEquals("1a", items2.getJSONObject(0).getString("value"));
        assertEquals("item1B", items2.getJSONObject(1).getString("name"));
        assertEquals("1b", items2.getJSONObject(1).getString("value"));
    }
}
