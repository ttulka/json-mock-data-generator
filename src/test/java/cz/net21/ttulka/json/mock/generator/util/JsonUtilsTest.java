package cz.net21.ttulka.json.mock.generator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JSON Utils test.
 * 
 * @author ttulka
 *
 */
public class JsonUtilsTest {
	
	private static final Path PATH = Paths.get(JsonUtilsTest.class.getName() + ".tmp");
	private static final String CONF_JSON = "{ items: [ { name: \"test\", value: \"my example\" } ] }";
	
	@BeforeClass
	public static void setUpResources() throws IOException {
		Files.write(PATH, CONF_JSON.getBytes());		
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
		assertEquals(1, json.getJSONArray("items").length());
		assertEquals("test", json.getJSONArray("items").getJSONObject(0).getString("name"));
		assertEquals("my example", json.getJSONArray("items").getJSONObject(0).getString("value"));
	}
}
