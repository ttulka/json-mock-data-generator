package cz.net21.ttulka.json.mock.generator.model;

/**
 * Types of a node.
 * 
 * @author ttulka
 *
 */
public enum NodeTypes {
	
	VALUE,
	COMPOSITE,
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

	public static NodeTypes parse(String str) {
		return valueOf(str.replaceAll("(.)(\\p{Upper})", "$1_$2").toUpperCase());
	}
}
