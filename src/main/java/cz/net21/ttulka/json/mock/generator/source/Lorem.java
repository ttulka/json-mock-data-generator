package cz.net21.ttulka.json.mock.generator.source;

import java.util.*;
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

	private final int maxWords;
	private final Random random;
	
	public Lorem(Random random, int maxWords) {
		super();
		this.maxWords = maxWords <= 0 ? Integer.MAX_VALUE : maxWords;
		this.random = random;
	}
	
	public Lorem(Random random) {
		this(random, 100);
	}
	
	String generateLorem(int maxWords) {
		List<String> list = Arrays.stream(LOREM_IPSUM.split("[,\\.\\s]"))
				.filter(s -> s.length() > 1)
				.map(String::trim)
				.map(String::toLowerCase)
				.collect(Collectors.toList());

		shuffleWords(list);

		list = cutWords(list, maxWords);

		capitalizeFirstWordAndAddPeriod(list);

		addSomeCommas(list);
		addSomePeriods(list);
		
		return String.join(" ", list);
	}

	private void shuffleWords(List<String> list) {
		Collections.shuffle(list);
	}

	private void addSomeCommas(List<String> list) {
		int index = 5;
		while (index < list.size() - 15) {
			index += random.nextInt(10) + 5;
			list.set(index, list.get(index) + ",");
		}
	}

	private void addSomePeriods(List<String> list) {
		int index = 10;
		while (index < list.size() - 25) {
			index += random.nextInt(15) + 10;
			list.set(index, StringUtils.capitalize(list.get(index)));
			list.set(index - 1, list.get(index - 1) + ".");
		}
	}

	private void capitalizeFirstWordAndAddPeriod(List<String> list) {
		list.set(0, StringUtils.capitalize(list.get(0)));
		list.set(list.size() - 1, list.get(list.size() - 1) + ".");
	}

	private List<String> cutWords(List<String> list, int maxWords) {
		if (maxWords < list.size()) {
			return list.subList(0, maxWords);
		}
		return list;
	}
	
	@Override
	public String getNext() {
		return generateLorem(maxWords);
	}
}
