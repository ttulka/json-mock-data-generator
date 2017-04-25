package cz.net21.ttulka.json.mock.generator.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * File content as a values source.
 * 
 * @author ttulka
 *
 */
public class File implements Source<String> {
	
	private final String content;
		
	public File(Path path) {
		super();		
		this.content = readFile(path);
	}
	
	String readFile(Path path) {
		try (BufferedReader br = Files.newBufferedReader(path)) {
			StringBuilder sb = new StringBuilder();
			br.lines().forEach(sb::append);
			return sb.toString();
		} 
		catch (IOException e) {
			// ignore a missing file
			System.err.println("File not found: " + e.getMessage());
		}
		return "";
	}
	
	@Override
	public String getNext() {
		return content;
	}
}
