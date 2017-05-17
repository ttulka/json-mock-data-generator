package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

/**
 * Guesser.
 *
 * @author ttulka
 */
public class Guesser {

    private final Path jsonToGuessFrom;
    private final Path output;

    public Guesser(Path jsonToGuessFrom, Path output) throws IOException {
        this.jsonToGuessFrom = jsonToGuessFrom;
        this.output = output;
    }

    public void run() throws IOException {
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
            writer.write("name: \"" + entry.getKey() + "\",");

            if (entry.getValue() instanceof Map) {
                writer.write("items: [");
                generateConfiguration((Map<String, ?>) entry.getValue(), writer);
                writer.write("]");
            } else if (entry.getValue() instanceof List) {
                if (((List<?>) entry.getValue()).get(0) instanceof Map) {
                    generateConfigurationList((List<?>) entry.getValue(), writer);
                } else {
                    generateConfigurationArray((List<?>) entry.getValue(), writer);
                }
            } else {
                generateConfigurationItem(entry.getValue(), writer);
            }
            first = false;
            writer.write("}");
        }
    }

    private void generateConfigurationList(List<?> list, Writer writer) throws IOException {
        writer.write("repeat: \"" + list.size() + "\",");
        writer.write("items: [");
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                writer.write(",");
            }
            if (item instanceof Map) {
                generateConfiguration((Map<String, ?>) item, writer);
            } else {
                generateConfigurationItem(item, writer);
            }
            first = false;
        }
        writer.write("]");
    }

    private void generateConfigurationArray(List<?> list, Writer writer) throws IOException {
        writer.write("type: \"array\",");
        writer.write("repeat: \"" + list.size() + "\",");
        writer.write("values: \"");
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                writer.write(",");
            }
            writer.write(item.toString());
            first = false;
        }
        writer.write("\"");
    }

    private void generateConfigurationItem(Object value, Writer writer) throws IOException {
        writer.write("value: \"" + value + "\"");
    }
}
