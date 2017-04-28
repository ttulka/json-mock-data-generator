package cz.net21.ttulka.json.mock.generator;

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
		Path confPath = readConfigPath();
		Path outputPath = readOutputPath(OUTPUT_DEFAULT);

		try {
			new Generator(confPath, outputPath).run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Path readConfigPath() {
		String conf = null;
		while (conf == null || conf.isEmpty()) {
			conf = System.console().readLine("Configuration file: ");

			Path path = Paths.get(conf);
			if (Files.notExists(path) || !Files.isRegularFile(path) || !Files.isReadable(path)) {
				System.err.println("Configuration file '" + conf + "' is not a readable file.");
				conf = null;
			}
		}
		return Paths.get(conf);
	}

	private static Path readOutputPath(String defaultOutputPath) {
		Path path = null;
		while (path == null) {
			String output = System.console().readLine("Output JSON file (%s): ", defaultOutputPath);
			if (output == null || output.isEmpty()) {
				output = defaultOutputPath;
			}

			path = Paths.get(output);
			if (Files.exists(path) && (!Files.isRegularFile(path) || !Files.isWritable(path))) {
				System.err.println("Output file '" + output + "' is not a writable file.");
				path = null;
			}
		}
		return path;
	}
}
