package cz.net21.ttulka.json.mock.generator.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Source File test.
 * 
 * @author ttulka
 *
 */
public class FileTest {
	
	private static final Path PATH = Paths.get(FileTest.class.getName() + ".tmp");
	private static final String TEXT = "test";

	private File source;
	
	@BeforeClass
	public static void setUpResources() throws IOException {
		Files.write(PATH, TEXT.getBytes());
	}
	
	@AfterClass
	public static void cleanUp() throws IOException {
		Files.delete(PATH);
	}
	
	@Before
	public void setUp() throws IOException {
		source = new File(PATH);
	}
	
	@Test
	public void readFileTest() {
		String text = source.readFile(PATH);
		
		assertNotNull(text);
		assertEquals(TEXT, text);
	}
	
	@Test
	public void getNextTest() {
		String text = source.getNext();
		
		assertNotNull(text);
		assertEquals(TEXT, text);
	}
}
