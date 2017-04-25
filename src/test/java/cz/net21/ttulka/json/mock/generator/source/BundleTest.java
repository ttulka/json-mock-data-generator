package cz.net21.ttulka.json.mock.generator.source;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cz.net21.ttulka.json.mock.generator.model.NodeTypes;

/**
 * Source Bundle test.
 * 
 * @author ttulka
 *
 */
public class BundleTest {

	private Bundle source;
	
	@Before
	public void setUp() {
		source = new Bundle(NodeTypes.TITLE);
	}
	
	@Test
	public void getNextTest() {
		String text = source.getNext();
		
		assertNotNull(text);
	}
}
