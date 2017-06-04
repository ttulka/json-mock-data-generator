package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        testBasicConfigStructure(json);

        JSONObject patients = json.getJSONArray("items").getJSONObject(0);
        assertEquals("patients", patients.get("name"));
        assertEquals("3", patients.get("repeat"));
        assertEquals("personalId", patients.getJSONArray("items").getJSONObject(0).get("name"));
        assertEquals("id", patients.getJSONArray("items").getJSONObject(0).get("type"));
        assertEquals("name", patients.getJSONArray("items").getJSONObject(1).get("name"));
        assertEquals("fullName", patients.getJSONArray("items").getJSONObject(1).get("type"));

        JSONObject doctor = json.getJSONArray("items").getJSONObject(1);
        assertEquals("doctor", doctor.get("name"));
        assertEquals("name", doctor.getJSONArray("items").getJSONObject(0).get("name"));
        assertEquals("firstName", doctor.getJSONArray("items").getJSONObject(0).get("type"));
        assertEquals("surname", doctor.getJSONArray("items").getJSONObject(1).get("name"));
        assertEquals("lastName", doctor.getJSONArray("items").getJSONObject(1).get("type"));
    }

    @Test
    public void runFullTest() throws Exception {
        new Guesser(JSON_FULL, OUTPUT).run();

        JSONObject json = JsonUtils.readJson(OUTPUT);

        testBasicConfigStructure(json);

        JSONObject patients = json.getJSONArray("items").getJSONObject(0);
        assertEquals("patients", patients.get("name"));
        assertEquals("3", patients.get("repeat"));

        assertEquals("personalId", patients.getJSONArray("items").getJSONObject(0).get("name"));
        assertEquals("id", patients.getJSONArray("items").getJSONObject(0).get("type"));

        assertEquals("title", patients.getJSONArray("items").getJSONObject(1).get("name"));
        assertEquals("title", patients.getJSONArray("items").getJSONObject(1).get("type"));

        assertEquals("name", patients.getJSONArray("items").getJSONObject(2).get("name"));
        assertEquals("firstName", patients.getJSONArray("items").getJSONObject(2).get("type"));

        assertEquals("surname", patients.getJSONArray("items").getJSONObject(3).get("name"));
        assertEquals("lastName", patients.getJSONArray("items").getJSONObject(3).get("type"));

        assertEquals("gender", patients.getJSONArray("items").getJSONObject(4).get("name"));

        assertEquals("age", patients.getJSONArray("items").getJSONObject(5).get("name"));
        assertEquals("integer", patients.getJSONArray("items").getJSONObject(5).get("type"));

        assertEquals("details", patients.getJSONArray("items").getJSONObject(6).get("name"));

        assertEquals("diagnosis", patients.getJSONArray("items").getJSONObject(7).get("name"));
        assertEquals("what", patients.getJSONArray("items").getJSONObject(7).getJSONArray("items").getJSONObject(0).get("name"));
        assertEquals("measuredValue", patients.getJSONArray("items").getJSONObject(7).getJSONArray("items").getJSONObject(1).get("name"));

        assertEquals("comments", patients.getJSONArray("items").getJSONObject(8).get("name"));

        JSONObject metaInfo = json.getJSONArray("items").getJSONObject(1);
        assertEquals("metaInfo", metaInfo.get("name"));

        assertEquals("name", metaInfo.getJSONArray("items").getJSONObject(0).get("name"));

        JSONObject doctor = metaInfo.getJSONArray("items").getJSONObject(1);
        assertEquals("doctor", doctor.get("name"));
        assertEquals("name", doctor.getJSONArray("items").getJSONObject(0).get("name"));
        assertEquals("fullName", doctor.getJSONArray("items").getJSONObject(0).get("type"));
        assertEquals("email", doctor.getJSONArray("items").getJSONObject(1).get("name"));
        assertEquals("email", doctor.getJSONArray("items").getJSONObject(1).get("type"));
        assertEquals("contact", doctor.getJSONArray("items").getJSONObject(2).get("name"));
        assertEquals("phone", doctor.getJSONArray("items").getJSONObject(2).get("type"));
        assertEquals("home", doctor.getJSONArray("items").getJSONObject(3).get("name"));
        assertEquals("address", doctor.getJSONArray("items").getJSONObject(3).get("type"));
        assertEquals("departments", doctor.getJSONArray("items").getJSONObject(4).get("name"));
        assertEquals("array", doctor.getJSONArray("items").getJSONObject(4).get("type"));

        assertEquals("updated", metaInfo.getJSONArray("items").getJSONObject(2).get("name"));
    }

    private void testBasicConfigStructure(JSONObject json) throws Exception {
        assertNotNull(json);
        assertNotNull(json.get("items"));

        JSONArray items = json.getJSONArray("items");
        assertNotNull(items);

        assertEquals(2, items.length());
        assertNotNull(items.getJSONObject(0));
        assertNotNull(items.getJSONObject(1));

        assertNotNull(items.getJSONObject(0).get("name"));
        assertNotNull(items.getJSONObject(0).get("items"));

        assertNotNull(items.getJSONObject(1).get("name"));
        assertNotNull(items.getJSONObject(1).get("items"));
    }
}
