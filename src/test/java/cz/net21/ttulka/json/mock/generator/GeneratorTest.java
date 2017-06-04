package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test Generator.
 *
 * @author ttulka
 */
public class GeneratorTest {

    private static final Path CONFIG_SIMPLE = Paths.get("examples/simple.json");
    private static final Path CONFIG_FULL = Paths.get("examples/full.json");

    private static final Path OUTPUT = Paths.get("output.json.test");

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.delete(OUTPUT);
    }

    @Test
    public void runSimpleTest() throws Exception {
        new Generator(CONFIG_SIMPLE, OUTPUT).run();

        JSONObject json = JsonUtils.readJson(OUTPUT);

        assertNotNull(json.getJSONArray("patients"));
        assertEquals(3, json.getJSONArray("patients").length());
        assertNotNull(json.getJSONArray("patients").getJSONObject(0).get("personalId"));
        assertNotNull(json.getJSONArray("patients").getJSONObject(0).get("name"));
        assertNotNull(json.getJSONArray("patients").getJSONObject(1).get("personalId"));
        assertNotNull(json.getJSONArray("patients").getJSONObject(1).get("name"));
        assertNotNull(json.getJSONArray("patients").getJSONObject(2).get("personalId"));
        assertNotNull(json.getJSONArray("patients").getJSONObject(2).get("name"));

        assertNotNull(json.getJSONObject("doctor"));
        assertNotNull(json.getJSONObject("doctor").get("name"));
        assertNotNull(json.getJSONObject("doctor").get("surname"));
    }

    @Test
    public void runFullTest() throws Exception {
        new Generator(CONFIG_FULL, OUTPUT).run();

        JSONObject json = JsonUtils.readJson(OUTPUT);

        assertNotNull(json.getJSONArray("patients"));

        JSONObject patient0 = json.getJSONArray("patients").getJSONObject(0);
        assertEquals(3, json.getJSONArray("patients").length());
        assertNotNull(patient0.get("personalId"));
        assertNotNull(patient0.get("title"));
        assertNotNull(patient0.get("name"));
        assertNotNull(patient0.get("surname"));
        assertNotNull(patient0.get("gender"));
        assertNotNull(patient0.get("age"));
        assertNotNull(patient0.get("details"));
        assertNotNull(patient0.get("diagnosis"));
        assertNotNull(patient0.getJSONArray("diagnosis").getJSONObject(0).get("what"));
        assertNotNull(patient0.getJSONArray("diagnosis").getJSONObject(0).get("measuredValue"));
        assertNotNull(patient0.get("comments"));

        assertNotNull(json.getJSONObject("metaInfo"));
        assertNotNull(json.getJSONObject("metaInfo").get("name"));
        assertNotNull(json.getJSONObject("metaInfo").get("doctor"));

        JSONObject doctor = json.getJSONObject("metaInfo").getJSONObject("doctor");
        assertNotNull(doctor.get("name"));
        assertNotNull(doctor.get("email"));
        assertNotNull(doctor.get("contact"));
        assertNotNull(doctor.get("home"));
        assertNotNull(doctor.get("departments"));
        assertNotNull(doctor.getJSONArray("departments"));

        assertNotNull(json.getJSONObject("metaInfo").get("updated"));
    }
}
