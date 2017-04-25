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
	public void getSourceTest() {
		when(node.getType()).thenReturn(NodeTypes.COMPOSITE);
		Source<?> source1 = factory.getSource(node);
		
		assertNull(source1);
		
		when(node.getType()).thenReturn(NodeTypes.VALUE);
		when(node.getValue()).thenReturn("123");
		Source<?> source2 = factory.getSource(node);
		
		assertNotNull(source2);
		assertEquals("123", source2.getNext());
		
		when(node.getType()).thenReturn(NodeTypes.INTEGER);
		when(node.getMin()).thenReturn(123);
		when(node.getMax()).thenReturn(123);
		Source<?> source3 = factory.getSource(node);
		
		assertNotNull(source3);
		
		testManyTimes(100, () -> {
			assertEquals(123, source3.getNext());
		});		
		
		when(node.getMax()).thenReturn(125);
		Source<?> source4 = factory.getSource(node);
		
		assertNotNull(source4);
		
		testManyTimes(100, () -> {
			assertTrue((Integer)source4.getNext() >= 123);
			assertTrue((Integer)source4.getNext() <= 125);
		});
		
		when(node.getType()).thenReturn(NodeTypes.FLOAT);
		when(node.getMin()).thenReturn(123);
		when(node.getMax()).thenReturn(124);
		Source<?> source5 = factory.getSource(node);
		
		assertNotNull(source5);

		testManyTimes(1000, () -> {
			assertTrue((Float)source5.getNext() >= 123f);
			assertTrue((Float)source5.getNext() <= 125f);
		});
		
		Date today = new Date();		
		
		when(node.getType()).thenReturn(NodeTypes.DATE);
		when(node.getMinDate()).thenReturn(today);
		Source<?> source6 = factory.getSource(node);
		
		assertNotNull(source6);
		
		testManyTimes(100, () -> {
			try {
				Date next = factory.dateFormat.parse(source6.getNext().toString());
				assertTrue(next.compareTo(today) != -1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}			
		});
		
		when(node.getType()).thenReturn(NodeTypes.ID);
		Source<?> source7 = factory.getSource(node);
		
		assertNotNull(source7);
		assertFalse(source7.getNext().toString().isEmpty());
		
		when(node.getType()).thenReturn(NodeTypes.LOREM);
		Source<?> source8 = factory.getSource(node);
		
		assertNotNull(source8);
		assertFalse(source8.getNext().toString().isEmpty());
		
		when(node.getType()).thenReturn(NodeTypes.RANDOM);
		when(node.getValues()).thenReturn(Arrays.asList("a", "b", "c"));
		Source<?> source9 = factory.getSource(node);
		
		assertNotNull(source9);
		assertFalse(source9.getNext().toString().isEmpty());
		assertThat(source9.getNext(), anyOf(is("a"), is("b"), is("c")));
		
		when(node.getType()).thenReturn(NodeTypes.FULL_NAME);
		Source<?> source10 = factory.getSource(node);
		
		assertNotNull(source10);
		assertFalse(source10.getNext().toString().isEmpty());
		assertTrue(source10.getNext().toString().contains(" "));
		
		when(node.getType()).thenReturn(NodeTypes.FIRST_NAME);
		Source<?> source11 = factory.getSource(node);
		
		assertNotNull(source11);
		assertFalse(source11.getNext().toString().isEmpty());
		
		when(node.getType()).thenReturn(NodeTypes.EMAIL);
		Source<?> source12 = factory.getSource(node);
		
		assertNotNull(source12);
		assertFalse(source12.getNext().toString().isEmpty());
		assertTrue(source12.getNext().toString().contains("@"));
		
		when(node.getType()).thenReturn(NodeTypes.FILE);
		when(node.getPath()).thenReturn(Paths.get("examples/patient.dat"));
		Source<?> source13 = factory.getSource(node);
		
		assertNotNull(source13);
		assertFalse(source13.getNext().toString().isEmpty());
		assertEquals("Some details about a patient.", source13.getNext());
	}
		
	private void testManyTimes(int repeats, Runnable test) {
		Stream.iterate(0, i -> i + 1)
			.limit(repeats)
			.forEach(i -> test.run());
	}
}
