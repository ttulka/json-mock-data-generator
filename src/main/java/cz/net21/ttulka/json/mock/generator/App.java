package cz.net21.ttulka.json.mock.generator;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Entry point of the application.
 * 
 * @author ttulka
 */
public class App {
	
	static final String OUTPUT_DEFAULT = "output.json";
	
	public static void main(String[] argv) {
		final Console console = System.console();
		
		String conf = null;
		while (conf == null || conf.isEmpty()) {
			conf = console.readLine("Configuration file: ");

			Path path = Paths.get(conf);
			if (Files.notExists(path) || !Files.isRegularFile(path) || !Files.isReadable(path)) {
				System.err.println("Configuration file '" + conf + "' is not a readable file.");
				conf = null;
			}
		}
		Path confPath = Paths.get(conf);
		
		String output = console.readLine("Output JSON file (%s): ", OUTPUT_DEFAULT);
		if (output == null || output.isEmpty()) {
			output = OUTPUT_DEFAULT;
		}

		Path outputPath = Paths.get(output);
		if (Files.exists(outputPath) && !Files.isWritable(outputPath)) {
			System.err.println("Output file '" + output + "' is incorrect.");
			outputPath = Paths.get(OUTPUT_DEFAULT);
		}

		try {
			new Generator(confPath, outputPath).run();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
