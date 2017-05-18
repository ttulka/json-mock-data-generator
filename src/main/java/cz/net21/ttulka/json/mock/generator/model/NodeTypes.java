package cz.net21.ttulka.json.mock.generator.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Types of a node.
 * 
 * @author ttulka
 *
 */
public enum NodeTypes {
	
	VALUE,
	COMPOSITE,
	LIST,
	ARRAY,
	
	INTEGER,
	FLOAT,
	DATE,
	RANDOM,
	ID,
	LOREM,
	FULL_NAME,
	FIRST_NAME,
	LAST_NAME,
	TITLE,
	EMAIL,
	PHONE,
	ADDRESS,
	FILE;

	public String getCamelCase() {
		return StringUtils.remove(WordUtils.uncapitalize(WordUtils.capitalizeFully(this.toString(), '_')), "_");
	}

	public static NodeTypes parse(String str) {
		return valueOf(str.replaceAll("(.)(\\p{Upper})", "$1_$2").toUpperCase());
	}
}
