package aufg1;

public class TreeDictionary<K extends Comparable<? super K>, V> implements
		Dictionary<K, V> {

	private Node<K, V> root;
	private V oldValue;
	private int size;

	public TreeDictionary() {
		this.size = 0;
		this.root = null;
	}

	private static class Node<K, V> {
		private K key;
		private V value;
		private int height;
		private Node<K, V> left;
		private Node<K, V> right;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.height = 0;
			this.left = null;
			this.right = null;
		}
	}

	private int getHeight(Node<K, V> p) {
		if (p == null) {
			return -1;
		} else {
			return p.height;
		}
	}

	private int getBalance(Node<K, V> p) { // Hoehendifferenz
		if (p == null) {
			return 0;
		} else {
			return getHeight(p.right) - getHeight(p.left);
		}
	}

	// #########################################################################

	@Override
	public V insert(K key, V value) {
		root = insertR(key, value, root);
		return oldValue;
	}

	private Node<K, V> insertR(K key, V value, Node<K, V> p) {
		// bin am ende der Rekursion angelangt
		// Schlüssel noch nicht vorhanden, es wird ein neuer erstellt
		// Rückgabewert von insert() gibt null zurück
		// size erhoeht sich
		if (p == null) {
			p = new Node<>(key, value);
			oldValue = null;
			size++;
		} else if (key.compareTo(p.key) < 0) { // key ist kleiner als aktueller
												// Knoten gehe nach links
			p.left = insertR(key, value, p.left);
		} else if (key.compareTo(p.key) > 0) { // key ist groesser als aktueller
												// Knoten gehe nach rechts
			p.right = insertR(key, value, p.right);
		} else {
			// key ist gleich groß zu Knoten
			// speichere alten wert fuer Rueckgabewert und ueberschreibe value
			// des knoten
			oldValue = p.value;
			p.value = value;
		}
		p = balance(p);

		return p;
	}

	private Node<K, V> balance(Node<K, V> p) {
		if (p == null) {
			return null;
		}
		// aktualisiere Hoehe
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;

		// betrachte Hohendifferenz von p
		if (getBalance(p) == -2) { // Fall A
			if (getBalance(p.left) <= 0) {
				p = rotateRight(p); // Fall A1: q hat eine Hoehendif
									// von 0 oder -1
			} else {
				p = rotateLeftRight(p); // Fall A2: q hat eine Hoehendif von +1
			}
		} else if (getBalance(p) == +2) { // Fall B
			if (getBalance(p.right) >= 0) {
				p = rotateLeft(p); // Fall B1: q hat eine Hoehendif
									// von 0 oder +1
			} else {
				p = rotateRightLeft(p); // Fall B2: q hat eine Hoehendif von -1
			}
		}
		return p;
	}

	private Node<K, V> rotateRight(Node<K, V> p) {
		assert p.left != null;
		Node<K, V> q = p.left;
		p.left = q.right;
		q.right = p;
		// aktualiesiere Hoehen von Knoten p und q
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
		return q;
	}

	private Node<K, V> rotateLeft(Node<K, V> p) {
		assert p.right != null;
		Node<K, V> q = p.right;
		p.right = q.left;
		q.left = p;
		// aktualiesiere Hoehen von Knoten p und q
		p.height = Math.max(getHeight(p.right), getHeight(p.left)) + 1;
		q.height = Math.max(getHeight(q.right), getHeight(q.left)) + 1;
		return q;
	}

	private Node<K, V> rotateLeftRight(Node<K, V> p) {
		assert p.left != null;
		p.left = rotateLeft(p.left);
		return rotateRight(p);
	}

	private Node<K, V> rotateRightLeft(Node<K, V> p) {
		assert p.right != null;
		p.right = rotateRight(p.right);
		return rotateLeft(p);
	}

	// #########################################################################

	@Override
	public V search(K key) {
		return searchR(key, root);
	}

	private V searchR(K key, Node<K, V> p) {
		if (p == null) {
			return null;
		} else if (key.compareTo(p.key) < 0) {
			return searchR(key, p.left);
		} else if (key.compareTo(p.key) > 0) {
			return searchR(key, p.right);
		} else {
			return p.value;
		}
	}

	// #########################################################################
	@Override
	public V remove(K key) {
		root = removeR(key, root);
		return oldValue;
	}

	private Node<K, V> removeR(K key, Node<K, V> p) {
		// 1.Fall key wurde nicht gefunden
		// es wird null zurueckgegeben
		if (p == null) {
			oldValue = null;
		} else if (key.compareTo(p.key) < 0) {
			p.left = removeR(key, p.left);
		} else if (key.compareTo(p.key) > 0) {
			p.right = removeR(key, p.right);
		} else {
			if (p.left == null || p.right == null) {
				// 2. und 3. Fall p hat ein oder kein Kind:
				oldValue = p.value;
				p = (p.left != null) ? p.left : p.right;
			} else {
				// 4. Fall p hat zwei Kinder:
				Entry<K, V> min = new Entry<K, V>();
				p.right = getRemMinR(p.right, min);
				oldValue = p.value;
				p.key = min.key; // lese aus min wieder daten die ich durch getRemMinR erhalten habe
				p.value = min.value;
			}
			size--;
		}
		p = balance(p); //TODO!!!!!
		return p;
	}
	

	private Node<K, V> getRemMinR(Node<K, V> p, Entry<K, V> min) {
		assert p != null;
		if (p.left == null) {
			// bin ganz unten links angekommen
			min.key = p.key; //speicher daten zwischen in min
			min.value = p.value;
			p = p.right; //loeschen durch neu verketten
		} else {
			// gehe immer weiter nach links
			p.left = getRemMinR(p.left, min);
		}
		p = balance(p); //TODO!!!!!
		return p; // gebe neu verkettung zurueck
	}

	private static class Entry<K, V> {
		private K key;
		private V value;

		public Entry() {
		}
	}

	// #########################################################################

	@Override
	public int size() {
		return size;
	}
	
	// #########################################################################
	
	@Override
	public String toString() {
		StringBuilder stringB = new StringBuilder();
		preOrder(root, stringB);
		return stringB.toString();

	}

	private void preOrder(Node<K, V> p, StringBuilder sb) {
		if (p != null) {
			sb.append(p.key + " - " + p.value + "\n");
			preOrder(p.left, sb);
			preOrder(p.right, sb);
		}
	}

}
