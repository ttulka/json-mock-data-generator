package cz.net21.ttulka.json.mock.generator.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Source Lorem test.
 * 
 * @author ttulka
 *
 */
public class LoremTest {

	private Lorem source;
	
	@Before
	public void setUp() {
		source = new Lorem(new Random());
	}
	
	@Test
	public void generateLoremTest() {
		String lorem = source.generateLorem(50);
		
		assertNotNull(lorem);
		assertTrue(lorem.endsWith("."));
		assertTrue(lorem.contains(","));
		assertEquals(50, lorem.split(" ").length);
	}
}
