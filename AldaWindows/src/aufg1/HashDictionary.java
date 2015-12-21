package aufg1;

public class HashDictionary<K, V> implements Dictionary<K, V> {

	private Entry<K, V>[] tab;
	private int size;
	private int capacity;

	@SuppressWarnings("unchecked")
	public HashDictionary(int m) {
		// create an Array of EntryList Elements
		tab = new Entry[m];
		capacity = m;
		size = 0;
	}

	private static class Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K k, V v, Entry<K, V> n) {
			key = k;
			value = v;
			next = n;
		}
	}

	private int createHash(K key) {
		int hash = key.hashCode();
		if (hash < 0) {
			hash = -hash;
		}
		return hash % tab.length;
	}

	@Override
	public V search(K key) {
		// create hash-address
		int adr = createHash(key);
		Entry<K, V> p = searchKey(adr, key);

		if (p == null) { // check if Entry with key exists
			return null;
		}
		return p.value;
	}

	private Entry<K, V> searchKey(int adr, K key) {
		for (Entry<K, V> p = tab[adr]; p != null; p = p.next) {
			if (key.equals(p.key)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public V insert(K key, V value) {
		// create hash-address
		int adr = createHash(key);
		Entry<K, V> p = searchKey(adr, key);

		if (p == null) { // search has failed no Entry jet
			// insert at the front of the List
			tab[adr] = new Entry<K, V>(key, value, tab[adr]);
			size++;
			return null;
		} else { // update Entry
			V temp = p.value;
			p.value = value;
			return temp;
		}
	}

	@Override
	public V remove(K key) {
		// create hash-address
		int adr = createHash(key);
		Entry<K, V> p = tab[adr];
		
		// nothing in the List
		if (p == null) {
			return null;
		}
		
		//first entry is the key
		if (key.equals(p.key)) {
			V temp = p.value;
			tab[adr] = p.next;
			size--;
			return temp;
		}
		
		//iterate list for key
		while (p.next != null) {
			if (key.equals(p.next.key)) {
				V temp = p.next.value;
				p.next = p.next.next;
				return temp;
			}
			p = p.next;
		}
		//Not in the list
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("");
		for (int i = 0; i < capacity; i++) {
			for (Entry<K, V> p = tab[i]; p != null; p = p.next) {
				str.append("Deu:" + p.key + " Eng: " + p.value + "\n");
			}
		}
		return str.toString();
	}
}
