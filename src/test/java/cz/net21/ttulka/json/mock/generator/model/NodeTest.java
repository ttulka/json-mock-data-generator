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
	public void builderValueTest() {
		NodeBuilder builder = Node.builder(NodeTypes.VALUE, "xxx");		
		assertNotNull(builder);
		
		Node node = builder
				.value("test")
				.build();
		
		assertNotNull(node);
		
		assertEquals(NodeTypes.VALUE, node.getType());
		assertEquals("xxx", node.getName());
		assertEquals("test", node.getValue());
	}

	@Test
	public void builderValuesTest() {
		NodeBuilder builder = Node.builder(NodeTypes.RANDOM, "xxx");
		assertNotNull(builder);

		Node node = builder
				.values("123,abc")
				.repeat("11,22")
				.build();

		assertNotNull(node);
		assertEquals(NodeTypes.RANDOM, node.getType());

		assertNotNull(node.getValues());
		assertEquals(2, node.getValues().size());
		assertEquals("123", node.getValues().get(0));
		assertEquals("abc", node.getValues().get(1));

		assertEquals(11, node.getRepeatMin());
		assertEquals(22, node.getRepeatMax());
	}

	@Test
	public void builderMinMaxTest() {
		NodeBuilder builder = Node.builder(NodeTypes.COMPOSITE, "xxx");
		assertNotNull(builder);

		Node node = builder
				.min("3")
				.max("4")
				.build();

		assertNotNull(node);
		assertEquals(NodeTypes.COMPOSITE, node.getType());

		assertEquals(new Integer(3), node.getMin());
		assertEquals(new Integer(4), node.getMax());
		assertNull(node.getMinDate());
		assertNull(node.getMaxDate());
	}

	@Test
	public void builderPathTest() {
		NodeBuilder builder = Node.builder(NodeTypes.FILE, "xxx");
		assertNotNull(builder);

		Node node = builder
				.path(Paths.get("."))
				.build();

		assertNotNull(node);
		assertEquals(NodeTypes.FILE, node.getType());

		assertEquals(".", node.getPath().toString());
	}

	@Test
	public void builderDateTest() {
		NodeBuilder builder = Node.builder(NodeTypes.DATE, "xxx");
		assertNotNull(builder);

		Node node = builder
				.repeat("123")
				.min("2014-04-24")
				.max("25.05.2015")
				.build();

		assertNotNull(node);
		assertEquals(NodeTypes.DATE, node.getType());

		assertEquals(123, node.getRepeatMin());
		assertEquals(123, node.getRepeatMax());

		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		assertEquals("2014-04-24", sdformat.format(node.getMinDate()));
		assertEquals("2015-05-25", sdformat.format(node.getMaxDate()));
		assertNull(node.getMin());
		assertNull(node.getMax());
	}
}
