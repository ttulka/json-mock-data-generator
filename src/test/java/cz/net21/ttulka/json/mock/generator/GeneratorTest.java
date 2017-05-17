package cz.net21.ttulka.json.mock.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

/**
 * Test Generator.
 * 
 * @author ttulka
 *
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
		Map<String, ?> map = json.toMap();
		
		assertNotNull(map.get("patients"));
		assertTrue(map.get("patients") instanceof List);
		assertEquals(3, ((List<?>)map.get("patients")).size());
		
		assertNotNull(map.get("doctor"));
		assertTrue(map.get("doctor") instanceof Map);
        assertNotNull(((Map<String, ?>)map.get("doctor")).get("name"));
        assertNotNull(((Map<String, ?>)map.get("doctor")).get("surname"));
	}
	
	@Test
	public void runFullTest() throws Exception {
		new Generator(CONFIG_FULL, OUTPUT).run();
				
		JSONObject json = JsonUtils.readJson(OUTPUT);
		Map<String, ?> map = json.toMap();
		
		assertNotNull(map.get("patients"));
		assertTrue(map.get("patients") instanceof List);
		assertEquals(3, ((List<?>)map.get("patients")).size());
		
		assertNotNull(map.get("metaInfo"));
		assertTrue(map.get("metaInfo") instanceof Map);
        assertNotNull(((Map<String, ?>)map.get("metaInfo")).get("name"));
	}
}
