package cz.net21.ttulka.json.mock.generator.source;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Source ID test.
 * 
 * @author ttulka
 *
 */
public class IdTest {

	private Id source;
	
	@Before
	public void setUp() throws Exception {
		source = new Id();
	}
	
	@Test
	public void getNextTest() {
		String id = source.getNext();
		
		assertNotNull(id);
		assertFalse(id.isEmpty());
	}
}
