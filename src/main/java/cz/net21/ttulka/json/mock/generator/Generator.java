package cz.net21.ttulka.json.mock.generator;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

import cz.net21.ttulka.json.mock.generator.model.Configuration;
import cz.net21.ttulka.json.mock.generator.model.Node;
import cz.net21.ttulka.json.mock.generator.model.NodeTypes;
import cz.net21.ttulka.json.mock.generator.source.Source;
import cz.net21.ttulka.json.mock.generator.source.SourceFactory;

/**
 * Generator.
 * 
 * @author ttulka
 */
public class Generator {
	
	private final Path output;
	private final SourceFactory factory;
	private final Configuration config;
		
	public Generator(Path config, Path output) throws IOException {
		this.output = output;
		this.factory = new SourceFactory();
		this.config = new Configuration(config);
	}
	
	public void run() throws IOException {
		List<Node> nodes = config.getRootNodes();
		
		try (Writer writer = Files.newBufferedWriter(output, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {	
			generateNodesList(nodes, writer, true);
		} 
	}
	
	void generateNode(Node node, Writer writer, boolean first) throws IOException {		
		if (!first) {
			writer.write(",");
		}
		writer.write(node.getName() + ":");
		
		if (node.getType() == NodeTypes.COMPOSITE) {
			generateCompositeNode(node, writer);
		}
		else if (node.getType() == NodeTypes.LIST) {
			generateListNode(node, writer);
		}
		else if (node.getType() == NodeTypes.ARRAY) {
			generateArrayNode(node, writer);
		}
		else {
			Source<?> source = factory.getSource(node);
			writer.write("\"" + source.getNext() + "\"");
		}
	}

	private void generateArrayNode(Node node, Writer writer) throws IOException {
		writer.write("[");
		int repeat = new Random().nextInt(node.getRepeatMax() - node.getRepeatMin() + 1) + node.getRepeatMin();
		Source<?> source = factory.getSource(node);
		boolean first = true;
		for (int i = 0; i < repeat; i ++) {
			if (!first) {
				writer.write(",");
			}
			writer.write("\"" + source.getNext() + "\"");
			first = false;
		}
		writer.write("]");
	}

	private void generateCompositeNode(Node node, Writer writer) throws IOException {
		writer.write("{");
		int repeat = new Random().nextInt(node.getRepeatMax() - node.getRepeatMin() + 1) + node.getRepeatMin();
		for (int i = 0; i < repeat; i ++) {
            boolean first = true;
            for (Node n : node.getChildren()) {
                generateNode(n, writer, first);
                first = false;
            }
		}
		writer.write("}");
	}
	
	private void generateListNode(Node node, Writer writer) throws IOException {
		writer.write("[");
		int repeat = new Random().nextInt(node.getRepeatMax() - node.getRepeatMin() + 1) + node.getRepeatMin();
		boolean first = true;
		for (int i = 0; i < repeat; i ++) {
			generateNodesList(node.getChildren(), writer, first);
			first = false;
		}
		writer.write("]");
	}
	
	private void generateNodesList(List<Node> nodes, Writer writer, boolean first) throws IOException {
		if (!first) {
			writer.write(",");
		}
		writer.write("{");
		first = true;
		for (Node node : nodes) {				
			generateNode(node, writer, first);
			first = false;
		}	
		writer.write("}");
	}
}
