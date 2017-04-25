package cz.net21.ttulka.json.mock.generator.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

import cz.net21.ttulka.json.mock.generator.model.Node.NodeBuilder;
import cz.net21.ttulka.json.mock.generator.util.JsonUtils;

/**
 * Configuration of the generator run.
 * 
 * @author ttulka
 *
 */
public class Configuration {
	
	private final Path path;
	private final List<Node> rootNodes;

	public Configuration(Path path) throws IOException {
		super();
		this.path = path;
		JSONObject json = JsonUtils.readJson(path);
		rootNodes = readNodes(json);
	}
	
	@SuppressWarnings("unchecked")
	List<Node> readNodes(JSONObject json) {
		return ((List<Map<String,?>>) json.toMap().entrySet().iterator().next().getValue()).stream()
				.map(this::mapToNode)
				.collect(Collectors.toList());		
	}
	
	@SuppressWarnings("unchecked")
	Node mapToNode(Map<String,?> map) {
		NodeTypes type = getNodeType(map);
		String name = (String) map.get("name");
		
		NodeBuilder builder = Node.builder(type, name);
		
		if (map.containsKey("value")) {
			builder.value((String)map.get("value"));
		}
		if (map.containsKey("values")) {
			builder.values((String)map.get("values"));
		}
		if (map.containsKey("repeat")) {
			builder.repeat((String)map.get("repeat"));
		}
		if (map.containsKey("min")) {
			builder.min((String)map.get("min"));
		}
		if (map.containsKey("max")) {
			builder.max((String)map.get("max"));
		}
		if (map.containsKey("path")) {
			builder.path(path.getParent().resolve((String)map.get("path")));
		}
		if (map.containsKey("items")) {
			builder.children(((List<Map<String,?>>)map.get("items")).stream()
					.map(this::mapToNode)
					.collect(Collectors.toList()));
		}
		
		return builder.build();
	}
	
	NodeTypes getNodeType(Map<String,?> map) {
		if (map.containsKey("type")) {
			return NodeTypes.parse((String) map.get("type"));
		}
		if (map.containsKey("items")) {
			return NodeTypes.COMPOSITE;
		}
		if (map.containsKey("value")) {
			return NodeTypes.VALUE;
		}
		if (map.containsKey("values")) {
			return NodeTypes.RANDOM;
		}
		throw new IllegalStateException("JSON item '" + map.get("name") + "' is wrong declared.");
	}
	
	public List<Node> getRootNodes() {
		return new ArrayList<>(rootNodes);
	}
}
