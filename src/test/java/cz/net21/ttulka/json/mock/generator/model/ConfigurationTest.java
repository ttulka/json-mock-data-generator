package cz.net21.ttulka.json.mock.generator.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

/**
 * Configuration test.
 * 
 * @author ttulka
 *
 */
public class ConfigurationTest {
	
	private static final Path PATH = Paths.get(ConfigurationTest.class.getName() + ".tmp");
	private static final String CONF_JSON = "{ items: [ { name: \"test\", value: \"my example\" } ] }";
	
	private Configuration conf;

	@BeforeClass
	public static void setUpResources() throws IOException {
		Files.write(PATH, CONF_JSON.getBytes());		
	}
	
	@AfterClass
	public static void cleanUp() throws IOException {
		Files.delete(PATH);
	}
	
	@Before
	public void setUp() throws IOException {
		conf = new Configuration(PATH);
	}
	
	@Test
	public void readNodesTest() throws IOException {		
		JSONObject json = JsonUtils.readJson(PATH);
		
		List<Node> nodes = conf.readNodes(json);
		
		assertNotNull(nodes);
		assertEquals(1, nodes.size());
		assertEquals("test", nodes.get(0).getName());
		assertEquals("my example", nodes.get(0).getValue());
	}
	
	@Test
	public void mapToNodeTest() throws IOException {		
		Map<String,String> map = new HashMap<>();
		map.put("name", "test");
		map.put("value", "my example");
		
		Node node = conf.mapToNode(map);
		
		assertNotNull(node);
		assertEquals("test", node.getName());
		assertEquals("my example", node.getValue());
	}
	
	@Test
	public void getNodeTypeTest() {
		NodeTypes type;
		Map<String,Object> map = new HashMap<>();
		
		map.put("values", "1,2,3");		
		type = conf.getNodeType(map);
		
		assertNotNull(type);
		assertEquals(NodeTypes.RANDOM, type);
		
		map.put("value", "my example");		
		type = conf.getNodeType(map);
		
		assertNotNull(type);
		assertEquals(NodeTypes.VALUE, type);
				
		map.put("items", new ArrayList<>());
		type = conf.getNodeType(map);
		
		assertNotNull(type);
		assertEquals(NodeTypes.COMPOSITE, type);

		map.put("type", "firstName");		
		type = conf.getNodeType(map);
		
		assertNotNull(type);
		assertEquals(NodeTypes.FIRST_NAME, type);
	}
}
