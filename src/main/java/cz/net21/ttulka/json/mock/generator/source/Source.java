package cz.net21.ttulka.json.mock.generator.source;

/**
 * Source of values.
 * 
 * @author TT
 *
 */
@FunctionalInterface
public interface Source<T> {
	
	/**
	 * Returns a next values.
	 * @return the next value
	 */
	public T getNext();
}
