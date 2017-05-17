package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import cz.net21.ttulka.json.mock.generator.model.Configuration;
import cz.net21.ttulka.json.mock.generator.model.Node;
import cz.net21.ttulka.json.mock.generator.model.NodeTypes;
import cz.net21.ttulka.json.mock.generator.source.Source;
import cz.net21.ttulka.json.mock.generator.source.SourceFactory;
import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

/**
 * Guesser.
 * 
 * @author ttulka
 */
public class Guesser {

	private final Path jsonToGuessFrom;

	public Guesser(Path jsonToGuessFrom) throws IOException {
		this.jsonToGuessFrom = jsonToGuessFrom;
	}
	
	public void run() throws IOException {
		Path output = jsonToGuessFrom.resolve(".conf-guess");

		JSONObject json = JsonUtils.readJson(jsonToGuessFrom);
		Map<String, ?> map = json.toMap();

		try (Writer writer = Files.newBufferedWriter(output, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write("{ items: [");
			generateConfiguration(map, writer);
			writer.write("]}");
		}
	}

	private void generateConfiguration(Map<String, ?> json, Writer writer) throws IOException {
		boolean first = true;
		for (Map.Entry<String, ?> entry : json.entrySet()) {
			if (!first) {
				writer.write(",");
			}
			writer.write("{");
			writer.write("name: \"" + entry.getKey() + "\"");

			if (entry.getValue() instanceof Map) {
				generateConfiguration((Map<String, ?>)entry.getValue(), writer);
			}
			else if (entry.getValue() instanceof List) {
				generateConfigurationList((List<?>)entry.getValue(), writer);
			}
			else {
				generateConfigurationItem(entry.getValue(), writer);
			}
			first = false;
			writer.write("}");
		}
	}

	private void generateConfigurationList(List<?> list, Writer writer) throws IOException {
		writer.write("[");
		boolean first = true;
		for (Object item : list) {
			if (!first) {
				writer.write(",");
			}
			if (item instanceof Map) {
				generateConfiguration((Map<String, ?>)item, writer);
			}
			else {
				generateConfigurationItem(item, writer);
			}
			first = false;
		}
		writer.write("]");
	}

	private void generateConfigurationItem(Object item, Writer writer) throws IOException {

	}
}
