package cz.net21.ttulka.json.mock.generator.source;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Source Lorem Ipsum.
 * 
 * @author ttulka
 *
 */
public class Lorem implements Source<String> {
	
	private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

	private final int max;
	private final Random random;
	
	public Lorem(Random random, int max) {
		super();
		this.max = max;
		this.random = random;
	}
	
	public Lorem(Random random) {
		this(random, 100);
	}
	
	String generateLorem(int max) {
		List<String> list = Arrays.stream(LOREM_IPSUM.split("[,\\.\\s]"))
				.filter(s -> s.length() > 1)
				.map(String::trim)
				.map(String::toLowerCase)
				.collect(Collectors.toList());
		
		Collections.shuffle(list);
		
		if (max < list.size()) {
			list = list.subList(0, max);
		}
		
		list.set(0, StringUtils.capitalize(list.get(0)));
		list.set(list.size() - 1, list.get(list.size() - 1) + ".");
		
		// add commas
		int index = 5;
		while (index < list.size() - 15) {
			index += random.nextInt(10) + 5;
			list.set(index, list.get(index) + ",");
		}
		// add periods
		index = 10;
		while (index < list.size() - 25) {
			index += random.nextInt(15) + 10;
			list.set(index, StringUtils.capitalize(list.get(index)));
			list.set(index - 1, list.get(index - 1) + ".");
		}
		
		return String.join(" ", list);
	}
	
	@Override
	public String getNext() {
		return generateLorem(max);
	}
}
