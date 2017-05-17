package cz.net21.ttulka.json.mock.generator.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class JsonUtils {
	
	private JsonUtils() { }
	
	public static JSONObject readJson(Path path) throws IOException {
		StringBuilder sb = new StringBuilder();
		Files.lines(path).forEach(sb::append);
		return new JSONObject(sb.toString());
	}
}
