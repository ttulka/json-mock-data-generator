package cz.net21.ttulka.json.mock.generator.source;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.net21.ttulka.json.mock.generator.model.NodeTypes;

/**
 * Bundle of example values.
 * 
 * @author ttulka
 *
 */
public class Bundle implements Source<String> {
	
	private static final String BUNDLES_PATH = "bundles";
	private static final String BUNDLES_SUFFIX = ".dat";

	private final List<String> values;
	
	private int index = 0;
		
	private Bundle() {
		super();
		values = new ArrayList<>();
	}
	
	public Bundle(NodeTypes type) {
		this();		
		InputStream is = Bundle.class.getResourceAsStream("/" + BUNDLES_PATH + "/" + type + BUNDLES_SUFFIX);
		BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
		br.lines().forEach(values::add);
		Collections.shuffle(values);
	}
	
	@Override
	public String getNext() {
		return values.get(index ++ % values.size());
	}

	public List<String> getValues() {
		return new ArrayList<>(values);
	}
}
