package aufg1;

/**
*
* @author oliverbittel
* @since 23.01.2015
* @param <K> the type of keys maintained by this map.
* @param <V> the type of mapped values.
*/
public interface Dictionary<K,V> {
	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key,
	 * the old value is replaced by the specified value.
	 * Returns the previous value associated with key,
	 * or null if there was no mapping for key.
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with key, or null if there was no mapping for key.
	 */
	V insert(K key, V value);

	/**
	 * Returns the value to which the specified key is mapped,
	 * or null if this map contains no mapping for the key.
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 */
	V search(K key);

	/**
	 * Removes the key-value-pair associated with the key.
	 * Returns the value to which the key was previously associated,
	 * or null if the key is not contained in the dictionary.
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with key, or null if there was no mapping for key. 
	 */
	V remove(K key);

	/**
	 * Returns the number of elements in this dictionary.
	 * @return the number of elements in this dictionary.
	 */
	int size();
}
