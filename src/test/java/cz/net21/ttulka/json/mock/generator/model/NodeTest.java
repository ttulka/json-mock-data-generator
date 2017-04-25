package cz.net21.ttulka.json.mock.generator.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.model.Node.NodeBuilder;

/**
 * Node test.
 * 
 * @author ttulka
 *
 */
public class NodeTest {
	
	@Test
	public void nodeTest() {
		Node node = new Node(NodeTypes.ID, "id");
		
		assertNotNull(node);
		assertEquals(NodeTypes.ID, node.getType());
		assertEquals("id", node.getName());
	}

	@Test
	public void builderTest() {
		NodeBuilder builder = Node.builder(NodeTypes.VALUE, "xxx");		
		assertNotNull(builder);
		
		Node node = builder
				.value("test")
				.values("123,abc")
				.repeat("11,22")
				.min("3")
				.max("4")
				.path(Paths.get("."))
				.build();
		
		assertNotNull(node);
		
		assertEquals(NodeTypes.VALUE, node.getType());
		assertEquals("xxx", node.getName());
		assertEquals("test", node.getValue());
		
		assertNotNull(node.getValues());
		assertEquals(2, node.getValues().size());
		assertEquals("123", node.getValues().get(0));
		assertEquals("abc", node.getValues().get(1));
		
		assertEquals(11, node.getRepeatMin());
		assertEquals(22, node.getRepeatMax());
		
		assertEquals(new Integer(3), node.getMin());
		assertEquals(new Integer(4), node.getMax());
		assertNull(node.getMinDate());
		assertNull(node.getMaxDate());
		
		assertEquals(".", node.getPath().toString());
		
		builder = Node.builder(NodeTypes.VALUE, "xxx");		
		assertNotNull(builder);
		
		node = builder
				.repeat("123")
				.min("2014-04-24")
				.max("25.05.2015")
				.build();
		
		assertNotNull(node);
		
		assertEquals(123, node.getRepeatMin());
		assertEquals(123, node.getRepeatMax());
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		
		assertEquals("2014-04-24", sdformat.format(node.getMinDate()));
		assertEquals("2015-05-25", sdformat.format(node.getMaxDate()));		
		assertNull(node.getMin());
		assertNull(node.getMax());
	}
}
