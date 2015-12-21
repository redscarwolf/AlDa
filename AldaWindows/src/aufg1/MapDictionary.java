package aufg1;

import java.util.Map;

public class MapDictionary<K, V> implements Dictionary<K, V> {

	private Map<K, V> map;

	public MapDictionary(Map<K, V> map) {
		this.map = map;
	}

	@Override
	public V insert(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V search(K key) {
		return map.get(key);
	}

	@Override
	public V remove(K key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.toString();
	}

	public String getInternalClassName() {
        return this.map.getClass().getName();
    }
}
