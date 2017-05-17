package cz.net21.ttulka.json.mock.generator.source;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.model.Node;
import cz.net21.ttulka.json.mock.generator.model.NodeTypes;

/**
 * Source factory test.
 * 
 * @author ttulka
 *
 */
public class SourceFactoryTest {
	
	private Node node;
	private SourceFactory factory;
	
	@Before
	public void setUp() {
		node = mock(Node.class);
		factory = new SourceFactory();
	}

	@Test
	public void getSourceCompositeTest() {
		when(node.getType()).thenReturn(NodeTypes.COMPOSITE);
		Source<?> source = factory.getSource(node);

		assertNull(source);
	}

	@Test
	public void getSourceValueTest() {
		when(node.getType()).thenReturn(NodeTypes.VALUE);
		when(node.getValue()).thenReturn("123");
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertEquals("123", source.getNext());
	}

	@Test
	public void getSourceIntegerOneTest() {
		when(node.getType()).thenReturn(NodeTypes.INTEGER);
		when(node.getValue()).thenReturn("123");
		when(node.getMin()).thenReturn(123);
		when(node.getMax()).thenReturn(123);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);

		testManyTimes(100, () -> {
			assertEquals(123, source.getNext());
		});
	}

	@Test
	public void getSourceIntegerMinMaxTest() {
		when(node.getType()).thenReturn(NodeTypes.INTEGER);
		when(node.getMin()).thenReturn(123);
		when(node.getMax()).thenReturn(125);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);

		testManyTimes(100, () -> {
			assertTrue((Integer) source.getNext() >= 123);
			assertTrue((Integer) source.getNext() <= 125);
		});
	}

	@Test
	public void getSourceTest() {
		when(node.getType()).thenReturn(NodeTypes.FLOAT);
		when(node.getValue()).thenReturn("123");
		when(node.getMin()).thenReturn(123);
		when(node.getMax()).thenReturn(124);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);

		testManyTimes(1000, () -> {
			assertTrue((Float) source.getNext() >= 123f);
			assertTrue((Float) source.getNext() <= 125f);
		});
	}

	@Test
	public void getSourceDateTest() {
		Date today = new Date();

		when(node.getType()).thenReturn(NodeTypes.DATE);
		when(node.getMinDate()).thenReturn(today);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);

		testManyTimes(100, () -> {
			try {
				Date next = factory.dateFormat.parse(source.getNext().toString());
				assertTrue(next.compareTo(today) != -1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	public void getSourceIdTest() {
		when(node.getType()).thenReturn(NodeTypes.ID);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
	}

	@Test
	public void getSourceLoremIpsumTest() {
		when(node.getType()).thenReturn(NodeTypes.LOREM);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
	}

	@Test
	public void getSourceRandomTest() {
		when(node.getType()).thenReturn(NodeTypes.RANDOM);
		when(node.getValues()).thenReturn(Arrays.asList("a", "b", "c"));
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
		assertThat(source.getNext(), anyOf(is("a"), is("b"), is("c")));
	}

	@Test
	public void getSourceArrayTest() {
		when(node.getType()).thenReturn(NodeTypes.ARRAY);
		when(node.getValues()).thenReturn(Arrays.asList("a", "b", "c"));
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
		assertThat(source.getNext(), anyOf(is("a"), is("b"), is("c")));
	}

	@Test
	public void getSourceFullNameTest() {
		when(node.getType()).thenReturn(NodeTypes.FULL_NAME);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
		assertTrue(source.getNext().toString().contains(" "));
	}

	@Test
	public void getSourceFirstNameTest() {
		when(node.getType()).thenReturn(NodeTypes.FIRST_NAME);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
	}

	@Test
	public void getSourceEmailTest() {
		when(node.getType()).thenReturn(NodeTypes.EMAIL);
		Source<?> source = factory.getSource(node);

		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
		assertTrue(source.getNext().toString().contains("@"));
	}

	@Test
	public void getSourceFileTest() {
		when(node.getType()).thenReturn(NodeTypes.FILE);
		when(node.getPath()).thenReturn(Paths.get("examples/patient.dat"));
		Source<?> source = factory.getSource(node);
		
		assertNotNull(source);
		assertFalse(source.getNext().toString().isEmpty());
		assertEquals("Some details about a patient.", source.getNext());
	}
		
	private void testManyTimes(int repeats, Runnable test) {
		Stream.iterate(0, i -> i + 1)
			.limit(repeats)
			.forEach(i -> test.run());
	}
}
