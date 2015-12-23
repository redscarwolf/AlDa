package aufg3;

import java.util.LinkedList;
import java.util.List;

public class PriorityList<V> {
	private List<Entry<V, Double>> list;

	public PriorityList() {
		list = new LinkedList<Entry<V, Double>>();
	}

	private static class Entry<V, Int> {
		private V v;
		private Double distance;

		public Entry(V v, Double d) {
			this.v = v;
			this.distance = d;
		}

		public void setDistance(Double distance) {
			this.distance = distance;
		}
	}

	public V deleteMin() {
		// find Entry with least distance
		Double min = 999.9;
		Entry<V, Double> entry = new Entry<>(null, null);
		for (Entry<V, Double> li : list) {
			if (li.distance < min) {
				min = li.distance;
				entry = li; // save found Entry with least distance
			}
		}

		// remove entry
		if (!list.remove(entry))
			System.out.println("ERROR: entry is not in the List.");

		return entry.v;
	}

	public void insert(V v, Double d) {
		list.add(new Entry<>(v, d));
	}
	
	public boolean change(V v, Double dNew) {
		for (Entry<V, Double> li : list) {
			if (v.equals(li.v)) {  // found vertex v in list
				li.setDistance(dNew);
				return true;
			}
		}
		return false;
	}

	public boolean isEmty() {
		return list.isEmpty();
	}

	public void print() {
		StringBuilder str = new StringBuilder("PriorityList: ");

		for (Entry<V, Double> li : list) {
			str.append("[" + li.v + ", " + li.distance + "] ");
		}
		System.out.println(str.toString());
	}

}
