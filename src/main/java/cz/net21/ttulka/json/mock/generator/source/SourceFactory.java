package cz.net21.ttulka.json.mock.generator.source;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import cz.net21.ttulka.json.mock.generator.model.Node;
import cz.net21.ttulka.json.mock.generator.model.NodeTypes;

/**
 * Factory for the sources.
 * 
 * @author ttulka
 *
 */
public class SourceFactory {	
	
	private final Map<NodeTypes, Source<String>> cache;
	
	protected final Random random;	
	protected final SimpleDateFormat dateFormat;
	
	public SourceFactory() {
		super();
		cache = new HashMap<>();
		random = new Random(new Date().getTime());
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public Source<?> getSource(Node node) {
		switch (node.getType()) {
			case COMPOSITE:
			case LIST:
				return null;
			case VALUE:		
				return () -> node.getValue();
			case INTEGER:	
				return getNumberSource(node.getMin(), node.getMax(), 
						(min, max) -> getRandomInt(min, max));
			case FLOAT:		
				return getNumberSource(node.getMin(), node.getMax(), 
						(min, max) -> getRandomInt(min, max) + random.nextFloat());
			case DATE:		
				return getDateSource(node.getMinDate(), node.getMaxDate());
			case ID: 	
				return getCachedSource(node.getType(), (t) -> new Id());
			case LOREM: 	
				return getCachedSource(node.getType(), (t) -> new Lorem(random, node.getMax()));
			case RANDOM:
			case ARRAY:
				return () -> node.getValues().get(random.nextInt(node.getValues().size()));
			case FILE: 	
				return new File(node.getPath());
			case FULL_NAME:
				return getFullNameSource();
			default:		
				return cache.computeIfAbsent(node.getType(), Bundle::new);				
		}
	}
	
	private int getRandomInt(int min, int max) {
		return Math.abs(random.nextInt()) % (max - min + 1) + min;
	}

	private Source<String> getCachedSource(NodeTypes type, Function<NodeTypes,Source<String>> create) {
		return cache.computeIfAbsent(type, create);
	}

	private Source<Number> getNumberSource(Integer min, Integer max, RandomSupplier<Number> supplier) {
		Integer minimum = min != null ? min : 0;
		Integer maximum = max != null ? max : Integer.MAX_VALUE;
		return () -> supplier.get(minimum, maximum);
	}

	private Source<String> getDateSource(Date min, Date max) {
		long minimum = min != null ? min.getTime() : 0;
		long maximum = max != null ? max.getTime() : new Date().getTime();
		return () -> dateFormat.format(new Date(Math.abs(random.nextLong()) % (maximum + minimum) + minimum));
	}

	private Source<String> getFullNameSource() {
		return () -> getCachedSource(NodeTypes.FIRST_NAME, Bundle::new).getNext()
				+ " " + getCachedSource(NodeTypes.LAST_NAME, Bundle::new).getNext();
	}
	
	@FunctionalInterface
	private interface RandomSupplier<T extends Number> {	
		T get(int min, int max);
	}
}
