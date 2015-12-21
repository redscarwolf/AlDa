package aufg1;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
	
	private static final int INITIAL_CAPACITY = 32;
	private Entry<K,V>[] arr;
	private int size;
	
	/**
	 * Constructs an empty array with an initial capacity sufficient to hold 32 entries.
	 */
	@SuppressWarnings("unchecked")
	public SortedArrayDictionary() {
		arr = new Entry[INITIAL_CAPACITY];
		size = 0;
	}
	
	/**
	 * Constructs an Element with two parameters.
	 * @author Marcel
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	private static class Entry<K,V> {
		K key;
		V value;
		public Entry(K k, V v) {
			this.key = k;
			this.value = v;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newSize) {
		if (newSize < size)
			return;
		Entry<K,V>[] old = arr;
		arr = new Entry[newSize];
		System.arraycopy(old, 0, arr,0, size);
	}
	
	@Override
	public V insert(K key, V value) {
		int status = searchKey(key);
		
		//existing key will be replaced
		if ( status >= 0) {
			V temp = arr[status].value;
			arr[status].value = value;
			return temp;
		} else { //new key
			//checking size of Array 
			if (arr.length == size) {
				ensureCapacity(2*size);
			}
			int j = size -1;
			//insert into a sorted Array ( small to large key)
			while (j >= 0 && key.compareTo(arr[j].key) < 0) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = new Entry<K,V>(key,value);
			size++;
			return null;
		}
		
		
	}

	@Override
	public V search(K key) {
		int status = searchKey(key);
		if (status < 0) {
			return null;
		} else {
			return arr[status].value;
		}
	}
	
	private int searchKey(K key){
		int li = 0;
		int re = size -1;
		
		while (re >= li) {
			int m = (li + re)/2;
			if (key.compareTo(arr[m].key) < 0) {  //if key is less than the key of the middle
				re = m - 1;
			} else if (key.compareTo(arr[m].key) > 0) { //if key is greater than the key of the middle
				li = m + 1;
			} else {
				return m; //found key
			}
		}
		return -1; //key not found
	}

	@Override
	public V remove(K key) {
		int pos = searchKey(key);
		if (pos == -1) {
			return null;
		}
		
		V oldValue = arr[pos].value;
		for (int i = pos; i < size-1 ;i++) {
			arr[i] = arr[i+1];
		}
		arr[--size] = null;
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("");
		for (int i = 0; i < size; i++) {
			str.append(arr[i].key).append(", ").append(arr[i].value)
				.append("\n");
		}
		return str.toString(); 
	}

}
